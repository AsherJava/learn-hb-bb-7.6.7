/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.examine.facade;

import java.util.List;
import java.util.Map;

public class MdInfoUpgradeParamsDTO {
    private List<String> dataFieldKeys;
    private List<String> formKeys;
    private Map<String, String> dataDimValues;

    public List<String> getDataFieldKeys() {
        return this.dataFieldKeys;
    }

    public void setDataFieldKeys(List<String> dataFieldKeys) {
        this.dataFieldKeys = dataFieldKeys;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public Map<String, String> getDataDimValues() {
        return this.dataDimValues;
    }

    public void setDataDimValues(Map<String, String> dataDimValues) {
        this.dataDimValues = dataDimValues;
    }
}

