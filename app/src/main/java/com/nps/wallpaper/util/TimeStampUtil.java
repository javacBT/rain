package com.nps.wallpaper.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 时间戳转换
 * 2018/11/5.
 */

public class TimeStampUtil {

    public static String timeToString(long lt,String gshi) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(gshi);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间 带小时
     */
    public static String stampTo(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }


    /*
     * 将时间戳转换为时间 带小时
     */
    public static String stampToTime(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd HH:mm");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间 带小时
     */
    public static String nmsl(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /*
     * 将时间戳转换为时间 带小时
     */
    public static String stampToYear(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String getTimeZone(){
        TimeZone tz = TimeZone.getDefault();
        return createGmtOffsetString(true,true,tz.getRawOffset());

    }

    public static String createGmtOffsetString(boolean includeGmt,
                                               boolean includeMinuteSeparator, int offsetMillis) {
        int offsetMinutes = offsetMillis / 60000;
        char sign = '+';
        if (offsetMinutes < 0) {
            sign = '-';
            offsetMinutes = -offsetMinutes;
        }
        StringBuilder builder = new StringBuilder(9);
        if (includeGmt) {
            builder.append("GMT");
        }
        builder.append(sign);
        appendNumber(builder, 2, offsetMinutes / 60);
        if (includeMinuteSeparator) {
            builder.append(':');
        }
        appendNumber(builder, 2, offsetMinutes % 60);
        return builder.toString();
    }

    private static void appendNumber(StringBuilder builder, int count, int value) {
        String string = Integer.toString(value);
        for (int i = 0; i < count - string.length(); i++) {
            builder.append('0');
        }
        builder.append(string);
    }

}