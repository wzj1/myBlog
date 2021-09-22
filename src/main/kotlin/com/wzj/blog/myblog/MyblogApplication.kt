package com.wzj.blog.myblog

import com.wzj.blog.myblog.config.rsaUtil.annotation.EnableSecurity
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
@ServletComponentScan
@EnableSecurity
open class MyblogApplication
fun main(args: Array<String>) {
    runApplication<MyblogApplication>(*args)
}


