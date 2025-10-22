/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.paramInfo;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.List;
import java.util.Map;

public class DimensionValueFormInfo {
    private Map<String, DimensionValue> dimensionValue;
    private List<String> forms;

    public DimensionValueFormInfo(Map<String, DimensionValue> dimensionValue, List<String> forms) {
        this.dimensionValue = dimensionValue;
        this.forms = forms;
    }

    public Map<String, DimensionValue> getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(Map<String, DimensionValue> dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public List<String> getForms() {
        return this.forms;
    }

    public void setForms(List<String> forms) {
        this.forms = forms;
    }

    public String getFormStr() {
        StringBuffer formStr = new StringBuffer();
        for (String form : this.forms) {
            formStr.append(form).append(";");
        }
        return formStr.substring(0, formStr.length() - 1);
    }
}

