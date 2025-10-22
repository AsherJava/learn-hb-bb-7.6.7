/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.time.setting.de.FillTimeDataSetting
 *  com.jiuqi.nr.time.setting.de.HolidayRange
 */
package com.jiuqi.nr.dataentry.internal.service.util;

import com.jiuqi.nr.time.setting.de.FillTimeDataSetting;
import com.jiuqi.nr.time.setting.de.HolidayRange;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DateTimeUtil {
    public static Date getDateOfAfterDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, day);
        calendar.set(11, 0);
        return calendar.getTime();
    }

    public static Date getDateOfAfterWorkDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int num = 1;
        while (num <= day) {
            calendar.add(5, 1);
            ++num;
            if (calendar.get(7) != 1 && calendar.get(7) != 7) continue;
            --num;
        }
        calendar.set(11, 0);
        return calendar.getTime();
    }

    public static Date getDateOfBeforeDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        calendar.add(5, ~day);
        calendar.set(11, 0);
        return calendar.getTime();
    }

    public static Date getDateOfBeforeWorkDay(Date date, int day) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        int num = -1;
        while (num >= ~day) {
            calendar.add(5, -1);
            --num;
            if (calendar.get(7) != 1 && calendar.get(7) != 7) continue;
            ++num;
        }
        calendar.set(11, 0);
        return calendar.getTime();
    }

    public static Date getDateOfBeforeWorkDay(Date date, int day, List<HolidayRange> holidayRanges) {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(date);
        int num = -1;
        while (num >= ~day) {
            calendar.add(5, -1);
            --num;
            if (!FillTimeDataSetting.checkHoliday((Calendar)calendar, holidayRanges)) continue;
            ++num;
        }
        calendar.set(11, 0);
        return calendar.getTime();
    }

    public static Date getDay() {
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        return calendar.getTime();
    }

    public static Date getFormatDay(String date) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTime(simpleDateFormat.parse(date));
        return calendar.getTime();
    }

    public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
        if (nowTime.getTime() == startTime.getTime() || nowTime.getTime() == endTime.getTime()) {
            return true;
        }
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        return date.after(begin) && date.before(end);
    }
}

