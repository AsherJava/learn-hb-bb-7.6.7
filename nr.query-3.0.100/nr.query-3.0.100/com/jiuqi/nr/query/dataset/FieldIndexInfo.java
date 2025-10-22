/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.model.field.TimeGranularity
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.nr.query.dataset;

import com.jiuqi.bi.dataset.model.field.TimeGranularity;
import com.jiuqi.np.definition.facade.FieldDefine;

public class FieldIndexInfo {
    private FieldDefine field;
    private int fieldIndex;
    private int codeIndex;
    private int titleIndex;
    private int parentIndex;
    private TimeGranularity timeGranularity;

    public FieldIndexInfo(FieldDefine field, int fieldIndex) {
        this.field = field;
        this.fieldIndex = fieldIndex;
    }

    public FieldDefine getField() {
        return this.field;
    }

    public int getFieldIndex() {
        return this.fieldIndex;
    }

    public int getCodeIndex() {
        return this.codeIndex;
    }

    public int getTitleIndex() {
        return this.titleIndex;
    }

    public int getParentIndex() {
        return this.parentIndex;
    }

    public void setField(FieldDefine field) {
        this.field = field;
    }

    public void setFieldIndex(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public String getRefEntityKey() {
        return this.field.getEntityKey();
    }

    public void setCodeIndex(int codeIndex) {
        this.codeIndex = codeIndex;
    }

    public void setTitleIndex(int titleIndex) {
        this.titleIndex = titleIndex;
    }

    public void setParentIndex(int parentIndex) {
        this.parentIndex = parentIndex;
    }

    public TimeGranularity getTimeGranularity() {
        return this.timeGranularity;
    }

    public void setTimeGranularity(TimeGranularity timeGranularity) {
        this.timeGranularity = timeGranularity;
    }
}

