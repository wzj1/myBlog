package com.wzj.blog.myblog

import com.wzj.blog.myblog.config.ApplicationConfig
import com.wzj.blog.myblog.listener.SessionListener
import lombok.extern.slf4j.Slf4j
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import java.util.*

@SpringBootApplication
@ServletComponentScan
open class MyblogApplication

fun main(args: Array<String>) {
    runApplication<MyblogApplication>(*args)
}


