/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nrdt.parampacket.manage.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static String curDate() {
        Date curentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        return dateFormat.format(curentDate);
    }

    public static String getDate() {
        Date curentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        return dateFormat.format(curentDate);
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.getDate());
    }
}

