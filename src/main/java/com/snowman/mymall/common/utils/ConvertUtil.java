package com.snowman.mymall.common.utils;

import java.math.BigDecimal;
import java.util.Date;

public class ConvertUtil {

    public static String toString(Object obj){
        if(null == obj){
            return "";
        }
        return (String)obj;
    }

    public static Date toDate(Object obj){
        if(null == obj){
            return null;
        }
        return (Date)obj;
    }

    public static Integer toInteger(Object obj){
        if(null == obj){
            return null;
        }
        return (Integer) obj;
    }

    public static Double toDouble(Object obj){
        if(null == obj){
            return null;
        }
        return (Double) obj;
    }

    public static BigDecimal toBigDecimal(Object obj){
        if(null == obj){
            return null;
        }
        BigDecimal b = new BigDecimal(obj.toString());
        return b;
    }


}