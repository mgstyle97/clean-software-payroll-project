package com.example.payday.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static boolean between(Date target, Date start, Date end) {
        return !(target.before(start) || target.after(end));
    }

    public static Calendar toCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    public static Date toDate(Long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            return dateFormat.parse(date.toString());
        } catch (ParseException e) {
            return null;
        }

    }

}
