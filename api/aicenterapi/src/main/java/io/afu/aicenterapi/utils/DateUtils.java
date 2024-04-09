package io.afu.aicenterapi.utils;


import io.afu.aicenterapi.exceptions.BaseException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author RffanLAB.方露宇
 * @version 0.1
 */
public class DateUtils {

    /**
     * 获取当前日期格式 按照yyyyMMddhhmmss这样子的来进行
     * @param format 时间格式
     * @return String
     */
    public static String getTodayByFormat(String format){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(now);
    }


    /**
     * 多少天后的某某天
     * @param days 多少天
     * @return Date类型
     */
    public static Date someDaysLaterAfterToday(int days){
        days = Math.abs(days);
        return someDaysChange(new Date(),days);
    }

    /**
     * 当前时间多少天前的时间
     * @param days 多少天
     * @return Date类型
     */
    public static Date someDaysBeforeToday(int days){
        days = -Math.abs(days);
        return someDaysChange(new Date(),days);
    }

    /**
     * 某天后
     * @param theDate 某天
     * @param days 多少天以后
     * @return Date类型
     */
    public static Date someDaysChange(Date theDate,int days){
        Calendar c = Calendar.getInstance();
        c.setTime(theDate);
        c.add(Calendar.DATE,days);
        return c.getTime();
    }

    /**
     * 计算两个日期间相差天数
     * @param date1 靠前的时间
     * @param date2 靠后的时间
     * @return 时间差
     */
    public static long getLengthDays(Date date1,Date date2){
        long timediff= date2.getTime()-date1.getTime();
        return timediff/(24*60*60*1000);
    }

    /**
     * 获取当前的日期，格式为yyyy-MM-dd
     * @return String
     */
    public static String getToday(){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(now);
    }

    /**
     * 按照格式来获取日期
     * @param format 时间格式
     * @return 格式化好的时间
     */
    public static String getToday(String format){
        Date now = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(now);
    }


