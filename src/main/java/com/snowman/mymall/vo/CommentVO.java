package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:48
 * @Version 1.0
 **/
@Data
public class CommentVO implements Serializable {

    //主键
    private Integer id;

    //用户评论的类型;0评论的是商品,1评论的是文章
    private Integer typeId;

    //产品Id
    private Integer valueId;

    //储存为base64编码
    private String content;

    //记录时间
    private Long addTime;

    //状态 是否被管理员批准显示;1是;0未批准显示
    private Integer status;

    //会员Id
    private Integer userId;

    //会员Id
    private UserVO userInfo;

    private List<CommentPictureVO> picList;
}
