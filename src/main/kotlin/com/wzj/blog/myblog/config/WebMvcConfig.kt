package com.wzj.blog.myblog.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
open class WebAPPConfig : WebMvcConfigurationSupport() {
    //配置拦截器
    override fun addInterceptors(registry: InterceptorRegistry) {
        //registry.addInterceptor此方法添加拦截器
        registry.addInterceptor(StaticResourceConfig()).addPathPatterns("/**")
                .excludePathPatterns("/", "/var/image/**", "/static/**", "/user/login/**", "/login/**", "/welcome/**", "/error/**") //需要配置2：----------- 告知拦截器：/static/admin/** 与 /static/user/** 不需要拦截 （配置的是 路径）
    }

    //    /**
//     * 添加静态资源文件，外部可以直接访问地址
//     * @param registry
//     */
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        //需要配置1：----------- 需要告知系统，这是要被当成静态文件的！
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("**/image/**")
                .addResourceLocations("/upload/**")
                .addResourceLocations("classpath:/static/")

        super.addResourceHandlers(registry)
    }

}