package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 11:11
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "goods_specification")
public class GoodsSpecificationEntity implements Serializable {

    private static final long serialVersionUID = -4307450854921002413L;
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //商品
    @Column(name = "goods_id")
    private Integer goodsId;

    //规格Id
    @Column(name = "specification_id")
    private Integer specificationId;

    //规格说明
    @Column(name = "value")
    private String value;

    //规格图片
    @Column(name = "pic_url")
    private String picUrl;
}
