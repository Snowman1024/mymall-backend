package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 17:43
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "related_goods")
public class RelatedGoodsEntity implements Serializable {
    private static final long serialVersionUID = 769453447331383466L;

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //商品Id
    @Column(name = "goods_id")
    private Integer goodsId;

    //关联商品id
    @Column(name = "related_goods_id")
    private Integer relatedGoodsId;
}
