package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 11:15
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "specification")
public class SpecificationEntity implements Serializable {
    private static final long serialVersionUID = -3214604522594191301L;

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //规格名称
    @Column(name = "name")
    private String name;

    //排序
    @Column(name = "sort_order",columnDefinition = "smallint")
    private Integer sortOrder;
}
