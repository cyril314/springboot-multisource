package com.fit.util;

import org.joda.time.DateTime;
import org.springframework.util.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 日期相关工具类
 */
public class DateUtil {

    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FULL_STR = "yyyy-MM-dd HH:mm:ss";
    /**
     * 完整时间 yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATE_LONG_STR = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * 年月日(下划线) yyyy-MM-dd
     */
    public static final String DATE_SMALL_STR = "yyyy-MM-dd";
    /**
     * 年月日时分秒(无下划线) yyMMddHHmmss
     */
    public static final String DATE_KEY_STR = "yyMMddHHmmss";
    /**
     * 年月日时分秒(无下划线) yyyyMMddHHmmss
     */
    public static final String DATE_All_KEY_STR = "yyyyMMddHHmmss";
    /**
     * 年月日(无下划线) yyyyMMdd
     */
    public static final String DATE_KEY_SHORT = "yyyyMMdd";
    /**
     * 完整时间 yyyy/MM/dd HH:mm:ss
     */
    public static final String STR_DATE_SLASH = "yyyy/MM/dd HH:mm:ss";
    /**
     * 年月日 yyyy/MM/dd
     */
    public static final String STR_DATE_SLASH_SMALL = "yyyy/MM/dd";

    public static long getDateNowMillis() {
        return new Date().getTime();
    }

    public static long getDateTimeMillis(String dateBefore) {
        if (ObjectUtils.isEmpty(dateBefore)) {
            return 0l;
        }
        try {
            SimpleDateFormat simple1 = new SimpleDateFormat(DATE_All_KEY_STR);
            return simple1.parse(dateBefore).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0l;
    }

    public static Date getDateNow() {
        Date d = new Date();
        try {
            SimpleDateFormat df = new SimpleDateFormat(DATE_FULL_STR);
            return df.parse(String.valueOf(d.getTime()));
        } catch (ParseException e) {
        }
        return d;
    }

    public static String getCurrentGMT() {
        Date d = new Date();

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        return format.format(d);
    }

    public static String formatGMTToLocal(String gmt) {
        if (ObjectUtils.isEmpty(gmt)) {
            return "";
        }
        try {
            SimpleDateFormat simple1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            simple1.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date dateD = simple1.parse(gmt);
            SimpleDateFormat simpeleDataFormat = new SimpleDateFormat(DATE_All_KEY_STR);
            return simpeleDataFormat.format(dateD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatGMTNoMilliSecondToLocal(String gmt) {
        if (ObjectUtils.isEmpty(gmt)) {
            return "";
        }
        try {
            SimpleDateFormat simple1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            simple1.setTimeZone(TimeZone.getTimeZone("GMT"));
            Date dateD = simple1.parse(gmt);

            SimpleDateFormat simpeleDataFormat = new SimpleDateFormat(DATE_All_KEY_STR);

            return simpeleDataFormat.format(dateD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String formatLocalToGMT(String local) {
        if (ObjectUtils.isEmpty(local)) {
            return "";
        }
        try {
            SimpleDateFormat simple1 = new SimpleDateFormat(DATE_All_KEY_STR);

            Date dateD = simple1.parse(local);
            SimpleDateFormat simpeleDataFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            simpeleDataFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
            return simpeleDataFormat.format(dateD);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取当前日期，格式为yyyy-MM-dd HH:mm:ss
     */
    public static String getTime() {
        Date date = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat(DATE_FULL_STR);
        return simpleFormat.format(date);
    }

    /**
     * 获取当前日期，格式为yyyyMMddHHmmss
     */
    public static String getNowTime() {
        Date date = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat(DATE_All_KEY_STR);
        return simpleFormat.format(date);
    }

    /**
     * 获取当前日期，格式为yyyyMMdd
     */
    public static String getNowDate() {
        Date date = new Date();
        SimpleDateFormat simpleFormat = new SimpleDateFormat(DATE_KEY_SHORT);
        return simpleFormat.format(date);
    }

    /**
     * 获取指定日期前一天的日期
     *
     * @param date 格式为yyyyMMdd
     * @return 格式为yyyyMMdd
     */
    public static String getDateLessOne(String date) {
        if (ObjectUtils.isEmpty(date)) {
            return "";
        }

        SimpleDateFormat format = new SimpleDateFormat(DATE_KEY_SHORT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(date));
            c.add(Calendar.DAY_OF_MONTH, -1);

            return format.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getDateLessOrMoreMinutes(String date, int minutes) {
        if (ObjectUtils.isEmpty(date)) {
            return "";
        }
        if (minutes == 0) {
            return date;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_All_KEY_STR);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(date));
            c.add(Calendar.MINUTE, minutes);

            return format.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param date    时间字符串
     * @param pattern 转换格式
     * @return 指定指定日期字符串
     */
    public static Date parse(String date, String pattern) {
        String[] formats = {DATE_LONG_STR, DATE_FULL_STR, DATE_SMALL_STR, STR_DATE_SLASH, STR_DATE_SLASH_SMALL};
        try {
            long times = Long.parseLong(date);
            Date d = new Date(times);
            return d;
        } catch (NumberFormatException e) {
            try {
                SimpleDateFormat df = new SimpleDateFormat(pattern);
                return df.parse(date);
            } catch (Exception ex) {
                for (String format : formats) {
                    try {
                        SimpleDateFormat df = new SimpleDateFormat(format);
                        return df.parse(date);
                    } catch (ParseException pe) {
                        continue;
                    }
                }
            }
        }

        return null;
    }

    /**
     * @param date    时间字符串
     * @param pattern 转换格式
     * @return 转换指定格式字符串
     */
    public static String format(String date, String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(parse(date, pattern));
    }

    /**
     * @param date 时间字符串
     * @return 转换为yyyyMMddHHmmss格式的字符串
     */
    public static String format(String date) {
        return format(date, DATE_All_KEY_STR);
    }

    public static String getDateLessOrMoreDay(String date, int day) {
        if (ObjectUtils.isEmpty(date)) {
            return "";
        }
        if (day == 0) {
            return date;
        }
        SimpleDateFormat format = new SimpleDateFormat(DATE_KEY_SHORT);
        try {
            Calendar c = Calendar.getInstance();
            c.setTime(format.parse(date));
            c.add(Calendar.DATE, day);

            return format.format(c.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getYearWeek(DateTime dateTime) {
        if (null == dateTime) {
            dateTime = new DateTime(new Date());
        }

        int year = dateTime.getWeekyear();
        int week = dateTime.getWeekOfWeekyear();
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(year);
        if (week < 10) {
            sBuilder.append("0");
        }
        sBuilder.append(week);

        return sBuilder.toString();
    }

    public static String getPastDate(Date date, int past) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_All_KEY_STR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, past);
        Date pastDate = calendar.getTime();
        return format.format(pastDate);
    }

    public static String getPastMonth(Date date, int past) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_All_KEY_STR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, past);
        Date pastDate = calendar.getTime();
        return format.format(pastDate);
    }

    public static String getPastYear(Date date, int past) {
        SimpleDateFormat format = new SimpleDateFormat(DATE_All_KEY_STR);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, past);
        Date pastDate = calendar.getTime();
        return format.format(pastDate);
    }

    public static String getYearWeek() {
        return getYearWeek(null);
    }
}
