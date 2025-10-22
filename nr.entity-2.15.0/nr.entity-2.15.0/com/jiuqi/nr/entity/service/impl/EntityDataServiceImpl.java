/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.OrderGenerator
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 */
package com.jiuqi.nr.entity.service.impl;

import com.jiuqi.bi.util.OrderGenerator;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.executors.QueryService;
import com.jiuqi.nr.entity.engine.intf.IEntityModify;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.impl.EntityModifyImpl;
import com.jiuqi.nr.entity.engine.intf.impl.EntityQueryImpl;
import com.jiuqi.nr.entity.ext.filter.IEntityReferFilter;
import com.jiuqi.nr.entity.ext.version.IVersionQueryService;
import com.jiuqi.nr.entity.internal.service.AdapterService;
import com.jiuqi.nr.entity.log.SystemEntityLog;
import com.jiuqi.nr.entity.service.IEntityDataService;
import org.springframework.beans.factory.annotation.Autowired;

public class EntityDataServiceImpl
implements IEntityDataService {
    @Autowired
    private AdapterService adapterService;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IVersionQueryService versionQueryService;
    @Autowired
    private IEntityReferFilter entityReferFilter;

    @Override
    public IEntityQuery newEntityQuery() {
        QueryContext queryContext = new QueryContext(new SystemEntityLog(OrderGenerator.newOrder()), this.getQueryService());
        return new EntityQueryImpl(queryContext);
    }

    @Override
    public IEntityModify newEntityUpdate() {
        QueryContext queryContext = new QueryContext(new SystemEntityLog(OrderGenerator.newOrder()), this.getQueryService());
        return new EntityModifyImpl(queryContext);
    }

    private QueryService getQueryService() {
        return new QueryService(this.runtimeController, this.adapterService, this.versionQueryService, this.entityReferFilter);
    }
}

