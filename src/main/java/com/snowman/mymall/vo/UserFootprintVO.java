package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 14:31
 * @Version 1.0
 **/
@Data
public class UserFootprintVO implements Serializable {

    private static final long serialVersionUID = 3231148365694850338L;

    //主键
    private Integer id;

    //会员Id
    private Integer userId;

    //商品id
    private Integer goodsId;

    //记录时间
    private Date addTime;

    //推荐人
    private Integer referrer;

    // 商品冗余字段
    private String name;
    private String listPicUrl;
    private String goodsBrief;
    //
    private BigDecimal retailPrice;
    // 会员
    private String nickName;
    private String avatar;
}
