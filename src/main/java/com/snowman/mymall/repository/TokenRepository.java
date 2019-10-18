package com.snowman.mymall.repository;

import com.snowman.mymall.entity.TokenEntity;
import com.snowman.mymall.common.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 15:10
 * @Version 1.0
 **/
public interface TokenRepository extends BaseRepository<TokenEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM user_token WHERE token=:token")
    TokenEntity queryByToken(@Param(value = "token") String token);

    @Query(nativeQuery = true,value = "SELECT * FROM user_token WHERE user_id=:userId")
    TokenEntity queryByUserId(@Param(value = "userId") Integer userId);

    @Modifying
    @Query(nativeQuery = true,value = "UPDATE user_token SET token=:token,update_time=:updateTime," +
            " expire_time=:expireTime WHERE user_id=:userId")
    Integer updateToken(@Param(value = "userId") Integer userId,
                        @Param(value = "token")String token,
                        @Param(value = "updateTime")Date updateTime,
                        @Param(value = "expireTime")Date expireTime);

}
