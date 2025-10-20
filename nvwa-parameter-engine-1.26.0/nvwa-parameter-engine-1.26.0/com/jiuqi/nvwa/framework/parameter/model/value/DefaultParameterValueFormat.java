/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.text.DateFormatEx
 */
package com.jiuqi.nvwa.framework.parameter.model.value;

import com.jiuqi.bi.text.DateFormatEx;
import com.jiuqi.nvwa.framework.parameter.ParameterException;
import com.jiuqi.nvwa.framework.parameter.model.AbstractParameterDataSourceModel;
import com.jiuqi.nvwa.framework.parameter.model.value.IParameterValueFormat;
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DefaultParameterValueFormat
implements IParameterValueFormat {
    private AbstractParameterDataSourceModel datasource;
    private int dataType;

    public DefaultParameterValueFormat(int dataType) {
        this.dataType = dataType;
    }

    public DefaultParameterValueFormat(AbstractParameterDataSourceModel datasource) {
        this.datasource = datasource;
        this.dataType = datasource.getDataType();
    }

    @Override
    public String format(Object value) throws ParameterException {
        if (value == null) {
            return null;
        }
        if (this.dataType == 2) {
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
            if (value instanceof String) {
                return (String)value;
            }
            if (value instanceof Date) {
                return sdf1.format((Date)value);
            }
            if (value instanceof Calendar) {
                return sdf1.format(((Calendar)value).getTime());
            }
            throw new ParameterException("\u65e5\u671f\u53c2\u6570\u53d6\u503c\u5bf9\u8c61\u9519\u8bef");
        }
        return value.toString();
    }

    @Override
    public Object parse(String valueString) throws ParameterException {
        if (valueString == null) {
            return null;
        }
        try {
            if (this.dataType == 6) {
                return valueString;
            }
            if (this.dataType == 5) {
                return valueString.isEmpty() ? null : Integer.valueOf(valueString);
            }
            if (this.dataType == 3) {
                return valueString.isEmpty() ? null : Double.valueOf(valueString);
            }
            if (this.dataType == 10) {
                return valueString.isEmpty() ? null : new BigDecimal(valueString);
            }
            if (this.dataType == 2) {
                return valueString.isEmpty() ? null : DefaultParameterValueFormat.parseDate(valueString);
            }
            if (this.dataType == 1) {
                return valueString.isEmpty() ? null : Boolean.valueOf(Boolean.parseBoolean(valueString) || valueString.equals("1"));
            }
        }
        catch (NumberFormatException e) {
            throw new ParameterException("\u89e3\u6790\u53c2\u6570\u503c\u51fa\u9519");
        }
        throw new ParameterException("\u672a\u652f\u6301\u7684\u6570\u636e\u7c7b\u578b");
    }

    @Override
    public Format getDataShowFormat(Locale locale) {
        if (this.dataType == 2) {
            int tg = this.datasource == null ? 5 : this.datasource.getTimegranularity();
            String formatstr = "yyyy\u5e74MM\u6708dd\u65e5";
            if (tg == 0) {
                formatstr = "yyyy\u5e74";
            } else if (tg == 3) {
                formatstr = "yyyy\u5e74MM\u6708";
            } else if (tg == 5) {
                formatstr = "yyyy\u5e74MM\u6708dd\u65e5";
            } else if (tg == 2) {
                formatstr = "\u7b2cQ\u5b63\u5ea6";
            } else if (tg == 6) {
                formatstr = "\u7b2cW\u5468";
            }
            return new DateFormatEx(formatstr, locale);
        }
        return null;
    }

    public static Calendar parseDate(String value) throws ParameterException {
        int day;
        int month;
        int year;
        int len = value.length();
        if (len < 8) {
            throw new ParameterException("\u53c2\u6570\u65e5\u671f\u683c\u5f0f\u9519\u8bef\uff1a" + value);
        }
        try {
            year = Integer.parseInt(value.substring(0, 4));
            char sep = value.charAt(4);
            if (sep == '-' || sep == '/' || sep == ';') {
                int next = value.indexOf(sep, 5);
                month = Integer.parseInt(value.substring(5, next));
                day = Integer.parseInt(value.substring(next + 1));
            } else {
                month = Integer.parseInt(value.substring(4, 6));
                day = Integer.parseInt(value.substring(6));
            }
        }
        catch (NumberFormatException e) {
            throw new ParameterException("\u65e5\u671f\u683c\u5f0f\u9519\u8bef\uff1a" + value);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(1, year);
        calendar.set(2, month - 1);
        calendar.set(5, day);
        calendar.set(10, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }
}

