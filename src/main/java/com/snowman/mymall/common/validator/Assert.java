package com.snowman.mymall.common.validator;

import com.snowman.mymall.common.exception.ServiceException;
import org.apache.commons.lang3.StringUtils;

/**
 * @Description 数据校验
 * @Author Snowman2014
 * @Date 2019/10/10 9:08
 * @Version 1.0
 **/
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new ServiceException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new ServiceException(message);
        }
    }

}
