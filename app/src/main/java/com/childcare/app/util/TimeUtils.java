package com.childcare.app.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import timber.log.Timber;

/**
 * 获取当前日期，时间，年，月，日
 * Created by john on 5/16/2016
 */

@SuppressWarnings("ALL")
public class TimeUtils {

    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";

    public static String getCurrentTime(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 格式化时间
     *
     * @param time 时间字符串(格式yyyy-MM-dd HH:mm)
     * @return 昨天，今天。。。
     */
    public static String getWhatDay(String time) {
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HH_MM, Locale.CHINA);
        if (time == null || "".equals(time)) {
            return "";
        }
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            Timber.e(e.getMessage());
        }

        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {

            return "昨天 " + time.split(" ")[1];
        } else {
            int index = time.indexOf('-') + 1;
            return time.substring(index, time.length());
        }
    }


    public static String getCurrentTime() {
        return getCurrentTime(YYYY_MM_DD_HH_MM);
    }

    public static String getCurrentDate() {
        return getCurrentTime(YYYY_MM_DD);
    }

    public static int getCurrentYear() {
        return Integer.parseInt(getCurrentTime("yyyy"));
    }

    public static int getCurrentMonth() {
        return Integer.parseInt(getCurrentTime("MM"));
    }

    public static int getCurrentDay() {
        return Integer.parseInt(getCurrentTime("dd"));
    }

    /**
     * 获取周X("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
     *
     * @param dateStr 日期：yyyy-MM-dd格式
     * @return 周X
     */
    public static String getWeekDay(String dateStr) {
        String chineseWeekDay = null;
        int day = 0;
        //把用户输入的日期转成 java 日期类
        Date date = convertString2Date(dateStr);
        //输出结果
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        day = cal.get(Calendar.DAY_OF_WEEK) - 1;
        switch (day) {
            case 0:
                chineseWeekDay = "周日";
                break;
            case 1:
                chineseWeekDay = "周一";
                break;
            case 2:
                chineseWeekDay = "周二";
                break;
            case 3:
                chineseWeekDay = "周三";
                break;
            case 4:
                chineseWeekDay = "周四";
                break;
            case 5:
                chineseWeekDay = "周五";
                break;
            case 6:
                chineseWeekDay = "周六";
                break;
            default:
                break;
        }

        return chineseWeekDay;
    }

    /**
     * 获取0~6("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")
     *
     * @param dateStr 日期：yyyy-MM-dd格式
     * @return 0~6
     */
    public static int getWeekDayInt(String dateStr) {
        int day = 0;
        //把用户输入的日期转成 java 日期类
        Date d = convertString2Date(dateStr);
        //输出结果
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        day = cal.get(Calendar.DAY_OF_WEEK) - 1;

        return day;
    }

    /**
     * 是否是今天
     *
     * @param dateStr 日期：2015-12-04
     * @return 今天：true，非今天：false
     */
    public static boolean isToday(String dateStr) {
        if (dateStr == null || "".equals(dateStr)) {
            return false;
        }

        return getToday().equals(dateStr);
    }

    /**
     * 是否是昨天
     *
     * @param dateStr 日期：2015-12-03
     * @return 昨天：true，非昨天：false
     */
    public static boolean isYesterday(String dateStr) {
        if (dateStr == null || "".equals(dateStr)) {
            return false;
        }

        return getYesterday().equals(dateStr);
    }

    /**
     * 是否是前天
     *
     * @param dateStr 日期：2015-12-02
     * @return 前天：true，非前天：false
     */
    public static boolean isDayBeforeYesterday(String dateStr) {
        if (dateStr == null || "".equals(dateStr)) {
            return false;
        }

        return getDayBeforeYesterday().equals(dateStr);
    }

    /**
     * 获取今天
     *
     * @return 日期：20151204
     */
    public static String getToday() {
        String format = "yyyyMMdd";
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 获取昨天
     *
     * @return 日期：2015-12-03
     */
    public static String getYesterday() {
        Calendar yesterday = Calendar.getInstance();
        Calendar current = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        Date date = yesterday.getTime();
        String format = YYYY_MM_DD;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }

    /**
     * 获取前天
     *
     * @return 日期：2015-12-02
     */
    public static String getDayBeforeYesterday() {
        Calendar yesterday = Calendar.getInstance();
        Calendar current = Calendar.getInstance();
        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 2);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);
        Date date = yesterday.getTime();
        String format = YYYY_MM_DD;
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        return sdf.format(date);
    }


    /**
     * 转换“x日”
     *
     * @param date 日期（如：2015-05-15）
     * @return 15日
     */
    public static String convertWeekDay(String date) {
        String formattedDate = null;
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat(
                    YYYY_MM_DD, Locale.CHINESE);
            SimpleDateFormat targetFormat = new SimpleDateFormat("M月dd日");
            Date date12 = originalFormat.parse(date);
            formattedDate = targetFormat.format(date12);
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }

        return formattedDate;

    }

    /**
     * 转换为“月/日”
     *
     * @param date 日期（如：2015-05-15)
     * @return 05/15
     */
    public static String convertWeekDays(String date) {
        String formattedDate = null;
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat(
                    YYYY_MM_DD, Locale.CHINESE);
            SimpleDateFormat targetFormat = new SimpleDateFormat("MM/dd", Locale.CHINA);
            Date date12 = originalFormat.parse(date);
            formattedDate = targetFormat.format(date12);
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }

        return formattedDate;
    }


    /**
     * 转换为某年某月
     *
     * @param date 日期（如：2015-05-15)
     * @return 2015年5月
     */
    public static String convertWeekDaysMonth(String date) {
        String formattedDate = null;
        try {
            SimpleDateFormat originalFormat = new SimpleDateFormat(YYYY_MM_DD, Locale.CHINESE);
            SimpleDateFormat targetFormat = new SimpleDateFormat("M月", Locale.CHINA);
            Date date12 = originalFormat.parse(date);
            formattedDate = targetFormat.format(date12);
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }

        return formattedDate;

    }

    /**
     * 将时间戳转换成为日期
     *
     * @param timestamp 时间戳
     * @return "yyyy-MM-dd"format
     */
    public static String parseTimestamp2Date(long timestamp) {
        Timestamp ts = new Timestamp(timestamp);
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD, Locale.CHINA);
        try {
            date = ts;
        } catch (Exception e) {
            Timber.e(e.getMessage());
        }
        return sdf.format(date);
    }

    public static Date convertString2Date(String dateStr) {
        //把用户输入的日期转成 java 日期类
        DateFormat df = new SimpleDateFormat(YYYY_MM_DD, Locale.CHINA);
        Date date = null;
        try {
            date = df.parse(dateStr);
        } catch (ParseException e) {
            Timber.e(e.getMessage());
        }
        return date;

    }

    public static int getDayFromDate(String dateStr) {
        int day = 0;
        //把用户输入的日期转成 java 日期类
        Date d = convertString2Date(dateStr);
        //输出结果
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        day = cal.get(Calendar.DAY_OF_MONTH); //日

        return day;
    }

    public static int getMonthFromDate(String dateStr) {
        int month = 0;
        //把用户输入的日期转成 java 日期类
        Date d = convertString2Date(dateStr);
        //输出结果
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        month = cal.get(Calendar.MONTH) + 1; //月(从0开始, 一般加1，实际是否 Calendar 里面常量的值决定的)
        return month;
    }


    public static int getYearFromDate(String dateStr) {
        int year = 0;
        //把用户输入的日期转成 java 日期类
        Date d = convertString2Date(dateStr);
        //输出结果
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        year = cal.get(Calendar.YEAR); //年
        return year;
    }


    public static boolean isBigThanTarget(String sourceDateStr, String targetDateStr) {
        Date sourceDate = convertString2Date(sourceDateStr);
        Date targetDate = TimeUtils.convertString2Date(targetDateStr);
        int result = sourceDate.compareTo(targetDate);
        boolean isBigger = false;
        switch (result) {
            //equals date
            case 0:
                isBigger = false;
                break;
            //source date smaller
            case -1:
                isBigger = false;
                break;
            //source date bigger
            case 1:
                isBigger = true;
                break;
            default:
                break;

        }
        return isBigger;
    }

    public static boolean isDateInRange(String sourceDateStr,
                                        String beginDateStr,
                                        String endDateStr) {
        if (isBigThanTarget(sourceDateStr, endDateStr)) {
            return false;
        } else if (isBigThanTarget(beginDateStr, sourceDateStr)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取更早的天数
     *
     * @param sourceDateStr 原始日期
     * @param rangeDays     差距天数
     * @return 目标日期
     */
    public static String getEarlierDate(String sourceDateStr, int rangeDays) {
        Date sourceDate = convertString2Date(sourceDateStr);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.DATE, -rangeDays);
        Date targetDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD, Locale.CHINA);

        return sdf.format(targetDate);
    }

    /**
     * 获取更迟的天数
     *
     * @param sourceDateStr 原始日期
     * @param rangeDays     差距天数
     * @return 目标日期
     */
    public static String getLaterDate(String sourceDateStr, int rangeDays) {
        Date sourceDate = convertString2Date(sourceDateStr);
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(sourceDate);
        calendar.add(Calendar.DATE, rangeDays);
        Date targetDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD, Locale.CHINA);

        return sdf.format(targetDate);
    }


    /**
     * 获取两个日期之间的间隔天数
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 日期相隔天数
     */
    public static int getGapDaysCount(Date startDate, Date endDate) {
        Calendar fromCalendar = Calendar.getInstance();
        fromCalendar.setTime(startDate);
        fromCalendar.set(Calendar.HOUR_OF_DAY, 0);
        fromCalendar.set(Calendar.MINUTE, 0);
        fromCalendar.set(Calendar.SECOND, 0);
        fromCalendar.set(Calendar.MILLISECOND, 0);

        Calendar toCalendar = Calendar.getInstance();
        toCalendar.setTime(endDate);
        toCalendar.set(Calendar.HOUR_OF_DAY, 0);
        toCalendar.set(Calendar.MINUTE, 0);
        toCalendar.set(Calendar.SECOND, 0);
        toCalendar.set(Calendar.MILLISECOND, 0);

        return (int) ((toCalendar.getTime().getTime() -
                fromCalendar.getTime().getTime()) / (1000 * 60 * 60 * 24));
    }

    /**
     * 获取日期到今天的间隔天数
     *
     * @param startDateStr 开始日期
     * @return 日期相隔天数
     */
    public static int getGapDaysFromNowCount(String startDateStr) {
        Date startDate = convertString2Date(startDateStr);
        Date endDate = new Date();

        return getGapDaysCount(startDate, endDate);
    }


    private static final String yyyyMMddHHmmss = "yyyyMMddHHmmss";

    /***
     * 两个日期相差多少秒
     *
     * @param date1
     * @param date2
     */
    public static int getTimeDelta(Date date1, Date date2) {
        //单位是秒
        long timeDelta = (date1.getTime() - date2.getTime()) / 1000;
        return timeDelta > 0 ? (int) timeDelta : (int) Math.abs(timeDelta);
    }

    /***
     * 两个日期相差多少秒
     * @param dateStr1  :yyyy-MM-dd HH:mm:ss
     * @param dateStr2 :yyyy-MM-dd HH:mm:ss

     */
    public static int getTimeDelta(String dateStr1, String dateStr2) {
        Date date1 = parseDateByPattern(dateStr1, yyyyMMddHHmmss);
        Date date2 = parseDateByPattern(dateStr2, yyyyMMddHHmmss);
        return getTimeDelta(date1, date2);
    }

    public static Date parseDateByPattern(String dateStr, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        try {
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            Timber.e(e.getMessage());
        }
        return null;
    }


}