/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 */
package com.jiuqi.nr.hshdcheck;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.hshdcheck.DescribeParam;

public class DescribeQueryParam
extends DescribeParam {
    private String formSchemeKey;

    @Override
    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    @Override
    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    @Override
    public DimensionCollection getMasterKey() {
        return this.masterKey;
    }

    @Override
    public void setMasterKey(DimensionCollection masterKey) {
        this.masterKey = masterKey;
    }

    @Override
    public ParamsMapping getParamMapping() {
        return this.paramMapping;
    }

    @Override
    public void setParamMapping(ParamsMapping paramMapping) {
        this.paramMapping = paramMapping;
    }
}

