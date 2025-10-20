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

public class YearHalfyearTimeKey
extends AbstractTimeKeyField {
    private int yearIndex;
    private int halfIndex;
    private BIDataSetFieldInfo yearField;
    private BIDataSetFieldInfo halfyearField;
    private SimpleDateFormat yearFormat;
    private SimpleDateFormat halfyearFormat;

    public YearHalfyearTimeKey(int yearIndex, int halfyearIndex, BIDataSetFieldInfo yearField, BIDataSetFieldInfo halfyearField) {
        this.yearIndex = yearIndex;
        this.halfIndex = halfyearIndex;
        this.yearField = yearField;
        this.halfyearField = halfyearField;
        if (StringUtils.isNotEmpty((String)yearField.getDataPattern())) {
            this.yearFormat = new DateFormatEx(yearField.getDataPattern());
        }
        if (StringUtils.isNotEmpty((String)halfyearField.getDataPattern())) {
            this.halfyearFormat = new DateFormatEx(halfyearField.getDataPattern());
        }
    }

    @Override
    public String generateTimekey(Object[] data) {
        Object year = data[this.yearIndex];
        Object halfyear = data[this.halfIndex];
        int yearValue = AbstractTimeKeyField.parseValueToYear(year, this.yearField.getValType(), this.yearFormat);
        int halfyearValue = AbstractTimeKeyField.parseValueToHalfyear(halfyear, this.halfyearField.getValType(), this.halfyearFormat);
        if (yearValue != -1 && halfyearValue != -1) {
            StringBuffer buf = new StringBuffer();
            buf.append(yearValue);
            if (halfyearValue == 1) {
                buf.append("01");
            } else {
                buf.append("07");
            }
            buf.append("01");
            return buf.toString();
        }
        return null;
    }
}

