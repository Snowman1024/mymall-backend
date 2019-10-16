package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:47
 * @Version 1.0
 **/
@Data
public class GoodsGalleryVO implements Serializable {

    private static final long serialVersionUID = 8855974466721579648L;
    //主键
    private Integer id;
    //商品id
    private Integer goodsId;
    //图片
    private String imgUrl;
    //描述
    private String imgDesc;
    //排序
    private Integer sortOrder;
}
