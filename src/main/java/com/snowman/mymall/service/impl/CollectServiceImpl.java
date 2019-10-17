package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.CollectEntity;
import com.snowman.mymall.repository.CollectRepository;
import com.snowman.mymall.service.CollectService;
import com.snowman.mymall.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * @param userId
     * @param valueId
     * @param typeId
     * @return
     */
    @Override
    public Integer queryTotal(Integer userId, Integer valueId, Integer typeId) {
        return collectRepository.queryTotal(userId, valueId, typeId);
    }

    /**
     * 添加取消收藏
     *
     * @param loginUser
     * @param typeId
     * @param valueId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> addOrDelete(UserVO loginUser, Integer typeId, Integer valueId) {
        List<CollectEntity> entityList = collectRepository.queryList(loginUser.getUserId(), valueId, typeId);
        //
        String handleType = "add";
        if (CollectionUtils.isEmpty(entityList)) {
            CollectEntity collectEntity = new CollectEntity();
            collectEntity.setUserId(loginUser.getUserId());
            collectEntity.setValueId(valueId);
            collectEntity.setTypeId(typeId);
            collectEntity.setAddTime(new Date());
            collectEntity.setIsAttention(0);
            //添加收藏
            collectRepository.save(collectEntity);

        } else {
            //取消收藏
            collectRepository.delete(entityList.get(0));
            handleType = "delete";
        }
        Map data = new HashMap();
        data.put("type", handleType);
        return data;
    }
}
