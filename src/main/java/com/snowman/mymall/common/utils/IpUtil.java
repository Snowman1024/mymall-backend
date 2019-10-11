package com.snowman.mymall.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/10 11:10
 * @Version 1.0
 **/
public class IpUtil {

    public static String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Real-IP");
        if (xff != null) {
            return xff;
        }
        xff = request.getHeader("x-forwarded-for");
        if (xff == null) {
            return "8.8.8.8";
        }
        return xff;
    }
}
