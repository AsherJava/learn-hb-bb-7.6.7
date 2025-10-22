/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.data.checkdes.obj;

import com.jiuqi.nr.data.checkdes.api.ICKDParamMapping;
import com.jiuqi.nr.data.checkdes.api.IMonitor;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class BasePar {
    protected DimensionCollection dimensionCollection;
    protected String formSchemeKey;
    protected List<String> formKeys;
    protected List<String> formulaSchemeKeys;
    protected ICKDParamMapping ckdParamMapping;
    protected IMonitor monitor;

    public DimensionCollection getDimensionCollection() {
        return this.dimensionCollection;
    }

    public void setDimensionCollection(DimensionCollection dimensionCollection) {
        this.dimensionCollection = dimensionCollection;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public List<String> getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(List<String> formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public ICKDParamMapping getCkdParamMapping() {
        return this.ckdParamMapping;
    }

    public void setCkdParamMapping(ICKDParamMapping ckdParamMapping) {
        this.ckdParamMapping = ckdParamMapping;
    }

    public IMonitor getMonitor() {
        return this.monitor;
    }

    public void setMonitor(IMonitor monitor) {
        this.monitor = monitor;
    }
}

