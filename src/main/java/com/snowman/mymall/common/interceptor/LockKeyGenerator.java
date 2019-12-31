package com.snowman.mymall.common.interceptor;

import com.snowman.mymall.common.annotation.CacheLock;
import com.snowman.mymall.common.annotation.CacheParam;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/11/7 10:18
 * @Version 1.0
 **/
public class LockKeyGenerator implements CacheKeyGenerator {


    /**
     * 缓存key的生成规则
     *
     * @param pjp PJP
     * @return
     */
    @Override
    public String getLockKey(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        CacheLock lockAnnotation = method.getAnnotation(CacheLock.class);
        //实参
        final Object[] args = pjp.getArgs();
        //方法参数列表名
        final Parameter[] parameters = method.getParameters();
        StringBuilder builder = new StringBuilder();

        // 默认解析方法里面带 CacheParam 注解的属性,如果没有尝试着解析实体对象中的
        for (int i = 0; i < parameters.length; i++) {
            final CacheParam paramAnnotation = parameters[i].getAnnotation(CacheParam.class);
            if (null == paramAnnotation) {
                continue;
            }
            builder.append(lockAnnotation.delimiter()).append(args[i]);
        }

        if (StringUtils.isBlank(builder.toString())) {
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                final Object object = args[i];
                final Field[] fields = object.getClass().getDeclaredFields();

                for (Field field : fields) {
                    final CacheParam annotation = field.getAnnotation(CacheParam.class);
                    if (null == annotation) {
                        continue;
                    }
                    field.setAccessible(true);
                    builder.append(lockAnnotation.delimiter()).append(ReflectionUtils.getField(field, object));

                }
            }
        }
        return lockAnnotation.prefix() + builder.toString();
    }
}
