package com.venus.testutils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneOffset.UTC);
    }

    public static Date nowPlusDays(int seconds) {
        return nowPlus(Calendar.DAY_OF_MONTH, seconds);
    }

    public static Date nowPlusSeconds(int seconds) {
        return nowPlus(Calendar.SECOND, seconds);
    }

    public static Date nowMinusSeconds(int seconds) {
        return nowPlusSeconds(-seconds);
    }

    public static Date datePlusSeconds(Date date, int seconds) {
        return datePlus(date, Calendar.SECOND, seconds);
    }

    public static Date dateMinusSeconds(Date date, int seconds) {
        return datePlusSeconds(date, -seconds);
    }

    private static Date nowPlus(int field, int quantity) {
        return datePlus(new Date(), field, quantity);
    }

    private static Date datePlus(Date date, int field, int quantity) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(field, quantity);
        return cal.getTime();
    }
}
