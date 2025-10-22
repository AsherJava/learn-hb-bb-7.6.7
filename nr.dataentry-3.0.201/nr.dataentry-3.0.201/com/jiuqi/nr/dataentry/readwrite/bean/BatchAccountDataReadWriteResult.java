/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class BatchAccountDataReadWriteResult {
    private DimensionValueSet masterKey;
    private String formKey;

    public BatchAccountDataReadWriteResult(DimensionValueSet masterKey, String formKey) {
        this.masterKey = masterKey;
        this.formKey = formKey;
    }

    public DimensionValueSet getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionValueSet masterKey) {
        this.masterKey = masterKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }
}

