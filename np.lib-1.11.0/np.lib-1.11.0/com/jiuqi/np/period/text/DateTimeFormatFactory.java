/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.period.text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Stack;

public class DateTimeFormatFactory {
    private static HashMap cachedFormats = new HashMap();

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static DateFormat createDateTimeFormat(String pattern) {
        DateFormat format = null;
        HashMap hashMap = cachedFormats;
        synchronized (hashMap) {
            Stack formats = (Stack)cachedFormats.get(pattern);
            if (formats == null) {
                formats = new Stack();
                cachedFormats.put(pattern, formats);
            }
            if (!formats.isEmpty()) {
                format = (DateFormat)formats.pop();
            }
        }
        if (format == null) {
            format = new PatternDecimalFormat(pattern);
            ((SimpleDateFormat)format).setLenient(true);
        }
        return format;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void recycleDateTimeFormat(DateFormat format) {
        String pattern = ((PatternDecimalFormat)format).pattern;
        HashMap hashMap = cachedFormats;
        synchronized (hashMap) {
            Stack<DateFormat> formats = (Stack<DateFormat>)cachedFormats.get(pattern);
            if (formats == null) {
                formats = new Stack<DateFormat>();
                cachedFormats.put(pattern, formats);
            }
            if (formats != null) {
                formats.push(format);
            }
        }
    }

    static class PatternDecimalFormat
    extends SimpleDateFormat {
        private static final long serialVersionUID = -8585318792250640020L;
        public final String pattern;

        public PatternDecimalFormat(String pattern) {
            super(pattern);
            this.pattern = pattern;
        }
    }
}

