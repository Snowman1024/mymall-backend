package com.snowman.mymall.service;

import com.snowman.mymall.vo.GoodsGalleryVO;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:50
 * @Version 1.0
 **/
public interface GoodsGalleryService {

    public List<GoodsGalleryVO> queryByGoodsId(Integer goodsId);
}
