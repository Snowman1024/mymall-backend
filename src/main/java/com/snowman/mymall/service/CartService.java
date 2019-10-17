package com.snowman.mymall.service;

import com.snowman.mymall.vo.CartVO;
import com.snowman.mymall.vo.UserVO;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 11:08
 * @Version 1.0
 **/
public interface CartService {

    public List<CartVO> queryByUserId(Integer userId);

    public Map<String,Object> goodsCount(UserVO loginUser);

    public Map<String,Object> add(UserVO loginUser,Integer goodsId,Integer productId,Integer number);

    public List<CartVO> queryList(Integer userId,Integer goodsId,Integer productId);

    public Map<String,Object> getCart(UserVO loginUser);
}
