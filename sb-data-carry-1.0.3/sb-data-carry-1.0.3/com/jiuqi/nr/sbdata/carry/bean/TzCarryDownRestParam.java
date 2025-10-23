/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.sbdata.carry.bean;

import com.jiuqi.nr.sbdata.carry.bean.TzCarryDownBaseParam;

public class TzCarryDownRestParam
extends TzCarryDownBaseParam {
    private String formKeys;
    private int parallelNumbers;

    public TzCarryDownRestParam() {
    }

    public TzCarryDownRestParam(TzCarryDownBaseParam param) {
        this.setSourceTaskKey(param.getSourceTaskKey());
        this.setSourceFormSchemeKey(param.getSourceFormSchemeKey());
        this.setDestTaskKey(param.getDestTaskKey());
        this.setDestFormSchemeKey(param.getDestFormSchemeKey());
        this.setSourceDimensionSet(param.getSourceDimensionSet());
        this.setDestPeriod(param.getDestPeriod());
        this.setMappingKey(param.getMappingKey());
    }

    public String getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(String formKeys) {
        this.formKeys = formKeys;
    }

    public int getParallelNumbers() {
        return this.parallelNumbers;
    }

    public void setParallelNumbers(int parallelNumbers) {
        this.parallelNumbers = parallelNumbers;
    }
}

