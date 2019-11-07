package com.snowman.mymall.controller.api;

import com.snowman.mymall.common.Constant;
import com.snowman.mymall.common.annotation.CacheLock;
import com.snowman.mymall.common.annotation.CacheParam;
import com.snowman.mymall.common.annotation.LoginUser;
import com.snowman.mymall.common.redis.RedisService;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.vo.BuyGoodsVO;
import com.snowman.mymall.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 14:18
 * @Version 1.0
 **/
@Api(tags = "商品购买")
@RestController
@RequestMapping("/api/buy")
public class BuyController {

    private Logger logger = LoggerFactory.getLogger(BuyController.class);


    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "商品添加")
    @CacheLock(prefix = "buy")
    @PostMapping("/add")
    public Result addBuy(@CacheParam(name = "loginUser") @LoginUser UserVO loginUser,
                         @CacheParam(name = "goodsId") Integer goodsId,
                         @CacheParam(name = "productId")Integer productId,
                         @CacheParam(name = "number")Integer number) {
        try {
            BuyGoodsVO goodsVo = new BuyGoodsVO();
            goodsVo.setGoodsId(goodsId);
            goodsVo.setProductId(productId);
            goodsVo.setNumber(number);
            redisService.setValue(Constant.SHOP_CACHE_NAME + "_goods" + loginUser.getUserId() + "", goodsVo);
            return Result.ok("添加成功");
        } catch (Exception e) {
            logger.error("商品添加controller失败:{}",e);
            return Result.error("添加失败");
        }
    }
}
