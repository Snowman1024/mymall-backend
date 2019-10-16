package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:13
 * @Version 1.0
 **/
@Data
public class ProductVO implements Serializable {

    private static final long serialVersionUID = 1594243460882702152L;

    //主键
    private Integer id;
    //商品Id
    private Integer goodsId;
    //产品Id
    private Integer productId;
    //商品规格ids
    private String goodsSpecificationIds;
    //商品序列号
    private String goodsSn;
    //商品库存
    private Integer goodsNumber;
    //零售价格
    private BigDecimal marketPrice;
    //时长价
    private BigDecimal retailPrice;
    //商品名称
    private String goodsName;
    //商品图片
    private String listPicUrl;
}
