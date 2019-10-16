package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.utils.ConvertUtil;
import com.snowman.mymall.repository.AttributeRepository;
import com.snowman.mymall.service.AttributeService;
import com.snowman.mymall.vo.AttributeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 15:20
 * @Version 1.0
 **/
@Service
public class AttributeServiceImpl implements AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    /**
     * 通过商品id查
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<AttributeVO> queryByGoodsId(Integer goodsId) {
        List<Object> attributeObjList = attributeRepository.queryByGoodsId(goodsId);
        List<AttributeVO> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(attributeObjList)) {
            return voList;
        }
        Object[] attributeObjArr = attributeObjList.toArray();

        for (int i = 0; i < attributeObjArr.length; i++) {
            Object[] item = (Object[]) attributeObjArr[i];

            AttributeVO vo = new AttributeVO();
            vo.setId(ConvertUtil.toInteger(item[0]));
            vo.setAttributeCategoryId(ConvertUtil.toInteger(item[1]));
            vo.setName(ConvertUtil.toString(item[2]));
            vo.setInputType(ConvertUtil.toInteger(item[3]));
            //被覆盖了
            vo.setValue(ConvertUtil.toString(item[4]));
            vo.setSortOrder(ConvertUtil.toInteger(item[5]));
            vo.setValue(ConvertUtil.toString(item[6]));
            voList.add(vo);
        }
        return voList;
    }
}
