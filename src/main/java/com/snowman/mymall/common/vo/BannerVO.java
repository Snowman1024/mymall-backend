package com.snowman.mymall.common.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 11:27
 * @Version 1.0
 **/
@Data
public class BannerVO implements Serializable {

    private static final long serialVersionUID = 6171422250087980225L;

    private Integer id;

    private Integer positionId;

    /**
     * 媒体类型（1图片）
     */
    private Integer mediaType;

    /**
     * 名字
     */
    private String name;

    /**
     * 跳转地址
     */
    private String link;

    /**
     * 图片url
     */
    private String imageUrl;

    /**
     * 内容
     */
    private String content;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 是否可用（1可用，0不可用）
     */
    private Integer enabled;
}
