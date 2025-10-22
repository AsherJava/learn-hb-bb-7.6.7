/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.data;

import com.jiuqi.nr.entity.engine.data.AbstractData;

public class ObjectData
extends AbstractData {
    private static final long serialVersionUID = 4436836705068529157L;
    private Object value;
    public static final ObjectData NULL = new ObjectData();

    public ObjectData() {
        super(0, true);
        this.value = null;
    }

    public ObjectData(Object obj) {
        super(0, false);
        this.value = obj;
    }

    @Override
    public String getAsString() {
        if (this.isNull || this.value == null) {
            return "";
        }
        return this.value.toString();
    }

    @Override
    public Object getAsObject() {
        if (this.isNull) {
            return null;
        }
        return this.value;
    }
}

