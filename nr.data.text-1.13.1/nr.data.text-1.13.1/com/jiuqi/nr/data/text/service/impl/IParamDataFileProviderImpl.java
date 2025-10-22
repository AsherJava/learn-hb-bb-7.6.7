/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.common.service.ParamsMapping
 *  com.jiuqi.nr.fielddatacrud.spi.ParamProvider
 */
package com.jiuqi.nr.data.text.service.impl;

import com.jiuqi.nr.data.common.service.ParamsMapping;
import com.jiuqi.nr.data.text.spi.IParamDataFileProvider;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;

public class IParamDataFileProviderImpl
implements IParamDataFileProvider {
    private ParamProvider paramProvider;
    private ParamsMapping mapping;

    @Override
    public ParamsMapping getParamMapping() {
        return this.mapping;
    }

    public ParamProvider getParamProvider() {
        return this.paramProvider;
    }

    public void setParamProvider(ParamProvider paramProvider) {
        this.paramProvider = paramProvider;
    }

    public void setMapping(ParamsMapping mapping) {
        this.mapping = mapping;
    }
}

