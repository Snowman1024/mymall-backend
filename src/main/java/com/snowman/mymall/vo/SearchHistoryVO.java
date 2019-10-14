package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 16:52
 * @Version 1.0
 **/
@Data
public class SearchHistoryVO implements Serializable{

    private static final long serialVersionUID = 3757936440361366929L;

    //主键
    private Integer id;
    //关键字
    private String keyword;
    //搜索来源，如PC、小程序、APP等
    private String from;
    //搜索时间
    private Long addTime;
    //会员Id
    private String userId;
}
