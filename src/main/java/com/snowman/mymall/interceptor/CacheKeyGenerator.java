package com.snowman.mymall.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * @Description key生成器
 * @Author Snowman2014
 * @Date 2019/11/7 10:17
 * @Version 1.0
 **/
public interface CacheKeyGenerator {

    /**
     * 获取AOP参数,生成指定缓存Key
     *
     * @param pjp PJP
     * @return 缓存KEY
     */
    String getLockKey(ProceedingJoinPoint pjp);
}
