package io.afu.aicenterapi.utils;


import io.afu.aicenterapi.exceptions.BaseException;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class TimeUtils {
    /**
     * 从奇怪的时间格式中解析出时间
     * @param formatedTime 需要解析的时间
     * @return 返回时间对象
     * @throws BaseException 解析抛错
     */
    public static Date parseStrangeTimeFormat(String formatedTime) throws BaseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        Date theDate = null;
        try {
            theDate = simpleDateFormat.parse(formatedTime);
        }catch (Exception e){
            simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                theDate = simpleDateFormat.parse(formatedTime);
            }catch (Exception e1){
                simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                try {
                    theDate = simpleDateFormat.parse(formatedTime);
                }catch (Exception e2){
                    simpleDateFormat = new SimpleDateFormat("yyyyMMddhhmm");
                    try {
                        theDate = simpleDateFormat.parse(formatedTime);
                    }catch (Exception e3){
                        if (formatedTime.length()==13){
                            theDate = parseTimestamp(formatedTime);
                        }else {
                            throw new BaseException(e3);
                        }
                    }
                }
            }
        }
        return theDate;
    }


    /**
     * 解析时间戳
     * @param timestamp 时间戳字符串
     * @return 时间格式
     * @throws BaseException 抛错
     */
    public static Date parseTimestamp(String timestamp) throws BaseException{
        if (null== timestamp || "".equals(timestamp)){
            return null;
        }
        try {
            SimpleDateFormat format =  new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
            Long time= Long.valueOf(timestamp);
            String d = format.format(time);
            return format.parse(d);
        }catch (Exception e){
            throw new BaseException(e);
        }
    }

    /**
     * 按照 yyyy-MM-dd HH:mm:ss 格式解析时间
     * @param datetime 时间字符串
     * @return 时间对象
     * @throws BaseException 解析抛错
     */
    public static Date parseDatetime(String datetime) throws BaseException{
        if (null== datetime || "".equals(datetime)){
            return null;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return format.parse(datetime);
        }catch (Exception e){
            return null;
        }

    }

    /**
     * 按照格式来解析时间
     * @param datetime 时间字符串
     * @param formatStr 时间格式
     * @return 时间对象
     * @throws BaseException 解析时抛错
     */
    public static Date parseDatetime(String datetime,String formatStr) throws BaseException {
        if (null== datetime || "".equals(datetime)){
            return null;
        }
        try {
            SimpleDateFormat format = new SimpleDateFormat(formatStr);
            return format.parse(datetime);
        }catch (Exception e){
            return null;
        }
    }

    public static String toTimestamp(String date) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        Date theDate = format.parse(date);
        System.out.println("date = [" + theDate + "]");
        return String.valueOf(theDate.getTime());
    }

    /**
     * 对比时间
     * @param time1 时间1 被比较时间
     * @param time2 时间2 比较时间
     * @return 返回时间time1-time2的差值 返回值是秒
     * @throws BaseException 抛错
     */
    public static long compareTime(Date time1,Date time2) throws BaseException {
        long from = time1.getTime();
        long to = time2.getTime();
        return (to - from) / 1000;
    }

    /**
     * 获取当前的详细时间格式为：yyyy-MM-dd HH:mm:ss
     * @return 详细时间的返回字符串
     */
    public static String getNow(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前签名时间戳
     * @return 时间戳字符串
     */
    public static String getNowTimestamp(){
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * 时间戳转换为时间，请务必使用11位的时间戳
     * @param timestamp 时间戳
     * @return 返回时间
     * @throws BaseException 抛错
     */
    public static Date getDateByTimestamp(String timestamp) throws BaseException {
        if (timestamp.length() != 11){
            throw new BaseException("Timestamp length is not a Vaild one");
        }
        return new Date(Long.valueOf(timestamp));
    }

    /**
     * 行政区划长码获取短码（长码就是短码补足道12位）
     * @param fullAreaCode 长码
     * @return 短码
     */
    public static String getCurrentShortAreaCode(String fullAreaCode) {
        char[] characters = fullAreaCode.toCharArray();
        int idx = characters.length-1;
        boolean meetNoZero = false;
        while (!meetNoZero){
            if (characters[idx]=='0'){
                idx -=1;
            }else {
                meetNoZero = true;
            }
        }
        return new String(Arrays.copyOfRange(characters,0,idx+1));
    }


    public static void main(String[] args) {
        System.out.println(getCurrentShortAreaCode("331002001000"));
    }



}
