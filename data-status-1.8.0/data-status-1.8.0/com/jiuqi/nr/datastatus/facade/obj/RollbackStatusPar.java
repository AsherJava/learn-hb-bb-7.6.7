/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datastatus.facade.obj;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;

public class RollbackStatusPar {
    private DimensionCombination rollbackDim;
    private String formSchemeKey;
    private String formKey;

    public DimensionCombination getRollbackDim() {
        return this.rollbackDim;
    }

    public void setRollbackDim(DimensionCombination rollbackDim) {
        this.rollbackDim = rollbackDim;
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
}

