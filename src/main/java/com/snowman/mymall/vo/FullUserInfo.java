package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/10 9:53
 * @Version 1.0
 **/
@Data
public class FullUserInfo implements Serializable {
    private static final long serialVersionUID = 5220963100344749192L;

    private String errMsg;

    private String rawData;

    private String signature;

    private String encryptedData;

    private String iv;

    private UserInfo userInfo;

}
