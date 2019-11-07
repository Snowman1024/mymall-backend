package com.snowman.mymall.service.impl;

import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.entity.CommentEntity;
import com.snowman.mymall.repository.CommentRepository;
import com.snowman.mymall.service.CommentService;
import com.snowman.mymall.vo.CommentVO;
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

    /**
     * 有图的个数
     * @param typeId
     * @param valudId
     * @return
     */
    @Override
    public int queryHasPicTotal(Integer typeId, Integer valudId) {
        return commentRepository.queryHasPicTotal(typeId, valudId);
    }

    /**
     * 查询评论列表
     * @param commentVO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Result list(CommentVO commentVO, Integer pageNum, Integer pageSize){


        return null;
//        Map<String, Object> resultObj = new HashMap();
//        List<CommentVo> commentList = new ArrayList();
//        Map param = new HashMap();
//        param.put("type_id", typeId);
//        param.put("value_id", valueId);
//        param.put("page", page);
//        param.put("limit", size);
//        if (StringUtils.isNullOrEmpty(sort)) {
//            param.put("order", "desc");
//        } else {
//            param.put("order", sort);
//        }
//        if (StringUtils.isNullOrEmpty(order)) {
//            param.put("sidx", "id");
//        } else {
//            param.put("sidx", order);
//        }
//        if (null != showType && showType == 1) {
//            param.put("hasPic", 1);
//        }
//        //查询列表数据
//        Query query = new Query(param);
//        commentList = commentService.queryList(query);
//        int total = commentService.queryTotal(query);
//        ApiPageUtils pageUtil = new ApiPageUtils(commentList, total, query.getLimit(), query.getPage());
//        //
//        for (CommentVo commentItem : commentList) {
//            commentItem.setContent(Base64Util.decode(commentItem.getContent()));
//            commentItem.setUser_info(userService.queryObject(commentItem.getUser_id()));
//
//            Map paramPicture = new HashMap();
//            paramPicture.put("comment_id", commentItem.getId());
//            List<CommentPictureVo> commentPictureEntities = commentPictureService.queryList(paramPicture);
//            commentItem.setPic_list(commentPictureEntities);
//        }
//        return toResponsSuccess(pageUtil);
    }
}
