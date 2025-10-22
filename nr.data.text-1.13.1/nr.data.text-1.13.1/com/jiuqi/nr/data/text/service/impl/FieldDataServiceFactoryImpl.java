/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.attachment.service.ExpFieldDataFileService
 *  com.jiuqi.nr.data.attachment.service.ImpFieldDataFileService
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.fielddatacrud.FieldRelationFactory
 *  com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory
 */
package com.jiuqi.nr.data.text.service.impl;

import com.jiuqi.nr.data.attachment.service.ExpFieldDataFileService;
import com.jiuqi.nr.data.attachment.service.ImpFieldDataFileService;
import com.jiuqi.nr.data.text.api.IFieldDataFileServiceFactory;
import com.jiuqi.nr.data.text.service.ExpFieldDataService;
import com.jiuqi.nr.data.text.service.ImpFieldDataService;
import com.jiuqi.nr.data.text.service.impl.ExpFieldDataServiceImpl;
import com.jiuqi.nr.data.text.service.impl.ImpFieldDataServiceImpl;
import com.jiuqi.nr.data.text.spi.IParamDataFileProvider;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.dataservice.core.log.DataServiceLoggerFactory;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldDataServiceFactoryImpl
implements IFieldDataFileServiceFactory {
    @Autowired
    private IFieldDataServiceFactory fieldDataServiceFactory;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private FieldRelationFactory fieldRelationFactory;
    @Autowired
    private ExpFieldDataFileService expFieldDataFileService;
    @Autowired
    private ImpFieldDataFileService impFieldDataFileService;
    @Autowired
    private DataServiceLoggerFactory dataServiceLoggerFactory;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IProviderStore providerStore;

    @Override
    public ExpFieldDataService getExpFieldDataService(IParamDataFileProvider paramDataProvider) {
        ExpFieldDataServiceImpl expFieldDataService = new ExpFieldDataServiceImpl(this.fieldDataServiceFactory, this.runtimeDataSchemeService, this.fieldRelationFactory, this.expFieldDataFileService, this.dataServiceLoggerFactory);
        expFieldDataService.setProviderStore(this.providerStore);
        expFieldDataService.setParamDataProvider(paramDataProvider);
        return expFieldDataService;
    }

    @Override
    public ExpFieldDataService getExpFieldDataService(IProviderStore providerStore, IParamDataFileProvider paramDataProvider) {
        ExpFieldDataServiceImpl expFieldDataService = new ExpFieldDataServiceImpl(this.fieldDataServiceFactory, this.runtimeDataSchemeService, this.fieldRelationFactory, this.expFieldDataFileService, this.dataServiceLoggerFactory);
        expFieldDataService.setProviderStore(providerStore);
        expFieldDataService.setParamDataProvider(paramDataProvider);
        return expFieldDataService;
    }

    @Override
    public ImpFieldDataService getImpFieldDataService() {
        return new ImpFieldDataServiceImpl(this.fieldDataServiceFactory, this.runtimeDataSchemeService, this.fieldRelationFactory, this.impFieldDataFileService, this.dataServiceLoggerFactory, this.runTimeViewController);
    }

    @Override
    public ImpFieldDataService getImpFieldDataService(IProviderStore providerStore) {
        ImpFieldDataServiceImpl impFieldDataService = new ImpFieldDataServiceImpl(this.fieldDataServiceFactory, this.runtimeDataSchemeService, this.fieldRelationFactory, this.impFieldDataFileService, this.dataServiceLoggerFactory, this.runTimeViewController);
        impFieldDataService.setProviderStore(providerStore);
        return impFieldDataService;
    }

    @Override
    public ImpFieldDataService getImpFieldDataService(IParamDataFileProvider paramDataProvider) {
        ImpFieldDataServiceImpl impFieldDataService = new ImpFieldDataServiceImpl(this.fieldDataServiceFactory, this.runtimeDataSchemeService, this.fieldRelationFactory, this.impFieldDataFileService, this.dataServiceLoggerFactory, this.runTimeViewController);
        impFieldDataService.setParamDataFileProvider(paramDataProvider);
        return impFieldDataService;
    }

    @Override
    public ImpFieldDataService getImpFieldDataService(IProviderStore providerStore, IParamDataFileProvider paramDataProvider) {
        ImpFieldDataServiceImpl impFieldDataService = new ImpFieldDataServiceImpl(this.fieldDataServiceFactory, this.runtimeDataSchemeService, this.fieldRelationFactory, this.impFieldDataFileService, this.dataServiceLoggerFactory, this.runTimeViewController);
        impFieldDataService.setProviderStore(providerStore);
        impFieldDataService.setParamDataFileProvider(paramDataProvider);
        return impFieldDataService;
    }
}

