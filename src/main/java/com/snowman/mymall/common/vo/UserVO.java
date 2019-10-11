package com.snowman.mymall.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 15:40
 * @Version 1.0
 **/
@Data
public class UserVO implements Serializable {

    private static final long serialVersionUID = 722254900434179076L;

    //主键
    private Integer userId;
    //会员名称
    private String userName;
    //会员密码
    private String passWord;
    //性别
    private Integer gender;
    //出生日期
    private Date birthday;
    //注册时间
    private Date registerTime;
    //最后登录时间
    private Date lastLoginTime;
    //最后登录Ip
    private String lastLoginIp;
    //会员等级
    private Integer userLevelId;
    //别名
    private String nickName;
    //手机号码
    private String mobile;
    //注册Ip
    private String registerIp;
    //头像
    private String avatar;
    //微信Id
    private String weixinOpenId;
}
