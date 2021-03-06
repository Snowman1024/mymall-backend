package com.snowman.mymall.common.utils;

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/10 10:46
 * @Version 1.0
 **/
public class MyX509TrustManager implements X509TrustManager {

    public MyX509TrustManager() {
    }

    /**
     * 检查客户端证书
     * @param chain
     * @param authType
     * @throws CertificateException
     */
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    /**
     * 检查服务器端证书
     * @param chain
     * @param authType
     * @throws CertificateException
     */
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
    }

    /**
     * 返回受信任的X509证书数组
     * @return
     */
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }
}
