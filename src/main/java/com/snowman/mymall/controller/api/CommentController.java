package com.snowman.mymall.controller.api;

import com.alibaba.fastjson.JSON;
import com.snowman.mymall.common.annotation.IgnoreAuth;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.service.CommentService;
import com.snowman.mymall.vo.CommentVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/11/6 17:13
 * @Version 1.0
 **/
@Api(tags = "评论")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private CommentService commentService;

    /**
     * 查询评论数量
     *
     * @param typeId
     * @param valueId
     * @return
     */
    @ApiOperation(value = "评论数量")
    @PostMapping("/count")
    public Result count(Integer typeId, Integer valueId) {
        logger.info("查询评论数量controller开始，typeId:{},valueId:{}", typeId, valueId);
        if (null == typeId || null == valueId) {
            logger.error("查询评论数量controller参数异常");
            return Result.error("参数异常");
        }
        Result result;
        try {
            Integer allCount = commentService.queryTotal(typeId, valueId);
            Integer hasPicCount = commentService.queryHasPicTotal(typeId, valueId);

            Map<String, Object> returnObj = new HashMap<>();
            returnObj.put("allCount", allCount);
            returnObj.put("hasPicCount", hasPicCount);

            result = Result.ok(returnObj);
        } catch (Exception e) {
            logger.error("查询评论数量controller异常:{}", e);
            return Result.error("查询评论数量异常");
        }
        logger.info("查询评论数量controller结束，{}", JSON.toJSONString(result));
        return result;
    }


    /**
     * 查询评论列表
     * @param commentVO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "查询评论列表")
    @PostMapping("/list")
    public Object list(CommentVO commentVO,
                       @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        logger.info("查询评论列表controller开始,commentVO:{},pageNum:{},pageSize:{}",
                JSON.toJSONString(commentVO),pageNum,pageSize);
        if(null == commentVO){
            logger.error("查询评论列表controller参数异常");
            return Result.error("参数异常");
        }
        Result result;
        try{
            result = commentService.list(commentVO,pageNum,pageSize);
        }catch (Exception e){
            logger.error("查询评论列表controller异常:{}",e);
            return Result.error("查询评论列表异常");
        }
        return result;

    }
}
