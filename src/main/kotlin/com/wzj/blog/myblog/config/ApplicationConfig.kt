package com.wzj.blog.myblog.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter


@Configuration
open class ApplicationConfig : WebMvcConfigurerAdapter() {

    @Value("\${static.image}")
    private val filepath: String? = null

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/upload/")
        registry.addResourceHandler("/image/**").addResourceLocations("/var/image/upload")
        super.addResourceHandlers(registry)
    }
}