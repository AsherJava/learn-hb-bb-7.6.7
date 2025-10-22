/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.impl.service.DataEngineService
 *  com.jiuqi.nr.datacrud.spi.IExecutorContextFactory
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DataTableType
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 *  com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor
 */
package com.jiuqi.nr.fielddatacrud.impl;

import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datacrud.spi.IExecutorContextFactory;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DataTableType;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.config.FieldDataProperties;
import com.jiuqi.nr.fielddatacrud.impl.IFieldDataStrategy;
import com.jiuqi.nr.fielddatacrud.impl.strategy.SingleTableDataStrategy;
import com.jiuqi.nr.fielddatacrud.impl.strategy.SplitSingleTableDataStrategy;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nvwa.encryption.desensitization.common.DesensitizedEncryptor;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldDataStrategyFactory {
    @Autowired
    private DataEngineService dataEngineService;
    @Autowired
    private IExecutorContextFactory executorContextFactory;
    @Autowired
    private IProviderStore providerStore;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired(required=false)
    private IParamDataProvider paramDataProvider;
    @Autowired
    private FieldDataProperties fieldDataProperties;
    @Autowired
    private DesensitizedEncryptor desensitizedEncryptor;

    public IFieldDataStrategy getStrategy(IFieldQueryInfo queryInfo, FieldRelation fieldRelation) {
        List<IMetaData> metaData = fieldRelation.getMetaData(queryInfo.selectFieldItr());
        DataTableType dataTableType = fieldRelation.getDataTableType(metaData.get(0));
        if (dataTableType == DataTableType.ACCOUNT) {
            return new SplitSingleTableDataStrategy(this);
        }
        return new SingleTableDataStrategy(this);
    }

    public DataEngineService getDataEngineService() {
        return this.dataEngineService;
    }

    public IExecutorContextFactory getExecutorContextFactory() {
        return this.executorContextFactory;
    }

    public IProviderStore getProviderStore() {
        return this.providerStore;
    }

    public IRuntimeDataSchemeService getRuntimeDataSchemeService() {
        return this.runtimeDataSchemeService;
    }

    public IParamDataProvider getParamDataProvider() {
        return this.paramDataProvider;
    }

    public void setParamDataProvider(IParamDataProvider paramDataProvider) {
        this.paramDataProvider = paramDataProvider;
    }

    public FieldDataProperties getFieldDataProperties() {
        return this.fieldDataProperties;
    }

    public void copy(FieldDataStrategyFactory factory) {
        factory.dataEngineService = this.dataEngineService;
        factory.executorContextFactory = this.executorContextFactory;
        factory.providerStore = this.providerStore;
        factory.runtimeDataSchemeService = this.runtimeDataSchemeService;
        factory.paramDataProvider = this.paramDataProvider;
        factory.fieldDataProperties = this.fieldDataProperties;
        factory.desensitizedEncryptor = this.desensitizedEncryptor;
    }

    public void setDataEngineService(DataEngineService dataEngineService) {
        this.dataEngineService = dataEngineService;
    }

    public void setExecutorContextFactory(IExecutorContextFactory executorContextFactory) {
        this.executorContextFactory = executorContextFactory;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public void setFieldDataProperties(FieldDataProperties fieldDataProperties) {
        this.fieldDataProperties = fieldDataProperties;
    }

    public DesensitizedEncryptor getDesensitizedEncryptor() {
        return this.desensitizedEncryptor;
    }
}

