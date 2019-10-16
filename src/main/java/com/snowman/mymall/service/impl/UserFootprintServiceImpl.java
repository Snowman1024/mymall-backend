package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.UserFootprintEntity;
import com.snowman.mymall.repository.UserFootprintRepository;
import com.snowman.mymall.service.UserFootprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 14:35
 * @Version 1.0
 **/
@Service
public class UserFootprintServiceImpl implements UserFootprintService {

    @Autowired
    private UserFootprintRepository userFootprintRepository;

    /**
     * 保存
     * @param entity
     */
    @Override
    public void save(UserFootprintEntity entity){
      userFootprintRepository.save(entity);
    }

    @Override
    public Integer queryTotalByGoodsId(Integer goodsId,Integer referrer){
        return userFootprintRepository.queryTotalByGoodsId(goodsId,referrer);
    }
}
