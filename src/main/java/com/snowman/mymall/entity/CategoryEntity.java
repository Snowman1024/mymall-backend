package com.snowman.mymall.entity;

import com.snowman.mymall.vo.CategoryVO;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/14 10:00
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "category")
public class CategoryEntity implements Serializable {

    private static final long serialVersionUID = -6063647604720661190L;

    //主键
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //分类名称
    @Column(name = "name")
    private String name;

    //关键字
    @Column(name = "keywords")
    private String keywords;

    //描述
    @Column(name = "front_desc")
    private String frontDesc;

    //父节点
    @Column(name = "parent_id")
    private Integer parentId;

    //排序
    @Column(name = "sort_order")
    private Integer sortOrder;

    //首页展示
    @Column(name = "show_index")
    private Integer showIndex;

    //显示
    @Column(name = "is_show")
    private Integer isShow;

    //banner图片
    @Column(name = "banner_url")
    private String bannerUrl;

    //icon链接
    @Column(name = "icon_url")
    private String iconUrl;

    //图片
    @Column(name = "img_url")
    private String imgUrl;

    //手机banner
    @Column(name = "wap_banner_url")
    private String wapBannerUrl;

    //级别
    @Column(name = "level")
    private String level;

    //类型
    @Column(name = "type")
    private Integer type;

    //
    @Column(name = "front_name")
    private String frontName;

}
