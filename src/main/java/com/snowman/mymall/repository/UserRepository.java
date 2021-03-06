package com.snowman.mymall.repository;

import com.snowman.mymall.entity.UserEntity;
import com.snowman.mymall.common.jpa.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 15:55
 * @Version 1.0
 **/
public interface UserRepository extends BaseRepository<UserEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT * FROM user WHERE id = :userId")
    UserEntity queryByUserId(@Param(value = "userId") Integer userId);

    @Query(nativeQuery = true,value = "SELECT * FROM user WHERE mobile = :mobile")
    UserEntity queryByMobile(@Param(value = "mobile")String mobile);

    @Query(nativeQuery = true,value = "SELECT * FROM user WHERE weixin_openid = :openId")
    UserEntity queryByOpenId(@Param(value = "openId")String openId);

    @Modifying
    @Query(nativeQuery = true,value = "UPDATE user SET last_login_ip=:ip,last_login_time=:lastLoginTime " +
            " WHERE id =:id ")
    Integer updateUser(@Param(value = "id") Integer id,
                       @Param(value = "ip") String ip,
                       @Param(value = "lastLoginTime") Date lastLoginTime);

}
