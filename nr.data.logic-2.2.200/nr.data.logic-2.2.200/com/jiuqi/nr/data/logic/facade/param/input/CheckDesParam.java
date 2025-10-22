/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.logic.facade.param.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.ArrayList;
import java.util.List;

public class CheckDesParam {
    private DimensionCollection dimensionCollection;
    private String formSchemeKey;
    private List<String> formulaSchemeKey = new ArrayList<String>();
    private List<String> formKey = new ArrayList<String>();
    private List<String> formulaKey = new ArrayList<String>();

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public List<String> getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(List<String> formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public List<String> getFormKey() {
        return this.formKey;
    }

    public void setFormKey(List<String> formKey) {
        this.formKey = formKey;
    }

    public List<String> getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(List<String> formulaKey) {
        this.formulaKey = formulaKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }
}

