/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class ReadOnlyBean {
    private String msg;
    private boolean readOnly;
    private DimensionValueSet dim;
    private String formKey;
    private String groupKey;

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public DimensionValueSet getDim() {
        return this.dim;
    }

    public void setDim(DimensionValueSet dim) {
        this.dim = dim;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }
}

