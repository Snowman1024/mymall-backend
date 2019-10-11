package com.snowman.mymall.controller.api;

import com.snowman.mymall.common.annotation.IgnoreAuth;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.common.vo.GoodsVO;
import com.snowman.mymall.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private GoodsService goodsService;

    /**
     * 测试
     */
    @IgnoreAuth
    @PostMapping("/test")
    public Object test() {
        return Result.ok("请求成功yyy");
    }

    /**
     * app首页
     */
    @ApiOperation(value = "新商品信息")
    @IgnoreAuth
    @PostMapping(value = "/newGoods")
    public Object newGoods() {
        Map<String, Object> resultObj = new HashMap<String, Object>();
        List<GoodsVO> newGoods = goodsService.queryNewGoodsList();
        resultObj.put("newGoodsList", newGoods);
        return Result.ok(resultObj);
    }

//    @ApiOperation(value = "banner")
//    @IgnoreAuth
//    @PostMapping(value = "/banner")
//    public Object banner() {
//        Map<String, Object> resultObj = new HashMap<String, Object>();
//        //
//        Map<String, Object> param = new HashMap<String, Object>();
//        param.put("ad_position_id", 1);
//        List<AdVo> banner = adService.queryList(param);
//        resultObj.put("banner", banner);
//        //
//
//        return toResponsSuccess(resultObj);
//    }
}
