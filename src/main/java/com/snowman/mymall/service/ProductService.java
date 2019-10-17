package com.snowman.mymall.service;

import com.snowman.mymall.vo.ProductVO;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:27
 * @Version 1.0
 **/
public interface ProductService {

    List<ProductVO> queryByGoodsId(Integer goodsId);

    public ProductVO queryById(Integer id);
}
