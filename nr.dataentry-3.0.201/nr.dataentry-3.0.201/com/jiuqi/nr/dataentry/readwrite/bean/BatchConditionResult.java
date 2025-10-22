/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.readwrite.bean;

import com.jiuqi.nr.common.params.DimensionValue;
import java.io.Serializable;
import java.util.Map;

public class BatchConditionResult
implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<String, DimensionValue> dimensionSet;
    private String formKey;

    public Map<String, DimensionValue> getDimensionSet() {
        return this.dimensionSet;
    }

    public void setDimensionSet(Map<String, DimensionValue> dimensionSet) {
        this.dimensionSet = dimensionSet;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = 31 * result + (this.dimensionSet == null ? 0 : this.dimensionSet.hashCode());
        result = 31 * result + (this.formKey == null ? 0 : this.formKey.hashCode());
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        BatchConditionResult other = (BatchConditionResult)obj;
        if (this.dimensionSet == null ? other.dimensionSet != null : !this.dimensionSet.equals(other.dimensionSet)) {
            return false;
        }
        return !(this.formKey == null ? other.formKey != null : !this.formKey.equals(other.formKey));
    }
}

