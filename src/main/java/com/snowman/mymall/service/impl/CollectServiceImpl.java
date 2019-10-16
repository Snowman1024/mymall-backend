package com.snowman.mymall.service.impl;

import com.snowman.mymall.repository.CollectRepository;
import com.snowman.mymall.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 14:21
 * @Version 1.0
 **/
@Service
public class CollectServiceImpl implements CollectService {

    @Autowired
    private CollectRepository collectRepository;

    /**
     *
     * @param userId
     * @param valueId
     * @param typeId
     * @return
     */
    @Override
    public Integer queryTotal(Integer userId,Integer valueId,Integer typeId){
       return collectRepository.queryTotal(userId,valueId,typeId);
    }
}
