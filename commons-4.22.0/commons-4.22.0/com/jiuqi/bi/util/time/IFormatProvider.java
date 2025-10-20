/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.text.ParseException;
import java.util.Locale;

public interface IFormatProvider {
    public String name();

    public Parser createParser(TimeFieldInfo var1, String var2, Locale var3);

    public Formatter createFormatter(TimeFieldInfo var1, String var2, Locale var3);

    @FunctionalInterface
    public static interface Formatter {
        public String format(Object var1);
    }

    @FunctionalInterface
    public static interface Parser {
        public Object parse(String var1) throws ParseException;
    }
}

