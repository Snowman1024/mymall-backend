package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 14:03
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "collect")
public class CollectEntity implements Serializable {

    private static final long serialVersionUID = 9003221020941284584L;
    //主键
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //用户Id
    @Column(name = "user_id")
    private Integer userId;

    //产品Id
    @Column(name = "value_id")
    private Integer valueId;

    //添加时间
    @Column(name = "add_time")
    private Date addTime;

    //是否是关注
    @Column(name = "is_attention",columnDefinition = "smallint")
    private Integer isAttention;

    //用户收藏的类型;0是商品,1文章
    @Column(name = "type_id")
    private Integer typeId;
}
