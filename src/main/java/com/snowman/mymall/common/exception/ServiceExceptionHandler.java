package com.snowman.mymall.common.exception;

import com.snowman.mymall.common.utils.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/**
 * @Description
 * @Author Snowman2014
 * @Date 2019/10/17 16:52
 * @Version 1.0
 **/
@RestControllerAdvice(value = {"com.snowman.mymall"})
public class ServiceExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    /**
     * 自定义异常
     */
    @ExceptionHandler(ServiceException.class)
    public Result handleServiceException(ServiceException e) {
        Result r = new Result();
        r.put("code", e.getCode());
        r.put("msg", e.getMessage());
        logger.error(e.toString());
        return r;
    }

    @ExceptionHandler(ApiServiceException.class)
    public Object handleApiRRException(ApiServiceException e) {
        Result r = new Result();
        r.put("code", e.getErrno());
        r.put("msg", e.getMessage());
        logger.error(e.toString());
        return r;
    }
}
