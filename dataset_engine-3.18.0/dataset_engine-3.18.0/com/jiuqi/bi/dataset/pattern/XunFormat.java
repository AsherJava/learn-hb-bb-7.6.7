/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.pattern;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.pattern.DataSetTimeDimFormat;
import com.jiuqi.bi.dataset.pattern.TimeDimDataPattern;
import com.jiuqi.bi.dataset.pattern.XunShowPattern;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class XunFormat
extends DataSetTimeDimFormat {
    private static final long serialVersionUID = -7636807282270022184L;
    private BIDataSetFieldInfo info;
    private TimeDimDataPattern dataPattern;
    private XunShowPattern showPattern;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    public XunFormat(BIDataSetFieldInfo fieldInfo) {
        this.info = fieldInfo;
        String dataPatternString = this.info.getDataPattern();
        this.dataPattern = TimeDimDataPattern.find(dataPatternString);
        String showPatternString = this.info.getShowPattern();
        this.showPattern = XunShowPattern.find(showPatternString);
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
                Date d = this.sdf.parse(source);
                Calendar c = Calendar.getInstance();
                c.setTime(d);
                int day = c.get(5);
                toAppendTo.append(this.showPattern.getShowText(day));
            }
            catch (ParseException e) {
                toAppendTo.append(source);
            }
        } else {
            int day;
            if (obj instanceof Integer) {
                day = (Integer)obj;
            } else if (obj instanceof String) {
                try {
                    day = Integer.parseInt((String)obj);
                }
                catch (NumberFormatException e) {
                    day = -1;
                }
            } else {
                day = -1;
            }
            if (day != -1) {
                toAppendTo.append(this.showPattern.getShowText(day));
            }
        }
        return toAppendTo;
    }
}

