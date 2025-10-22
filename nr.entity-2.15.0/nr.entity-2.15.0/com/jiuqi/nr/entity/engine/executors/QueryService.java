/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.nr.entity.engine.executors;

import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.entity.ext.filter.IEntityReferFilter;
import com.jiuqi.nr.entity.ext.version.IVersionQueryService;
import com.jiuqi.nr.entity.internal.service.AdapterService;

public class QueryService {
    private IDataDefinitionRuntimeController runtimeController;
    private AdapterService adapterService;
    private IVersionQueryService versionQueryService;
    private IEntityReferFilter entityReferFilter;

    public QueryService(IDataDefinitionRuntimeController runtimeController, AdapterService adapterService, IVersionQueryService versionQueryService, IEntityReferFilter entityReferFilter) {
        this.runtimeController = runtimeController;
        this.adapterService = adapterService;
        this.versionQueryService = versionQueryService;
        this.entityReferFilter = entityReferFilter;
    }

    public AdapterService getAdapterService() {
        return this.adapterService;
    }

    public IDataDefinitionRuntimeController getRuntimeController() {
        return this.runtimeController;
    }

    public IVersionQueryService getVersionQueryService() {
        return this.versionQueryService;
    }

    public IEntityReferFilter getEntityReferFilter() {
        return this.entityReferFilter;
    }
}

