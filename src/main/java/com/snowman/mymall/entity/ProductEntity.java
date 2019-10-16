package com.snowman.mymall.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:16
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "product")
public class ProductEntity implements Serializable {
    private static final long serialVersionUID = 5479245314216030726L;

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //商品Id
    @Column(name = "goods_id")
    private Integer goodsId;

    //商品规格ids
    @Column(name = "goods_specification_ids")
    private String goodsSpecificationIds;

    //商品序列号
    @Column(name = "goods_sn")
    private String goodsSn;

    //商品库存
    @Column(name = "goods_number")
    private Integer goodsNumber;

    //零售价格
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    //时长价
    @Column(name = "retail_price")
    private BigDecimal retailPrice;
}
