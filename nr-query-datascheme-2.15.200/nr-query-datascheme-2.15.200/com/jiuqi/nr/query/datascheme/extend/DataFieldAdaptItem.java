/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.DesignDataField
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 */
package com.jiuqi.nr.query.datascheme.extend;

import com.jiuqi.nr.datascheme.api.DesignDataField;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;

public class DataFieldAdaptItem<T> {
    private DesignDataField field;
    private T sourceColumn;
    private DataFieldDeployInfo deployInfo;

    public DataFieldAdaptItem(DesignDataField field, T sourceColumn) {
        this.field = field;
        this.sourceColumn = sourceColumn;
    }

    public DesignDataField getField() {
        return this.field;
    }

    public void setField(DesignDataField field) {
        this.field = field;
    }

    public T getSourceColumn() {
        return this.sourceColumn;
    }

    public void setSourceColumn(T sourceColumn) {
        this.sourceColumn = sourceColumn;
    }

    public DataFieldDeployInfo getDeployInfo() {
        return this.deployInfo;
    }

    public void setDeployInfo(DataFieldDeployInfo deployInfo) {
        this.deployInfo = deployInfo;
    }
}

