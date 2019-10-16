package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 16:53
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "search_history")
public class SearchHistoryEntity implements Serializable {

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //关键字
    @Column(name = "keyword")
    private String keyword;

    //搜索来源，如PC、小程序、APP等
    @Column(name = "from")
    private String from;

    //搜索时间
    @Column(name = "add_time")
    private Date addTime;

    //会员Id
    @Column(name = "user_id")
    private String userId;
}
