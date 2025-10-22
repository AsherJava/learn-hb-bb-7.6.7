/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.Map;

public class FormLockBatchReadWriteResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, DimensionValue> dimensionSet;
    private String formKey;
    private boolean isLock;
    private String userId;

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public boolean isLock() {
        return this.isLock;
    }

    public void setLock(boolean isLock) {
        this.isLock = isLock;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

