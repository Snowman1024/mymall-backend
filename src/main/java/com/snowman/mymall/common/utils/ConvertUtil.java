package com.snowman.mymall.common.utils;

import java.math.BigDecimal;
import java.util.Date;

public class ConvertUtil {

    public ConvertUtil() {
    }

    public static String toString(Object obj){
        if(null == obj){
            return "";
        }
        return obj.toString();
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
        return Integer.valueOf(obj.toString());
    }

    public static Double toDouble(Object obj){
        if(null == obj){
            return null;
        }
        return Double.valueOf(obj.toString());
    }

    public static BigDecimal toBigDecimal(Object obj){
        if(null == obj){
            return null;
        }
        BigDecimal b = new BigDecimal(obj.toString());
        return b;
    }



}
