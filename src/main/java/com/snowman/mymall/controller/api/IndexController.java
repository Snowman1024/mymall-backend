package com.snowman.mymall.controller.api;

import com.alibaba.fastjson.JSON;
import com.snowman.mymall.common.Constant;
import com.snowman.mymall.common.annotation.IgnoreAuth;
import com.snowman.mymall.common.redis.RedisService;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.vo.BannerVO;
import com.snowman.mymall.vo.GoodsVO;
import com.snowman.mymall.service.BannerService;
import com.snowman.mymall.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/11 11:23
 * @Version 1.0
 **/
@Api(tags = "首页接口文档")
@RestController
@RequestMapping("/api/index")
public class IndexController {

    private final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private BannerService bannerService;




    /**
     * 测试
     */
    @IgnoreAuth
    @PostMapping("/test")
    public Result test() {
        return Result.ok("请求成功yyy");
    }


    /**
     * app首页
     */
    @ApiOperation(value = "新商品信息")
    @PostMapping(value = "/newGoods")
    public Result newGoods() {
        logger.info("首页查询新商品信息controller开始");
        Result result;
        try {

            List<GoodsVO> newGoodsList = (List<GoodsVO>) redisService.getValue(Constant.NEW_GOODS_KEY);

            if (CollectionUtils.isEmpty(newGoodsList)) {
                newGoodsList = goodsService.queryNewGoodsList();
                redisService.setValue(Constant.NEW_GOODS_KEY, newGoodsList, Constant.CACHE_VALID_TIME * 1000);
            }

            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("newGoodsList", newGoodsList);
            result = Result.ok(resultObj);

        } catch (Exception e) {
            logger.error("首页查询新商品信息controller异常:{}", e);
            return Result.error(e.toString());
        }
        logger.info("首页查询新商品信息controller结束,{}", JSON.toJSONString(result));
        return result;
    }

    /**
     * 首页查询banner信息
     * @return
     */
    @ApiOperation(value = "banner")
    @PostMapping(value = "/banner")
    public Object banner() {
        logger.info("首页查询banner信息controller开始");
        Result result;
        try {
            List<BannerVO> bannerVOList = (List<BannerVO>) redisService.getValue(Constant.BANNER_KEY);

            if (CollectionUtils.isEmpty(bannerVOList)) {
                bannerVOList = bannerService.queryBannerList();
                redisService.setValue(Constant.BANNER_KEY, bannerVOList,Constant.CACHE_VALID_TIME * 1000);
            }

            Map<String, Object> resultObj = new HashMap<>();
            resultObj.put("banner", bannerVOList);
            result = Result.ok(resultObj);

        } catch (Exception e) {
            logger.error("首页查询banner信息controller异常:{}", e);
            return Result.error(e.toString());
        }
        logger.info("首页查询banner信息controller结束,{}",JSON.toJSONString(result));
        return result;
    }
}
