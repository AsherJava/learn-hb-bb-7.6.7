/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class DateParser {
    private static final List<String> TIME_PATTERNS = Arrays.asList("yyyy-MM-dd HH:mm:ss", "yyyyMMddHHmmss");
    private static final List<String> DATE_PATTERNS = Arrays.asList("yyyy-MM-dd", "yyyyMMdd", "yyyy\u5e74MM\u6708dd\u65e5", "yyyy/MM/dd");
    private String recentPattern;

    public static Date toDate(String text) {
        DateParser parser = new DateParser();
        return parser.parseDate(text);
    }

    public Date parseDate(String text) {
        Date date;
        Date date2;
        if (text == null || text.length() == 0) {
            return null;
        }
        if (this.recentPattern != null && (date2 = this.tryPattern(text, this.recentPattern)) != null) {
            return date2;
        }
        boolean separated = text.contains("-");
        if (text.indexOf(58) >= 0 || text.length() >= 14) {
            for (String pattern : TIME_PATTERNS) {
                if (separated && !pattern.contains("-") || (date = this.tryPattern(text, pattern)) == null) continue;
                return date;
            }
        }
        for (String pattern : DATE_PATTERNS) {
            if (separated && !pattern.contains("-") || (date = this.tryPattern(text, pattern)) == null) continue;
            return date;
        }
        return null;
    }

    private Date tryPattern(String value, String pattern) {
        Date date;
        SimpleDateFormat fmt = new SimpleDateFormat(pattern);
        try {
            date = fmt.parse(value);
        }
        catch (ParseException e) {
            this.recentPattern = null;
            return null;
        }
        this.recentPattern = pattern;
        return date;
    }
}

