/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Strings
 *  com.google.common.collect.Lists
 */
package com.jiuqi.nr.table.data.type;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.data.type.DateTimeColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateTimeParser
extends AbstractColumnParser<LocalDateTime> {
    private static final DateTimeFormatter dtTimef0 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter dtTimef2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    private static final DateTimeFormatter dtTimef4 = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
    private static final DateTimeFormatter dtTimef5 = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    private static final DateTimeFormatter dtTimef6;
    private static final DateTimeFormatter dtTimef7;
    private static final DateTimeFormatter dtTimef8;
    public static final DateTimeFormatter DEFAULT_FORMATTER;
    private Locale locale = Locale.getDefault();
    private DateTimeFormatter formatter = DEFAULT_FORMATTER;

    public DateTimeParser(ColumnType columnType) {
        super(columnType);
    }

    public DateTimeParser(DateTimeColumnType dateTimeColumnType, ReadOptions readOptions) {
        super(dateTimeColumnType);
        if (readOptions.missingValueIndicators().length > 0) {
            this.missingValueStrings = Lists.newArrayList((Object[])readOptions.missingValueIndicators());
        }
    }

    @Override
    public boolean canParse(String s) {
        if (this.isMissing(s)) {
            return true;
        }
        try {
            LocalDateTime.parse(s, this.formatter.withLocale(this.locale));
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }

    @Override
    public LocalDateTime parse(String value) {
        if (this.isMissing(value)) {
            return null;
        }
        String paddedValue = Strings.padStart((String)value, (int)4, (char)'0');
        return LocalDateTime.parse(paddedValue, this.formatter);
    }

    static {
        dtTimef7 = DateTimeFormatter.ofPattern("M/d/yy H:mm");
        dtTimef8 = DateTimeFormatter.ofPattern("M/d/yyyy h:mm:ss a");
        dtTimef6 = new DateTimeFormatterBuilder().parseCaseInsensitive().append(DateTimeFormatter.ISO_LOCAL_DATE_TIME).appendLiteral('.').appendPattern("SSS").toFormatter();
        DEFAULT_FORMATTER = new DateTimeFormatterBuilder().appendOptional(dtTimef7).appendOptional(dtTimef8).appendOptional(dtTimef2).appendOptional(dtTimef4).appendOptional(dtTimef0).appendOptional(dtTimef5).appendOptional(dtTimef6).toFormatter();
    }
}

