/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.np.dataengine.update;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;
import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.dataengine.data.DataTypes;
import java.math.BigDecimal;

public class UpdateDataColumn {
    private String name;
    private Object value;
    private Object oldValue;
    private int type;
    private int scale = Integer.MIN_VALUE;

    public UpdateDataColumn(String name, int type, int scale, Object value, Object oldValue) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.oldValue = oldValue;
        this.scale = scale;
    }

    public UpdateDataColumn(String name, int type, Object value, Object oldValue) {
        this(name, type, Integer.MIN_VALUE, value, oldValue);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public String toString() {
        return this.name + ":" + this.oldValue + "->" + this.value;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object obj) {
        return this.name.equals(((UpdateDataColumn)obj).name);
    }

    public boolean isModified() {
        try {
            if (this.value != null && this.oldValue == null || this.value == null && this.oldValue != null) {
                return true;
            }
            if (this.type == 10 && this.scale >= 0) {
                return DataType.compare((BigDecimal)DataTypesConvert.toBigDecimal(this.value), (BigDecimal)DataTypesConvert.toBigDecimal(this.oldValue), (int)this.scale) != 0;
            }
            if (DataTypes.isNum(this.type)) {
                return DataType.compare((Number)((Number)this.value), (Number)((Number)this.oldValue)) != 0;
            }
            return DataType.compare((int)this.type, (Object)this.value, (Object)this.oldValue) != 0;
        }
        catch (SyntaxException e) {
            return true;
        }
    }
}

