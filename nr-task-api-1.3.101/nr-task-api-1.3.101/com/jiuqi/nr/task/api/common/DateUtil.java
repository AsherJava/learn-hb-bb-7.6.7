/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.nr.task.api.common;

import com.jiuqi.np.definition.common.StringUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import org.springframework.util.Assert;

public class DateUtil {
    public static ThreadLocal<SimpleDateFormat> sdf_time = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static Date format(String source) throws ParseException {
        Assert.hasLength(source, "\u65e5\u671f\u683c\u5f0f\u5316\u7684\u6e90\u6570\u636e\u4e3a\u7a7a");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(source);
    }

    public static String format(LocalDateTime source) {
        Assert.notNull((Object)source, "\u65e5\u671f\u683c\u5f0f\u5316\u7684\u6e90\u6570\u636e\u4e3a\u7a7a");
        return source.format(dtf);
    }

    public static String getCron(String dataStr) throws Exception {
        Date date = DateUtil.format(dataStr);
        return DateUtil.getCron(date);
    }

    public static String getCron(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static Date getDate(String dataStr) throws Exception {
        Date date = null;
        if (!StringUtils.isEmpty((String)dataStr)) {
            date = sdf_time.get().parse(dataStr);
        }
        return date;
    }

    public static String getCurrDate() {
        return sdf_time.get().format(new Date());
    }

    public static boolean publishExpired(Date date) throws Exception {
        if (date != null) {
            return date.before(new Date());
        }
        throw new Exception("\u9884\u7ea6\u53d1\u5e03\u65f6\u95f4\u4e3a\u7a7a");
    }

    public static boolean publishExpired(String dateStr) throws Exception {
        if (!StringUtils.isEmpty((String)dateStr)) {
            Date date = DateUtil.getDate(dateStr);
            if (date == null) {
                throw new Exception("\u9884\u7ea6\u53d1\u5e03\u65f6\u95f4\u683c\u5f0f\u5316\u5931\u8d25");
            }
            return date.before(new Date());
        }
        throw new Exception("\u9884\u7ea6\u53d1\u5e03\u65f6\u95f4\u4e3a\u7a7a");
    }

    public static LocalDateTime dateToLocalDateTime(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}

