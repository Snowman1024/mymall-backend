package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 15:40
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "user")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -9134632449644303414L;

    /**
     * 主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;

    /**
     * 会员名称
     */
    @Column(name = "username")
    private String userName;

    /**
     * 会员密码
     */
    @Column(name = "password")
    private String passWord;

    /**
     * 性别
     */
    @Column(name = "gender")
    private Integer gender;

    /**
     * 出生日期
     */
    @Column(name = "birthday")
    private Date birthday;

    /**
     * 注册时间
     */
    @Column(name = "register_time")
    private Date registerTime;

    /**
     * 最后登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 最后登录Ip
     */
    @Column(name = "last_login_ip")
    private String lastLoginIp;

    /**
     * 会员等级
     */
    @Column(name = "user_level_id")
    private Integer userLevelId;

    /**
     * 别名
     */
    @Column(name = "nickname")
    private String nickName;

    /**
     * 手机号码
     */
    @Column(name = "mobile")
    private String mobile;

    /**
     * 注册Ip
     */
    @Column(name = "register_ip")
    private String registerIp;

    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;

    /**
     * 微信Id
     */
    @Column(name = "weixin_openid")
    private String weixinOpenId;
}
