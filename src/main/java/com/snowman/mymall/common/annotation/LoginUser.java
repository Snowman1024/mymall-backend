package com.snowman.mymall.common.annotation;

import java.lang.annotation.*;

/**
 * @Description 登录用户信息
 * @Author Snowman2014
 * @Date 2019/10/8 14:57
 * @Version 1.0
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LoginUser {
}
