package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 16:05
 * @Version 1.0
 **/
@Data
public class CouponInfoVO implements Serializable {

    private String msg; // 显示信息
    private Integer type = 0; // 是否凑单 0否 1是
}
