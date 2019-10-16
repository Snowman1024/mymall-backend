package com.snowman.mymall.common.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 11:21
 * @Version 1.0
 **/
public class Base64Util {

    /**
     * 加密
     *
     * @param str
     * @return
     */
    public static String encode(String str) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] b = str.getBytes(StandardCharsets.UTF_8);
        return encoder.encodeToString(b);
    }

    /**
     * 解密
     *
     * @param s
     * @return
     */
    public static String decode(String s) {
        Base64.Decoder decoder = Base64.getDecoder();
        return new String(decoder.decode(s), StandardCharsets.UTF_8);
    }
}
