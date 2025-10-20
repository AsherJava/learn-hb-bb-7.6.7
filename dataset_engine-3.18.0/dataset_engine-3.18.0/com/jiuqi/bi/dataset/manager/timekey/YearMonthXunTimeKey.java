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

public class YearMonthXunTimeKey
extends AbstractTimeKeyField {
    private int yearIndex;
    private int monthIndex;
    private int xunIndex;
    private BIDataSetFieldInfo yearField;
    private BIDataSetFieldInfo monthField;
    private BIDataSetFieldInfo xunField;
    private SimpleDateFormat yearFormat;
    private SimpleDateFormat monthFormat;
    private SimpleDateFormat xunFormat;

    public YearMonthXunTimeKey(int yearIndex, int monthIndex, int xunIndex, BIDataSetFieldInfo yearField, BIDataSetFieldInfo monthField, BIDataSetFieldInfo xunField) {
        this.yearIndex = yearIndex;
        this.monthIndex = monthIndex;
        this.xunIndex = xunIndex;
        this.yearField = yearField;
        this.monthField = monthField;
        this.xunField = xunField;
        if (StringUtils.isNotEmpty((String)yearField.getDataPattern())) {
            this.yearFormat = new DateFormatEx(yearField.getDataPattern());
        }
        if (StringUtils.isNotEmpty((String)monthField.getDataPattern())) {
            this.monthFormat = new DateFormatEx(monthField.getDataPattern());
        }
        if (StringUtils.isNotEmpty((String)xunField.getDataPattern())) {
            this.xunFormat = new DateFormatEx(xunField.getDataPattern());
        }
    }

    @Override
    public String generateTimekey(Object[] data) {
        Object year = data[this.yearIndex];
        Object month = data[this.monthIndex];
        Object xun = data[this.xunIndex];
        int yearValue = AbstractTimeKeyField.parseValueToYear(year, this.yearField.getValType(), this.yearFormat);
        int monthValue = AbstractTimeKeyField.parseValueToMonth(month, this.monthField.getValType(), this.monthFormat);
        int xunValue = AbstractTimeKeyField.parseValueToXun(xun, this.xunField.getValType(), this.xunFormat);
        if (yearValue != -1 && monthValue != -1 && xunValue != -1) {
            StringBuilder builder = new StringBuilder();
            builder.append(yearValue);
            if (monthValue < 10) {
                builder.append('0');
            }
            builder.append(monthValue);
            if (xunValue < 10) {
                builder.append('0');
            }
            builder.append(xunValue);
            return builder.toString();
        }
        return null;
    }
}

