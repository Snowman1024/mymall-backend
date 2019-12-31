package com.snowman.mymall.controller.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.snowman.mymall.common.annotation.LoginUser;
import com.snowman.mymall.common.token.JwtTokenUtil;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.common.config.HostInfo;
import com.snowman.mymall.entity.TokenEntity;
import com.snowman.mymall.common.interceptor.AuthorizationInterceptor;
import com.snowman.mymall.service.GoodsService;
import com.snowman.mymall.service.TokenService;
import com.snowman.mymall.vo.GoodsVO;
import com.snowman.mymall.vo.UserVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    private TokenService tokenService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private HostInfo hostInfo;

    /**
     * 　　人气推荐
     */
    @ApiOperation(value = "人气推荐")
    @PostMapping(value = "/hot")
    public Result hot() {
        Map<String, Object> resultObj = new HashMap();
        Map bannerInfo = new HashMap();
        bannerInfo.put("url", "");
        bannerInfo.put("name", "大家都在买的严选好物");
        bannerInfo.put("imgUrl", hostInfo.getUrl() + "/image/goods/goodshot.png");
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

    /**
     * 商品详情
     */
    @ApiOperation(value = " 商品详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "id", value = "商品id", paramType = "path", required = true),
            @ApiImplicitParam(name = "referrer", value = "商品referrer", paramType = "path", required = false)})
    @PostMapping(value = "detail")
    public Result detail(Integer id, Integer referrer, HttpServletRequest request) {
        logger.info("商品详情controller开始,id:{},referrer:{}", id, referrer);
        if (null == id) {
            logger.error("商品详情controller参数异常");
            return Result.error("参数异常");
        }
        Result result;
        try {
            Integer userId = getUserId(request);
            Map<String, Object> resultObj = goodsService.detail(userId, id, referrer);
            result = Result.ok(resultObj);
        } catch (Exception e) {
            logger.error("商品详情controller异常:{}", e);
            return Result.error(e.toString());
        }
        logger.info("商品详情controller结束:{}",
                JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));
        return result;


    }

    /**
     * 获取请求的用户Id
     *
     * @return 客户端Ip
     */
    public Integer getUserId(HttpServletRequest request) {
        String token = request.getHeader(AuthorizationInterceptor.LOGIN_TOKEN_KEY);
        if (StringUtils.isBlank(token)) {
            return null;
        }
        //查询token信息
        TokenEntity tokenEntity = tokenService.queryByToken(token);
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            return null;
        }
        Boolean validateFlag = jwtTokenUtil.validateToken(token, tokenEntity);
        if (!validateFlag) {
            return null;
        }
        return tokenEntity.getUserId();
    }


    /**
     * 商品详情页的大家都在看的商品
     */
    @ApiOperation(value = "商品详情页")
    @PostMapping(value = "/related")
    public Result related(Integer id) {
        logger.info("商品详情页大家都在看的商品controller开始,id:{}", id);
        if (null == id) {
            logger.error("商品详情页大家都在看的商品controller参数异常");
            return Result.error("参数异常");
        }
        Result result;
        try {
            Map<String, Object> resultObj = goodsService.related(id);
            result = Result.ok(resultObj);
        } catch (Exception e) {
            logger.error("商品详情页大家都在看的商品controller异常:{}", e);
            return Result.error(e.toString());
        }
        logger.info("商品详情页大家都在看的商品controller结束:{}",
                JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect));
        return result;
    }
}
