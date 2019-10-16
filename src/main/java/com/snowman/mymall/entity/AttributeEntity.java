package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 15:04
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "attribute")
public class AttributeEntity implements Serializable {
    private static final long serialVersionUID = 3884127769675836924L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "attribute_category_id")
    private Integer attributeCategoryId;
    //
    @Column(name = "name")
    private String name;
    //
    @Column(name = "input_type",columnDefinition = "smallint")
    private Integer inputType;
    //
    @Column(name = "value",columnDefinition = "text")
    private String value;
    //
    @Column(name = "sort_order",columnDefinition = "smallint")
    private Integer sortOrder;
}
