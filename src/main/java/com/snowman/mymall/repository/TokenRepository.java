package com.snowman.mymall.repository;

import com.snowman.mymall.entity.TokenEntity;
import com.snowman.mymall.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 15:10
 * @Version 1.0
 **/
public interface TokenRepository extends BaseRepository<TokenEntity, String> {

    @Query(nativeQuery = true,value = "SELECT * FROM user_token WHERE token=:token")
    TokenEntity queryByToken(@Param(value = "token") String token);
}
