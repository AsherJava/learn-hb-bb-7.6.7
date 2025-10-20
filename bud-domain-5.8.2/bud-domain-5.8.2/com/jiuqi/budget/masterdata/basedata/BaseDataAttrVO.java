/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.masterdata.basedata;

import com.jiuqi.budget.masterdata.basedata.BaseDataObjDTO;

public class BaseDataAttrVO
extends BaseDataObjDTO {
    private String fieldName;

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public String toString() {
        return "BaseDataAttrVO{fieldName='" + this.fieldName + '\'' + "} " + super.toString();
    }
}

