package com.caoyawen.code_search.utils;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * 时间方面的工具类
 */
public class Time {
    public static Date now(){
        final java.util.Date date = new java.util.Date();
        return new Date(date.getTime());
    }

    public static Timestamp nowTimestamp() {
        final java.util.Date date = new java.util.Date();
        return new Timestamp(date.getTime());
    }
}
