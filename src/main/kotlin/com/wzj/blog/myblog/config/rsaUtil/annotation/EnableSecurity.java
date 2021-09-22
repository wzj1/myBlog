package com.wzj.blog.myblog.config.rsaUtil.annotation;

import com.wzj.blog.myblog.config.rsaUtil.advice.EncryptRequestBodyAdvice;
import com.wzj.blog.myblog.config.rsaUtil.advice.EncryptResponseBodyAdvice;
import com.wzj.blog.myblog.config.rsaUtil.config.SecretKeyConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Author:Bobby
 * DateTime:2019/4/9 16:44
 **/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import({SecretKeyConfig.class,
        EncryptResponseBodyAdvice.class,
        EncryptRequestBodyAdvice.class})
public @interface EnableSecurity{

}
