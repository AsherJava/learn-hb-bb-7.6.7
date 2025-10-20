/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class FiscalCalendar
extends Calendar {
    private static final long serialVersionUID = 3090107753008772971L;
    private final int minMonth;
    private final int maxMonth;
    private int timeMonth = Integer.MIN_VALUE;
    public static final int MONTH_0 = -1;
    public static final int MONTH_13 = 12;
    public static final int MONTH_14 = 13;
    public static final int MONTH_15 = 14;
    public static final int MONTH_16 = 15;
    public static final int BC = 0;
    static final int BCE = 0;
    public static final int AD = 1;
    static final int CE = 1;
    static final int[] MONTH_LENGTH = new int[]{31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 30, 30, 30, 30};
    static final int[] LEAP_MONTH_LENGTH = new int[]{31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 30, 30, 30, 30};
    private static final int ONE_SECOND = 1000;
    private static final int ONE_MINUTE = 60000;
    private static final int ONE_HOUR = 3600000;
    static final int[] MIN_VALUES = new int[]{0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, -46800000, 0};
    static final int[] LEAST_MAX_VALUES = new int[]{1, 292269054, 11, 52, 4, 28, 365, 7, 4, 1, 11, 23, 59, 59, 999, 50400000, 1200000};
    static final int[] MAX_VALUES = new int[]{1, 292278994, 11, 53, 6, 31, 366, 7, 6, 1, 11, 23, 59, 59, 999, 50400000, 0x6DDD00};
    public static final int MAX_FISCALNUM = 20;
    private transient int[] stamp;
    private int nextStamp = 2;
    private static final int STAMP_UNSET = 0;
    private static final int STAMP_COMPUTED = 1;
    private static final int STAMP_FIRST = 2;

    public FiscalCalendar(int fiscalNum) {
        this(0, fiscalNum - 1);
    }

    public FiscalCalendar(int year, int month, int dayOfMonth, int fiscalNum) {
        this(0, fiscalNum - 1, year, month, dayOfMonth);
    }

    public FiscalCalendar(int minMonth, int maxMonth) {
        this.minMonth = minMonth;
        this.maxMonth = maxMonth;
        this.stamp = new int[17];
        this.checkFiscal();
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public FiscalCalendar(int minMonth, int maxMonth, int year, int month, int dayOfMonth) {
        this.minMonth = minMonth;
        this.maxMonth = maxMonth;
        this.stamp = new int[17];
        this.checkFiscal();
        this.set(1, year);
        this.set(2, month);
        this.set(5, dayOfMonth);
    }

    public FiscalCalendar(TimeZone zone, Locale aLocale, int minMonth, int maxMonth) {
        super(zone, aLocale);
        this.minMonth = minMonth;
        this.maxMonth = maxMonth;
        this.fields = new int[17];
        this.checkFiscal();
        this.setTimeInMillis(System.currentTimeMillis());
    }

    public FiscalCalendar(int minMonth, int maxMonth, Calendar date) {
        super(date.getTimeZone(), Locale.getDefault());
        this.minMonth = minMonth;
        this.maxMonth = maxMonth;
        this.fields = new int[17];
        this.checkFiscal();
        this.setTimeInMillis(date.getTimeInMillis());
        int month = date.get(2);
        if (month == -1 && minMonth == -1) {
            this.set(2, month);
        } else if (month > 11 && maxMonth > 11) {
            this.set(2, Math.min(maxMonth, month));
        }
    }

    private void checkFiscal() {
        if (this.minMonth != -1 && this.minMonth != 0) {
            throw new IllegalArgumentException("\u8d77\u59cb\u6708\u4efd\u9519\u8bef\uff0c\u53ea\u80fd\u4e3a\u7b2c0\u671f\u6216\u4e00\u6708\uff1a" + this.minMonth);
        }
        if (this.maxMonth < 11 || this.maxMonth >= 20) {
            throw new IllegalArgumentException("\u7ed3\u675f\u6708\u4efd\u9519\u8bef\uff0c\u4e0d\u5f97\u5c0f\u4e8e\u5341\u4e8c\u6708\u4e14\u6700\u591a20\u671f\uff1a" + this.maxMonth);
        }
    }

    public int getMinMonth() {
        return this.minMonth;
    }

    public int getMaxMonth() {
        return this.maxMonth;
    }

    @Override
    protected void computeTime() {
        this.resetFiscalTime();
        List<FieldValue> fieldValues = this.collectFields();
        this.time = this.timeOf(fieldValues);
    }

    private void resetFiscalTime() {
        if (!(this.isSet[1] && this.isSet[2] && this.isSet[5])) {
            return;
        }
        new FiscalBuilder().build();
    }

    private List<FieldValue> collectFields() {
        ArrayList<FieldValue> fieldValues = new ArrayList<FieldValue>();
        for (int i = 0; i < 17; ++i) {
            if (!this.isSet[i] || this.stamp[i] == 0) continue;
            FieldValue fv = new FieldValue(i, this.fields[i], this.stamp[i]);
            fieldValues.add(fv);
        }
        fieldValues.sort((f1, f2) -> f1.stamp - f2.stamp);
        return fieldValues;
    }

    private long timeOf(List<FieldValue> fieldValues) {
        Calendar calendar = Calendar.getInstance(this.getTimeZone());
        fieldValues.forEach(f -> {
            if (f.field == 2) {
                if (f.value < 0) {
                    calendar.set(2, 0);
                } else if (f.value > 11) {
                    calendar.set(2, 11);
                } else {
                    calendar.set(2, f.value);
                }
            } else {
                calendar.set(f.field, f.value);
            }
        });
        return calendar.getTimeInMillis();
    }

    @Override
    protected void computeFields() {
        Calendar calendar = Calendar.getInstance(this.getTimeZone());
        calendar.setTimeInMillis(this.time);
        for (int i = 0; i < 17; ++i) {
            this.fields[i] = i == 2 && this.timeMonth >= this.minMonth && this.timeMonth <= this.maxMonth ? this.timeMonth : calendar.get(i);
            this.isSet[i] = true;
            this.stamp[i] = 1;
        }
        this.areFieldsSet = true;
    }

    @Override
    public void setTimeInMillis(long millis) {
        this.timeMonth = Integer.MIN_VALUE;
        super.setTimeInMillis(millis);
    }

    @Override
    public void set(int field, int value) {
        this.setField(field, value);
        if (field == 1 || field == 2) {
            this.tryPinDayOfMonth();
        }
    }

    private void setField(int field, int value) {
        super.set(field, value);
        if (field == 2) {
            this.timeMonth = this.fields[field];
        }
        ++this.nextStamp;
        if (this.nextStamp == Integer.MAX_VALUE) {
            this.adjustStamp();
        }
    }

    @Override
    public void add(int field, int amount) {
        if (amount == 0) {
            return;
        }
        if (field < 0 || field >= 15) {
            throw new IllegalArgumentException();
        }
        this.complete();
        if (field == 1) {
            int year = this.internalGet(1);
            this.setField(1, year += amount);
            this.pinDayOfMonth();
        } else if (field == 2) {
            int year = this.internalGet(1);
            int month = this.internalGet(2) + amount - this.minMonth;
            int fiscalNum = this.maxMonth - this.minMonth + 1;
            int y_amount = month >= 0 ? month / fiscalNum : (month + 1) / fiscalNum - 1;
            if (y_amount != 0) {
                this.setField(1, year + y_amount);
            }
            if ((month %= fiscalNum) < 0) {
                month += fiscalNum;
            }
            this.setField(2, month + this.minMonth);
            this.pinDayOfMonth();
        } else if (field == 5) {
            int day;
            int year = this.internalGet(1);
            int month = this.internalGet(2);
            boolean yearChanged = false;
            boolean monthChanged = false;
            if (day > 0) {
                int monthLen = FiscalCalendar.getMonthLength(year, month);
                for (day = this.internalGet(5) + amount; day > monthLen; day -= monthLen) {
                    if (month + 1 > this.maxMonth) {
                        month = this.minMonth;
                        ++year;
                        yearChanged = true;
                    } else {
                        ++month;
                    }
                    monthChanged = true;
                    monthLen = FiscalCalendar.getMonthLength(year, month);
                }
            } else {
                while (day <= 0) {
                    if (month - 1 < this.minMonth) {
                        month = this.maxMonth;
                        --year;
                        yearChanged = true;
                    } else {
                        --month;
                    }
                    monthChanged = true;
                    int monthLen = FiscalCalendar.getMonthLength(year, month);
                    day += monthLen;
                }
            }
            if (yearChanged) {
                this.setField(1, year);
            }
            if (monthChanged) {
                this.setField(2, month);
            }
            this.setField(5, day);
        } else {
            int raw = this.internalGet(field);
            this.setField(field, raw + amount);
        }
    }

    private void tryPinDayOfMonth() {
        if (this.isSet[1] && this.isSet[2] && this.isSet[5]) {
            this.pinDayOfMonth();
        }
    }

    private void pinDayOfMonth() {
        int monthLen;
        int year = this.internalGet(1);
        int month = this.internalGet(2);
        int day = this.internalGet(5);
        this.set(5, day > (monthLen = FiscalCalendar.getMonthLength(year, month)) ? monthLen : day);
    }

    public static int getMonthLength(int year, int month) {
        if (month < 0 || month > 11) {
            return 30;
        }
        return FiscalCalendar.isLeapYear(year) ? LEAP_MONTH_LENGTH[month] : MONTH_LENGTH[month];
    }

    @Override
    public void roll(int field, boolean up) {
        this.add(field, up ? 1 : -1);
    }

    @Override
    public int getMinimum(int field) {
        return field == 2 ? this.minMonth : MIN_VALUES[field];
    }

    @Override
    public int getMaximum(int field) {
        switch (field) {
            case 2: {
                return this.maxMonth;
            }
            case 6: {
                int m = this.maxMonth - 11 + (this.minMonth == -1 ? 1 : 0);
                return MAX_VALUES[6] + m * 30;
            }
        }
        return MAX_VALUES[field];
    }

    @Override
    public int getGreatestMinimum(int field) {
        return MIN_VALUES[field];
    }

    @Override
    public int getLeastMaximum(int field) {
        if (field == 6) {
            int m = this.maxMonth - 11 + (this.minMonth == -1 ? 1 : 0);
            return LEAST_MAX_VALUES[6] + m * 30;
        }
        return LEAST_MAX_VALUES[field];
    }

    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    public void reset() {
        this.clear();
        Arrays.fill(this.stamp, 0);
    }

    public void reset(int field) {
        this.clear(field);
        this.stamp[field] = 0;
    }

    @Override
    public Object clone() {
        FiscalCalendar result = (FiscalCalendar)super.clone();
        result.stamp = Arrays.copyOf(this.stamp, this.stamp.length);
        return result;
    }

    private void adjustStamp() {
        int min;
        int max = 2;
        int newStamp = 2;
        do {
            int i;
            min = Integer.MAX_VALUE;
            for (i = 0; i < this.stamp.length; ++i) {
                int v = this.stamp[i];
                if (v >= newStamp && min > v) {
                    min = v;
                }
                if (max >= v) continue;
                max = v;
            }
            if (max != min && min == Integer.MAX_VALUE) break;
            for (i = 0; i < this.stamp.length; ++i) {
                if (this.stamp[i] != min) continue;
                this.stamp[i] = newStamp;
            }
            ++newStamp;
        } while (min != max);
        this.nextStamp = newStamp;
    }

    @Override
    public int hashCode() {
        return super.hashCode() * 31 + this.timeMonth;
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        if (obj instanceof FiscalCalendar) {
            return ((FiscalCalendar)obj).timeMonth == this.timeMonth;
        }
        return this.timeMonth == Integer.MIN_VALUE || this.timeMonth >= 0 && this.timeMonth <= 11;
    }

    @Override
    public int compareTo(Calendar anotherCalendar) {
        int cmp = this.get(1) - anotherCalendar.get(1);
        if (cmp != 0) {
            return cmp;
        }
        cmp = this.get(2) - anotherCalendar.get(2);
        if (cmp != 0) {
            return cmp;
        }
        cmp = this.get(5) - anotherCalendar.get(5);
        if (cmp != 0) {
            return cmp;
        }
        return super.compareTo(anotherCalendar);
    }

    private final class FiscalBuilder {
        private int year;
        private int month;
        private int day;
        private boolean yearChanged;
        private boolean monthChanged;
        private boolean dayChanged;

        private FiscalBuilder() {
            this.year = FiscalCalendar.this.fields[1];
            this.month = FiscalCalendar.this.fields[2];
            this.day = FiscalCalendar.this.fields[5];
        }

        public void build() {
            this.adjustMonth();
            this.adjustDay();
            this.update();
        }

        private void adjustMonth() {
            block3: {
                block2: {
                    if (this.month >= FiscalCalendar.this.minMonth) break block2;
                    while (this.month < FiscalCalendar.this.minMonth) {
                        --this.year;
                        this.month = FiscalCalendar.this.maxMonth + 1 + this.month - FiscalCalendar.this.minMonth;
                        this.monthChanged = true;
                        this.yearChanged = true;
                    }
                    break block3;
                }
                if (this.month <= FiscalCalendar.this.maxMonth) break block3;
                while (this.month > FiscalCalendar.this.maxMonth) {
                    ++this.year;
                    this.month = this.month - FiscalCalendar.this.maxMonth - 1 + FiscalCalendar.this.minMonth;
                    this.monthChanged = true;
                    this.yearChanged = true;
                }
            }
        }

        private void adjustDay() {
            if (this.day == 0) {
                throw new IllegalArgumentException("illegal day = " + this.day);
            }
            if (this.day < 0) {
                while (this.day < 0) {
                    --this.month;
                    this.adjustMonth();
                    int days = FiscalCalendar.getMonthLength(this.year, this.month);
                    this.day = days + this.day + 1;
                    this.dayChanged = true;
                    this.monthChanged = true;
                }
            } else {
                int days = FiscalCalendar.getMonthLength(this.year, this.month);
                while (this.day > days) {
                    ++this.month;
                    this.day -= days;
                    this.adjustMonth();
                    days = FiscalCalendar.getMonthLength(this.year, this.month);
                    this.dayChanged = true;
                    this.monthChanged = true;
                }
            }
        }

        private void update() {
            if (this.dayChanged) {
                FiscalCalendar.this.set(5, this.day);
            }
            if (this.monthChanged) {
                FiscalCalendar.this.set(2, this.month);
            }
            if (this.yearChanged) {
                FiscalCalendar.this.set(1, this.year);
            }
        }
    }

    private static final class FieldValue {
        public final int field;
        public final int value;
        public final int stamp;

        public FieldValue(int field, int value, int stamp) {
            this.field = field;
            this.value = value;
            this.stamp = stamp;
        }

        public String toString() {
            return this.field + " = " + this.value + " @ " + this.stamp;
        }
    }
}

