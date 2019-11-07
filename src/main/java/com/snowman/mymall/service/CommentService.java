package com.snowman.mymall.service;

import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.vo.CommentVO;

import java.util.List;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/16 10:57
 * @Version 1.0
 **/
public interface CommentService {

    public int queryTotal(Integer typeId,Integer valudId);

    public List<CommentVO> queryByTypeIdAndValueId(Integer typeId, Integer valudId);

    public int queryHasPicTotal(Integer typeId, Integer valudId);

    public Result list(CommentVO commentVO, Integer pageNum, Integer pageSize);
}
