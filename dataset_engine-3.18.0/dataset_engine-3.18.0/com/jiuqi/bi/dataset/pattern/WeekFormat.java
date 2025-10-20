/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.time.TimeCalcException
 *  com.jiuqi.bi.util.time.TimeHelper
 */
package com.jiuqi.bi.dataset.pattern;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.pattern.DataSetTimeDimFormat;
import com.jiuqi.bi.dataset.pattern.TimeDimDataPattern;
import com.jiuqi.bi.dataset.pattern.WeekShowPattern;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeHelper;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class WeekFormat
extends DataSetTimeDimFormat {
    private static final long serialVersionUID = -9060128730673537281L;
    private BIDataSetFieldInfo info;
    private TimeDimDataPattern dataPattern;
    private WeekShowPattern showPattern;
    private SimpleDateFormat timekeyFormat = new SimpleDateFormat("yyyyMMdd");

    public WeekFormat(BIDataSetFieldInfo fieldInfo) {
        this.info = fieldInfo;
        this.dataPattern = TimeDimDataPattern.find(this.info.getDataPattern());
        this.showPattern = WeekShowPattern.find(this.info.getShowPattern());
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (toAppendTo == null) {
            toAppendTo = new StringBuffer();
        }
        if (obj == null) {
            return toAppendTo;
        }
        if (this.dataPattern == TimeDimDataPattern.TIMEKEY) {
            String source = obj.toString();
            try {
                int week;
                Date d = this.timekeyFormat.parse(source);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int year = c.get(1);
                try {
                    week = TimeHelper.getTimeValue((Calendar)c, (int)6);
                }
                catch (TimeCalcException e) {
                    week = -1;
                }
                toAppendTo.append(this.showPattern.getShowText(year, week));
            }
            catch (ParseException e) {
                toAppendTo.append(source);
            }
        }
        return toAppendTo;
    }
}

