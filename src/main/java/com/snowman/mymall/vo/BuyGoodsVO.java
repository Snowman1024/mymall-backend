package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 14:19
 * @Version 1.0
 **/
@Data
public class BuyGoodsVO implements Serializable {

    private static final long serialVersionUID = 2081656368470624995L;
    private Integer goodsId;
    private Integer productId;
    private Integer number;
    private String name;
}
