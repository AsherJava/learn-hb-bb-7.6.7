/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.pattern;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.pattern.DataSetTimeDimFormat;
import com.jiuqi.bi.dataset.pattern.HalfYearShowPattern;
import com.jiuqi.bi.dataset.pattern.TimeDimDataPattern;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HalfYearFormat
extends DataSetTimeDimFormat {
    private static final long serialVersionUID = -2460279100434905148L;
    private BIDataSetFieldInfo info;
    private TimeDimDataPattern dataPattern;
    private HalfYearShowPattern showPattern;
    private SimpleDateFormat timekeyFormat = new SimpleDateFormat("yyyyMMdd");

    public HalfYearFormat(BIDataSetFieldInfo fieldInfo) {
        this.info = fieldInfo;
        String dataPatternString = this.info.getDataPattern();
        this.dataPattern = TimeDimDataPattern.find(dataPatternString);
        String showPatternString = this.info.getShowPattern();
        this.showPattern = HalfYearShowPattern.find(showPatternString);
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
                Date d = this.timekeyFormat.parse(source);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int month = c.get(2);
                toAppendTo.append(this.showPattern.getShowText(month < 6 ? 1 : 2));
            }
            catch (ParseException e) {
                toAppendTo.append(source);
            }
        } else {
            int half;
            if (obj instanceof Integer) {
                half = (Integer)obj;
            } else if (obj instanceof String) {
                try {
                    half = Integer.parseInt((String)obj);
                }
                catch (NumberFormatException e) {
                    half = -1;
                }
            } else {
                half = -1;
            }
            if (half != -1) {
                toAppendTo.append(this.showPattern.getShowText(half));
            }
        }
        return toAppendTo;
    }
}

