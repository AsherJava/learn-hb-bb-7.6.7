/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 *  com.jiuqi.bi.syntax.SyntaxException
 */
package com.jiuqi.nr.io.tz.listener;

import com.jiuqi.bi.syntax.DataType;
import com.jiuqi.bi.syntax.SyntaxException;

public class ColumnData {
    private Object value;
    private Object oldValue;
    private int type;

    public Object getValue() {
        return this.value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getOldValue() {
        return this.oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isModified() {
        try {
            return DataType.compare((int)this.type, (Object)this.value, (Object)this.oldValue) != 0;
        }
        catch (SyntaxException e) {
            return true;
        }
    }

    public ColumnData() {
    }

    public ColumnData(Object value, int type) {
        this.value = value;
        this.type = type;
    }

    public ColumnData(Object value, Object oldValue, int type) {
        this.value = value;
        this.oldValue = oldValue;
        this.type = type;
    }
}

