package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 10:02
 * @Version 1.0
 **/
@Data
public class CartVO implements Serializable {

    private static final long serialVersionUID = -5860497831306557674L;
    //主键
    private Integer id;

    //会员Id
    private Integer userId;

    //sessionId
    private String sessionId;

    //商品Id
    private Integer goodsId;

    //商品序列号
    private String goodsSn;

    //产品Id
    private Integer productId;

    //产品名称
    private String goodsName;

    //市场价
    private BigDecimal marketPrice;

    //零售价格
    private BigDecimal retailPrice;

    //product表中的零售价格
    private BigDecimal retailProductPrice;

    //数量
    private Integer number;

    //规格属性组成的字符串，用来显示用
    private String goodsSpecifitionNameValue;

    //product表对应的goods_specifition_ids
    private String goodsSpecifitionIds;
    //
    private Integer checked;

    // 节省金额
    private BigDecimal crashSavePrice;

    //商品图片
    private String listPicUrl;


    public BigDecimal getCrashSavePrice() {
        if (null == crashSavePrice && null != retailProductPrice && null != retailPrice) {
            crashSavePrice = retailProductPrice.subtract(retailPrice);
        }
        return crashSavePrice;
    }

    public void setCrashSavePrice(BigDecimal crashSavePrice) {
        this.crashSavePrice = crashSavePrice;
    }
}
