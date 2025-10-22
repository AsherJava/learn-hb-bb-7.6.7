/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.logic.api.param.cksr;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import java.util.List;
import org.springframework.lang.NonNull;

public class CheckStatusQueryPar {
    private String formSchemeKey;
    private List<String> formulaSchemeKeys;
    private List<String> formKeys;
    private DimensionValueSet dimensionValueSet;

    public CheckStatusQueryPar(@NonNull String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(List<String> formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }
}