    /**
     * 通过样式解析日期
     * @param sourceDate 原始日期
     * @param format 原始日期的格式
     * @return Date
     * @throws Exception 解析出错后抛出的错误
     */
    public static Date parseDateByFormat(String sourceDate,String format) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.parse(sourceDate);
    }


    /**
     * 获取多少个月前的第一天的Date对象
     * @param monthes 月份数量（uint 正整数）
     * @return 返回时间
     */
    public static Date getFirstDateOfSeveralMonthAgo(Integer monthes) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,-monthes);
        c.set(Calendar.DAY_OF_MONTH,1);
        return c.getTime();
    }

    /**
     * 获取多少个月后的第一天
     * @param monthes 月份数量(uint正整数)
     * @return 返回时间
     */
    public static Date getFirstDateOfNextSeveralMonth(Integer monthes){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,monthes);
        c.set(Calendar.DAY_OF_MONTH,1);
        return c.getTime();
    }

    /**
     * 获取某些月份的第一天的字符串
     * @param monthes 几个月前
     * @param format 格式化的格式
     * @return 返回值
     */
    public static String getFirstDateOfSeveralMonthAgo(Integer monthes,String format){
        Date date = getFirstDateOfSeveralMonthAgo(monthes);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取以后某些月份的第一天的字符串
     * @param monthes 几个月后
     * @param format 格式化字符串
     * @return 返回值
     */
    public static String getFirstDateOfNextSeveralMonth(Integer monthes,String format){
        Date date = getFirstDateOfNextSeveralMonth(monthes);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        return simpleDateFormat.format(date);
    }

    /**
     * 获取本月的第一天
     * @return Date
     */
    public static Date getThisMonthFirstDate(){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH,0);
        c.set(Calendar.DAY_OF_MONTH,1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        System.out.println(sdf.format(c.getTime()));
        return c.getTime();
    }

    /**
     * 获取上个个月的最后一天时间
     * @return Date
     */
    public static Date getThisMonthLastDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        int lastMonthMaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);
        System.out.println(sdf.format(c.getTime()));
        return c.getTime();
    }

    /**
     * 获取上个月的第一天时间
     * @return Date
     */
    public static Date getLastMonthFirstDate(){
        Calendar c= Calendar.getInstance();
        c.add(Calendar.MONTH,-1);
        c.set(Calendar.DAY_OF_MONTH,1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        System.out.println(sdf.format(c.getTime()));
        return c.getTime();
    }


    /**
     * 获取上个个月的最后一天时间
     * @return Date
     */
    public static Date getLastMonthLastDate(){
        Calendar c=Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        int lastMonthMaxDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), lastMonthMaxDay, 23, 59, 59);
        System.out.println(sdf.format(c.getTime()));
        return c.getTime();
    }

    /**
     * 获取当前日期的几天后的时间
     * @param days 天数
     * @return 计算好的时间
     */
    public static Date getSomeDaysLater(Integer days){
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        now.set(Calendar.DATE,now.get(Calendar.DATE) + days);
        return now.getTime();
    }

    public static Date getSomeDaysLater(Date theNow,Integer days){
        Calendar now = Calendar.getInstance();
        now.setTime(theNow);
        now.set(Calendar.DATE,now.get(Calendar.DATE) + days);
        return now.getTime();
    }


    /**
     * 获取当前时间几天前的时间
     * @param days 天数
     * @return 计算好的时间
     */
    public static Date getSomeDaysAgo(Integer days){
        return getSomeDaysLater(-Math.abs(days));
    }

    /**
     * 返回某个时间几天前的时间
     * @param theNow 某个时间点
     * @param days 天数
     * @return 计算好的时间点
     */
    public static Date getSomeDaysAgo(Date theNow,Integer days){
        return getSomeDaysLater(theNow,-Math.abs(days));
    }



    /**
     * 更当前时间对比
     * @param toCompare 需要比对的时间
     * @return String
     */
    public static String compareNowTime(Date toCompare){
        String result = "";

        Long nd = 1000L * 24L * 60L * 60L;// 一天的毫秒数
        Long nh = 1000L * 60L * 60L;// 一小时的毫秒数
        Long nm = 1000L * 60L;// 一分钟的毫秒数
        Long ns = 1000L;// 一秒钟的毫秒数
        Long day = 0L;
        Long hour = 0L;
        Long min = 0L;
        Long sec = 0L;

        Date now = new Date();
        Long diff = toCompare.getTime() - now.getTime();
        String suffix = "前";
        if (diff >0){
            suffix = "后";
        }
        diff = Math.abs(diff);
        day = diff / nd;
        hour = diff / nh;
        min = diff / nm;
        sec = diff / ns;
        if (day > 1){
            return day.toString()+"天"+suffix;
        }
        if (hour >1 ){
            return hour.toString()+"小时"+suffix;
        }
        if (min > 1 ){
            return min.toString() + "分钟"+ suffix;
        }
        return sec.toString() + "秒"+ suffix;
    }

    /**
     * 获取多少年之前的今天
     * @param yearDifference (整是往后的几年，负是往前的纪念)
     * @param theDate (格式为yyyy-MM-dd)
     * @return Date
     * @throws Exception 解析时间出错时抛出的错误
     */
    public static Date getYearsAgoOfToday(Integer yearDifference,String theDate) throws Exception{
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date theDateOfsomeday = simpleDateFormat.parse(theDate);
        c.setTime(theDateOfsomeday);
        c.add(Calendar.YEAR,yearDifference);
        return c.getTime();
    }

    /**
     * 检查这个日期格式是正常日历上的日期，格式为yyyyMMdd，该方法是为了防止出现2月31号这种日期的出现。
     * @param date 需要检查的时间
     * @return Boolean
     * @throws Exception 解析时间出错时抛出的错误
     */
    public static Boolean checkDateIsTheTrueCalendarDate(String date) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date theDate = simpleDateFormat.parse(date);
        if (date.equals(simpleDateFormat.format(theDate))){
            return true;
        }
        return false;
    }

    public static Boolean checkDateIsTheTrueCalendarDate(String date,String format) throws Exception {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date theDate = simpleDateFormat.parse(date);
        if (date.equals(simpleDateFormat.format(theDate))){
            return true;
        }
        return false;
    }

    /**
     * 判断该日期是否在日期之间
     * @param theDateStr 需要判断的日期
     * @param startDateStr 周期开始的日期
     * @param endDateStr 周期结束的日期
     * @param format 日期格式
     * @return Boolean
     * @throws Exception 解析时间时出错抛出的错误
     */
    public static Boolean theDateIsBetween(String theDateStr,String startDateStr,String endDateStr,String format) throws Exception{
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date theDate = simpleDateFormat.parse(theDateStr);
        Date startDate = simpleDateFormat.parse(startDateStr);
        Date endDate = simpleDateFormat.parse(endDateStr);
        if (endDate.getTime()-theDate.getTime()>=0 && theDate.getTime()-startDate.getTime()>=0){
            return true;
        }
        return false;
    }

    /**
     * 判断该日期是否在日期之间
     * @param theDate 需要判断的日期
     * @param startDate 周期开始的日期
     * @param endDate 周期结束的日期
     * @param format 日期格式
     * @return Boolean
     */
    public static Boolean theDateIsBetween(Date theDate,Date startDate,Date endDate,String format) {
        if (endDate.getTime()-theDate.getTime()>=0 && theDate.getTime()-startDate.getTime()>=0){
            return true;
        }
        return false;
    }

    /**
     * 获取当前时间的年份
     * @return String 返回当前时间的年份
     */
    public static String getThisYear(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前月份也以前月份比较后的相差月数
     * @param theMonth 以前的月份时间
     * @return 返回月数
     * @throws BaseException 抛错
     */
    public static int howManyMonthesCompareNow(String theMonth) throws BaseException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            String str1 = sdf.format(new Date());
            Calendar bef = Calendar.getInstance();
            Calendar aft = Calendar.getInstance();
            bef.setTime(sdf.parse(str1));
            aft.setTime(sdf.parse(theMonth));
            int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH);
            int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
            return Math.abs(month + result);
        }catch (Exception e){
            throw new BaseException(e);
        }

    }

    public static boolean inperiod(Date startDate,Date endDate,int second) {
        return endDate.getTime() - startDate.getTime()<second*1000L;
    }



}
