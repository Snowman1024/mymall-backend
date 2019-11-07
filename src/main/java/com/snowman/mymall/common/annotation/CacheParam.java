package com.snowman.mymall.common.annotation;

import java.lang.annotation.*;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/11/7 10:15
 * @Version 1.0
 **/
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CacheParam {
    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}
