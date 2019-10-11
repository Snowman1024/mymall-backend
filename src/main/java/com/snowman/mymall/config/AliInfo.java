package com.snowman.mymall.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 10:19
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "ali")
@Component
public class AliInfo {

    private String webAccessTokenHttps;
    private String appId;
    private String privateKey;
    private String pubKey;

    public String getWebAccessTokenHttps() {
        return webAccessTokenHttps;
    }

    public void setWebAccessTokenHttps(String webAccessTokenHttps) {
        this.webAccessTokenHttps = webAccessTokenHttps;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getPubKey() {
        return pubKey;
    }

    public void setPubKey(String pubKey) {
        this.pubKey = pubKey;
    }
}
