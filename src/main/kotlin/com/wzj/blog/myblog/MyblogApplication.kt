package com.wzj.blog.myblog

import com.wzj.blog.myblog.config.ApplicationConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Import

@SpringBootApplication
@Import(ApplicationConfig::class)
open class MyblogApplication

fun main(args: Array<String>) {
    runApplication<MyblogApplication>(*args)
}

