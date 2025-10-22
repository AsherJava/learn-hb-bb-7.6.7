/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class DateUtil {
    private static final Logger log = LoggerFactory.getLogger(DateUtil.class);
    private static ThreadLocal<SimpleDateFormat> sdf = ThreadLocal.withInitial(() -> new SimpleDateFormat(FORMAT_DATE));
    private static ThreadLocal<SimpleDateFormat> sdft = ThreadLocal.withInitial(() -> new SimpleDateFormat(FORMAT_DATE_TIME));
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    public static final String FORMAT_TIME = "HH:mm:ss";
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    public static SimpleDateFormat dfD() {
        return sdf.get();
    }

    public static SimpleDateFormat dfDT() {
        return sdft.get();
    }

    public static String getFormatDatetime(String date, String format) {
        SimpleDateFormat df = null;
        df = format.equals(FORMAT_DATE) ? DateUtil.dfD() : (format.equals(FORMAT_DATE_TIME) ? DateUtil.dfDT() : new SimpleDateFormat(format));
        try {
            date = df.format(df.parse(date));
        }
        catch (ParseException e) {
            log.debug("\u65e5\u671f\u683c\u5f0f\u5316\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
        return date;
    }

    public static Date getOriDatetime(String date, String format) {
        if (!StringUtils.hasLength(date)) {
            return null;
        }
        SimpleDateFormat df = null;
        df = format.equals(FORMAT_DATE) ? DateUtil.dfD() : (format.equals(FORMAT_DATE_TIME) ? DateUtil.dfDT() : new SimpleDateFormat(format));
        Date resulDate = null;
        try {
            resulDate = df.parse(date);
        }
        catch (NumberFormatException | ParseException e) {
            log.debug("\u65e5\u671f\u683c\u5f0f\u5316\u51fa\u9519{}", (Object)e.getMessage(), (Object)e);
        }
        return resulDate;
    }
}

