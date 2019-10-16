package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 14:57
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "coupon")
public class CouponEntity implements Serializable {

    private static final long serialVersionUID = 8310123426811169327L;

    // 优惠券主键
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    //优惠券名称
    @Column(name = "name")
    private String name;

    //金额
    @Column(name = "type_money")
    private BigDecimal typeMoney;

    //发放方式 0：按订单发放 1：按用户发放 2:商品转发送券 3：按商品发放
    // 4:新用户注册 5：线下发放 6评价好评红包（固定或随机红包） 7包邮
    @Column(name = "send_type",columnDefinition = "tinyiny")
    private Integer sendType;

    //最小金额
    @Column(name = "min_amount")
    private BigDecimal minAmount;

    //最大金额
    @Column(name = "max_amount")
    private BigDecimal maxAmount;

    //发放时间 yyyy.MM.dd
    @Column(name = "send_start_date")
    private Date sendStartDate;

    //发放时间 yyyy.MM.dd
    @Column(name = "send_end_date")
    private Date sendEndDate;

    //使用开始时间 yyyy.MM.dd
    @Column(name = "use_start_date")
    private Date useStartDate;

    //使用结束时间 yyyy年MM月dd日
    @Column(name = "use_end_date")
    private Date useEndDate;

    //最小商品金额
    @Column(name = "min_goods_amount")
    private BigDecimal minGoodsAmount;


    //转发次数
    @Column(name = "min_transmit_num")
    private Integer minTransmitNum;


}
