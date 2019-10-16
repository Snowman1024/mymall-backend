package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:03
 * @Version 1.0
 **/
@Data
public class GoodsIssueVO implements Serializable{

    private static final long serialVersionUID = 8822174932631578437L;
    //主键
    private Integer id;

    //问题
    private String question;
    //回答
    private String answer;

    //商品id
    private String goodsId;

}
