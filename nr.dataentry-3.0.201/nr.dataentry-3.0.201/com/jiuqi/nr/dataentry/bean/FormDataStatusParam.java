/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.context.infc.impl.NRContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.context.infc.impl.NRContext;
import java.util.List;

public class FormDataStatusParam
extends NRContext {
    private List<String> formSchemeKeyList;
    private String formSchemeKey;
    private DimensionValueSet dimensionValueSet;
    private List<String> dates;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionValueSet getDimensionValueSet() {
        return this.dimensionValueSet;
    }

    public void setDimensionValueSet(DimensionValueSet dimensionValueSet) {
        this.dimensionValueSet = dimensionValueSet;
    }

    public List<String> getDates() {
        return this.dates;
    }

    public void setDatas(List<String> dates) {
        this.dates = dates;
    }

    public List<String> getFormSchemeKeyList() {
        return this.formSchemeKeyList;
    }

    public void setFormSchemeKeyList(List<String> formSchemeKeyList) {
        this.formSchemeKeyList = formSchemeKeyList;
    }
}

