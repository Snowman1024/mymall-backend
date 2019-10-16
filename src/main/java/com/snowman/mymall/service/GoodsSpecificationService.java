package com.snowman.mymall.service;

import com.snowman.mymall.vo.GoodsSpecificationVO;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:20
 * @Version 1.0
 **/
public interface GoodsSpecificationService {

    List<GoodsSpecificationVO> queryByGoodsId(Integer goodsId);
}
