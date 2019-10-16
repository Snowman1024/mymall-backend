package com.snowman.mymall.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 15:03
 * @Version 1.0
 **/
@Data
public class AttributeVO implements Serializable {

    private static final long serialVersionUID = 6901444349586518721L;
    //
    private Integer id;
    //
    private Integer attributeCategoryId;
    //
    private String name;
    //
    private Integer inputType;
    //
    private String value;
    //
    private Integer sortOrder;

}
