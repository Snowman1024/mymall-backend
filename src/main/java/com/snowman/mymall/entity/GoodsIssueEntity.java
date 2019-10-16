package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:04
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "goods_issue")
public class GoodsIssueEntity implements Serializable{

    private static final long serialVersionUID = -4230033139377325682L;

    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    //问题
    @Column(name = "question")
    private String question;

    //回答
    @Column(name = "answer")
    private String answer;

}
