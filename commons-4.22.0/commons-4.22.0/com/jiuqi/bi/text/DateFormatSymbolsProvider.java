/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.collections4.map.HashedMap
 */
package com.jiuqi.bi.text;

import java.util.Locale;
import java.util.Map;
import org.apache.commons.collections4.map.HashedMap;

class DateFormatSymbolsProvider {
    private final Map<String, ISymbolsEx> map = new HashedMap();

    public DateFormatSymbolsProvider() {
        this.map.put(Locale.ENGLISH.getLanguage(), new SymbolsEx_EN());
        this.map.put(Locale.CHINESE.getLanguage(), new SymbolsEx_ZH());
    }

    public static String[] getHALFYEAR_VALUES(Locale locale) {
        return new DateFormatSymbolsProvider().getSymbolsEx(locale).getHALFYEAR_VALUES();
    }

    public static String[] getShortQuater(Locale locale) {
        return new DateFormatSymbolsProvider().getSymbolsEx(locale).getShortQuater();
    }

    public static String[] getXun_values(Locale locale) {
        return new DateFormatSymbolsProvider().getSymbolsEx(locale).getXun_values();
    }

    public ISymbolsEx getSymbolsEx(Locale locale) {
        ISymbolsEx symbolsEx = this.map.get(locale.getLanguage());
        if (symbolsEx == null) {
            return this.getSymbolsEx(Locale.ENGLISH);
        }
        return symbolsEx;
    }

    static interface ISymbolsEx {
        public String[] getHALFYEAR_VALUES();

        public String[] getShortQuater();

        public String[] getXun_values();

        public String[] getMonths();

        public String[] getShortMonths();

        public String[] getMonth0s();
    }

    static class SymbolsEx_EN
    implements ISymbolsEx {
        private final String[] halfYears = new String[]{"H1", "H2"};
        private final String[] shortQuaters = new String[]{"Q1", "Q2", "Q3", "Q4"};
        private final String[] xuns = new String[]{"Early", "Mid", "Late"};
        private final String[] months = new String[]{"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December", "Month-13", "Month-14", "Month-15", "Month-16", "Month-17", "Month-18", "Month-19", "Month-20"};
        private final String[] shortMonths = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", "M13", "M14", "M15", "M16", "M17", "M18", "M19", "M20"};
        private final String[] month0s = new String[]{"M-0", "Month-0"};

        SymbolsEx_EN() {
        }

        @Override
        public String[] getHALFYEAR_VALUES() {
            return this.halfYears;
        }

        @Override
        public String[] getShortQuater() {
            return this.shortQuaters;
        }

        @Override
        public String[] getXun_values() {
            return this.xuns;
        }

        @Override
        public String[] getMonths() {
            return this.months;
        }

        @Override
        public String[] getShortMonths() {
            return this.shortMonths;
        }

        @Override
        public String[] getMonth0s() {
            return this.month0s;
        }
    }

    static class SymbolsEx_ZH
    implements ISymbolsEx {
        private final String[] halfYears = new String[]{"\u4e0a\u534a\u5e74", "\u4e0b\u534a\u5e74"};
        private final String[] shortQuaters = new String[]{"\u4e00\u5b63\u5ea6", "\u4e8c\u5b63\u5ea6", "\u4e09\u5b63\u5ea6", "\u56db\u5b63\u5ea6"};
        private final String[] xuns = new String[]{"\u4e0a\u65ec", "\u4e2d\u65ec", "\u4e0b\u65ec"};
        private final String[] months = new String[]{"\u4e00\u6708", "\u4e8c\u6708", "\u4e09\u6708", "\u56db\u6708", "\u4e94\u6708", "\u516d\u6708", "\u4e03\u6708", "\u516b\u6708", "\u4e5d\u6708", "\u5341\u6708", "\u5341\u4e00\u6708", "\u5341\u4e8c\u6708", "\u5341\u4e09\u671f", "\u5341\u56db\u671f", "\u5341\u4e94\u671f", "\u5341\u516d\u671f", "\u5341\u4e03\u671f", "\u5341\u516b\u671f", "\u5341\u4e5d\u671f", "\u4e8c\u5341\u671f"};
        private final String[] shortMonths = new String[]{"1\u6708", "2\u6708", "3\u6708", "4\u6708", "5\u6708", "6\u6708", "7\u6708", "8\u6708", "9\u6708", "10\u6708", "11\u6708", "12\u6708", "13\u671f", "14\u671f", "15\u671f", "16\u671f", "17\u671f", "18\u671f", "19\u671f", "20\u671f"};
        private final String[] month0s = new String[]{"0\u671f", "\u7b2c0\u671f"};

        SymbolsEx_ZH() {
        }

        @Override
        public String[] getHALFYEAR_VALUES() {
            return this.halfYears;
        }

        @Override
        public String[] getShortQuater() {
            return this.shortQuaters;
        }

        @Override
        public String[] getXun_values() {
            return this.xuns;
        }

        @Override
        public String[] getMonths() {
            return this.months;
        }

        @Override
        public String[] getShortMonths() {
            return this.shortMonths;
        }

        @Override
        public String[] getMonth0s() {
            return this.month0s;
        }
    }
}

