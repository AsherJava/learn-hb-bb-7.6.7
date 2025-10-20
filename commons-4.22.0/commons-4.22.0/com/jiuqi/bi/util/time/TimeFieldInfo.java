/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.types.TimeGranularityTypes
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.util.time;

import com.jiuqi.bi.text.CalendarFormatEx;
import com.jiuqi.bi.types.TimeGranularityTypes;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.bi.util.time.TimeCalcError;
import com.jiuqi.bi.util.time.TimeCalcException;
import com.jiuqi.bi.util.time.TimeHelper;
import java.text.Format;
import java.text.ParseException;
import java.util.Calendar;

public final class TimeFieldInfo
implements Cloneable {
    private String name;
    private int granularity;
    private String format;
    private boolean isTimeKey;
    private Object value;
    private Format _format;

    public TimeFieldInfo(String name, int granularity, String format) {
        this(name, granularity, format, false);
    }

    public TimeFieldInfo(String name, int granularity, String format, boolean isTimeKey) {
        this.name = name;
        this.granularity = granularity;
        this.format = format;
        this.isTimeKey = isTimeKey;
    }

    public String getName() {
        return this.name;
    }

    public int getGranularity() {
        return this.granularity;
    }

    public String getFormat() {
        return this.format;
    }

    public boolean isTimeKey() {
        return this.isTimeKey;
    }

    Format format() {
        if (this._format == null && !StringUtils.isEmpty((String)this.format)) {
            this._format = new CalendarFormatEx(this.format);
        }
        return this._format;
    }

    Object getValue() {
        return this.value;
    }

    boolean isNull() {
        return this.value == null || "".equals(this.value);
    }

    int getIntValue() throws TimeCalcException {
        if (this.value == null) {
            throw new TimeCalcException("\u83b7\u53d6\u5b57\u6bb5\u503c\u4e0d\u5b58\u5728\uff1a" + this.name);
        }
        if (this.value instanceof Number) {
            return ((Number)this.value).intValue();
        }
        if (this.value instanceof Calendar) {
            return TimeHelper.getTimeValue((Calendar)this.value, this.granularity);
        }
        if (this.value instanceof String) {
            return Integer.parseInt((String)this.value);
        }
        throw new TimeCalcException("\u65e0\u6cd5\u89e3\u6790\u5b57\u6bb5[" + this.name + "]\u503c\uff1a" + this.value);
    }

    int getIntValue(int defaultValue) throws TimeCalcException {
        return this.isNull() ? defaultValue : this.getIntValue();
    }

    void setValue(Object value) throws TimeCalcException {
        if (value instanceof Number) {
            this.value = ((Number)value).intValue();
        } else if (value instanceof String && this.format() != null) {
            try {
                this.value = this.format().parseObject((String)value);
            }
            catch (ParseException e) {
                throw new TimeCalcException("\u89e3\u6790\u5b57\u6bb5[" + this.name + "]\u503c\u9519\u8bef\uff1a" + value, e);
            }
        } else {
            this.value = value;
        }
    }

    Object formatValue(Calendar date) throws TimeCalcException {
        if (this.format() == null) {
            return TimeHelper.getTimeValue(date, this.granularity);
        }
        return this.format().format(date);
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        if (this.name == null) {
            buffer.append('*');
        } else {
            buffer.append(this.name);
        }
        buffer.append('(').append(TimeGranularityTypes.toString((int)this.granularity)).append(')');
        if (this.value instanceof Calendar) {
            buffer.append('=').append(this.format().format(this.value));
        } else if (this.value != null) {
            buffer.append('=').append(this.value);
        }
        return buffer.toString();
    }

    public TimeFieldInfo clone() {
        TimeFieldInfo fieldInfo;
        try {
            fieldInfo = (TimeFieldInfo)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new TimeCalcError(e);
        }
        fieldInfo._format = null;
        return fieldInfo;
    }

    public static TimeFieldInfo createTimeKey(String name, int granularity, String format) {
        return new TimeFieldInfo(name, granularity, format, true);
    }

    public static TimeFieldInfo createField(String name, int gruanularity) {
        return new TimeFieldInfo(name, gruanularity, null);
    }
}

