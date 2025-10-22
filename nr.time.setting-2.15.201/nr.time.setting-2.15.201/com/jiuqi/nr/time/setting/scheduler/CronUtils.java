/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.time.setting.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CronUtils {
    private CronUtils() {
    }

    private static String formatDateByPattern(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        String formatTimeStr = null;
        if (Objects.nonNull(date)) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static String getCron(Date date) {
        return CronUtils.formatDateByPattern(date);
    }

    private static Date parseCronByPattern(String cron) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        Date formatTimeStr = null;
        if (Objects.nonNull(cron)) {
            formatTimeStr = sdf.parse(cron);
        }
        return formatTimeStr;
    }

    public static Date getDate(String cron) throws ParseException {
        return CronUtils.parseCronByPattern(cron);
    }
}

