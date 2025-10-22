/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.datacrud.impl.out.CrudException
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.common.IProviderStore
 */
package com.jiuqi.nr.fielddatacrud.impl;

import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.datacrud.impl.out.CrudException;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.common.IProviderStore;
import com.jiuqi.nr.fielddatacrud.FieldRelationFactory;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.IFieldQueryInfo;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.api.IFieldDataService;
import com.jiuqi.nr.fielddatacrud.impl.DefaultFieldDataQueryServiceImpl;
import com.jiuqi.nr.fielddatacrud.impl.FieldDataStrategyFactory;
import com.jiuqi.nr.fielddatacrud.spi.IDataReader;
import com.jiuqi.nr.fielddatacrud.spi.IParamDataProvider;
import com.jiuqi.nr.fielddatacrud.spi.TableUpdaterProvider;

public class FieldDataQueryServiceImpl
extends DefaultFieldDataQueryServiceImpl
implements IFieldDataService {
    private IProviderStore providerStore;
    private IParamDataProvider paramDataProvider;

    @Override
    public void queryTableData(IFieldQueryInfo queryInfo, IDataReader dataReader) throws CrudException {
        FieldDataStrategyFactory strategyFactory = new FieldDataStrategyFactory();
        this.strategyFactory.copy(strategyFactory);
        if (this.providerStore != null) {
            strategyFactory.setProviderStore(this.providerStore);
        }
        if (this.paramDataProvider != null) {
            strategyFactory.setParamDataProvider(this.paramDataProvider);
        }
        this.strategyFactory = strategyFactory;
        super.queryTableData(queryInfo, dataReader);
    }

    @Override
    public TableUpdater getTableUpdater(FieldSaveInfo saveInfo) {
        FieldDataStrategyFactory strategyFactory = new FieldDataStrategyFactory();
        this.strategyFactory.copy(strategyFactory);
        if (this.providerStore != null) {
            strategyFactory.setProviderStore(this.providerStore);
        }
        if (this.paramDataProvider != null) {
            strategyFactory.setParamDataProvider(this.paramDataProvider);
        }
        this.strategyFactory = strategyFactory;
        return super.getTableUpdater(saveInfo);
    }

    public void setFieldRelationFactory(FieldRelationFactory fieldRelationFactory) {
        this.fieldRelationFactory = fieldRelationFactory;
    }

    public void setStrategyFactory(FieldDataStrategyFactory strategyFactory) {
        this.strategyFactory = strategyFactory;
    }

    public void setProviderStore(IProviderStore providerStore) {
        this.providerStore = providerStore;
    }

    public void setParamDataProvider(IParamDataProvider paramDataProvider) {
        this.paramDataProvider = paramDataProvider;
    }

    public void setTableUpdaterProvider(TableUpdaterProvider tableUpdaterProvider) {
        this.tableUpdaterProvider = tableUpdaterProvider;
    }

    public void setRuntimeDataSchemeService(IRuntimeDataSchemeService runtimeDataSchemeService) {
        this.runtimeDataSchemeService = runtimeDataSchemeService;
    }

    public void setDimCollectionBuildUtil(DimCollectionBuildUtil dimCollectionBuildUtil) {
        this.dimCollectionBuildUtil = dimCollectionBuildUtil;
    }
}

