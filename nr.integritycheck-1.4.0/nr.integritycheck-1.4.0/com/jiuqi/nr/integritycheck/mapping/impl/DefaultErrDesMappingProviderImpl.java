/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.integritycheck.mapping.impl;

import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.integritycheck.mapping.IErrDesMappingProvider;
import com.jiuqi.nr.integritycheck.mapping.IErrDesMappingService;
import com.jiuqi.nr.integritycheck.mapping.impl.DefaultErrDesMappingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultErrDesMappingProviderImpl
implements IErrDesMappingProvider {
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public IErrDesMappingService getIErrDesMappingService() {
        return new DefaultErrDesMappingServiceImpl(this.runTimeViewController);
    }
}

