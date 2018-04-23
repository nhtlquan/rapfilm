package com.example.lequan.film.common;

import android.content.Context;
import com.example.lequan.film.R;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import org.apache.commons.lang3.StringUtils;

public class TimeUtils {
    public static int getNextHour(int currentHourOfDay) {
        if (currentHourOfDay == 23) {
            return 0;
        }
        int i = currentHourOfDay;
        currentHourOfDay++;
        return i;
    }

    public static int getCurrentHourOfDay() {
        return Calendar.getInstance().get(11);
    }

    public static int getTimezoneValue() {
        return new GregorianCalendar().getTimeZone().getRawOffset();
    }

    public static long getTimeStamp() {
        return Calendar.getInstance().getTimeInMillis();
    }

    public static String getTimeAgo(Context context, long time) {
        long timeAgo = (getTimeStamp() - (((1000 * time) - 25200000) + ((long) getTimezoneValue()))) / 1000;
        if (timeAgo < 60) {
            return timeAgo + StringUtils.SPACE + context.getString(R.string.second_ago);
        }
        if (timeAgo < 3600) {
            return (timeAgo / 60) + StringUtils.SPACE + context.getString(R.string.minute_ago);
        }
        if (timeAgo < 86400) {
            return (timeAgo / 3600) + StringUtils.SPACE + context.getString(R.string.hour_ago);
        }
        if (timeAgo < 2592000) {
            return (timeAgo / 86400) + StringUtils.SPACE + context.getString(R.string.date_ago);
        }
        if (timeAgo < 31536000) {
            return (timeAgo / 2592000) + StringUtils.SPACE + context.getString(R.string.month_ago);
        }
        return (timeAgo / 31536000) + StringUtils.SPACE + context.getString(R.string.year_ago);
    }

    public static String getCurrentDay(String format) {
        return new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
    }

    public static String getTime(long time) {
        int minute = (int) ((time / 1000) / 60);
        int second = (int) ((time / 1000) - ((long) (minute * 60)));
        if (minute < 10 && second < 10) {
            return "0" + minute + ":0" + second;
        }
        if (minute < 10) {
            return "0" + minute + ":" + second;
        }
        if (second < 10) {
            return minute + ":0" + second;
        }
        return minute + ":" + second;
    }
}
