/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package com.jiuqi.nr.table.data.type;

import com.google.common.collect.Lists;
import com.jiuqi.nr.table.data.AbstractColumnParser;
import com.jiuqi.nr.table.data.ColumnType;
import com.jiuqi.nr.table.io.ReadOptions;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DateParser
extends AbstractColumnParser<LocalDate> {
    private static final DateTimeFormatter dtf1 = DateTimeFormatter.ofPattern("yyyyMMdd");
    private static final DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter dtf3 = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static final DateTimeFormatter dtf4 = DateTimeFormatter.ofPattern("MM.dd.yyyy");
    private static final DateTimeFormatter dtf5 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dtf6 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static final DateTimeFormatter dtf7 = DateTimeFormatter.ofPattern("dd/MMM/yyyy");
    private static final DateTimeFormatter dtf8 = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
    private static final DateTimeFormatter dtf9 = DateTimeFormatter.ofPattern("M/d/yyyy");
    private static final DateTimeFormatter dtf10 = DateTimeFormatter.ofPattern("M/d/yy");
    private static final DateTimeFormatter dtf11 = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
    private static final DateTimeFormatter dtf12 = DateTimeFormatter.ofPattern("MMM-dd-yyyy");
    private static final DateTimeFormatter dtf13 = DateTimeFormatter.ofPattern("MMM/dd/yy");
    private static final DateTimeFormatter dtf14 = DateTimeFormatter.ofPattern("MMM-dd-yy");
    private static final DateTimeFormatter dtf15 = DateTimeFormatter.ofPattern("MMM/dd/yyyy");
    private static final DateTimeFormatter dtf16 = DateTimeFormatter.ofPattern("MMM/d/yyyy");
    private static final DateTimeFormatter dtf17 = DateTimeFormatter.ofPattern("MMM-dd-yy");
    private static final DateTimeFormatter dtf18 = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    private static final DateTimeFormatter dtf19 = DateTimeFormatter.ofPattern("MMM d, yyyy");
    private static final DateTimeFormatter dtf20 = DateTimeFormatter.ofPattern("yyyy/M/dd");
    public static final DateTimeFormatter DEFAULT_FORMATTER = new DateTimeFormatterBuilder().appendOptional(dtf1).appendOptional(dtf2).appendOptional(dtf3).appendOptional(dtf4).appendOptional(dtf5).appendOptional(dtf6).appendOptional(dtf7).appendOptional(dtf8).appendOptional(dtf9).appendOptional(dtf10).appendOptional(dtf11).appendOptional(dtf12).appendOptional(dtf13).appendOptional(dtf14).appendOptional(dtf15).appendOptional(dtf16).appendOptional(dtf17).appendOptional(dtf18).appendOptional(dtf19).appendOptional(dtf20).toFormatter();
    private Locale locale = Locale.getDefault();
    private DateTimeFormatter formatter = DEFAULT_FORMATTER;

    public DateParser(ColumnType type, ReadOptions readOptions) {
        super(type);
        DateTimeFormatter readCsvFormatter = readOptions.dateFormatter();
        if (readCsvFormatter != null) {
            this.formatter = readCsvFormatter;
        }
        if (readOptions.locale() != null) {
            this.locale = readOptions.locale();
        }
        if (readOptions.missingValueIndicators().length > 0) {
            this.missingValueStrings = Lists.newArrayList((Object[])readOptions.missingValueIndicators());
        }
    }

    public DateParser(ColumnType type) {
        super(type);
    }

    @Override
    public boolean canParse(String s) {
        if (this.isMissing(s)) {
            return true;
        }
        try {
            LocalDate.parse(s, this.formatter.withLocale(this.locale));
            return true;
        }
        catch (DateTimeParseException e) {
            return false;
        }
    }

    public void setCustomFormatter(DateTimeFormatter f) {
        this.formatter = f;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    @Override
    public LocalDate parse(String s) {
        if (this.isMissing(s)) {
            return null;
        }
        return LocalDate.parse(s, this.formatter);
    }
}

