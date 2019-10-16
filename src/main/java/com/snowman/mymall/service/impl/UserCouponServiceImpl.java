package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.UserCouponEntity;
import com.snowman.mymall.repository.UserCouponRepository;
import com.snowman.mymall.service.UserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 16:26
 * @Version 1.0
 **/
@Service
public class UserCouponServiceImpl implements UserCouponService {

    @Autowired
    private UserCouponRepository userCouponRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(UserCouponEntity entity){
        userCouponRepository.save(entity);
    }
}
