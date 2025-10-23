/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final ThreadLocal<DateFormat> dateFormatThreadLocal = ThreadLocal.withInitial(() -> new SimpleDateFormat(TIME_PATTERN));

    private DateTimeUtils() {
    }

    public static String format(Date date) {
        if (date == null) {
            return null;
        }
        return dateFormatThreadLocal.get().format(date);
    }

    public static Date parse(String time) {
        if (time == null) {
            return null;
        }
        try {
            return dateFormatThreadLocal.get().parse(time);
        }
        catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

