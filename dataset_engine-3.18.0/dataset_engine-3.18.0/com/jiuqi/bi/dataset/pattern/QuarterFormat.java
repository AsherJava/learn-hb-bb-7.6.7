/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.pattern;

import com.jiuqi.bi.dataset.BIDataSetFieldInfo;
import com.jiuqi.bi.dataset.pattern.DataSetTimeDimFormat;
import com.jiuqi.bi.dataset.pattern.QuarterShowPattern;
import com.jiuqi.bi.dataset.pattern.TimeDimDataPattern;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QuarterFormat
extends DataSetTimeDimFormat {
    private static final long serialVersionUID = -8037887633415752251L;
    private BIDataSetFieldInfo info;
    private TimeDimDataPattern dataPattern;
    private QuarterShowPattern showPattern;
    private SimpleDateFormat timekeyFormat = new SimpleDateFormat("yyyyMMdd");

    public QuarterFormat(BIDataSetFieldInfo fieldInfo) {
        this.info = fieldInfo;
        String dataPatternString = this.info.getDataPattern();
        this.dataPattern = TimeDimDataPattern.find(dataPatternString);
        String showPatternString = this.info.getShowPattern();
        this.showPattern = QuarterShowPattern.find(showPatternString);
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        block17: {
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
                    if (month < 3) {
                        toAppendTo.append(this.showPattern.getShowText(1));
                        break block17;
                    }
                    if (month < 6) {
                        toAppendTo.append(this.showPattern.getShowText(2));
                        break block17;
                    }
                    if (month < 9) {
                        toAppendTo.append(this.showPattern.getShowText(3));
                        break block17;
                    }
                    if (month < 12) {
                        toAppendTo.append(this.showPattern.getShowText(4));
                        break block17;
                    }
                    toAppendTo.append(this.showPattern.getShowText(-1));
                }
                catch (ParseException e) {
                    toAppendTo.append(source);
                }
            } else {
                int quarter;
                if (obj instanceof Integer) {
                    quarter = (Integer)obj;
                } else if (obj instanceof String) {
                    try {
                        quarter = Integer.parseInt((String)obj);
                    }
                    catch (NumberFormatException e) {
                        quarter = -1;
                    }
                } else {
                    quarter = -1;
                }
                if (quarter != -1) {
                    toAppendTo.append(this.showPattern.getShowText(quarter));
                }
            }
        }
        return toAppendTo;
    }
}

