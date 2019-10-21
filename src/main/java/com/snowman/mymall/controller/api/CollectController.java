package com.snowman.mymall.controller.api;

import com.alibaba.fastjson.JSON;
import com.snowman.mymall.common.annotation.LoginUser;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.service.CollectService;
import com.snowman.mymall.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 11:25
 * @Version 1.0
 **/
@Api(tags = "用户收藏")
@RestController
@RequestMapping("/api/collect")
public class CollectController {

    private Logger logger = LoggerFactory.getLogger(CollectController.class);

    @Autowired
    private CollectService collectService;

    /**
     * 获取用户收藏
     */
    @ApiOperation(value = "添加取消收藏")
    @PostMapping("/addordelete")
    public Result addOrDelete(@LoginUser UserVO loginUser, Integer typeId, Integer valueId) {
        logger.info("添加取消收藏controller开始,typeId:{},valueId:{},loginUser:{}",
                typeId, valueId, JSON.toJSONString(loginUser));

        if (null == typeId || null == valueId) {
            logger.error("添加取消收藏controller参数异常");
            return Result.error("参数异常");
        }

        Result result;
        try {
            Map<String, Object> returnObj = collectService.addOrDelete(loginUser, typeId, valueId);
            result = Result.ok(returnObj);
        } catch (Exception e) {
            logger.error("添加取消收藏controller异常:{}", e);
            return Result.error("操作失败");
        }
        logger.info("添加取消收藏controller结束,{}", result);
        return result;
    }
}
