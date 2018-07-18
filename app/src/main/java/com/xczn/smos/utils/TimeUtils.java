package com.xczn.smos.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by zhangxiao
 * Date on 2018/5/16.
 */
public class TimeUtils {

    private static int[] _afxMonthDays = new int[]{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365};
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    private static final SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static final SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM", Locale.CHINA);
    private static final SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.CHINA);

    //获得当前时间
    public static String getCurrentTime(){
        Date date= new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    //获得当前日期，天
    public static String getCurrentDay(){
        Date date= new Date(System.currentTimeMillis());
        return dayFormat.format(date);
    }

    //获得当前日期，月
    public static String getCurrentMonth(){
        Date date= new Date(System.currentTimeMillis());
        return monthFormat.format(date);
    }

    //获得当前日期，年
    public static String getCurrentYear(){
        Date date= new Date(System.currentTimeMillis());
        return yearFormat.format(date);
    }

    private static Date double2Date(Double d)
    {
        Date t = null;
        try{
            Calendar base = Calendar.getInstance();
            //Delphi的日期类型从1899-12-30开始
            base.set(1899, 11, 30, 0, 0, 0);
            base.add(Calendar.DATE, d.intValue());
            base.add(Calendar.MILLISECOND, (int)((d % 1) * 24 * 60 * 60 * 1000));
            t = base.getTime();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return t;
    }

    public static double change2Double(String str) throws java.text.ParseException{

        Calendar curCalendar = Calendar.getInstance();
        curCalendar.setTime(simpleDateFormat.parse(str));

        Double result = _DateFromTm(curCalendar.get(Calendar.YEAR), curCalendar.get(Calendar.MONTH)+1, curCalendar.get(Calendar.DAY_OF_MONTH),
                curCalendar.get(Calendar.HOUR_OF_DAY), curCalendar.get(Calendar.MINUTE), curCalendar.get(Calendar.SECOND), 0);

        return result;
    }

    private static double _DateFromTm(int nYear, int nMonth, int nDay,
                                     int nHour, int nMinute, int nSecond, int nMillis)
    {
        // Validate year and month (ignore day of week and milliseconds)
        if (nYear > 9999 || nMonth < 1 || nMonth > 12)
            return 0;

        //  Check for leap year and set the number of days in the month
        Boolean bLeapYear = ((nYear & 3) == 0) && ((nYear % 100) != 0 || (nYear % 400) == 0);

        int nDaysInMonth =
                _afxMonthDays[nMonth] - _afxMonthDays[nMonth-1] +
                        ((bLeapYear && nDay == 29 && nMonth == 2) ? 1 : 0);

        // Finish validating the date
        if (nDay < 1 || nDay > nDaysInMonth ||
                nHour > 23 || nMinute > 59 ||
                nSecond > 59) {
            return 0;
        }

        // Cache the date in days and time in fractional days
        int nDate;
        double dblTime;

        //It is a valid date; make Jan 1, 1AD be 1
        nDate = nYear*365 + nYear/4 - nYear/100 + nYear/400 +
                _afxMonthDays[nMonth-1] + nDay;

        //  If leap year and it's before March, subtract 1:
        if (nMonth <= 2 && bLeapYear)
            --nDate;

        //  Offset so that 12/30/1899 is 0
        nDate -= 693959L;

        dblTime = (((int)nHour * 3600L) +  // hrs in seconds
                ((int)nMinute * 60L) +  // mins in seconds
                ((int)nSecond)) / 86400. + ((int)nMillis) / 86400000.0;

        double dtDest = (double) nDate + ((nDate >= 0) ? dblTime : -dblTime);

        return dtDest;
    }
    
    public static String date2String(Double d){
        return simpleDateFormat.format(double2Date(d));
    }
}
