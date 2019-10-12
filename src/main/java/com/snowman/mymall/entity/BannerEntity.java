package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 11:20
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "banner")
public class BannerEntity implements Serializable {

    private static final long serialVersionUID = 8057540951825362464L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "position_id",columnDefinition = "smallint")
    private Integer positionId;

    /**
     * 媒体类型（1图片）
     */
    @Column(name = "media_type",columnDefinition = "smallint")
    private Integer mediaType;

    /**
     * 名字
     */
    @Column(name = "name")
    private String name;

    /**
     * 跳转地址
     */
    @Column(name = "link")
    private String link;

    /**
     * 图片url
     */
    @Column(name = "image_url",columnDefinition = "TEXT")
    private String imageUrl;

    /**
     * 内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 结束时间
     */
    @Column(name = "end_time")
    private Date endTime;

    /**
     * 是否可用（1可用，0不可用）
     */
    @Column(name = "enabled",columnDefinition = "smallint")
    private Integer enabled;
}
