package com.handy.base.utils;

import android.annotation.SuppressLint;
import android.content.Context;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 时间和日期的工具类，包含了标准的时间和日期格式，以及这些格式在字符串及日期之间转换的方法
 * <p>
 * Created by LiuJie on 2016/1/19.
 */

public class DateUT {

    private static DateUT dateUT = null;

    private String datePattern = "yyyy-MM-dd";
    private String timePattern = "HH:mm:ss";
    private String calendarPattern = datePattern + " " + timePattern;


    private DateUT() {
    }

    public synchronized static DateUT getInstance() {
        if (dateUT == null) {
            dateUT = new DateUT();
        }
        return dateUT;
    }

    /**
     * ===================================================================
     * 获得年月日默认格式
     */
    public String getDatePattern() {
        return datePattern;
    }

    /**
     * 获得时间默认格式
     */
    public String getTimePattern() {
        return timePattern;
    }

    /**
     * 获得日期默认格式
     */
    public String getCalendarPattern() {
        return calendarPattern;
    }

    /**
     * ===================================================================
     * 获得默认格式的年月日
     *
     * @param date 日期对象
     * @return yyyy-MM-dd
     */
    public String getDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        String returnValue = "";
        if (date != null) {
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 获得自定义格式的日期
     *
     * @param pattern 日期格式
     * @param date    日期对象
     * @return
     */
    public String getDate(String pattern, Date date) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String returnValue = "";
        if (date != null) {
            returnValue = df.format(date);
        }
        return (returnValue);
    }

