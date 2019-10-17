package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 17:42
 * @Version 1.0
 **/
@Data
public class RelatedGoodsVO implements Serializable {

    private static final long serialVersionUID = 3855229227999571934L;
    //主键
    private Integer id;

    //商品Id
    private Integer goodsId;

    //关联商品id
    private Integer relatedGoodsId;

}
