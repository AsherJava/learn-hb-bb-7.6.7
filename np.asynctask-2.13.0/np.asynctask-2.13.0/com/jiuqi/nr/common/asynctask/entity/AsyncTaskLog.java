/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.common.asynctask.entity;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.io.Serializable;

public class AsyncTaskLog
implements Serializable {
    private static final long serialVersionUID = 746411340802046995L;
    private DimensionValueSet dimensionValueSet;
    private String message;
    private Boolean success = Boolean.TRUE;

    public AsyncTaskLog(Boolean success) {
        this.success = success;
    }

    public AsyncTaskLog(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public AsyncTaskLog(DimensionValueSet dimensionValueSet, Boolean success) {
        this.dimensionValueSet = dimensionValueSet;
        this.success = success;
    }

    public AsyncTaskLog(DimensionValueSet dimensionValueSet, String message) {
        this.dimensionValueSet = dimensionValueSet;
        this.message = message;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}

