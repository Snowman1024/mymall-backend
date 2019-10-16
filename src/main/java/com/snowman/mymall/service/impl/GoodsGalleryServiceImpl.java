package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.GoodsGalleryEntity;
import com.snowman.mymall.repository.GoodsGalleryRepository;
import com.snowman.mymall.service.GoodsGalleryService;
import com.snowman.mymall.vo.GoodsGalleryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/15 14:50
 * @Version 1.0
 **/
@Service
public class GoodsGalleryServiceImpl implements GoodsGalleryService {

    @Autowired
    private GoodsGalleryRepository goodsGalleryRepository;

    /**
     * 通过商品id查询
     *
     * @param goodsId
     * @return
     */
    @Override
    public List<GoodsGalleryVO> queryByGoodsId(Integer goodsId) {
        List<GoodsGalleryEntity> entityList = goodsGalleryRepository.queryByGoodsId(goodsId);
        List<GoodsGalleryVO> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return voList;
        }
        BeanCopier copier = BeanCopier.create(GoodsGalleryEntity.class, GoodsGalleryVO.class, false);
        for (GoodsGalleryEntity entity : entityList) {
            GoodsGalleryVO vo = new GoodsGalleryVO();
            copier.copy(entity, vo, null);
            voList.add(vo);
        }
        return voList;
    }
}
