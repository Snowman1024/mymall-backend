package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 11:16
 * @Version 1.0
 **/
@Data
public class SpecificationVO implements Serializable {

    private static final long serialVersionUID = 1687523591363133509L;
    //主键
    private Integer id;
    //规格名称
    private String name;
    //排序
    private Integer sortOrder;
}
