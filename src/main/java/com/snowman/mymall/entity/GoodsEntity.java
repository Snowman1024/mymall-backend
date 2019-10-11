package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 14:24
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "goods")
public class GoodsEntity implements Serializable {

    private static final long serialVersionUID = -1208848653805703269L;

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //商品类型Id
    @Column(name = "category_id")
    private Integer categoryId;

    //商品序列号
    @Column(name = "goods_sn")
    private String goodsSn;

    //名称
    @Column(name = "name")
    private String name;

    //品牌Id
    @Column(name = "brand_id")
    private Integer brandId;

    //商品序列号
    @Column(name = "goods_number",columnDefinition = "mediumint")
    private Integer goodsNumber;

    //关键字
    @Column(name = "keywords")
    private String keywords;

    //简明介绍
    @Column(name = "goods_brief")
    private String goodsBrief;

    //商品描述
    @Column(name = "goods_desc",columnDefinition = "TEXT")
    private String goodsDesc;

    //上架
    @Column(name = "is_on_sale",columnDefinition = "smallint")
    private Integer isOnSale;

    //添加时间
    @Column(name = "add_time")
    private Date addTime;

    //排序
    @Column(name = "sort_order",columnDefinition = "smallint")
    private Integer sortOrder;

    //删除状态
    @Column(name = "is_delete",columnDefinition = "smallint")
    private Integer isDelete;

    //属性类别
    @Column(name = "attribute_category")
    private Integer attributeCategory;

    //专柜价格
    @Column(name = "counter_price")
    private BigDecimal counterPrice;

    //附加价格
    @Column(name = "extra_price")
    private BigDecimal extraPrice;

    //是否新商品
    @Column(name = "is_new",columnDefinition = "smallint")
    private Integer isNew;

    //商品单位
    @Column(name = "goods_unit")
    private String goodsUnit;

    //商品主图
    @Column(name = "primary_pic_url")
    private String primaryPicUrl;

    //商品列表图
    @Column(name = "list_pic_url")
    private String listPicUrl;

    //市场价
    @Column(name = "market_price")
    private BigDecimal marketPrice;

    //零售价格(现价)
    @Column(name = "retail_price")
    private BigDecimal retailPrice;

    //销售量
    @Column(name = "sell_volume")
    private Integer sellVolume;

    //主sku　product_id
    @Column(name = "primary_product_id")
    private Integer primaryProductId;

    //单位价格，单价
    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    //推广描述
    @Column(name = "promotion_desc")
    private String promotionDesc;

    //推广标签
    @Column(name = "promotion_tag")
    private String promotionTag;

    //APP专享价
    @Column(name = "app_exclusive_price")
    private BigDecimal appExclusivePrice;

    //是否是APP专属
    @Column(name = "is_app_exclusive",columnDefinition = "smallint")
    private Integer isAppExclusive;

    //限购
    @Column(name = "is_limited",columnDefinition = "smallint")
    private Integer isLimited;

    //热销
    @Column(name = "is_hot",columnDefinition = "smallint")
    private Integer isHot;

    //创建人ID
    @Column(name = "create_user_id")
    private Integer createUserId;

    //修改人ID
    @Column(name = "update_user_id")
    private Integer updateUserId;

    //修改时间
    @Column(name = "update_time")
    private Date updateTime;

    @Column(name = "create_user_dept_id")
    private Integer createUserDeptId;
}
