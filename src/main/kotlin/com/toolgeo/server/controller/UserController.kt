package com.toolgeo.server.controller;

import cn.dev33.satoken.stp.StpUtil
import cn.hutool.core.bean.BeanUtil
import cn.hutool.crypto.asymmetric.KeyType
import cn.hutool.crypto.asymmetric.RSA
import cn.hutool.crypto.digest.BCrypt
import com.baomidou.mybatisplus.core.toolkit.Wrappers
import com.toolgeo.server.entity.User
import com.toolgeo.server.service.IUserService
import com.toolgeo.server.util.result.ResultBean
import com.toolgeo.server.util.result.ResultUtil
import com.toolgeo.server.view.CmsLoginForm
import com.toolgeo.server.view.CmsRegisterForm
import com.toolgeo.server.view.CmsUserInfo
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

    @Value("\${sa-token.public-key}")
    lateinit var publicKey: String

    @Resource
    lateinit var iUserService: IUserService

    @Resource
    lateinit var rsa: RSA

    @PostMapping("/cms/login")
    fun login(@RequestBody cmsLoginForm: CmsLoginForm): ResultBean {
        try {
            cmsLoginForm.password = String(rsa.decrypt(cmsLoginForm.password, KeyType.PrivateKey))
        } catch (e: BadBlockException) {
            e.printStackTrace()
            return ResultUtil.error("密码解密失败")
        }
        val one = iUserService.getOne(Wrappers.query<User?>().eq("username", cmsLoginForm.username))
        if (BCrypt.checkpw(cmsLoginForm.password, one?.password)) {
            StpUtil.login(one.userId)
            val  cmsUserInfo = CmsUserInfo()
            BeanUtil.copyProperties(one, cmsUserInfo)
            return ResultUtil.ok(cmsUserInfo)
        }
        return ResultUtil.error()
    }


    @PostMapping("/cms/register")
    fun register(@RequestBody cmsRegisterForm: CmsRegisterForm): ResultBean {
        cmsRegisterForm.password = String(rsa.decrypt(cmsRegisterForm.password, KeyType.PrivateKey))
        val insert = User()
        BeanUtil.copyProperties(cmsRegisterForm, insert)
        insert.password = BCrypt.hashpw(insert.password)

        return if (iUserService.save(insert)) {
            ResultUtil.ok()
        } else{
            ResultUtil.error()
        }
    }

    @GetMapping("/cms/public-key")
    fun getPublicKey(): ResultBean {
        return ResultUtil.ok(publicKey)
    }

    @PutMapping("")
    fun update(): ResultBean {
        return ResultUtil.ok()
    }
}
