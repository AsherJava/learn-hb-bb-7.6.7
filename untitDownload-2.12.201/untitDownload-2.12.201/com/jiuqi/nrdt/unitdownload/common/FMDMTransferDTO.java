/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nrdt.unitdownload.common;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FMDMTransferDTO
implements Serializable {
    private String formulaSchemeKey;
    private String fmdmKey;
    private Map<String, DimensionValue> dimensionSet = new HashMap<String, DimensionValue>();
    private Map<String, Object> modifyData = new HashMap<String, Object>();

    public String getFmdmKey() {
        return this.fmdmKey;
    }

    public void setFmdmKey(String fmdmKey) {
        this.fmdmKey = fmdmKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }

    public void setFormulaSchemeKey(String formulaSchemeKey) {
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public Map<String, Object> getModifyData() {
        return this.modifyData;
    }

    public void setModifyData(Map<String, Object> modifyData) {
        this.modifyData = modifyData;
    }
}

