package com.snowman.mymall.common;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/14 16:14
 * @Version 1.0
 **/
public class Constant {

    public static final String HOST_URL = "http://localhost:8080";
    /**
     * 缓存有效时间12小时
     */
    public static final long CACHE_VALID_TIME = 3600 * 2L;

    /**
     * 首页查询banner
     */
    public static final String NEW_GOODS_KEY = "new_goods_list";

    /**
     * 首页新商品
     */
    public static final String BANNER_KEY = "banner_list";

    /**
     * 商城业务缓存
     */
    public static final String SHOP_CACHE_NAME = "shopCache";
}
