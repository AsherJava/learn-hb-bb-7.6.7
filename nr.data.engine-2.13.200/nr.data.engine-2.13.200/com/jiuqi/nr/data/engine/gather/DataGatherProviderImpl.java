/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.event.OperateRowEventListener
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.dataengine.intf.IDimensionProvider
 *  com.jiuqi.np.dataengine.intf.IMemTableLoader
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.event.OperateRowEventListener;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.IDimensionProvider;
import com.jiuqi.np.dataengine.intf.IMemTableLoader;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.controller.SubDatabaseTableNamesProvider;
import com.jiuqi.nr.data.engine.gather.DataGatherImpl;
import com.jiuqi.nr.data.engine.gather.GatherEventHandler;
import com.jiuqi.nr.data.engine.gather.IDataGather;
import com.jiuqi.nr.data.engine.gather.IDataGatherFactory;
import com.jiuqi.nr.data.engine.gather.IDataGatherProvider;
import com.jiuqi.nr.data.engine.gather.util.FileCalculateService;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataGatherProviderImpl
implements IDataGatherProvider {
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IConnectionProvider connectionProvider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IDataDefinitionDesignTimeController designTimeController;
    @Autowired
    private EntityIdentityService entityLinkService;
    @Autowired
    private OperateRowEventListener eventListener;
    @Autowired
    private NedisCacheManager cacheManager;
    @Autowired
    private EntityResetCacheService entityResetCacheService;
    @Autowired(required=false)
    private GatherEventHandler gatherEventHandler;
    @Autowired
    private IRunTimeViewController viewController;
    private IDataGatherFactory dataGatherFactory;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired(required=false)
    private IMemTableLoader memTableLoader;
    @Autowired
    private SubDatabaseTableNamesProvider subDatabaseTableNamesProvider;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private IDimensionProvider dimensionProvider;
    @Autowired
    private FileCalculateService fileCalculateService;

    @Override
    public IDataGather newDataGather() {
        DataGatherImpl dataGather = new DataGatherImpl();
        QueryParam queryParam = this.getQueryParam();
        dataGather.setQueryParam(queryParam);
        dataGather.setGatherEventHandler(this.gatherEventHandler);
        dataGather.setViewController(this.viewController);
        dataGather.setEntityMetaService(this.entityMetaService);
        dataGather.setSubDatabaseTableNamesProvider(this.subDatabaseTableNamesProvider);
        dataGather.setRuntimeDataSchemeService(this.runtimeDataSchemeService);
        dataGather.setMemTableLoader(this.memTableLoader);
        dataGather.setDimensionProvider(this.dimensionProvider);
        dataGather.setFileCalculateService(this.fileCalculateService);
        return dataGather;
    }

    @Override
    public IDataGather newDataGather(QueryEnvironment environment) {
        IDataGather dataGather = this.dataGatherFactory == null ? null : this.dataGatherFactory.getDataGather(environment);
        QueryParam queryParam = this.getQueryParam();
        if (dataGather == null) {
            return this.newDataGather();
        }
        dataGather.setQueryParam(queryParam);
        dataGather.setGatherEventHandler(this.gatherEventHandler);
        ((DataGatherImpl)dataGather).setEntityMetaService(this.entityMetaService);
        if (dataGather instanceof DataGatherImpl) {
            ((DataGatherImpl)dataGather).setViewController(this.viewController);
            ((DataGatherImpl)dataGather).setSubDatabaseTableNamesProvider(this.subDatabaseTableNamesProvider);
            ((DataGatherImpl)dataGather).setRuntimeDataSchemeService(this.runtimeDataSchemeService);
            ((DataGatherImpl)dataGather).setMemTableLoader(this.memTableLoader);
            ((DataGatherImpl)dataGather).setDimensionProvider(this.dimensionProvider);
            ((DataGatherImpl)dataGather).setFileCalculateService(this.fileCalculateService);
        }
        return dataGather;
    }

    @Override
    public void registerDataGather(IDataGatherFactory dataGatherFactory) {
        this.dataGatherFactory = dataGatherFactory;
    }

    private QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController, this.designTimeController, this.entityViewRunTimeController);
        queryParam.setEntityLinkService(this.entityLinkService);
        queryParam.setEventListener(this.eventListener);
        queryParam.setCacheManager(this.cacheManager);
        queryParam.setEntityResetCacheService(this.entityResetCacheService);
        return queryParam;
    }
}

