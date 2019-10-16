package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:48
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "goods_gallery")
public class GoodsGalleryEntity implements Serializable {
    private static final long serialVersionUID = -1297836254034261662L;

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //商品id
    @Column(name = "goods_id")
    private Integer goodsId;

    //图片
    @Column(name = "img_url",columnDefinition = "longblob")
    private String imgUrl;

    //描述
    @Column(name = "img_desc")
    private String imgDesc;

    //排序
    @Column(name = "sort_order")
    private Integer sortOrder;
}
