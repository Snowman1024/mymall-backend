package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.utils.Page;
import com.snowman.mymall.entity.BrandEntity;
import com.snowman.mymall.repository.BrandRepository;
import com.snowman.mymall.service.BrandService;
import com.snowman.mymall.vo.BrandVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        BrandVO vo = toVo(entity);
        return vo;
    }

    /**
     * 获取品牌分页
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page list(Integer pageNum, Integer pageSize) {
        Page page = null;
        Map<String, Object> params = new HashMap<>();

        String countJsql = "select count(1) from BrandEntity b where b.isShow=1";
        List<BrandEntity> countObj = brandRepository.exeQueryCustNameParm(countJsql, params);

        if (CollectionUtils.isEmpty(countObj)) {
            return page;
        }
        Long totalCount = (Long) countObj.toArray()[0];
        if (null != totalCount && totalCount > 0) {
            String jsql = "select b from BrandEntity b where b.isShow=1 order by b.id asc";
            List<BrandEntity> brandEntityList = brandRepository.exeQueryCustNameParm(jsql, params, pageNum, pageSize);
            List<BrandVO> voList = toVoList(brandEntityList);
            page = new Page(voList, totalCount.intValue(), pageSize, pageNum);
        }
        return page;
    }



    private List<BrandVO> toVoList(List<BrandEntity> entitieList) {
        List<BrandVO> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(entitieList)) {
            return voList;
        }
        BeanCopier copier = BeanCopier.create(BrandEntity.class, BrandVO.class, false);
        for (BrandEntity entity : entitieList) {
            BrandVO vo = new BrandVO();
            copier.copy(entity, vo, null);
            voList.add(vo);
        }
        return voList;
    }

    private BrandVO toVo(BrandEntity entity) {
        BrandVO vo = new BrandVO();
        if (null == entity) {
            return vo;
        }
        BeanCopier copier = BeanCopier.create(BrandEntity.class, BrandVO.class, false);
        copier.copy(entity, vo, null);
        return vo;

    }
}
