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

public class DescribeParam {
    protected DimensionCollection masterKey;
    protected ParamsMapping paramMapping;
    protected String formSchemeKey;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionCollection getMasterKey() {
        return this.masterKey;
    }

    public void setMasterKey(DimensionCollection masterKey) {
        this.masterKey = masterKey;
    }

    public ParamsMapping getParamMapping() {
        return this.paramMapping;
    }

    public void setParamMapping(ParamsMapping paramMapping) {
        this.paramMapping = paramMapping;
    }
}

