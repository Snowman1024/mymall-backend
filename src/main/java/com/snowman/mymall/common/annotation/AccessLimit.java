package com.snowman.mymall.common.annotation;

import java.lang.annotation.*;

/**
 * @Description 可用于方法限流
 * @Author guoqf
 * @Date 2019/12/31 11:05
 * @Version 1.0
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AccessLimit {

    /**
     * 指定秒数
     * @return
     */
    int seconds() default 5;

    /**
     * 最大访问次数
     * @return
     */
    int maxCount() default 100;

    /**
     * 是否需要登录
     * @return
     */
    boolean needLogin() default true;
}
