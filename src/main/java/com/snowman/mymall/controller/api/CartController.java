package com.snowman.mymall.controller.api;

import com.alibaba.fastjson.JSON;
import com.snowman.mymall.common.annotation.LoginUser;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.service.CartService;
import com.snowman.mymall.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 9:54
 * @Version 1.0
 **/
@Api(tags = "购物车")
@RestController
@RequestMapping("/api/cart")
public class CartController {

    private Logger logger = LoggerFactory.getLogger(CartController.class);

    @Autowired
    private CartService cartService;

    /**
     * 获取购物车商品的总件件数
     *
     * @param loginUser
     * @return
     */
    @ApiOperation(value = "获取购物车商品的总件件数")
    @PostMapping("/goodscount")
    public Object goodsCount(@LoginUser UserVO loginUser) {
        logger.info("获取购物车商品的总件件数controller开始,loginUser:{}", JSON.toJSONString(loginUser));
        if (null == loginUser || null == loginUser.getUserId()) {
            return Result.error("未登录");
        }
        Result result;
        try {
            Map<String, Object> resultObj = cartService.goodsCount(loginUser);
            result = Result.ok(resultObj);
        } catch (Exception e) {
            logger.error("获取购物车商品的总件件数controller异常:{}", e);
            return Result.error(e.toString());
        }
        logger.info("获取购物车商品的总件件数controller结束,{}", JSON.toJSONString(result));
        return result;
    }

    /**
     * 添加商品到购物车
     */
    @ApiOperation(value = "添加商品到购物车")
    @PostMapping("add")
    public Object add(@LoginUser UserVO loginUser, Integer goodsId, Integer productId, Integer number) {
        logger.info("添加商品到购物车controller开始;goodsId:{},productId:{},number:{},loginUser:{}",
                goodsId, productId, number, JSON.toJSONString(loginUser));
        if (null == goodsId  || null == productId || null == number
                || null == loginUser || null == loginUser.getUserId()) {
            logger.error("添加商品到购物车controller参数异常");
            return Result.error("参数异常");
        }
        Result result;
        try{
           Map<String,Object> returnObj =  cartService.add(loginUser,goodsId,productId,number);
           result = Result.ok(returnObj);
        }catch (Exception e){
            logger.error("添加商品到购物车controller异常:{}",e);
            return Result.error("添加异常");
        }
        logger.info("添加商品到购物车controlle结束,{}",JSON.toJSONString(result));
        return result;
    }

    /**
     * 获取购物车中的数据
     */
    @ApiOperation(value = "获取购物车中的数据")
    @PostMapping("/getCart")
    public Result getCart(@LoginUser UserVO loginUser) {
        if (null == loginUser || null == loginUser.getUserId()) {
            logger.error("获取购物车中的数据controller参数异常");
            return Result.error("参数异常");
        }
        Result result;
        try{
            Map<String,Object> returnObj =  cartService.getCart(loginUser);
            result = Result.ok(returnObj);
        }catch (Exception e){
            logger.error("获取购物车中的数据controller异常:{}",e);
            return Result.error("获取购物车中数据异常");
        }
        logger.info("获取购物车中的数据controller结束,{}",JSON.toJSONString(result));
        return result;
    }
}
