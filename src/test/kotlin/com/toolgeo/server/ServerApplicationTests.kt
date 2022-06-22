package com.toolgeo.server

import com.baomidou.mybatisplus.core.toolkit.AES
import com.toolgeo.server.service.IUserService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest
class ServerApplicationTests {

    @Test
    fun contextLoads() {
        val randomKey: String = AES.generateRandomKey()
        println(randomKey)
        println(AES.encrypt("jdbc:mysql://localhost:3306/server", "e5d70a1e42b3bf5f"))
    }

}
