package com.snowman.mymall.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/10 10:32
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "wx")
@Component
public class WeixinInfo {

    private String appId;
    private String secret;
    private String mchId;
    private String paySignKey;
    private String tradeType;
    private String certName;
    private String notifyUrl;
    private String codeUrl;
    private String webAccessTokenHttps;
    private String userMessageUrl;
    private String uniformOrderUrl;
    private String refundUrl;
    private String refundQueryUrl;
    private String orderQueryUrl;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getPaySignKey() {
        return paySignKey;
    }

    public void setPaySignKey(String paySignKey) {
        this.paySignKey = paySignKey;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getCertName() {
        return certName;
    }

    public void setCertName(String certName) {
        this.certName = certName;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getWebAccessTokenHttps() {
        return webAccessTokenHttps;
    }

    public void setWebAccessTokenHttps(String webAccessTokenHttps) {
        this.webAccessTokenHttps = webAccessTokenHttps;
    }

    public String getUserMessageUrl() {
        return userMessageUrl;
    }

    public void setUserMessageUrl(String userMessageUrl) {
        this.userMessageUrl = userMessageUrl;
    }

    public String getUniformOrderUrl() {
        return uniformOrderUrl;
    }

    public void setUniformOrderUrl(String uniformOrderUrl) {
        this.uniformOrderUrl = uniformOrderUrl;
    }

    public String getRefundUrl() {
        return refundUrl;
    }

    public void setRefundUrl(String refundUrl) {
        this.refundUrl = refundUrl;
    }

    public String getRefundQueryUrl() {
        return refundQueryUrl;
    }

    public void setRefundQueryUrl(String refundQueryUrl) {
        this.refundQueryUrl = refundQueryUrl;
    }

    public String getOrderQueryUrl() {
        return orderQueryUrl;
    }

    public void setOrderQueryUrl(String orderQueryUrl) {
        this.orderQueryUrl = orderQueryUrl;
    }
}
