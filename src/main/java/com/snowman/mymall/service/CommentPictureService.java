package com.snowman.mymall.service;

import com.snowman.mymall.vo.CommentPictureVO;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 13:47
 * @Version 1.0
 **/
public interface CommentPictureService {

    public List<CommentPictureVO> queryByComementId(Integer commentId);
}
