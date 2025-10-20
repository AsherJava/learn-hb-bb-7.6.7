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

public class YearQuarterTimeKey
extends AbstractTimeKeyField {
    private int yearIndex;
    private int quarterIndex;
    private BIDataSetFieldInfo yearField;
    private BIDataSetFieldInfo quarterField;
    private SimpleDateFormat yearFormat;
    private SimpleDateFormat quarterFormat;

    public YearQuarterTimeKey(int yearIndex, int quarterIndex, BIDataSetFieldInfo yearField, BIDataSetFieldInfo quarterField) {
        this.yearIndex = yearIndex;
        this.quarterIndex = quarterIndex;
        this.yearField = yearField;
        this.quarterField = quarterField;
        if (StringUtils.isNotEmpty((String)yearField.getDataPattern())) {
            this.yearFormat = new DateFormatEx(yearField.getDataPattern());
        }
        if (StringUtils.isNotEmpty((String)quarterField.getDataPattern())) {
            this.quarterFormat = new DateFormatEx(quarterField.getDataPattern());
        }
    }

    @Override
    public String generateTimekey(Object[] data) {
        Object year = data[this.yearIndex];
        Object quarter = data[this.quarterIndex];
        int yearValue = AbstractTimeKeyField.parseValueToYear(year, this.yearField.getValType(), this.yearFormat);
        int quarterValue = AbstractTimeKeyField.parseValueToQuarter(quarter, this.quarterField.getValType(), this.quarterFormat);
        if (yearValue != -1) {
            if (quarterValue == 1) {
                return yearValue + "0101";
            }
            if (quarterValue == 2) {
                return yearValue + "0401";
            }
            if (quarterValue == 3) {
                return yearValue + "0701";
            }
            if (quarterValue == 4) {
                return yearValue + "1001";
            }
            return null;
        }
        return null;
    }
}

