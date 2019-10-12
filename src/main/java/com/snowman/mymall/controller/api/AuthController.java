package com.snowman.mymall.controller.api;

import com.alibaba.fastjson.JSON;
import com.snowman.mymall.common.annotation.IgnoreAuth;
import com.snowman.mymall.common.utils.IpUtil;
import com.snowman.mymall.common.utils.Result;
import com.snowman.mymall.common.vo.LoginVO;
import com.snowman.mymall.service.AuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 17:33
 * @Version 1.0
 **/
@Api(tags = "登录授权接口")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    /**
     * 通过手机，密码登录
     *
     * @param mobile
     * @param password
     * @return
     */
    @ApiOperation(value = "通过手机，密码登录")
    @IgnoreAuth
    @PostMapping("/login")
    public Result login(String mobile, String password) {
        logger.info("通过手机，密码登录controller开始,mobile:{},password;{}", mobile, password);
        if (StringUtils.isBlank(mobile) || StringUtils.isBlank(password)) {
            logger.error("通过手机，密码登录controller-参数异常");
            return Result.error("手机号或密码不能为空");
        }
        Result result;
        try {
            result = authService.login(mobile, password);

        } catch (Exception e) {
            logger.error("通过手机，密码登录controller异常:{}", e);
            return Result.error(e.getMessage());
        }
        logger.info("通过手机，密码登录controller结束:", JSON.toJSONString(result));
        return result;
    }


    /**
     * 微信登录
     *
     * @param loginVO
     * @param request
     * @return
     */
    @ApiOperation(value = "微信登录")
    @IgnoreAuth
    @PostMapping("/login_by_weixin")
    public Result loginByWeixin(LoginVO loginVO, HttpServletRequest request) {
        logger.info("微信登录controller开始,参数:{}", JSON.toJSONString(loginVO));
        if (null == loginVO || null == loginVO.getFullUserInfo()
                || null == loginVO.getFullUserInfo().getUserInfo()) {
            logger.error("微信登录controller参数异常");
            return Result.error("登录失败");
        }
        Result result;
        try {
            result = authService.loginByWeixin(loginVO, IpUtil.getClientIp(request));
        } catch (Exception e) {
            logger.error("微信登录controller异常:{}", e);
            return Result.error(e.toString());
        }
        logger.info("微信登录controller结束:", JSON.toJSONString(result));
        return result;
    }

    /**
     * 支付宝登录
     *
     * @param loginVO
     * @param httpServletRequest
     * @return
     */
    @ApiOperation(value = "支付宝登录")
    @IgnoreAuth
    @PostMapping("/login_by_ali")
    public Object login_by_ali(LoginVO loginVO, HttpServletRequest httpServletRequest) {
        logger.info("支付宝登录controller开始:", JSON.toJSONString(loginVO));
        if (null == loginVO) {
            logger.error("支付宝登录controller参数异常");
            return Result.error("登录失败");
        }
        Result result;
        try {
            result = authService.loginByAli(loginVO, IpUtil.getClientIp(httpServletRequest));
        } catch (Exception e) {
            logger.error("支付宝登录controller异常:{}", e);
            return Result.error(e.toString());
        }
        return result;
    }
}
