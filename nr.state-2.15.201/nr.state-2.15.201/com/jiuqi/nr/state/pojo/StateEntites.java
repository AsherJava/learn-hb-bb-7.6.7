/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.state.pojo;

import com.jiuqi.np.dataengine.common.DimensionValueSet;

public class StateEntites {
    private DimensionValueSet dims;
    private String formSchemeKey;
    private String userId;

    public DimensionValueSet getDims() {
        return this.dims;
    }

    public void setDims(DimensionValueSet dims) {
        this.dims = dims;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}

