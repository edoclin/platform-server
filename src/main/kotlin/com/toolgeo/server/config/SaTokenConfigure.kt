package com.toolgeo.server.config

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor
import cn.dev33.satoken.stp.StpInterface
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SaTokenConfigure : WebMvcConfigurer, StpInterface {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SaAnnotationInterceptor()).addPathPatterns("/**")
    }

    override fun getPermissionList(o: Any, s: String): List<String>? {
        return null
    }

    override fun getRoleList(o: Any, s: String): List<String>? {
        return null
    }
}