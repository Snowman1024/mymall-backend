package com.snowman.mymall.service;

import com.snowman.mymall.vo.AttributeVO;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 15:20
 * @Version 1.0
 **/
public interface AttributeService {

    public List<AttributeVO> queryByGoodsId(Integer goodsId);
}
