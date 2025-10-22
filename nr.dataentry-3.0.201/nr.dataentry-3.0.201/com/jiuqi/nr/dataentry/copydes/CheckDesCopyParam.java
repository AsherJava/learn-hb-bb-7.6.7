/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.copydes;

import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.copydes.IDimMappingProvider;
import com.jiuqi.nr.dataentry.copydes.IFmlMappingProvider;
import com.jiuqi.nr.dataentry.copydes.IUnsupportedDesHandler;
import java.util.Map;

public class CheckDesCopyParam {
    private String targetFormSchemeKey;
    private String targetFormulaSchemeKey;
    private Map<String, DimensionValue> targetDimensionSet;
    private String srcFormulaSchemeKey;
    private IDimMappingProvider dimMappingProvider;
    private IFmlMappingProvider fmlMappingProvider;
    private boolean updateUserTime;
    private IUnsupportedDesHandler unsupportedDesHandler;

    public String getTargetFormSchemeKey() {
        return this.targetFormSchemeKey;
    }

    public void setTargetFormSchemeKey(String targetFormSchemeKey) {
        this.targetFormSchemeKey = targetFormSchemeKey;
    }

    public String getTargetFormulaSchemeKey() {
        return this.targetFormulaSchemeKey;
    }

    public void setTargetFormulaSchemeKey(String targetFormulaSchemeKey) {
        this.targetFormulaSchemeKey = targetFormulaSchemeKey;
    }

    public void setTargetDimensionSet(Map<String, DimensionValue> targetDimensionSet) {
        this.targetDimensionSet = targetDimensionSet;
    }

    public Map<String, DimensionValue> getTargetDimensionSet() {
        return this.targetDimensionSet;
    }

    public IFmlMappingProvider getFmlMappingProvider() {
        return this.fmlMappingProvider;
    }

    public void setFmlMappingProvider(IFmlMappingProvider fmlMappingProvider) {
        this.fmlMappingProvider = fmlMappingProvider;
    }

    public IDimMappingProvider getDimMappingProvider() {
        return this.dimMappingProvider;
    }

    public void setDimMappingProvider(IDimMappingProvider dimMappingProvider) {
        this.dimMappingProvider = dimMappingProvider;
    }

    public boolean isUpdateUserTime() {
        return this.updateUserTime;
    }

    public void setUpdateUserTime(boolean updateUserTime) {
        this.updateUserTime = updateUserTime;
    }

    public String getSrcFormulaSchemeKey() {
        return this.srcFormulaSchemeKey;
    }

    public void setSrcFormulaSchemeKey(String srcFormulaSchemeKey) {
        this.srcFormulaSchemeKey = srcFormulaSchemeKey;
    }

    public IUnsupportedDesHandler getUnsupportedDesHandler() {
        return this.unsupportedDesHandler;
    }

    public void setUnsupportedDesHandler(IUnsupportedDesHandler unsupportedDesHandler) {
        this.unsupportedDesHandler = unsupportedDesHandler;
    }
}

