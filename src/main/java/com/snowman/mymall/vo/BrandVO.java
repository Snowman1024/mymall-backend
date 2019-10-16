package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:18
 * @Version 1.0
 **/
@Data
public class BrandVO implements Serializable {

    private static final long serialVersionUID = 5497141344073435815L;
    //主键
    private Integer id;
    //品牌名称
    private String name;
    //图片
    private String listPicUrl;
    //描述
    private String simpleDesc;
    //图片
    private String picUrl;
    //排序
    private Integer sortOrder;
    //显示
    private Integer isShow;
    //
    private BigDecimal floorPrice;
    //app显示图片
    private String appListPicUrl;
    //新品牌
    private Integer isNew;
    //图片
    private String newPicUrl;
    //排序
    private Integer newSortOrder;
}
