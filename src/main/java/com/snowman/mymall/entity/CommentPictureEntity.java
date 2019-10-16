package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:55
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "comment_picture")
public class CommentPictureEntity implements Serializable {

    private static final long serialVersionUID = 7989187008951662886L;
    //主键
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //评价Id
    @Column(name = "comment_id")
    private Integer commentId;

    //评价图片
    @Column(name = "pic_url")
    private String picUrl;

    //排序
    @Column(name = "sort_order",columnDefinition = "smallint")
    private Integer sortOrder;
}
