/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.DimensionValue
 */
package com.jiuqi.nr.dataentry.attachment.intf;

import com.jiuqi.nr.common.params.DimensionValue;
import java.util.Map;

public class QueryFileInfoParam {
    private String formScheme;
    private Map<String, DimensionValue> dimensionSet;
    private String formKey;
    private String fieldKey;

    public QueryFileInfoParam(String formScheme, Map<String, DimensionValue> dimensionSet, String formKey, String fieldKey) {
        this.formScheme = formScheme;
        this.dimensionSet = dimensionSet;
        this.formKey = formKey;
        this.fieldKey = fieldKey;
    }

    public String getFormScheme() {
        return this.formScheme;
    }

    public void setFormScheme(String formScheme) {
        this.formScheme = formScheme;
    }

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

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }
}

