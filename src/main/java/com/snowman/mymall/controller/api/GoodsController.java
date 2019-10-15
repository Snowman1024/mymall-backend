package com.snowman.mymall.controller.api;

import com.alibaba.fastjson.JSON;
import com.snowman.mymall.common.Constant;
import com.snowman.mymall.common.annotation.IgnoreAuth;
import com.snowman.mymall.common.annotation.LoginUser;
import com.snowman.mymall.common.utils.IpUtil;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.service.GoodsService;
import com.snowman.mymall.service.SearchHistoryService;
import com.snowman.mymall.vo.GoodsVO;
import com.snowman.mymall.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/12 16:39
 * @Version 1.0
 **/
@Api(tags = "商品")
@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    private Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private GoodsService goodsService;

    /**
     * 　　人气推荐
     */
    @ApiOperation(value = "人气推荐")
    @IgnoreAuth
    @PostMapping(value = "/hot")
    public Result hot() {
        Map<String, Object> resultObj = new HashMap();
        Map bannerInfo = new HashMap();
        bannerInfo.put("url", "");
        bannerInfo.put("name", "大家都在买的严选好物");
        bannerInfo.put("imgUrl", Constant.HOST_URL + "/image/goodshot.png");
        resultObj.put("bannerInfo", bannerInfo);
        return Result.ok(resultObj);
    }


    /**
     * 获取商品列表
     *
     * @param loginUser
     * @param goodsVO
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取商品列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "categoryId", value = "分类id", paramType = "path", required = true),
            @ApiImplicitParam(name = "brandId", value = "品牌Id", paramType = "path", required = true),
            @ApiImplicitParam(name = "isNew", value = "新商品", paramType = "path", required = true),
            @ApiImplicitParam(name = "isHot", value = "热卖商品", paramType = "path", required = true)})
    @IgnoreAuth
    @PostMapping(value = "/list")
    public Object list(@LoginUser UserVO loginUser, GoodsVO goodsVO,
                       @RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        logger.info("获取商品列表controller开始,loginUser:{},goodsVO:{},pageNum:{},pageSize:{}",
                JSON.toJSONString(loginUser), JSON.toJSONString(goodsVO), pageNum, pageSize);
        if (null == goodsVO) {
            logger.error("获取商品列表controller参数异常");
            return Result.error("参数异常");
        }
        Result result;
        try {
            result = goodsService.list(loginUser, goodsVO, pageNum, pageSize);

        } catch (Exception e) {
            logger.error("获取商品列表controller异常:{}", e);
            return Result.error(e.toString());
        }
        logger.info("获取商品列表controller结束");
        return result;
    }

}
