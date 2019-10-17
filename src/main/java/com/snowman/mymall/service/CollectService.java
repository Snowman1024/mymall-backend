package com.snowman.mymall.service;

import com.snowman.mymall.vo.UserVO;

import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 14:21
 * @Version 1.0
 **/
public interface CollectService {

    public Integer queryTotal(Integer userId,Integer valueId,Integer typeId);

    public Map<String, Object> addOrDelete(UserVO loginUser, Integer typeId, Integer valueId);
}
