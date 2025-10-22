/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RowDataUtil {
    private static final String TIME_FORMAT = "yyyy-MM-dd HH:mm";

    public static final String conversion(long fileSize) {
        double size = fileSize;
        String sizeString = "";
        sizeString = size != 0.0 ? ((size /= 1000.0) >= 1024.0 ? ((size /= 1024.0) >= 1024.0 ? ((size /= 1024.0) >= 1024.0 ? String.format("%.2f", size /= 1024.0).toString() + "TB" : String.format("%.2f", size).toString() + "GB") : String.format("%.2f", size).toString() + "MB") : String.format("%.2f", size).toString() + "KB") : "0KB";
        return sizeString;
    }

    public static final String dateToString(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
        return simpleDateFormat.format(date);
    }
}

