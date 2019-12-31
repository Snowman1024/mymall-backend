package com.snowman.mymall.common.interceptor;

import com.snowman.mymall.common.annotation.AccessLimit;
import com.snowman.mymall.common.exception.ServiceException;
import com.snowman.mymall.common.redis.RedisService;
import com.snowman.mymall.common.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @Author guoqf
 * @Date 2019/12/31 11:16
 * @Version 1.0
 **/
@Component
public class AccessLimitInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            AccessLimit accessLimit = handlerMethod.getMethodAnnotation(AccessLimit.class);

            if (null == accessLimit) {
                return true;
            }

            int seconds = accessLimit.seconds();
            int maxCount = accessLimit.maxCount();
            boolean needLogin = accessLimit.needLogin();

            if (needLogin) {
                //判断是否登录
            }

            String ip = IpUtil.getClientIp(request);
            String key = request.getContextPath() + ":" + request.getServletPath() + ":" + ip;

            Integer count = (Integer) redisService.getValue(key);
            if (null == count || count.equals(-1)) {
                redisService.setValue(key, 1, seconds * 1000);
                return true;
            }
            if (count < maxCount) {
                redisService.incr(key);
                return true;
            }
            if (count >= maxCount) {
                //返回 请求过于频繁请稍后再试
                throw new ServiceException("请求过于频繁请稍后再试");
//                return false;
            }
        }

        return true;
    }
}
