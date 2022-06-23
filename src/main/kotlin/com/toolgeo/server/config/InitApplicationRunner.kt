package com.toolgeo.server.config

import cn.hutool.core.collection.CollUtil
import cn.hutool.crypto.digest.BCrypt
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.toolgeo.server.entity.User
import com.toolgeo.server.service.IUserService
import com.toolgeo.server.util.slf4j.Slf4j
import com.toolgeo.server.util.slf4j.Slf4j.Companion.log
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Async
import org.springframework.scheduling.annotation.EnableAsync
import javax.annotation.Resource

@Configuration
@EnableAsync
@Slf4j
class InitApplicationRunner : ApplicationRunner {
    @Value("\${init.user.username}")
    private val username: String? = null

    @Value("\${init.user.password}")
    private val password: String? = null

    @Value("\${init.user.role}")
    private val role: String? = null

    @Resource
    lateinit var iUserService: IUserService

    @Async
    override fun run(args: ApplicationArguments) {
        val page: Page<User> = Page<User>(1, 1)
        if (CollUtil.isEmpty(iUserService.page(page).records)) {
            val user = User()
            user.username = username
            user.password = BCrypt.hashpw(password)
            if (iUserService.save(user)) {
                log.info("初始化用户成功")
            }
        }
    }
}