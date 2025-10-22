/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class DateUtils {
    public static final String HH_mm_ss = "HH:mm:ss";
    public static final String yyyy_mm_dd = "yyyy-MM-dd";
    public static final String yyyy_mm_dd_HH_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyymmdd = "yyyy/MM/dd";
    public static final String regx_yyyy_mm_dd = "\\b[0-9]{4}[-](0?[1-9]|1[012])[-](0?[1-9]|[12][0-9]|3[01])\\b";
    public static final String regx_yyyymmdd = "\\b[0-9]{4}[/](0?[1-9]|1[012])[/](0?[1-9]|[12][0-9]|3[01])\\b";
    public static final String regx_yyyy_mm_dd_HH_mm_ss = "(\\d{2}|\\d{4})(?:\\-)?([0]{1}\\d{1}|[1]{1}[0-2]{1})(?:\\-)?([0-2]{1}\\d{1}|[3]{1}[0-1]{1})(?:\\s)?([0-1]{1}\\d{1}|[2]{1}[0-3]{1})(?::)?([0-5]{1}\\d{1})(?::)?([0-5]{1}\\d{1})";

    private DateUtils() {
    }

    public static Date stringToDate(String dateStr) {
        DateRegx[] values;
        SimpleDateFormat format = new SimpleDateFormat();
        String trimDateStr = dateStr.trim();
        for (DateRegx dateRegx : values = DateRegx.values()) {
            boolean matches = Pattern.matches(dateRegx.regex, trimDateStr);
            if (!matches) continue;
            format.applyPattern(dateRegx.pattern);
            break;
        }
        try {
            return format.parse(trimDateStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String date_str_yyyy_mm_dd_HH_mm_ss(Date date) {
        return DateUtils.convertDateToString(date, yyyy_mm_dd_HH_mm_ss);
    }

    public static String dateToString(Date date, String regx) {
        SimpleDateFormat format = new SimpleDateFormat(regx);
        return format.format(date);
    }

    public static String get_HH_mm_ss() {
        return DateUtils.convertDateToString(new Date(), HH_mm_ss);
    }

    public static String convertDateToString(Date date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern(pattern);
        return format.format(date);
    }

    private static enum DateRegx {
        date_str_yyyy_mm_dd("yyyy-MM-dd", "\\b[0-9]{4}[-](0?[1-9]|1[012])[-](0?[1-9]|[12][0-9]|3[01])\\b"),
        date_str_yyyy_mm_dd_HH_mm_ss("yyyy-MM-dd HH:mm:ss", "(\\d{2}|\\d{4})(?:\\-)?([0]{1}\\d{1}|[1]{1}[0-2]{1})(?:\\-)?([0-2]{1}\\d{1}|[3]{1}[0-1]{1})(?:\\s)?([0-1]{1}\\d{1}|[2]{1}[0-3]{1})(?::)?([0-5]{1}\\d{1})(?::)?([0-5]{1}\\d{1})"),
        date_str_yyyymmdd("yyyy/MM/dd", "\\b[0-9]{4}[/](0?[1-9]|1[012])[/](0?[1-9]|[12][0-9]|3[01])\\b");

        public String pattern;
        public String regex;

        private DateRegx(String pattern, String regex) {
            this.pattern = pattern;
            this.regex = regex;
        }
    }
}

