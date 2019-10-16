package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:54
 * @Version 1.0
 **/
@Data
public class CommentPictureVO implements Serializable {

    private static final long serialVersionUID = 2649840952262380267L;
    //主键
    private Integer id;

    //评价Id
    private Integer commentId;

    //评价图片
    private String picUrl;

    //排序
    private Integer sortOrder;
}
