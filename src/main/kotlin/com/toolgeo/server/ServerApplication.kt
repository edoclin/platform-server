package com.toolgeo.server

import org.mybatis.spring.annotation.MapperScan
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@MapperScan(basePackages = ["com.toolgeo.server.mapper"])
class ServerApplication

fun main(args: Array<String>) {
    runApplication<ServerApplication>(*args)
}
