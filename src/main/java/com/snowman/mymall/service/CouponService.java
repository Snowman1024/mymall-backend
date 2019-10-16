package com.snowman.mymall.service;

import com.snowman.mymall.vo.CouponVO;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 15:08
 * @Version 1.0
 **/
public interface CouponService {

    public List<CouponVO> queryUserCoupons(Integer userId, Integer sendType);

    public List<CouponVO> queryCouponBySendType(Integer sendType);
}
