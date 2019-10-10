package com.snowman.mymall.controller.api;

import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    protected Logger logger = LoggerFactory.getLogger(getClass());

}
