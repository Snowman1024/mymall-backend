package com.snowman.mymall.annotation;

import java.lang.annotation.*;

/**
 * @Description 忽略Token验证
 * @Author Snowman2014
 * @Date 2019/10/8 14:56
 * @Version 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {
}
