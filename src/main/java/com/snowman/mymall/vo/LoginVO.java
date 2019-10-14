package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/10 10:17
 * @Version 1.0
 **/
@Data
public class LoginVO implements Serializable {

    private static final long serialVersionUID = 1511466068489409017L;

    private String code;

    private FullUserInfo fullUserInfo;
}
