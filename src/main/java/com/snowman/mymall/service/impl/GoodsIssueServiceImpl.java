package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.GoodsIssueEntity;
import com.snowman.mymall.repository.GoodsIssueRepository;
import com.snowman.mymall.service.GoodsIssueService;
import com.snowman.mymall.vo.GoodsIssueVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:06
 * @Version 1.0
 **/
@Service
public class GoodsIssueServiceImpl implements GoodsIssueService {

    @Autowired
    private GoodsIssueRepository goodsIssueRepository;

    /**
     * @return
     */
    @Override
    public List<GoodsIssueVO> queryGoodsIssueList() {
        List<GoodsIssueEntity> entityList = goodsIssueRepository.queryGoodsIssueList();
        List<GoodsIssueVO> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return voList;
        }
        BeanCopier copier = BeanCopier.create(GoodsIssueEntity.class, GoodsIssueVO.class, false);
        for (GoodsIssueEntity entity : entityList) {
            GoodsIssueVO vo = new GoodsIssueVO();
            copier.copy(entity, vo, null);
            voList.add(vo);
        }
        return voList;
    }
}
