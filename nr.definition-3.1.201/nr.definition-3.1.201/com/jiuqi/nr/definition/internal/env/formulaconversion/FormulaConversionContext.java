/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.env.formulaconversion;

import com.jiuqi.nr.definition.internal.env.formulaconversion.ConversionFormInfo;
import java.util.HashMap;
import java.util.Map;

public class FormulaConversionContext {
    private Map<String, ConversionFormInfo> conversionFormInfoMap = new HashMap<String, ConversionFormInfo>();
    private Map<String, String> entityMap;

    public Map<String, ConversionFormInfo> getConversionFormInfoMap() {
        return this.conversionFormInfoMap;
    }

    public void setConversionFormInfoMap(Map<String, ConversionFormInfo> conversionFormInfoMap) {
        this.conversionFormInfoMap = conversionFormInfoMap;
    }

    public Map<String, String> getEntityMap() {
        return this.entityMap;
    }

    public void setEntityMap(Map<String, String> entityMap) {
        this.entityMap = entityMap;
    }
}

