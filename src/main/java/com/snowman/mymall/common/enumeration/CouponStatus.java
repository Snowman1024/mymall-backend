package com.snowman.mymall.common.enumeration;

/**
 * 状态 1. 可用 2. 已用 3. 过期
 * @Author Snowman2014
 * @Date 2019/10/12 17:00
 * @Version 1.0
 **/
public enum CouponStatus {

    USUSE("可用", 1),
    USED("已用", 2),
    EXPIRE("过期", 3);

    private int key;
    private String value;

    private CouponStatus(String value, int key) {
        this.key = key;
        this.value = value;
    }

    public static String getNameByKey(int key) {
        for (CouponStatus state : CouponStatus.values()) {
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
