package com.snowman.mymall.common.exception;

import lombok.Data;

/**
 * @Description 自定义异常
 * @Author Snowman2014
 * @Date 2019/10/10 9:07
 * @Version 1.0
 **/
@Data
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = -2297127171445038686L;
    private String msg;
    private int code = 500;

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServiceException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public ServiceException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public ServiceException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
