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

public class YearMonthTimeKey
extends AbstractTimeKeyField {
    private int yearIndex;
    private int monthIndex;
    private BIDataSetFieldInfo yearField;
    private BIDataSetFieldInfo monthField;
    private SimpleDateFormat yearFormat;
    private SimpleDateFormat monthFormat;

    public YearMonthTimeKey(int yearIndex, int monthIndex, BIDataSetFieldInfo yearField, BIDataSetFieldInfo monthField) {
        this.yearIndex = yearIndex;
        this.monthIndex = monthIndex;
        this.yearField = yearField;
        this.monthField = monthField;
        if (StringUtils.isNotEmpty((String)yearField.getDataPattern())) {
            this.yearFormat = new DateFormatEx(yearField.getDataPattern());
        }
        if (StringUtils.isNotEmpty((String)monthField.getDataPattern())) {
            this.monthFormat = new DateFormatEx(monthField.getDataPattern());
        }
    }

    @Override
    public String generateTimekey(Object[] data) {
        Object year = data[this.yearIndex];
        Object month = data[this.monthIndex];
        int yearValue = AbstractTimeKeyField.parseValueToYear(year, this.yearField.getValType(), this.yearFormat);
        int monthValue = AbstractTimeKeyField.parseValueToMonth(month, this.monthField.getValType(), this.monthFormat);
        if (yearValue != -1 && monthValue != -1) {
            StringBuilder builder = new StringBuilder();
            builder.append(yearValue);
            if (monthValue < 10) {
                builder.append('0');
            }
            builder.append(monthValue).append("01");
            return builder.toString();
        }
        return null;
    }
}

