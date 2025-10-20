/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 */
package com.jiuqi.nr.designer.planpublish.util;

import com.jiuqi.np.definition.common.StringUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    public static String getCron(Date date) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("ss mm HH dd MM ? yyyy");
        String formatTimeStr = null;
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static String getCron(String dataStr) throws Exception {
        Date date = DateUtils.getDate(dataStr);
        return DateUtils.getCron(date);
    }

    public static Date getDate(String dataStr) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        if (!StringUtils.isEmpty((String)dataStr)) {
            date = sdf.parse(dataStr);
        }
        return date;
    }

    public static String getCurrDate() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    public static boolean publishExpired(Date date) throws Exception {
        if (date != null) {
            return date.before(new Date());
        }
        throw new Exception("\u9884\u7ea6\u53d1\u5e03\u65f6\u95f4\u4e3a\u7a7a");
    }

    public static boolean publishExpired(String dateStr) throws Exception {
        if (!StringUtils.isEmpty((String)dateStr)) {
            Date date = DateUtils.getDate(dateStr);
            return date.before(new Date());
        }
        throw new Exception("\u9884\u7ea6\u53d1\u5e03\u65f6\u95f4\u4e3a\u7a7a");
    }
}

