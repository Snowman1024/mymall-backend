package com.snowman.mymall.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/18 10:11
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "host")
@Component
public class HostInfo {

    private String url;

    private String port;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
