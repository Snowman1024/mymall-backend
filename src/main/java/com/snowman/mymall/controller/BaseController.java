package com.snowman.mymall.controller;

import com.alibaba.fastjson.JSONObject;
import com.snowman.mymall.entity.TokenEntity;
import com.snowman.mymall.interceptor.AuthorizationInterceptor;
import com.snowman.mymall.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/8 16:54
 * @Version 1.0
 **/
public class BaseController {

    protected Logger logger = LoggerFactory.getLogger(BaseController.class);

    /**
     * 得到request对象
     */
    @Autowired
    protected HttpServletRequest request;
    /**
     * 得到response对象
     */
    @Autowired
    protected HttpServletResponse response;

    @Autowired
    protected TokenService tokenService;

    /**
     * 参数绑定异常
     */
//    @ExceptionHandler({BindException.class, MissingServletRequestParameterException.class,
//            UnauthorizedException.class, TypeMismatchException.class})
//    @ResponseBody
//    public Map<String, Object> bindException(Exception e) {
//        if (e instanceof BindException) {
//            return toResponsObject(1, "参数绑定异常", e.getMessage());
//        } else if (e instanceof UnauthorizedException) {
//            return toResponsObject(1, "无访问权限", e.getMessage());
//        }
//        return toResponsObject(1, "处理异常", e.getMessage());
//    }

    /**
     * 构建统一格式返回对象
     *
     * @param requestCode
     * @param msg
     * @param data
     * @return
     */
    public Map<String, Object> toResponsObject(int requestCode, String msg, Object data) {
        Map<String, Object> obj = new HashMap<String, Object>();
        obj.put("errno", requestCode);
        obj.put("errmsg", msg);
        if (data != null) {
            obj.put("data", data);
        }
        return obj;
    }

    public Map<String, Object> toResponsSuccess(Object data) {
        Map<String, Object> rp = toResponsObject(0, "执行成功", data);
        logger.info("response:" + rp);
        return rp;
    }

    public Map<String, Object> toResponsMsgSuccess(String msg) {
        return toResponsObject(0, msg, "");
    }

    public Map<String, Object> toResponsSuccessForSelect(Object data) {
        Map<String, Object> result = new HashMap<>(2);
        result.put("list", data);
        return toResponsObject(0, "执行成功", result);
    }

    public Map<String, Object> toResponsFail(String msg) {
        return toResponsObject(1, msg, null);
    }

    /**
     * initBinder 初始化绑定 <br>
     *
     * @param binder  WebDataBinder 要注册的binder
     * @param request 前端请求
     */
    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        // 字符串自动Trim
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }

    /**
     * 获取请求方IP
     *
     * @return 客户端Ip
     */
    public String getClientIp() {
        String xff = request.getHeader("X-Real-IP");
        if (xff != null) {
            return xff;
        }
        xff = request.getHeader("x-forwarded-for");
        if (xff == null) {
            return "8.8.8.8";
        }
        return xff;
    }

    public JSONObject getJsonRequest() {
        JSONObject result = null;
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader();) {
            char[] buff = new char[1024];
            int len;
            while ((len = reader.read(buff)) != -1) {
                sb.append(buff, 0, len);
            }
            result = JSONObject.parseObject(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * 获取请求的用户Id
     *
     * @return 客户端Ip
     */
    public Integer getUserId() {
        String token = request.getHeader(AuthorizationInterceptor.LOGIN_TOKEN_KEY);
        //查询token信息
        TokenEntity tokenEntity = tokenService.queryByToken(token);
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
            return null;
        }
        return tokenEntity.getUserId();
    }
}
