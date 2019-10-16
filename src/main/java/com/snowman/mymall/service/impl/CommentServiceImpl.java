package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.CommentEntity;
import com.snowman.mymall.repository.CommentRepository;
import com.snowman.mymall.service.CommentService;
import com.snowman.mymall.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:57
 * @Version 1.0
 **/
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public int queryTotal(Integer typeId, Integer valudId) {
        return commentRepository.queryTotal(typeId, valudId);
    }

    /**
     * @param typeId
     * @param valudId
     * @return
     */
    @Override
    public List<CommentVO> queryByTypeIdAndValueId(Integer typeId, Integer valudId) {
        List<CommentEntity> entityList = commentRepository.queryByTypeIdAndValueId(typeId, valudId);
        List<CommentVO> voList = new ArrayList<>();
        if (CollectionUtils.isEmpty(entityList)) {
            return voList;
        }
        BeanCopier copier = BeanCopier.create(CommentEntity.class, CommentVO.class, false);
        for (CommentEntity entity : entityList) {
            CommentVO vo = new CommentVO();
            copier.copy(entity, vo, null);
            voList.add(vo);
        }
        return voList;
    }
}
