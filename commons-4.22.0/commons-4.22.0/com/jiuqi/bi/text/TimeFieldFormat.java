/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.DataTypes
 */
package com.jiuqi.bi.text;

import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.bi.text.DateFormatTransfer;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.bi.util.time.IFormatProvider;
import com.jiuqi.bi.util.time.TimeCalcError;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeFieldInfo;
import com.jiuqi.bi.util.time.TimeFormatManager;
import com.jiuqi.bi.util.time.TimeHelper;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeFieldFormat
extends Format {
    private static final long serialVersionUID = -1834674616002331587L;
    private final Locale locale;
    private final int granularity;
    private final int dataType;
    private final boolean timeKey;
    private final String dataPattern;
    private final String showPattern;
    private int fiscalMinMonth = -1;
    private int fiscalMaxMonth = -1;
    private DateFormatEx dataFormat;
    private DateFormatEx showFormat;
    private IFormatProvider.Parser customParser;
    private IFormatProvider.Formatter customFormatter;

    public TimeFieldFormat(int granularity, String showPattern, int dataType, boolean isTimeKey, String dataPattern) {
        this(granularity, showPattern, dataType, isTimeKey, dataPattern, Locale.getDefault());
    }

    public TimeFieldFormat(int granularity, String showPattern, int dataType, boolean isTimeKey, String dataPattern, Locale locale) {
        this.locale = locale;
        try {
            if (dataPattern == null || dataPattern.trim().isEmpty()) {
                dataPattern = TimeHelper.getDefaultDataPattern(granularity, isTimeKey);
            }
            if (showPattern == null || showPattern.trim().isEmpty()) {
                showPattern = TimeHelper.getDefaultShowPattern(granularity, isTimeKey);
            }
        }
        catch (TimeCalcException e) {
            throw new IllegalArgumentException(e);
        }
        this.dataType = dataType;
        this.timeKey = isTimeKey;
        this.dataPattern = dataPattern;
        this.showPattern = showPattern.startsWith("${") && showPattern.endsWith("}") ? showPattern : DateFormatTransfer.transfer(showPattern, locale);
        this.granularity = granularity;
        this.build();
    }

    private void build() {
        try {
            this.customParser = TimeFormatManager.tryCreateParser(new TimeFieldInfo("", this.granularity, this.dataPattern, this.timeKey), this.locale);
        }
        catch (TimeCalcException e) {
            throw new TimeCalcError(e);
        }
        if (this.customParser == null) {
            this.dataFormat = new DateFormatEx(this.dataPattern, this.locale);
            if (this.fiscalMinMonth >= 0 && this.fiscalMaxMonth >= 12) {
                this.dataFormat.setCalendar(TimeHelper.newCalendar(this.fiscalMinMonth, this.fiscalMaxMonth));
            }
        }
        try {
            this.customFormatter = TimeFormatManager.tryCreateFormatter(new TimeFieldInfo("", this.granularity, this.showPattern, this.timeKey), this.locale);
        }
        catch (TimeCalcException e) {
            throw new TimeCalcError(e);
        }
        if (this.customFormatter == null) {
            this.showFormat = new DateFormatEx(this.showPattern, this.locale);
        }
    }

    public int getFiscalMinMonth() {
        return this.fiscalMinMonth;
    }

    public int getFiscalMaxMonth() {
        return this.fiscalMaxMonth;
    }

    public void setFiscalMonth(int minMonth, int maxMonth) {
        if (this.fiscalMinMonth == minMonth && this.fiscalMaxMonth == maxMonth) {
            return;
        }
        if (this.dataFormat != null) {
            this.dataFormat.setCalendar(TimeHelper.newCalendar(minMonth, maxMonth));
        }
        this.fiscalMinMonth = minMonth;
        this.fiscalMaxMonth = maxMonth;
    }

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (obj == null) {
            return null;
        }
        if (this.dataType == 6) {
            Calendar date;
            try {
                date = this.parserCalendar((String)obj);
            }
            catch (ParseException e) {
                throw new IllegalArgumentException("\u5bf9\u65f6\u95f4\u7ef4\u5ea6\u5b57\u6bb5\u8fdb\u884c\u683c\u5f0f\u5316\u5904\u7406\u65f6\u9047\u5230\u9519\u8bef\u7684\u6570\u503c\uff1a\"" + obj + "\"\u3002", e);
            }
            return this.formatCalendar(date, toAppendTo, pos);
        }
        if (this.dataType == 2) {
            if (obj instanceof Calendar) {
                return this.formatCalendar((Calendar)obj, toAppendTo, pos);
            }
            if (this.customFormatter != null) {
                Calendar date = Calendar.getInstance(this.locale);
                if (obj instanceof Date) {
                    date.setTime((Date)obj);
                } else {
                    date.setTimeInMillis(((Number)obj).longValue());
                }
                return this.formatCalendar(date, toAppendTo, pos);
            }
            return this.showFormat.format(obj, toAppendTo, pos);
        }
        return this.formatNumber(obj, toAppendTo, pos);
    }

    private Calendar parserCalendar(String source) throws ParseException {
        if (this.customParser == null) {
            return this.dataFormat.parseCalendar(source);
        }
        if (this.timeKey) {
            return (Calendar)this.customParser.parse(source);
        }
        int value = (Integer)this.customParser.parse(source);
        Calendar date = TimeHelper.newCalendar(this.fiscalMinMonth, this.fiscalMaxMonth);
        date.set(this.granularity, value);
        return date;
    }

    private StringBuffer formatCalendar(Calendar date, StringBuffer toAppendTo, FieldPosition pos) {
        if (this.customFormatter != null) {
            String text;
            if (this.timeKey) {
                text = this.customFormatter.format(date);
            } else {
                int value;
                try {
                    value = TimeHelper.getTimeValue(date, this.granularity);
                }
                catch (TimeCalcException e) {
                    throw new TimeCalcError(e);
                }
                text = this.customFormatter.format(value);
            }
            return toAppendTo.append(text);
        }
        return this.showFormat.formatCalendar(date, toAppendTo, pos);
    }

    private StringBuffer formatNumber(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        int term = 0;
        if (DataTypes.isNumber((int)this.dataType)) {
            term = ((Number)obj).intValue();
        }
        if (this.customFormatter != null) {
            return toAppendTo.append(this.customFormatter.format(term));
        }
        Calendar date = TimeHelper.newCalendar(this.fiscalMinMonth, this.fiscalMaxMonth);
        date.setFirstDayOfWeek(2);
        switch (this.granularity) {
            case 0: {
                date.set(term, 0, 1);
                break;
            }
            case 1: {
                date.set(2, term == 1 ? 0 : 6);
                date.set(5, 1);
                break;
            }
            case 2: {
                int v1 = term;
                int month = -1;
                if (v1 == 1) {
                    month = 0;
                } else if (v1 == 2) {
                    month = 3;
                } else if (v1 == 3) {
                    month = 6;
                } else if (v1 == 4) {
                    month = 9;
                }
                if (month == -1) {
                    date.setTimeInMillis(0L);
                    break;
                }
                date.set(2, month);
                date.set(5, 1);
                break;
            }
            case 3: {
                if (term >= date.getMinimum(2) + 1 && term <= date.getMaximum(2) + 1) {
                    date.set(5, 1);
                    date.set(2, term - 1);
                    break;
                }
                date.setTimeInMillis(0L);
                break;
            }
            case 4: {
                int v2 = term;
                int day = -1;
                if (v2 == 1) {
                    day = 1;
                } else if (v2 == 2) {
                    day = 11;
                } else if (v2 == 3) {
                    day = 21;
                }
                if (day == -1) {
                    date.setTimeInMillis(0L);
                    break;
                }
                date.set(5, day);
                break;
            }
            case 6: {
                try {
                    if (term == 0) {
                        date.set(6, 1);
                        TimeHelper.alignWeekBackward(date);
                        break;
                    }
                    date = TimeHelper.setTimeValue(date, 6, term);
                    break;
                }
                catch (TimeCalcException e) {
                    throw new RuntimeException(e);
                }
            }
            case 5: {
                if (term > 0 && term <= 31) {
                    date.set(2, 0);
                    date.set(5, term);
                    break;
                }
                date.setTimeInMillis(0L);
                break;
            }
            default: {
                date.setTimeInMillis(0L);
            }
        }
        return this.showFormat.formatCalendar(date, toAppendTo, pos);
    }

    public String toDataPattern() {
        return this.dataPattern;
    }

    public String toShowPattern() {
        return this.showPattern;
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object clone() {
        TimeFieldFormat result = (TimeFieldFormat)super.clone();
        result.build();
        return result;
    }
}

