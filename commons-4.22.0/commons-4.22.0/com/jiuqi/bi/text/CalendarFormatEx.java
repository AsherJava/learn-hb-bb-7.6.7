/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.text;

import com.jiuqi.bi.text.DateFormatEx;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Locale;

public class CalendarFormatEx
extends Format {
    private static final long serialVersionUID = 1L;
    private DateFormatEx format;

    public CalendarFormatEx(DateFormatEx format) {
        this.format = format;
    }

    public CalendarFormatEx(String pattern) {
        this(new DateFormatEx(pattern));
    }

    public CalendarFormatEx(String pattern, int timeGranularity) {
        this(new DateFormatEx(pattern, timeGranularity));
    }

    public CalendarFormatEx(String pattern, Locale locale) {
        this(new DateFormatEx(pattern, locale));
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj instanceof Calendar) {
            Calendar cal = (Calendar)obj;
            return this.format.formatCalendar(cal, toAppendTo, pos);
        }
        return this.format.format(obj, toAppendTo, pos);
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        return this.format.parseCalendar(source, pos);
    }

    public DateFormatEx getDateFormat() {
        return this.format;
    }

    public String toPattern() {
        return this.format.toPattern();
    }
}

