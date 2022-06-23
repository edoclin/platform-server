package com.toolgeo.server.config

import cn.hutool.crypto.asymmetric.RSA
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RSAConfig {
    @Value("\${sa-token.public-key}")
    lateinit var publicKey: String

    @Value("\${sa-token.private-key}")
    lateinit var privateKey: String

    @Bean
    fun getRasInstance(): RSA {
        return RSA(privateKey, publicKey)
    }
}