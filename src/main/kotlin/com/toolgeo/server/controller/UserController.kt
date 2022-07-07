package com.toolgeo.server.controller;

import cn.dev33.satoken.annotation.SaCheckLogin
import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.bean.BeanUtil
import cn.hutool.core.util.IdcardUtil
import cn.hutool.core.util.NumberUtil
import cn.hutool.core.util.StrUtil
import cn.hutool.crypto.asymmetric.KeyType
import cn.hutool.crypto.asymmetric.RSA
import cn.hutool.crypto.digest.BCrypt
import cn.hutool.db.nosql.redis.RedisDS
import cn.hutool.http.HttpUtil
import cn.hutool.json.JSONUtil
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.toolgeo.server.entity.User
import com.toolgeo.server.entity.UserBase
import com.toolgeo.server.service.IBaseService
import com.toolgeo.server.service.IUserBaseService
import com.toolgeo.server.service.IUserService
import com.toolgeo.server.util.date.DateUtil
import com.toolgeo.server.util.result.ResultBean
import com.toolgeo.server.util.result.ResultUtil
import com.toolgeo.server.view.*
import org.bouncycastle.jcajce.provider.util.BadBlockException
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import javax.annotation.Resource


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author fengsx
 * @since 2022-06-22
 */
@RestController
@RequestMapping("/server/user")
class UserController {
    @Value("\${init.user.retry-limit}")
    lateinit var retryLimit: String


    @Value("\${init.user.retry-lock-time}")
    lateinit var retryLockTime: String

    @Value("\${tencent.wechat.app-id}")
    lateinit var appId: String

    @Value("\${tencent.wechat.app-secret}")
    lateinit var appSecret: String

    @Resource
    lateinit var iUserService: IUserService

    @Resource
    lateinit var rsa: RSA

    @Resource
    lateinit var iUserBaseService: IUserBaseService

    @Resource
    lateinit var iBaseService: IBaseService

    val jedis = RedisDS.create().jedis

    @PostMapping("/cms/login")
    fun login(@RequestBody cmsLoginForm: CmsLoginForm): ResultBean {

        if (StrUtil.equals(
                jedis.get(cmsLoginForm.username),
                "retryLockTime"
            ) || jedis.incr(cmsLoginForm.username) > NumberUtil.parseLong(retryLimit)
        ) {
            jedis.setex(cmsLoginForm.username, NumberUtil.parseLong(retryLockTime) * 60, "retryLockTime")
            return ResultUtil.error("登录重试超过上限, 请 $retryLockTime 分钟后重试")
        }
        try {
            cmsLoginForm.password = String(rsa.decrypt(cmsLoginForm.password, KeyType.PrivateKey))
        } catch (e: BadBlockException) {
            e.printStackTrace()
            return ResultUtil.error("密码解密失败")
        }
        val one = iUserService.getOne(Wrappers.query<User?>().eq("username", cmsLoginForm.username))
        if (BCrypt.checkpw(cmsLoginForm.password, one?.password)) {
            StpUtil.login(one.userId)
            val cmsUserInfo = CmsUserInfo()
            BeanUtil.copyProperties(one, cmsUserInfo)
            jedis.del(cmsLoginForm.username)
            return ResultUtil.ok(cmsUserInfo)
        }
        return ResultUtil.error("用户名或密码错误")
    }


    @PostMapping("/cms/register")
    fun register(@RequestBody cmsRegisterForm: CmsRegisterForm): ResultBean {
        cmsRegisterForm.password = String(rsa.decrypt(cmsRegisterForm.password, KeyType.PrivateKey))
        val insert = User()
        BeanUtil.copyProperties(cmsRegisterForm, insert)
        insert.password = BCrypt.hashpw(insert.password)

        return if (iUserService.save(insert)) {
            ResultUtil.ok()
        } else {
            ResultUtil.error()
        }
    }

    @GetMapping("/cms/public-key")
    fun getPublicKey(): ResultBean {
        return ResultUtil.ok(rsa.publicKeyBase64)
    }

    @PutMapping("")
    fun update(): ResultBean {
        return ResultUtil.ok()
    }

