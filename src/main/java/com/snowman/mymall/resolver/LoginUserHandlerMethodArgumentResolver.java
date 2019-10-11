package com.snowman.mymall.resolver;

import com.snowman.mymall.common.annotation.LoginUser;
import com.snowman.mymall.interceptor.AuthorizationInterceptor;
import com.snowman.mymall.service.UserService;
import com.snowman.mymall.common.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @Description 有@LoginUser注解的方法参数，注入当前登录用户
 * @Author Snowman2014
 * @Date 2019/10/8 15:36
 * @Version 1.0
 **/
@Component
public class LoginUserHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().isAssignableFrom(UserVO.class)
                && parameter.hasParameterAnnotation(LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest,
                                  WebDataBinderFactory webDataBinderFactory) throws Exception {
        //获取用户ID
        Object object = nativeWebRequest.getAttribute(AuthorizationInterceptor.LOGIN_USER_KEY,
                RequestAttributes.SCOPE_REQUEST);
        if (object == null) {
            return null;
        }
        //获取用户信息
        return userService.queryByUserId((Long) object);
    }
}
