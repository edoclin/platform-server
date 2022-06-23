package com.toolgeo.server

import cn.dev33.satoken.secure.SaSecureUtil
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ServerApplicationTests {

    @Test
    fun contextLoads() {
//        val randomKey: String = AES.generateRandomKey()
//        println(randomKey)
//        println(AES.encrypt("jdbc:mysql://localhost:3306/server", ""))
        var rsaGenerateKeyPair = SaSecureUtil.rsaGenerateKeyPair()
// 定义私钥和公钥
        // 定义私钥和公钥
        val privateKey = rsaGenerateKeyPair.get("private")
        val publicKey = rsaGenerateKeyPair.get("public")


        val text = "Sa-Token 一个轻量级java权限认证框架"

        val ciphertext = SaSecureUtil.rsaEncryptByPublic(publicKey, text)
        println("公钥加密后：$ciphertext")


        val text2 = SaSecureUtil.rsaDecryptByPrivate(privateKey, ciphertext)
        println("私钥解密后：$text2")
    }
}
