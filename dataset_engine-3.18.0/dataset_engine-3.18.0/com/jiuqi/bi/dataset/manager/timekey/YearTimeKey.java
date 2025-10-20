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

public class YearTimeKey
extends AbstractTimeKeyField {
    private int yearIndex;
    private BIDataSetFieldInfo yearField;
    private SimpleDateFormat yearFormat;

    public YearTimeKey(int yearIndex, BIDataSetFieldInfo yearField) {
        this.yearIndex = yearIndex;
        this.yearField = yearField;
        if (StringUtils.isNotEmpty((String)yearField.getDataPattern())) {
            this.yearFormat = new DateFormatEx(yearField.getDataPattern());
        }
    }

    @Override
    public String generateTimekey(Object[] data) {
        Object year = data[this.yearIndex];
        int yearValue = AbstractTimeKeyField.parseValueToYear(year, this.yearField.getValType(), this.yearFormat);
        if (yearValue != -1) {
            return yearValue + "0101";
        }
        return null;
    }
}

