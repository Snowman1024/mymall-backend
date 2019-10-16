package com.snowman.mymall.common.enumeration;

/**
 * 发放方式 0：按订单发放 1：按用户发放 2:商品转发送券 3：按商品发放
 * 4:新用户注册 5：线下发放 6评价好评红包（固定或随机红包） 7包邮
 * @Author Snowman2014
 * @Date 2019/10/12 17:00
 * @Version 1.0
 **/
public enum CouponSentType {

    ORDER("按订单发放", 0),
    USER("按用户发放", 1),
    GOOD_TRANFER("商品转发送券", 2),
    GOOD("按商品发放", 3),
    REGISTER("新用户注册", 4),
    OFF_LINE("线下发放", 5),
    COMMENT("评价好评红包", 6),
    FREE_SHIPPING("包邮", 7);

    private int key;
    private String value;

    private CouponSentType(String value, int key) {
        this.key = key;
        this.value = value;
    }

    public static String getNameByKey(int key) {
        for (CouponSentType state : CouponSentType.values()) {
            if (state.getKey() == (key)) {
                return state.getValue();
            }
        }
        return "";
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
