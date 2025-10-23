/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.service;

import com.jiuqi.nr.datascheme.api.DataField;

public interface IDeployFieldNameProvider {
    public String getFieldName(DataField var1);

    default public String getFieldName(DataField dataField, String defalutFieldName) {
        return this.getFieldName(dataField);
    }
}

