package com.snowman.mymall.interceptor;

import com.snowman.mymall.common.annotation.CacheLock;
import com.snowman.mymall.common.exception.ServiceException;
import com.snowman.mymall.config.RedisLockHelper;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/11/7 10:37
 * @Version 1.0
 **/
@Configuration
@Aspect
public class LockMethodInterceptor {

    private Logger logger = LoggerFactory.getLogger(LockMethodInterceptor.class);


    private final RedisLockHelper redisLockHelper;
    private final CacheKeyGenerator cacheKeyGenerator;

    @Autowired
    public LockMethodInterceptor(RedisLockHelper redisLockHelper, CacheKeyGenerator cacheKeyGenerator) {
        this.redisLockHelper = redisLockHelper;
        this.cacheKeyGenerator = cacheKeyGenerator;
    }


    @Around("execution(public * *(..)) && @annotation(com.snowman.mymall.common.annotation.CacheLock)")
    public Object interceptor(ProceedingJoinPoint pjp) {

        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lock = method.getAnnotation(CacheLock.class);
        if (StringUtils.isBlank(lock.prefix())) {
            throw new ServiceException("锁的key不能是空");
        }
        final String localKey = cacheKeyGenerator.getLockKey(pjp);
        logger.info("localKey:"+localKey);
        String value = UUID.randomUUID().toString();

        try {
            // 假设上锁成功，但是设置过期时间失效，以后拿到的都是 false
            final boolean success = redisLockHelper.lock(localKey, value, lock.expire(), lock.timeUnit());
            if (!success) {
                throw new ServiceException("重复提交");
            }
            try {
                return pjp.proceed();
            } catch (Throwable throwable) {
                throw new ServiceException("系统异常");
            }

        } finally {
            // 如果演示的话需要注释该代码;实际应该放开
            redisLockHelper.unlock(localKey, value);
        }

    }
}
