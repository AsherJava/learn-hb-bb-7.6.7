/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSourceService
 */
package com.jiuqi.bde.bizmodel.define.adaptor;

import com.jiuqi.bde.bizmodel.define.adaptor.AbstractFieldMappingProvider;
import com.jiuqi.dc.mappingscheme.impl.service.DataSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractBdeFieldMappingProvider
extends AbstractFieldMappingProvider {
    @Autowired
    protected DataSourceService dataSourceService;
    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractBdeFieldMappingProvider.class);

    @Override
    public Integer showOrder() {
        return 1;
    }
}

