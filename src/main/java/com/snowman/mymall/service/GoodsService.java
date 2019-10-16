package com.snowman.mymall.service;

import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.vo.GoodsVO;
import com.snowman.mymall.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 15:04
 * @Version 1.0
 **/
public interface GoodsService {

    public List<GoodsVO> queryNewGoodsList();

    public Result list(UserVO loginUser, GoodsVO goodsVO, Integer pageNum, Integer pageSize);

    public Map<String,Object> detail(Integer userId, Integer id, Integer referrer);
}