    /**
     * 获得自定义格式的日期
     *
     * @param pattern 自定义日期类型
     * @param strDate 字符串型的日期标石
     * @return
     * @throws ParseException
     */
    public Date getDate(String pattern, String strDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            return null;
        }
        return (date);
    }

    /**
     * ===================================================================
     * 获取当前日期
     *
     * @param pattern 日期格式 不传则为默认日期格式
     * @return
     */

    public String getNowDate(String pattern) {
        String strReturn = "";
        SimpleDateFormat sdf = new SimpleDateFormat(calendarPattern);
        try {
            if (pattern != null && !pattern.equals("")) {
                sdf = new SimpleDateFormat(pattern);
            }
            strReturn = sdf.format(new Date());
        } catch (Exception e) {
        }
        return strReturn;
    }

    /**
     * 从网络获取当前日期
     *
     * @param context
     * @return
     * @throws IOException
     */
    public String getNowDateNet(Context context) throws IOException {
        String strReturn = "";
        if (NetworkUT.getInstance().isAvailable(context)) {
            URL urlTime = new URL("http://www.bjtime.cn"); //取得资源对象
            URLConnection uc = urlTime.openConnection(); //生成连接对象
            uc.connect(); //发出连接
            long ld = uc.getDate(); //取得网站日期时间
            Date date = new Date(ld); //转换为标准时间对象
            SimpleDateFormat sdformat = new SimpleDateFormat(calendarPattern); //24小时制,12小时制则HH为hh
            strReturn = sdformat.format(date);
        } else {
            PrintfUT.getInstance().showShortToast(context, "未检测到网络");
            return "";
        }
        return strReturn;
    }

    /**
     * ===================================================================
     * 根据毫秒数获得时间
     *
     * @param timeInMillis
     * @return
     */
    public String getDateMs(long timeInMillis) {
        long result = 0;
        StringBuffer sbf = new StringBuffer();
        //
        result = (timeInMillis / 1000) / 3600;
        if (result > 0) {
            sbf.append(StringUT.getInstance().addPrefixZero((int) result)).append(":");
            timeInMillis = timeInMillis - result * 3600 * 1000;
        } else {
            sbf.append("00:");
        }
        //
        result = (timeInMillis / 1000) / 60;
        if (result > 0) {
            sbf.append(StringUT.getInstance().addPrefixZero((int) result)).append(":");
            timeInMillis = timeInMillis - result * 60 * 1000;
        } else {
            sbf.append("00:");
        }
        //
        result = timeInMillis / 1000;
        sbf.append(StringUT.getInstance().addPrefixZero((int) result));
        return sbf.toString();
    }

    public String getDateMsCN(long mss) {
        long days = mss / (1000 * 60 * 60 * 24);
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        String time = "";
        if (seconds != 0) {
            time = seconds + "秒";
        }
        if (minutes != 0) {
            time = minutes + "分" + time;
        }
        if (hours != 0) {
            time = hours + "小时" + time;
        }
        if (days != 0) {
            time = days + "天" + time;
        }
        return time;
    }

    /**
     * 根据毫秒 将毫秒的日期带时差求得新的日期。
     *
     * @param millisecond 毫秒值
     * @param Diff        小时差
     * @param type        true:往后增加时差；false：往前减小时差
     * @return
     */
    public String getDateMs(long millisecond, int Diff, boolean type) {
        Date dat;
        dat = type ? new Date(millisecond + (Diff * 60 * 60 * 1000)) : new Date(millisecond - (Diff * 60 * 60 * 1000));
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(gc.getTime());
    }

    /**
     * ===================================================================
     * 计算两个日期之间相差的天数
     *
     * @param strBeforeDate 较小的日期
     * @param strAfterDate  较大的日期
     * @return
     */
    public int countDiffDate(String strBeforeDate, String strAfterDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(strBeforeDate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(strAfterDate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的日期
     * @param bdate  较大的日期
     * @return 相差天数
     * @throws ParseException
     */
    public int countDiffDate(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * ===================================================================
     * 获得指定日期距离1970年1月1日0点0分0秒的毫秒数
     *
     * @param pattern
     * @param strDate
     * @return
     * @throws ParseException
     */
    public long getMS(String pattern, String strDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = df.parse(strDate);
        Long msTime = date.getTime(); //这就是距离1970年1月1日0点0分0秒的毫秒数
        return msTime;
    }

    /**
     * 获得当前日期距离1970年1月1日0点0分0秒的毫秒数
     *
     * @return
     * @throws ParseException
     */
    public long getMS() throws ParseException {
        Date dt = new Date();
        Long msTime = dt.getTime(); //这就是距离1970年1月1日0点0分0秒的毫秒数
        return msTime;
    }

    /**
     * ===================================================================
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public String getFriendlyDate(String sdate) throws ParseException {
        ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        };
        ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };
        Date time = dateFormater.get().parse(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * ===================================================================
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public boolean isToday(String sdate) throws ParseException {
        ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            }
        };
        ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
            @SuppressLint("SimpleDateFormat")
            @Override
            protected SimpleDateFormat initialValue() {
                return new SimpleDateFormat("yyyy-MM-dd");
            }
        };
        boolean b = false;
        Date time = dateFormater.get().parse(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }


    /**
     * ===================================================================
     * 获得指定月的天数
     *
     * @return
     */
    public int getMaxDayOfTheMonth(int year, int month) {
        int day = 1;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month - 1, day);
        int last = cal.getActualMaximum(Calendar.DATE);
        System.out.println(last);
        return last;
    }

    public int getMaxDayOfTheMonth(String year, String month) {
        int day = 1;
        Calendar cal = Calendar.getInstance();
        cal.set(StringUT.getInstance().toInt(year, 0), StringUT.getInstance().toInt(month, 0), day);
        int last = cal.getActualMaximum(Calendar.DATE);
        System.out.println(last);
        return last;
    }


    /**
     * ===================================================================
     * 获得年的后两位数字<br>
     * 例如，2008获得8，1998获得98<br>
     *
     * @param @return 设定文件
     * @return int 返回类型
     */
    public int getYearSimple(int year) {
        int yearSimple;
        if (year > 2000) {
            yearSimple = year - 2000;
        } else if (year > 1900) {
            yearSimple = year - 1900;
        } else if (year > 1800) {
            yearSimple = year - 1800;
        } else {
            yearSimple = year - 1700;
        }
        if (yearSimple < 10) {
            yearSimple = StringUT.getInstance().toInt(StringUT.getInstance().addPrefixZero(yearSimple), 0);
        }
        return yearSimple;
    }

    public int getYearSimple(String pattern, String strDate) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        Date date = df.parse(strDate);
        int year = date.getMonth();
        int yearSimple = 0;
        if (year > 2000) {
            yearSimple = year - 2000;
        } else if (year > 1900) {
            yearSimple = year - 1900;
        } else if (year > 1800) {
            yearSimple = year - 1800;
        } else {
            yearSimple = year - 1700;
        }
        if (yearSimple < 10) {
            yearSimple = StringUT.getInstance().toInt(StringUT.getInstance().addPrefixZero(yearSimple), 0);
        }
        return yearSimple;
    }
}