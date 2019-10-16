package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 14:33
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "user_footprint")
public class UserFootprintEntity implements Serializable {

    private static final long serialVersionUID = -8897010457683755306L;
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //会员Id
    @Column(name = "user_id")
    private Integer userId;

    //商品id
    @Column(name = "goods_id")
    private Integer goodsId;

    //记录时间
    @Column(name = "add_time")
    private Date addTime;

    //推荐人
    @Column(name = "referrer")
    private Integer referrer;
}
