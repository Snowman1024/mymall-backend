package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.utils.ConvertUtil;
import com.snowman.mymall.repository.GoodsSpecificationRepository;
import com.snowman.mymall.service.GoodsSpecificationService;
import com.snowman.mymall.vo.GoodsSpecificationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:21
 * @Version 1.0
 **/
@Service
public class GoodsSpecificationServiceImpl implements GoodsSpecificationService {

    @Autowired
    private GoodsSpecificationRepository goodsSpecificationRepository;

    /**
     * @param goodsId
     * @return
     */
    @Override
    public List<GoodsSpecificationVO> queryByGoodsId(Integer goodsId) {
        List<Object> goodsSpecificationObjList = goodsSpecificationRepository.queryByGoodsId(goodsId);
        List<GoodsSpecificationVO> goodsSpecificationVOList = new ArrayList<>();
        if (CollectionUtils.isEmpty(goodsSpecificationObjList)) {
            return goodsSpecificationVOList;
        }
        Object[] goodsSpecificationObjArr = goodsSpecificationObjList.toArray();

        for (int i = 0; i < goodsSpecificationObjArr.length; i++) {
            Object[] item = (Object[]) goodsSpecificationObjArr[i];
            GoodsSpecificationVO goodsSpecificationVO = new GoodsSpecificationVO();
            goodsSpecificationVO.setId(ConvertUtil.toInteger(item[0]));
            goodsSpecificationVO.setGoodsId(ConvertUtil.toInteger(item[1]));
            goodsSpecificationVO.setSpecificationId(ConvertUtil.toInteger(item[2]));
            goodsSpecificationVO.setValue(ConvertUtil.toString(item[3]));
            goodsSpecificationVO.setPicUrl(ConvertUtil.toString(item[4]));
            goodsSpecificationVO.setName(ConvertUtil.toString(item[5]));
            goodsSpecificationVOList.add(goodsSpecificationVO);
        }
        return goodsSpecificationVOList;
    }
}
