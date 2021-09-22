package com.wzj.blog.myblog.config.rsaUtil.advice;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.wzj.blog.myblog.config.rsaUtil.annotation.Encrypt;
import com.wzj.blog.myblog.config.rsaUtil.config.SecretKeyConfig;
import com.wzj.blog.myblog.config.rsaUtil.util.Base64Util;
import com.wzj.blog.myblog.config.rsaUtil.util.JsonUtils;
import com.wzj.blog.myblog.config.rsaUtil.util.RSAUtil;
import com.wzj.blog.myblog.result.Result;
import com.wzj.blog.myblog.util.key.RSAUtil1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Author:Bobby
 * DateTime:2019/4/9
 **/
@ControllerAdvice
public class EncryptResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private boolean encrypt;

    @Autowired
    private SecretKeyConfig secretKeyConfig;

    private static ThreadLocal<Boolean> encryptLocal = new ThreadLocal<>();

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        Method method = returnType.getMethod();
        if (Objects.isNull(method)) {
            return encrypt;
        }
        encrypt = method.isAnnotationPresent(Encrypt.class) && secretKeyConfig.isOpen();
        return encrypt;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // EncryptResponseBodyAdvice.setEncryptStatus(false);
        // Dynamic Settings Not Encrypted
        Boolean status = encryptLocal.get();
        if (null != status && !status) {
            encryptLocal.remove();
            return body;
        }
        if (encrypt) {
            String publicKey = secretKeyConfig.getPublicKey();
            try {
                String content = Result.Companion.log(new Gson().toJson(body));
                if (!StringUtils.hasText(publicKey)) {
                    throw new NullPointerException("Please configure rsa.encrypt.privatekeyc parameter!");
                }
//                byte[] data = content.getBytes(StandardCharsets.UTF_8);
                String result = RSAUtil1.encryptByPublic(content);
//                byte[] encodedData = RSAUtil1.encryptByPublic(content, publicKey);
//                String result  = Base64Util.encode(data);
                if(secretKeyConfig.isShowLog()) {
                    log.info("Pre-encrypted data：{}，After encryption：{}", content, result);
                }
                return result;
            } catch (Exception e) {
                log.error("Encrypted data exception", e);
            }
        }

        return body;
    }
}
