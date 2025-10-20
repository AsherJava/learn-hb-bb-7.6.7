/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.text;

import com.jiuqi.bi.text.DateFormatSymbolsProvider;
import java.text.DateFormatSymbols;
import java.util.Arrays;
import java.util.Locale;

public class DateFormatSymbolsEx
extends DateFormatSymbols {
    private static final long serialVersionUID = 1L;
    static final String PATTERN_CHARS = "GyMdkHmsSEDFwWahKzZYuXLBQTrNen";
    static final int PATTERN_ERA = 0;
    static final int PATTERN_YEAR = 1;
    static final int PATTERN_MONTH = 2;
    static final int PATTERN_DAY_OF_MONTH = 3;
    static final int PATTERN_HOUR_OF_DAY1 = 4;
    static final int PATTERN_HOUR_OF_DAY0 = 5;
    static final int PATTERN_MINUTE = 6;
    static final int PATTERN_SECOND = 7;
    static final int PATTERN_MILLISECOND = 8;
    static final int PATTERN_DAY_OF_WEEK = 9;
    static final int PATTERN_DAY_OF_YEAR = 10;
    static final int PATTERN_DAY_OF_WEEK_IN_MONTH = 11;
    static final int PATTERN_WEEK_OF_YEAR = 12;
    static final int PATTERN_WEEK_OF_MONTH = 13;
    static final int PATTERN_AM_PM = 14;
    static final int PATTERN_HOUR1 = 15;
    static final int PATTERN_HOUR0 = 16;
    static final int PATTERN_ZONE_NAME = 17;
    static final int PATTERN_ZONE_VALUE = 18;
    static final int PATTERN_WEEK_YEAR = 19;
    static final int PATTERN_ISO_DAY_OF_WEEK = 20;
    static final int PATTERN_ISO_ZONE = 21;
    static final int PATTERN_MONTH_STANDALONE = 22;
    static final int PATTERN_HALFYEAR = 23;
    static final int PATTERN_QUARTER = 24;
    static final int PATTERN_TEN_DAY = 25;
    static final int PATTERN_YEAR_OF_LASTDAY = 26;
    static final int PATTERN_MONTH_OF_LASTDAY = 27;
    static final int PATTERN_DAY_OF_LASTDAY = 28;
    static final int PATTERN_YEAR_ZH = 29;
    private final Locale locale;
    private String[] halfYears = null;
    private String[] shortQuarters = null;
    private String[] xuns = null;
    private String[] month0s = null;

    public DateFormatSymbolsEx(Locale locale) {
        super(locale);
        this.locale = locale;
        this.initData();
    }

    private void initData() {
        DateFormatSymbolsProvider.ISymbolsEx symbols = new DateFormatSymbolsProvider().getSymbolsEx(this.locale);
        if (symbols.getMonths() != null) {
            this.setMonths(symbols.getMonths());
        }
        if (symbols.getShortMonths() != null) {
            this.setShortMonths(symbols.getShortMonths());
        }
        this.halfYears = symbols.getHALFYEAR_VALUES();
        this.shortQuarters = symbols.getShortQuater();
        this.xuns = symbols.getXun_values();
        this.month0s = symbols.getMonth0s();
    }

    int getZoneIndex(String ID) {
        for (int index = 0; index < this.getZoneStrings().length; ++index) {
            if (!ID.equalsIgnoreCase(this.getZoneStrings()[index][0])) continue;
            return index;
        }
        return -1;
    }

    public String[] getHalfYears() {
        return Arrays.copyOf(this.halfYears, this.halfYears.length);
    }

    public String[] getShortQuaters() {
        return Arrays.copyOf(this.shortQuarters, this.shortQuarters.length);
    }

    public String[] getXuns() {
        return Arrays.copyOf(this.xuns, this.xuns.length);
    }

    public String[] getMonth0s() {
        return Arrays.copyOf(this.month0s, this.month0s.length);
    }

    public Locale getLocale() {
        return this.locale;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = hash * 31 + Arrays.hashCode(this.halfYears);
        hash = hash * 31 + Arrays.hashCode(this.shortQuarters);
        hash = hash * 31 + Arrays.hashCode(this.xuns);
        hash = hash * 31 + Arrays.hashCode(this.month0s);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        DateFormatSymbolsEx that = (DateFormatSymbolsEx)obj;
        return Arrays.equals(this.halfYears, that.halfYears) && Arrays.equals(this.shortQuarters, that.shortQuarters) && Arrays.equals(this.xuns, that.xuns) && Arrays.equals(this.month0s, that.month0s);
    }

    @Override
    public Object clone() {
        DateFormatSymbolsEx dfs = (DateFormatSymbolsEx)super.clone();
        dfs.halfYears = Arrays.copyOf(this.halfYears, this.halfYears.length);
        dfs.shortQuarters = Arrays.copyOf(this.shortQuarters, this.shortQuarters.length);
        dfs.xuns = Arrays.copyOf(this.xuns, this.xuns.length);
        dfs.month0s = Arrays.copyOf(this.month0s, this.month0s.length);
        return dfs;
    }
}

