/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.fielddatacrud.impl;

import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataQueryServiceImpl;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fielddatacrud.spi.TableUpdaterProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultFieldDataServiceFactory
implements IFieldDataServiceFactory {
    @Autowired
    private IFieldDataService fieldDataService;
    @Autowired
    private FieldRelationFactory fieldRelationFactory;
    @Autowired
    private FieldDataStrategyFactory strategyFactory;
    @Autowired
    private IParamDataProvider paramDataProvider;
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private TableUpdaterProvider tableUpdaterProvider;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;

    @Override
    public IFieldDataService getFieldDataFileService() {
        return this.fieldDataService;
    }

    @Override
    public IFieldDataService getFieldDataFileService(IProviderStore providerStore) {
        FieldDataQueryServiceImpl service = new FieldDataQueryServiceImpl();
        service.setProviderStore(providerStore);
        service.setFieldRelationFactory(this.fieldRelationFactory);
        service.setStrategyFactory(this.strategyFactory);
        service.setParamDataProvider(this.paramDataProvider);
        service.setTableUpdaterProvider(this.tableUpdaterProvider);
        service.setRuntimeDataSchemeService(this.runtimeDataSchemeService);
        service.setDimCollectionBuildUtil(this.dimCollectionBuildUtil);
        return service;
    }

    @Override
    public IFieldDataService getFieldDataFileService(IParamDataProvider paramDataProvider) {
        FieldDataQueryServiceImpl service = new FieldDataQueryServiceImpl();
        service.setProviderStore(this.providerStore);
        service.setFieldRelationFactory(this.fieldRelationFactory);
        service.setStrategyFactory(this.strategyFactory);
        service.setParamDataProvider(paramDataProvider);
        service.setTableUpdaterProvider(this.tableUpdaterProvider);
        service.setRuntimeDataSchemeService(this.runtimeDataSchemeService);
        service.setDimCollectionBuildUtil(this.dimCollectionBuildUtil);
        return service;
    }

    @Override
    public IFieldDataService getFieldDataFileService(IProviderStore providerStore, IParamDataProvider paramDataProvider) {
        FieldDataQueryServiceImpl service = new FieldDataQueryServiceImpl();
        service.setProviderStore(providerStore);
        service.setFieldRelationFactory(this.fieldRelationFactory);
        service.setStrategyFactory(this.strategyFactory);
        service.setParamDataProvider(paramDataProvider);
        service.setTableUpdaterProvider(this.tableUpdaterProvider);
        service.setRuntimeDataSchemeService(this.runtimeDataSchemeService);
        service.setDimCollectionBuildUtil(this.dimCollectionBuildUtil);
        return service;
    }
}

