package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/10 9:54
 * @Version 1.0
 **/
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1000814469411691053L;

    private String nickName;

    private Integer gender;

    private String language;

    private String city;

    private String province;

    private String country;

    private String avatarUrl;

}
