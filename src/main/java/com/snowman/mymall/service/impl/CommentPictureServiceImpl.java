package com.snowman.mymall.service.impl;

import com.snowman.mymall.entity.CommentEntity;
import com.snowman.mymall.entity.CommentPictureEntity;
import com.snowman.mymall.repository.CommentPictureRepository;
import com.snowman.mymall.service.CommentPictureService;
import com.snowman.mymall.vo.CommentPictureVO;
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
 * @Date 2019/10/16 13:48
 * @Version 1.0
 **/
@Service
public class CommentPictureServiceImpl implements CommentPictureService {

    @Autowired
    private CommentPictureRepository commentPictureRepository;

    /**
     * 通过评论表id查询
     * @param commentId
     * @return
     */
    @Override
    public List<CommentPictureVO> queryByComementId(Integer commentId){
      List<CommentPictureEntity> entityList = commentPictureRepository.queryByCommentId(commentId);
        List<CommentPictureVO> voList = new ArrayList<>();
        if(CollectionUtils.isEmpty(entityList)){
            return voList;
        }
        BeanCopier copier = BeanCopier.create(CommentPictureEntity.class, CommentPictureVO.class, false);
        for (CommentPictureEntity entity : entityList) {
            CommentPictureVO vo = new CommentPictureVO();
            copier.copy(entity, vo, null);
            voList.add(vo);
        }
        return voList;
    }
}
