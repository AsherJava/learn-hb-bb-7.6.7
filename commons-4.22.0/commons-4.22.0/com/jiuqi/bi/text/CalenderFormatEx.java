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

public class CalenderFormatEx
extends Format {
    private static final long serialVersionUID = -5834071374807638375L;
    private DateFormatEx format;

    public CalenderFormatEx(DateFormatEx format) {
        this.format = format;
    }

    public CalenderFormatEx(String pattern) {
        this(new DateFormatEx(pattern));
    }

    public CalenderFormatEx(String pattern, Locale local) {
        this(new DateFormatEx(pattern, local));
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj instanceof Calendar) {
            Calendar cal = (Calendar)obj;
            return this.format.format(cal.getTime(), toAppendTo, pos);
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
}

