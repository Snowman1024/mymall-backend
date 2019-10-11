package com.snowman.mymall.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description 用户Token
 * @Author Snowman2014
 * @Date 2019/10/8 15:08
 * @Version 1.0
 **/
@Data
@Entity
@Table(name = "user_token")
public class TokenEntity implements Serializable {

    private static final long serialVersionUID = -6573298148442520588L;

    /**
     * 用户ID
     */
    @Id
    @Column(name = "user_id")
    private Integer userId;

    /**
     * token
     */
    @Column(name = "token")
    private String token;

    /**
     * 过期时间
     */
    @Column(name = "expire_time")
    private Date expireTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

}
