/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.np.dataengine.common;

import com.jiuqi.np.definition.facade.FieldDefine;

public class LocaleFieldItem {
    private FieldDefine fieldDefine;
    private int fieldIndex;

    public LocaleFieldItem(FieldDefine fieldDefine, int fieldIndex) {
        this.fieldDefine = fieldDefine;
        this.fieldIndex = fieldIndex;
    }

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public void setFieldDefine(FieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
    }

    public int getFieldIndex() {
        return this.fieldIndex;
    }

    public void setFieldIndex(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }
}

