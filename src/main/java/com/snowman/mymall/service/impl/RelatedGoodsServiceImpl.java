package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.RelatedGoodsEntity;
import com.snowman.mymall.repository.RelatedGoodsRepository;
import com.snowman.mymall.service.RelatedGoodsService;
import com.snowman.mymall.vo.RelatedGoodsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 17:47
 * @Version 1.0
 **/
@Service
public class RelatedGoodsServiceImpl implements RelatedGoodsService {

    @Autowired
    private RelatedGoodsRepository relatedGoodsRepository;

    /**
     * 查所有
     *
     * @return
     */
    @Override
    public List<RelatedGoodsVO> queryRelatedGoods() {
        List<RelatedGoodsEntity> entityList = relatedGoodsRepository.queryRelatedGoods();
        List<RelatedGoodsVO> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return voList;
        }
        BeanCopier copier = BeanCopier.create(RelatedGoodsEntity.class, RelatedGoodsVO.class, false);
        for (RelatedGoodsEntity entity : entityList) {
            RelatedGoodsVO vo = new RelatedGoodsVO();
            copier.copy(entity, vo, null);
            voList.add(vo);
        }
        return voList;
    }
}
