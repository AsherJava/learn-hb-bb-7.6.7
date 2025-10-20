/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.pattern;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.pattern.DataSetTimeDimFormat;
import com.jiuqi.bi.dataset.pattern.MonthShowPattern;
import com.jiuqi.bi.dataset.pattern.TimeDimDataPattern;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MonthFormat
extends DataSetTimeDimFormat {
    private static final long serialVersionUID = -7373883823371886167L;
    private BIDataSetFieldInfo info;
    private TimeDimDataPattern dataPattern;
    private MonthShowPattern showPattern;
    private SimpleDateFormat timekeyFormat = new SimpleDateFormat("yyyyMMdd");

    public MonthFormat(BIDataSetFieldInfo fieldInfo) {
        this.info = fieldInfo;
        String dataPatternString = this.info.getDataPattern();
        this.dataPattern = TimeDimDataPattern.find(dataPatternString);
        String showPatternString = this.info.getShowPattern();
        this.showPattern = MonthShowPattern.find(showPatternString);
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
                toAppendTo.append(this.showPattern.getShowText(month + 1));
            }
            catch (ParseException e) {
                toAppendTo.append(source);
            }
        } else {
            int month;
            if (obj instanceof Integer) {
                month = (Integer)obj;
            } else if (obj instanceof String) {
                try {
                    month = Integer.parseInt((String)obj);
                }
                catch (NumberFormatException e) {
                    month = -1;
                }
            } else {
                month = -1;
            }
            if (month != -1) {
                toAppendTo.append(this.showPattern.getShowText(month));
            }
        }
        return toAppendTo;
    }
}

