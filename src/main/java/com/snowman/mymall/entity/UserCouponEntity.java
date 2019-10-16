package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 15:48
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "user_coupon")
public class UserCouponEntity implements Serializable {
    private static final long serialVersionUID = -4287757187008800041L;

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //优惠券Id
    @Column(name = "coupon_id")
    private Integer couponId;

    //优惠券序列号
    @Column(name = "coupon_no")
    private String couponNo;

    //会员Id
    @Column(name = "user_id")
    private Integer userId;

    //使用时间
    @Column(name = "used_time")
    private Date usedTime;

    //领取时间
    @Column(name = "add_time")
    private Date addTime;

    //订单Id
    @Column(name = "order_id")
    private Integer orderId;

    //来源key
    @Column(name = "source_key")
    private String sourceKey;

    //分享人
    @Column(name = "referrer")
    private Integer referrer;

    //状态 1. 可用 2. 已用 3. 过期
    @Column(name = "coupon_status")
    private Integer couponStatus;
}
