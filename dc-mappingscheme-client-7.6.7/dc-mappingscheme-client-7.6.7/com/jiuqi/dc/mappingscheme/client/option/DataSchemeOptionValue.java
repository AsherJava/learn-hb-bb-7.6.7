/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.base.util.DateUtils
 *  com.jiuqi.dc.base.common.enums.DataType
 */
package com.jiuqi.dc.mappingscheme.client.option;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.base.util.DateUtils;
import com.jiuqi.dc.base.common.enums.DataType;
import java.io.Serializable;
import java.util.Date;

public class DataSchemeOptionValue
implements Serializable {
    private static final long serialVersionUID = -7694450334128711709L;
    private DataType type;
    private Object value;

    public DataSchemeOptionValue(DataType type) {
        this(type, null);
    }

    public DataSchemeOptionValue(DataType type, Object value) {
        this.type = type;
        this.value = value;
    }

    public DataType getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }

    public Boolean getBooleanValue() {
        return ConverterUtils.getAsBoolean((Object)this.value, (Boolean)Boolean.FALSE);
    }

    public String getStringValue() {
        if (this.value == null) {
            return "";
        }
        return this.value.toString();
    }

    public Date getDateValue() {
        if (this.value instanceof Date) {
            return (Date)this.value;
        }
        if (this.value instanceof String) {
            return DateUtils.parse((String)((String)this.value), (String)DateUtils.DEFAULT_DATE_FORMAT);
        }
        return null;
    }

    public Date getDateTimeValue() {
        if (this.value instanceof Date) {
            return (Date)this.value;
        }
        if (this.value instanceof String) {
            return DateUtils.parse((String)((String)this.value), (String)"yyyy-MM-dd HH:mm:ss");
        }
        return null;
    }

    public Integer getIntegerValue() {
        return ConverterUtils.getAsInteger((Object)this.value);
    }

    public Double getDoubleValue() {
        return ConverterUtils.getAsDouble((Object)this.value);
    }
}

