package io.afu.aicenterapi.utils;

import io.afu.aicenterapi.consts.sys.CommonConst;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {


    /**
     * 转换时间为String 格式的
     * @param localDateTime 时间
     * @return String的时间
     */
    public static String toString(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        return localDateTime.format(DateTimeFormatter.ofPattern(CommonConst.DATE_TIME_FORMAT));
    }






}
