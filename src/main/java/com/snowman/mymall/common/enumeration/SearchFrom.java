package com.snowman.mymall.common.enumeration;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 17:00
 * @Version 1.0
 **/
public enum SearchFrom {

    PC("PC", "1"),
    MINIPROGRAM("微信小程序", "2"),
    APP("APP", "3");

    private String key;
    private String value;

    private SearchFrom(String value, String key) {
        this.key = key;
        this.value = value;
    }

    public static String getNameByKey(String key) {
        for (SearchFrom state : SearchFrom.values()) {
            if (state.getKey().equals(key)) {
                return state.getValue();
            }
        }
        return "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
