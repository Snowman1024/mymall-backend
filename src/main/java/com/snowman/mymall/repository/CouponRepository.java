package com.snowman.mymall.repository;

import com.snowman.mymall.common.jpa.BaseRepository;
import com.snowman.mymall.entity.AttributeEntity;
import com.snowman.mymall.entity.CouponEntity;
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
public interface CouponRepository extends BaseRepository<CouponEntity, Integer> {

    @Query(nativeQuery = true,value = "SELECT c.id,c.name,c.type_money,c.send_type,c.min_amount, " +
            " c.max_amount,c.send_start_date,c.send_end_date,c.use_start_date,c.use_end_date, " +
            " c.min_goods_amount,c.min_transmit_num, " +
            " uc.coupon_number,uc.user_id,uc.coupon_status,uc.id " +
            " FROM coupon c , user_coupon uc ON c.id = uc.coupon_id " +
            " WHERE uc.user_id = :userId AND c.send_type = :sendType " +
            " AND uc.used_time is null AND (uc.order_id IS NULL OR uc.order_id =0) " +
            " order by uc.coupon_status asc ")
    List<Object> queryUserCoupons(@Param(value = "userId") Integer userId,
                                  @Param(value = "sendType") Integer sendType);


    @Query(nativeQuery = true,value = "SELECT * FROM coupon WHERE send_type=:sendType " +
            " AND use_end_date >= now() AND now() >= use_start_date " +
            " order by id desc ")
    List<CouponEntity> queryCouponBySendType(@Param(value = "sendType") Integer sendType);
}
