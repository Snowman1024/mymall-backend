package com.snowman.mymall.service;

import com.snowman.mymall.vo.RelatedGoodsVO;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 17:47
 * @Version 1.0
 **/
public interface RelatedGoodsService {

    public List<RelatedGoodsVO> queryRelatedGoods();
}
