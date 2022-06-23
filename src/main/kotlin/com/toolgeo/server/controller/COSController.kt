package com.toolgeo.server.controller

import cn.hutool.core.date.DateUtil
import cn.hutool.json.JSONUtil
import com.qcloud.cos.ClientConfig
import com.qcloud.cos.Headers
import com.qcloud.cos.auth.BasicSessionCredentials
import com.qcloud.cos.auth.COSCredentials
import com.qcloud.cos.auth.COSSigner
import com.qcloud.cos.demo.BucketRefererDemo.bucketName
import com.qcloud.cos.http.HttpMethodName
import com.qcloud.cos.region.Region
import com.tencent.cloud.CosStsClient
import com.tencent.cloud.Response
import com.toolgeo.server.util.result.ResultBean
import com.toolgeo.server.util.result.ResultUtil
import com.toolgeo.server.view.COSConfig
import com.toolgeo.server.view.COSTmpToken
import com.toolgeo.server.view.SignParam
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/server/tencent-cos")
class COSController {

    @Value("\${tencent.cos.region}")
    lateinit var region: String

    @Value("\${tencent.cos.bucket}")
    lateinit var bucket: String

    @Value("\${tencent.cos.secret-id}")
    lateinit var secretId: String

    @Value("\${tencent.cos.secret-key}")
    lateinit var secretKey: String

    @GetMapping("/tmp-token")
    fun getTmpToken(): ResultBean {
        val config = TreeMap<String, Any>()
        try {
            config["secretId"] = secretId
            config["secretKey"] = secretKey
            // 临时密钥有效时长，单位是秒，默认 1800 秒，目前主账号最长 2 小时（即 7200 秒），子账号最长 36 小时（即 129600）秒
            config["durationSeconds"] = 1800
            config["bucket"] = bucket
            config["region"] = region

            // 这里改成允许的路径前缀，可以根据自己网站的用户登录态判断允许上传的具体路径
            // 列举几种典型的前缀授权场景：
            // 1、允许访问所有对象："*"
            // 2、允许访问指定的对象："a/a1.txt", "b/b1.txt"
            // 3、允许访问指定前缀的对象："a*", "a/*", "b/*"
            // 如果填写了“*”，将允许用户访问所有资源；除非业务需要，否则请按照最小权限原则授予用户相应的访问权限范围。
            config["allowPrefixes"] = arrayOf("*")

            // 密钥的权限列表。必须在这里指定本次临时密钥所需要的权限。
            // 简单上传、表单上传和分块上传需要以下的权限，其他权限列表请看 https://cloud.tencent.com/document/product/436/31923
            val allowActions = arrayOf(
                // 简单上传
                "name/cos:PutObject",
//                "name/cos:PutBucket",
                // 表单上传、小程序上传
                "name/cos:PostObject",
                // 分块上传
                "name/cos:InitiateMultipartUpload",
                "name/cos:ListMultipartUploads",
                "name/cos:ListParts",
                "name/cos:UploadPart",
                "name/cos:CompleteMultipartUpload"
            )
            config["allowActions"] = allowActions
            val startTime: Long = DateUtil.currentSeconds()
            val expiredTime: Long = startTime + 60 * 5
            val response: Response = CosStsClient.getCredential(config)
            return ResultUtil.ok(COSTmpToken(response.credentials.tmpSecretId, response.credentials.tmpSecretKey, response.credentials.sessionToken, startTime, expiredTime))
        } catch (e: Exception) {
            e.printStackTrace()
            return ResultUtil.error()
        }
    }

    @PostMapping("sign")
    fun getSign(@RequestBody signParam: SignParam): ResultBean? {
        val cred: COSCredentials = BasicSessionCredentials(signParam.tmpSecretId, signParam.tmpSecretKey, signParam.sessionToken)
        val clientConfig = ClientConfig(Region(region))
        val signer = COSSigner()
        val expirationDate = Date(System.currentTimeMillis() + 30L * 60L * 1000L)
        val params: MutableMap<String, String> = HashMap()
//        params["param1"] = "value1"
        val headers: MutableMap<String, String> = HashMap()
        headers[Headers.HOST] = clientConfig.endpointBuilder.buildGeneralApiEndpoint(bucket)
//        headers["header1"] = "value1"
        // 请求的 HTTP 方法，上传请求用 PUT，下载请求用 GET，删除请求用 DELETE
        val method = HttpMethodName.PUT
        val sign = signer.buildAuthorizationStr(method, signParam.key, headers, params, cred, expirationDate, true)
        return ResultUtil.ok(sign)
    }

    @GetMapping("/config")
    fun getCOSConfig(): ResultBean {
        return ResultUtil.ok(COSConfig(region, bucket))
    }

}