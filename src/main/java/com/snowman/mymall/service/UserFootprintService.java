package com.snowman.mymall.service;

import com.snowman.mymall.entity.UserFootprintEntity;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 14:35
 * @Version 1.0
 **/
public interface UserFootprintService {

    public void save(UserFootprintEntity entity);

    public Integer queryTotalByGoodsId(Integer goodsId,Integer referrer);
}
