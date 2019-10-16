package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 11:10
 * @Version 1.0
 **/
@Data
public class GoodsSpecificationVO implements Serializable {

    private static final long serialVersionUID = -6600193364687496332L;
    //主键
    private Integer id;
    //商品
    private Integer goodsId;
    //规范Id
    private Integer specificationId;
    //规范说明
    private String value;
    private String name;
    //规范图片
    private String picUrl;

}
