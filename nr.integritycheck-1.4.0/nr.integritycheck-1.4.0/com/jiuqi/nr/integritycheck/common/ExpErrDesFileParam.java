/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.integritycheck.common;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import java.util.List;

public class ExpErrDesFileParam {
    private DimensionCollection dims;
    private String formSchemeKey;
    private List<String> formKeys;
    private ParamsMapping paramsMapping;

    public DimensionCollection getDims() {
        return this.dims;
    }

    public void setDims(DimensionCollection dims) {
        this.dims = dims;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public ParamsMapping getParamsMapping() {
        return this.paramsMapping;
    }

    public void setParamsMapping(ParamsMapping paramsMapping) {
        this.paramsMapping = paramsMapping;
    }
}

