package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 14:02
 * @Version 1.0
 **/
@Data
public class CollectVO implements Serializable {

    private static final long serialVersionUID = 8086456609769829976L;
    //主键
    private Integer id;

    //用户Id
    private Integer userId;

    //产品Id
    private Integer valueId;

    //添加时间
    private Long addTime;

    //是否是关注
    private Integer isAttention;

    //用户收藏的类型;0是商品,1文章
    private Integer typeId;
    //
    private String name;
    private String listPicUrl;
    private String goodsGrief;
    private String retailPrice;
}