    @GetMapping("/app/login-by-wx/{code}")
    fun loginByWxCode(@PathVariable code: String): ResultBean {
        if (StrUtil.isNotBlank(code)) {
            val requestUrl =
                "https://api.weixin.qq.com/sns/jscode2session?appid=$appId&secret=$appSecret&js_code=$code&grant_type=authorization_code"
            val response = JSONUtil.toBean(HttpUtil.get(requestUrl), WeChatAuthResponse::class.java)
            if (StrUtil.isNotBlank(response?.session_key)) {
                val one = iUserService.getOne(Wrappers.query<User?>().eq("wechat_uid", response.openid))
                StpUtil.login(response.openid)
                val info = AppUserInfo()
                if (BeanUtil.isNotEmpty(one)) {
                    BeanUtil.copyProperties(one, info)
                    info.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(one.createdTime)
                    info.updatedTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(one.updatedTime)
                    info.sex = if (one.male == true) "男" else "女"
                    iUserBaseService.list(Wrappers.query<UserBase?>().eq("user_id", one.userId)).mapTo(info.bases) {
                        val item = AppBase()
                        val byId = iBaseService.getById(it.baseId)
                        item.name = byId.baseName
                        item.baseId = byId.baseId
                        item
                    }
                }
                info.token = StpUtil.getTokenValue()
                info.sessionKey = response.session_key
                jedis.set(one.wechatUid, JSONUtil.toJsonStr(info))

                return ResultUtil.ok(info);
            }
        }
        return ResultUtil.error("参数错误")
    }

    @PostMapping("/app/register")
    @SaCheckLogin
    fun registerApp(@RequestBody appRegisterForm: AppRegisterForm): ResultBean {
        if (BeanUtil.isNotEmpty(appRegisterForm)) {
            val user = User()
            BeanUtil.copyProperties(appRegisterForm, user)
            user.wechatUid = StpUtil.getLoginIdAsString()
            user.male = IdcardUtil.getGenderByIdCard(user.idNumber) == 1
            if (iUserService.save(user)) {

                val userBase = UserBase()
                userBase.userId = user.userId
                userBase.baseId = appRegisterForm.baseId
                if (iUserBaseService.save(userBase)) {
                    return ResultUtil.ok("注册成功")
                }
            }
        }
        return ResultUtil.error("参数错误")
    }

    @GetMapping("/app/logout")
    @SaCheckLogin
    fun logoutApp(): ResultBean {
        jedis.del(StpUtil.getLoginIdAsString())
        StpUtil.logout()
        return ResultUtil.ok()
    }

    @GetMapping("/app/refresh-userinfo")
    @SaCheckLogin
    fun refreshUserInfoApp(): ResultBean {
        return ResultUtil.ok()
    }

    @GetMapping("/app/need-audit/{type}/{current}")
    fun listNeedAudit(@PathVariable type: String, @PathVariable current: Long): ResultBean {
        val result = mutableListOf<AppNeedAuditUser>()
        var query = Wrappers.query<User?>()
        val page = Page<User>(current, 10)
        when (type) {
            "all" -> {
            }
            "need" -> {
                query = query.eq("audited", false)
            }
            "done" -> {
                query = query.eq("audited", true)
            }
            else -> {}
        }
        iUserService.page(page, query).records.mapTo(result) {
            val appNeedAuditUser = AppNeedAuditUser()
            BeanUtil.copyProperties(it, appNeedAuditUser)
            appNeedAuditUser.openId = it.wechatUid
            appNeedAuditUser.createdTime = DateUtil.formatLocalDateTime_yyyy_MM_dd_HH_mm(it.createdTime)
            appNeedAuditUser
        }
        return ResultUtil.ok(result)
    }


    @GetMapping("/live/user-info/{openId}")
    fun getUserInfoByOpenId(@PathVariable openId: String): ResultBean {
        if (StrUtil.isNotBlank(openId)) {
            val one = iUserService.getOne(Wrappers.query<User?>().eq("wechat_uid", openId))
            if (BeanUtil.isNotEmpty(one)) {
                val userInfo = LiveUserInfo()
                userInfo.avatarUrl = one.avatarUrl
                userInfo.nickName = one.nickName
                return ResultUtil.ok(userInfo)
            }
            return ResultUtil.ok()
        }
        return ResultUtil.error()
    }
}
