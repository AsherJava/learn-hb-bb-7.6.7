/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.dataset.manager.timekey;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.manager.timekey.AbstractTimeKeyField;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.StringUtils;
import java.text.SimpleDateFormat;

public class YearMonthDayTimeKey
extends AbstractTimeKeyField {
    private int yearIndex;
    private int monthIndex;
    private int dayIndex;
    private BIDataSetFieldInfo yearField;
    private BIDataSetFieldInfo monthField;
    private BIDataSetFieldInfo dayField;
    private SimpleDateFormat yearFormat;
    private SimpleDateFormat monthFormat;
    private SimpleDateFormat dayFormat;

    public YearMonthDayTimeKey(int yearIndex, int monthIndex, int dayIndex, BIDataSetFieldInfo yearField, BIDataSetFieldInfo monthField, BIDataSetFieldInfo dayField) {
        this.yearIndex = yearIndex;
        this.monthIndex = monthIndex;
        this.dayIndex = dayIndex;
        this.yearField = yearField;
        this.monthField = monthField;
        this.dayField = dayField;
        if (StringUtils.isNotEmpty((String)yearField.getDataPattern())) {
            this.yearFormat = new DateFormatEx(yearField.getDataPattern());
        }
        if (StringUtils.isNotEmpty((String)monthField.getDataPattern())) {
            this.monthFormat = new DateFormatEx(monthField.getDataPattern());
        }
        if (StringUtils.isNotEmpty((String)dayField.getDataPattern())) {
            this.dayFormat = new DateFormatEx(dayField.getDataPattern());
        }
    }

    @Override
    public String generateTimekey(Object[] data) {
        Object year = data[this.yearIndex];
        Object month = data[this.monthIndex];
        Object day = data[this.dayIndex];
        int yearValue = AbstractTimeKeyField.parseValueToYear(year, this.yearField.getValType(), this.yearFormat);
        int monthValue = AbstractTimeKeyField.parseValueToMonth(month, this.monthField.getValType(), this.monthFormat);
        int dayValue = AbstractTimeKeyField.parseValueToXun(day, this.dayField.getValType(), this.dayFormat);
        if (yearValue != -1 && monthValue != -1 && dayValue != -1) {
            StringBuilder builder = new StringBuilder();
            builder.append(yearValue);
            if (monthValue < 10) {
                builder.append('0');
            }
            builder.append(monthValue);
            if (dayValue < 10) {
                builder.append('0');
            }
            builder.append(dayValue);
            return builder.toString();
        }
        return null;
    }
}

