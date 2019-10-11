package com.snowman.mymall.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description 返回数据
 * @Author Snowman2014
 * @Date 2019/10/10 9:17
 * @Version 1.0
 **/
public class Result extends HashMap<String, Object> {

    private static final long serialVersionUID = -497111866554758038L;

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Result() {
        put("code", 0);
    }

    public static Result ok() {
        return new Result();
    }

    public static Result ok(String msg) {
        Result r = new Result();
        r.put("msg", msg);
        return r;
    }

    public static Result ok(Map<String, Object> map) {
        Result r = new Result();
        r.putAll(map);
        return r;
    }

    public static Result error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static Result error(String msg) {
        return error(500, msg);
    }

    public static Result error(int code, String msg) {
        Result r = new Result();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }





}
