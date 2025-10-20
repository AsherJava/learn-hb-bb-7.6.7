/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.text.DateFormatEx
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.bi.util.time.ITimeReader
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeCalculator
 *  com.jiuqi.bi.util.time.TimeFieldInfo
 */
package com.jiuqi.bi.dataset.manager.timekey;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.manager.timekey.AbstractTimeKeyField;
import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.time.ITimeReader;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeCalculator;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import java.text.SimpleDateFormat;

public class YearWeekTimeKey
extends AbstractTimeKeyField {
    private int yearIndex;
    private int weekIndex;
    private BIDataSetFieldInfo yearField;
    private BIDataSetFieldInfo weekField;
    private SimpleDateFormat yearFormat;
    private SimpleDateFormat weekFormat;
    private TimeCalculator calculator;

    public YearWeekTimeKey(int yearIndex, int weekIndex, BIDataSetFieldInfo yearField, BIDataSetFieldInfo weekField) {
        this.yearIndex = yearIndex;
        this.weekIndex = weekIndex;
        this.yearField = yearField;
        this.weekField = weekField;
        if (StringUtils.isNotEmpty((String)yearField.getDataPattern())) {
            this.yearFormat = new DateFormatEx(yearField.getDataPattern());
        }
        if (StringUtils.isNotEmpty((String)weekField.getDataPattern())) {
            this.weekFormat = new DateFormatEx(weekField.getDataPattern());
        }
        TimeFieldInfo yearFieldInfo = new TimeFieldInfo("year", 0, yearField.getDataPattern(), false);
        TimeFieldInfo weekFieldInfo = new TimeFieldInfo("week", 6, weekField.getDataPattern(), false);
        try {
            this.calculator = TimeCalculator.createCalculator((TimeFieldInfo[])new TimeFieldInfo[]{yearFieldInfo, weekFieldInfo});
        }
        catch (TimeCalcException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String generateTimekey(Object[] data) {
        Object year = data[this.yearIndex];
        Object week = data[this.weekIndex];
        int yearValue = AbstractTimeKeyField.parseValueToYear(year, this.yearField.getValType(), this.yearFormat);
        int weekValue = AbstractTimeKeyField.parseValueToQuarter(week, this.weekField.getValType(), this.weekFormat);
        try {
            this.calculator.setValue(0, (Object)yearValue);
            this.calculator.setValue(1, (Object)weekValue);
            ITimeReader reader = this.calculator.calculate();
            return reader.getTimeKey();
        }
        catch (TimeCalcException e) {
            return null;
        }
    }
}

