package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.CouponEntity;
import com.snowman.mymall.entity.UserCouponEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 15:42
 * @Version 1.0
 **/
public interface UserCouponRepository extends BaseRepository<UserCouponEntity, Integer> {

    @Modifying
    @Query(nativeQuery = true,value = "UPDATE user_coupon SET coupon_status=:couponStatus" +
            " WHERE id= :id")
    int updateUserCoupon(@Param(value = "id") Integer id,
                         @Param(value = "couponStatus") Integer couponStatus);
}
