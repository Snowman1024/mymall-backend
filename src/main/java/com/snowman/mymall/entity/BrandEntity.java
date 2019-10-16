package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:21
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "brand")
public class BrandEntity implements Serializable {

    private static final long serialVersionUID = 6103393055870908036L;

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //品牌名称
    @Column(name = "name")
    private String name;

    //图片
    @Column(name = "list_pic_url")
    private String listPicUrl;

    //描述
    @Column(name = "simple_desc")
    private String simpleDesc;

    //图片
    @Column(name = "pic_url")
    private String picUrl;

    //排序
    @Column(name = "sort_order",columnDefinition = "smallint")
    private Integer sortOrder;

    //显示
    @Column(name = "is_show",columnDefinition = "smallint")
    private Integer isShow;

    //
    @Column(name = "floor_price")
    private BigDecimal floorPrice;

    //app显示图片
    @Column(name = "app_list_pic_url")
    private String appListPicUrl;

    //新品牌
    @Column(name = "is_new",columnDefinition = "smallint")
    private Integer isNew;

    //图片
    @Column(name = "new_pic_url")
    private String newPicUrl;

    //排序
    @Column(name = "new_sort_order",columnDefinition = "smallint")
    private Integer newSortOrder;
}
