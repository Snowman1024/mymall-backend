package com.snowman.mymall.entity;

import lombok.Data;
import org.springframework.boot.convert.DataSizeUnit;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 10:07
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "cart")
public class CartEntity implements Serializable {

    private static final long serialVersionUID = -895426321966448431L;

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //会员Id
    @Column(name = "user_id")
    private Integer userId;

    //sessionId
    @Column(name = "session_id")
    private String sessionId;

    //商品Id
    @Column(name = "goods_id")
    private Integer goodsId;

    //商品序列号
    @Column(name = "goods_sn")
    private String goodsSn;

    //产品Id
    @Column(name = "product_id")
    private Integer productId;

    //产品名称
    @Column(name = "goods_name")
    private String goodsName;

    //市场价
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    //零售价格
    @Column(name = "retail_price")
    private BigDecimal retailPrice;

    //数量
    @Column(name = "number",columnDefinition = "smallint")
    private Integer number;

    //规格属性组成的字符串，用来显示用
    @Column(name = "goods_specifition_name_value",columnDefinition = "TEXT")
    private String goodsSpecifitionNameValue;

    //product表对应的goods_specifition_ids
    @Column(name = "goods_specifition_ids")
    private String goodsSpecifitionIds;

    //
    @Column(name = "checked",columnDefinition = "smallint")
    private Integer checked;

    //商品图片
    @Column(name = "list_pic_url")
    private String listPicUrl;
}
