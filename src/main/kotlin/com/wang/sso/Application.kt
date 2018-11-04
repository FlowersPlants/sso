package com.wang.sso

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer

/**
 * kotlin方式启动类
 */
@SpringBootApplication
open class Application : SpringBootServletInitializer() {
    override fun configure(builder: SpringApplicationBuilder?): SpringApplicationBuilder {
        return builder!!.sources(Application::class.java)
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}