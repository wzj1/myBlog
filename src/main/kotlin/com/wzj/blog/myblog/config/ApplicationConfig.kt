package com.wzj.blog.myblog.config

import com.wzj.blog.myblog.listener.SessionListener
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.http.HttpMessageConverters
import org.springframework.boot.web.servlet.MultipartConfigFactory
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.util.unit.DataSize
import org.springframework.util.unit.DataUnit
import org.springframework.web.servlet.config.annotation.*
import java.nio.charset.Charset
import javax.annotation.Resource
import javax.servlet.MultipartConfigElement


@Configuration
open class ApplicationConfig : WebMvcConfigurer {

    @Value("\${static.image}")
    private val filepath: String? = null

    @Resource
    lateinit var staticResource: StaticResourceConfig

    //配置拦截器
    override fun addInterceptors(registry: InterceptorRegistry) {
        //registry.addInterceptor此方法添加拦截器
        registry.addInterceptor(staticResource).addPathPatterns("/**")
                .excludePathPatterns("/", "/var/image/**", "/static/**", "/welcome/**", "/error/**") //需要配置2：----------- 告知拦截器：/static/admin/** 与 /static/user/** 不需要拦截 （配置的是 路径）
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        //设置允许跨域的路径
        registry.addMapping("/**")
                //设置允许跨域请求的域名
                .allowedOrigins("*")
                .allowedHeaders("*")
//                .allowCredentials(true)//是否允许证书 不再默认开启
                //设置允许的方法
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .maxAge(3600);//跨域允许时间
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/")
        registry.addResourceHandler("/upload/**").addResourceLocations("classpath:/upload/")
        registry.addResourceHandler("/image/**").addResourceLocations("/var/image/upload")


        super.addResourceHandlers(registry)
    }


    @Bean
    fun responseBodyConverter(): HttpMessageConverter<*>? {
        //解决返回值中文乱码
        return StringHttpMessageConverter(Charset.forName("UTF-8"))
    }

    override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        converters.add(responseBodyConverter())
    }

    @Bean
    fun listenerRegist(): ServletListenerRegistrationBean<*>? {
        val srb= ServletListenerRegistrationBean<SessionListener>()
        srb.listener = SessionListener()
        println("listener")
        return srb
    }





//    @Bean
//    fun multipartConfigElement(): MultipartConfigElement? {
//        val factory = MultipartConfigFactory()
//        factory.setLocation("/tmp/tomcat")
//        factory.setMaxFileSize(DataSize.of(1024L * 1024L,DataUnit.MEGABYTES))
//        return factory.createMultipartConfig()
//    }

//
//    @Bean
//    fun HttpMessageConverterscustomConverters(): HttpMessageConverters {
//        val messageConverters:Collection<HttpMessageConverter<*>>  = arrayListOf();
//
//        val  gsonHttpMessageConverter =  GsonHttpMessageConverter()
//        messageConverters.contains(gsonHttpMessageConverter)
//        messageConverters.contains(responseBodyConverter())
//        return  HttpMessageConverters(true,messageConverters)
//
//    }

}