package com.wzj.blog.myblog.config.rsaUtil.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Author:Bobby
 * DateTime:2019/4/9
 **/
@ConfigurationProperties(prefix = "rsa.encrypt")
@Configuration
public class SecretKeyConfig{
    @Value("${rsa.encrypt.privateKey}")
    private String privateKey;
    @Value("${rsa.encrypt.publicKey}")
    private String publicKey;

    private String charset = "UTF-8";
    @Value("${rsa.encrypt.open}")
    private boolean open = true;
    @Value("${rsa.encrypt.showLog}")
    private boolean showLog = false;

    /**
     * 请求数据时间戳校验时间差
     * 超过指定时间的数据认定为伪造
     */
    @Value("${rsa.encrypt.timestampCheck}")
    private boolean timestampCheck = false;

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isShowLog() {
        return showLog;
    }

    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }

    public boolean isTimestampCheck() {
        return timestampCheck;
    }

    public void setTimestampCheck(boolean timestampCheck) {
        this.timestampCheck = timestampCheck;
    }
}
