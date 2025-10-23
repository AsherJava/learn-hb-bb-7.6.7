/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.sbdata.carry.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.Map;

public class TzCarryDownBaseParam
implements Serializable {
    private String sourceTaskKey;
    private String sourceFormSchemeKey;
    private String destTaskKey;
    private String destFormSchemeKey;
    private Map<String, DimensionValue> sourceDimensionSet;
    private String destPeriod;
    private String mappingKey;

    public String getSourceTaskKey() {
        return this.sourceTaskKey;
    }

    public void setSourceTaskKey(String sourceTaskKey) {
        this.sourceTaskKey = sourceTaskKey;
    }

    public String getSourceFormSchemeKey() {
        return this.sourceFormSchemeKey;
    }

    public void setSourceFormSchemeKey(String sourceFormSchemeKey) {
        this.sourceFormSchemeKey = sourceFormSchemeKey;
    }

    public String getDestTaskKey() {
        return this.destTaskKey;
    }

    public void setDestTaskKey(String destTaskKey) {
        this.destTaskKey = destTaskKey;
    }

    public String getDestFormSchemeKey() {
        return this.destFormSchemeKey;
    }

    public void setDestFormSchemeKey(String destFormSchemeKey) {
        this.destFormSchemeKey = destFormSchemeKey;
    }

    public Map<String, DimensionValue> getSourceDimensionSet() {
        return this.sourceDimensionSet;
    }

    public void setSourceDimensionSet(Map<String, DimensionValue> sourceDimensionSet) {
        this.sourceDimensionSet = sourceDimensionSet;
    }

    public String getDestPeriod() {
        return this.destPeriod;
    }

    public void setDestPeriod(String destPeriod) {
        this.destPeriod = destPeriod;
    }

    public String getMappingKey() {
        return this.mappingKey;
    }

    public void setMappingKey(String mappingKey) {
        this.mappingKey = mappingKey;
    }
}

