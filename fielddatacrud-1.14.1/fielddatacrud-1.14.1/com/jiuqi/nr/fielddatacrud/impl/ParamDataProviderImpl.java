/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fielddatacrud.impl;

import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fielddatacrud.spi.ParamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ParamDataProviderImpl
implements IParamDataProvider {
    @Autowired
    private ParamProvider paramProvider;

    @Override
    public ParamProvider getParamProvider() {
        return this.paramProvider;
    }
}

