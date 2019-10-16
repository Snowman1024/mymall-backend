package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:50
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "comment")
public class CommentEntity implements Serializable {
    private static final long serialVersionUID = -311376065484274281L;

    //主键
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //用户评论的类型;0评论的是商品,1评论的是文章
    @Column(name = "type_id",columnDefinition = "smallint")
    private Integer typeId;

    //产品Id
    @Column(name = "value_id")
    private Integer valueId;

    //储存为base64编码
    @Column(name = "content")
    private String content;

    //记录时间
    @Column(name = "add_time")
    private Date addTime;

    //状态 是否被管理员批准显示;1是;0未批准显示
    @Column(name = "status",columnDefinition = "smallint")
    private Integer status;

    //会员Id
    @Column(name = "user_id")
    private Integer userId;
}
