/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 */
package com.jiuqi.nr.annotation.mapping.impl;

import com.jiuqi.nr.annotation.mapping.IAnnotationMappingProvider;
import com.jiuqi.nr.annotation.mapping.IAnnotationMappingService;
import com.jiuqi.nr.annotation.mapping.impl.DefaultAnnotationMappingServiceImpl;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultAnnotationMappingProviderImpl
implements IAnnotationMappingProvider {
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public IAnnotationMappingService getIAnnotationMappingService() {
        return new DefaultAnnotationMappingServiceImpl(this.runTimeViewController);
    }
}

