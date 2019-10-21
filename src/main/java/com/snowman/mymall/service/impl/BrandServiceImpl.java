package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.BrandEntity;
import com.snowman.mymall.repository.BrandRepository;
import com.snowman.mymall.service.BrandService;
import com.snowman.mymall.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:26
 * @Version 1.0
 **/
@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    /**
     * 通过主键查品牌
     *
     * @param id
     * @return
     */
    @Override
    public BrandVO queryById(Integer id) {
        BrandEntity entity = brandRepository.queryById(id);
        BrandVO vo = new BrandVO();
        if (null == entity) {
            return vo;
        }
        BeanCopier copier = BeanCopier.create(BrandEntity.class, BrandVO.class, false);
        copier.copy(entity, vo, null);
        return vo;
    }
}
