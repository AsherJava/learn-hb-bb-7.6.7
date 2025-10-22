/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.common.param;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.io.Serializable;

public class ReadOnlyContext
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskKey;
    private String formSchemeKey;
    private String formKey;
    private String regionKey;
    private DimensionValueSet dimensionValueSet;

    public ReadOnlyContext() {
    }

    public ReadOnlyContext(String taskKey, String formSchemeKey, String formKey, String regionKey, DimensionValueSet dimensionValueSet) {
        this.taskKey = taskKey;
        this.formSchemeKey = formSchemeKey;
        this.formKey = formKey;
        this.regionKey = regionKey;
        this.dimensionValueSet = dimensionValueSet;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }
}

