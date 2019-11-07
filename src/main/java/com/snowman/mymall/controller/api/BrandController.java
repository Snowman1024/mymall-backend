package com.snowman.mymall.controller.api;

import com.alibaba.fastjson.JSON;
import com.snowman.mymall.common.annotation.IgnoreAuth;
import com.snowman.mymall.common.utils.Page;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.service.BrandService;
import com.snowman.mymall.vo.BrandVO;
import io.swagger.annotations.Api;
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
 * @Date 2019/10/22 16:29
 * @Version 1.0
 **/
@Api(tags = "品牌")
@RestController
@RequestMapping("/api/brand")
public class BrandController {

    private Logger logger = LoggerFactory.getLogger(BrandController.class);

    @Autowired
    private BrandService brandService;

    /**
     * 获取品牌分页
     */
    @ApiOperation(value = "获取品牌分页")
    @IgnoreAuth
    @PostMapping("/list")
    public Result list(@RequestParam(value = "page", defaultValue = "1") Integer pageNum,
                       @RequestParam(value = "size", defaultValue = "10") Integer pageSize) {
        logger.info("获取品牌分页controller开始");

        Result result;
        try {
            Page page = brandService.list(pageNum, pageSize);
            result = Result.ok(page);
        } catch (Exception e) {
            logger.error("获取品牌分页controller异常:{}", e);
            return Result.error("获取品牌异常");
        }
        logger.info("获取品牌分页controller结束,{}", JSON.toJSONString(result));
        return result;
    }

    /**
     * 品牌详情
     */
    @ApiOperation(value = "品牌详情")
    @IgnoreAuth
    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        logger.info("品牌详情controller开始,id:{}", id);
        if (null == id) {
            logger.error("品牌详情controller参数异常");
            return Result.error("参数异常");
        }
        Result result;
        try {
            BrandVO vo = brandService.queryById(id);
            result = Result.ok(vo);
        } catch (Exception e) {
            logger.error("品牌详情controller异常:{}", e);
            return Result.error("查询品牌详情异常");
        }
        logger.info("品牌详情controller结束,{}", JSON.toJSONString(result));
        return result;

    }
}
