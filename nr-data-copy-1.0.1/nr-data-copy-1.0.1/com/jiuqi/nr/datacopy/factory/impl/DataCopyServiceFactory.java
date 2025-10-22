/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.event.OperateRowEventListener
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.attachment.factory.IFileCopyServiceFactory
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.datacopy.factory.impl;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.event.OperateRowEventListener;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.attachment.factory.IFileCopyServiceFactory;
import com.jiuqi.nr.datacopy.factory.IDataCopyServiceFactory;
import com.jiuqi.nr.datacopy.param.DataCopyParamProvider;
import com.jiuqi.nr.datacopy.service.IDataCopyService;
import com.jiuqi.nr.datacopy.service.impl.DataCopyServiceImpl;
import com.jiuqi.nr.datacopy.util.TempTableUtils;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.dimension.build.DimensionBuildUtil;
import com.jiuqi.nvwa.definition.service.DataModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataCopyServiceFactory
implements IDataCopyServiceFactory {
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IConnectionProvider connectionProvider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private EntityIdentityService entityLinkService;
    @Autowired
    private IDataDefinitionDesignTimeController designTimeController;
    @Autowired
    private OperateRowEventListener eventListener;
    @Autowired
    private NedisCacheManager cacheManager;
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private TempTableUtils tempTableUtils;
    @Autowired
    private DimensionBuildUtil dimensionBuildUtil;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private IFileCopyServiceFactory fileCopyServiceFactory;

    @Override
    public IDataCopyService getDataCopyService(DataCopyParamProvider dataCopyParamProvider) {
        QueryParam queryParam = this.getQueryParam();
        return new DataCopyServiceImpl(dataCopyParamProvider, this.providerStore, this.tempTableUtils, queryParam, this.dimensionBuildUtil, this.runtimeDataSchemeService, this.dataModelService, this.fileCopyServiceFactory);
    }

    @Override
    public IDataCopyService getDataCopyService(DataCopyParamProvider dataCopyParamProvider, IProviderStore providerStore) {
        QueryParam queryParam = this.getQueryParam();
        return new DataCopyServiceImpl(dataCopyParamProvider, providerStore, this.tempTableUtils, queryParam, this.dimensionBuildUtil, this.runtimeDataSchemeService, this.dataModelService, this.fileCopyServiceFactory);
    }

    private QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController, this.designTimeController, this.entityViewRunTimeController);
        queryParam.setEntityLinkService(this.entityLinkService);
        queryParam.setEventListener(this.eventListener);
        queryParam.setCacheManager(this.cacheManager);
        return queryParam;
    }
}

