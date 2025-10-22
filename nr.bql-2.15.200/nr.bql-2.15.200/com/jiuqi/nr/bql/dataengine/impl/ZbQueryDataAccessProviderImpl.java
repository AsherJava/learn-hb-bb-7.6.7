/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.event.OperateRowEventListener
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.dataengine.intf.IDataChangeListener
 *  com.jiuqi.np.dataengine.intf.IDataMaskingProcesser
 *  com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser
 *  com.jiuqi.np.dataengine.intf.IValueValidateHandler
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory
 */
package com.jiuqi.nr.bql.dataengine.impl;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.event.OperateRowEventListener;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.IDataChangeListener;
import com.jiuqi.np.dataengine.intf.IDataMaskingProcesser;
import com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser;
import com.jiuqi.np.dataengine.intf.IValueValidateHandler;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.bql.dataengine.ICommonQuery;
import com.jiuqi.nr.bql.dataengine.IDataAccessProvider;
import com.jiuqi.nr.bql.dataengine.IGroupingQuery;
import com.jiuqi.nr.bql.dataengine.impl.DataQueryImpl;
import com.jiuqi.nr.bql.dataengine.impl.GroupingQueryImpl;
import com.jiuqi.nr.bql.dataengine.query.account.AccountDataQueryImpl;
import com.jiuqi.nr.bql.dataengine.query.account.AccountGroupingQueryImpl;
import com.jiuqi.nr.bql.intf.ISqlConditionProcesser;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ZbQueryDataAccessProviderImpl
implements IDataAccessProvider {
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
    private IEncryptDecryptProcesser encryptDecryptProcesser;
    @Autowired(required=false)
    List<IValueValidateHandler> valueValidateHandlers;
    @Autowired(required=false)
    private List<IDataChangeListener> dataChangeListeners;
    @Autowired(required=false)
    private SplitTableHelper splitTableHelper;
    @Autowired(required=false)
    private ISqlConditionProcesser sqlConditionProcesser;
    @Autowired
    private SymmetricEncryptFactory symmetricEncryptFactory;
    @Autowired(required=false)
    private IDataMaskingProcesser dataMaskingProcesser;

    @Override
    public ICommonQuery newDataQuery() {
        DataQueryImpl dataQueryImpl = new DataQueryImpl();
        QueryParam queryParam = this.getQueryParam();
        dataQueryImpl.setQueryParam(queryParam);
        dataQueryImpl.setSqlConditionProcesser(this.sqlConditionProcesser);
        return dataQueryImpl;
    }

    @Override
    public IGroupingQuery newGroupingQuery() {
        GroupingQueryImpl groupingQueryImpl = new GroupingQueryImpl();
        QueryParam queryParam = this.getQueryParam();
        groupingQueryImpl.setQueryParam(queryParam);
        groupingQueryImpl.setSqlConditionProcesser(this.sqlConditionProcesser);
        return groupingQueryImpl;
    }

    @Override
    public ICommonQuery newAccountDataQuery() {
        AccountDataQueryImpl dataQueryImpl = new AccountDataQueryImpl();
        QueryParam queryParam = this.getQueryParam();
        dataQueryImpl.setQueryParam(queryParam);
        return dataQueryImpl;
    }

    @Override
    public IGroupingQuery newAccountGroupingQuery() {
        AccountGroupingQueryImpl groupingQueryImpl = new AccountGroupingQueryImpl();
        QueryParam queryParam = this.getQueryParam();
        groupingQueryImpl.setQueryParam(queryParam);
        return groupingQueryImpl;
    }

    protected QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController, this.designTimeController, this.entityViewRunTimeController);
        queryParam.setEntityLinkService(this.entityLinkService);
        queryParam.setEventListener(this.eventListener);
        queryParam.setCacheManager(this.cacheManager);
        queryParam.setEntityResetCacheService(this.entityResetCacheService);
        queryParam.setEncryptDecryptProcesser(this.encryptDecryptProcesser);
        queryParam.setValueValidateHandlers(this.valueValidateHandlers);
        queryParam.setDataChangeListeners(this.dataChangeListeners);
        queryParam.setSplitTableHelper(this.splitTableHelper);
        queryParam.setSymmetricEncryptFactory(this.symmetricEncryptFactory);
        queryParam.setDataMaskingProcesser(this.dataMaskingProcesser);
        return queryParam;
    }

    protected QueryParam getFormulaParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController, null);
        queryParam.setEntityLinkService(this.entityLinkService);
        queryParam.setEncryptDecryptProcesser(this.encryptDecryptProcesser);
        queryParam.setDataChangeListeners(this.dataChangeListeners);
        queryParam.setSplitTableHelper(this.splitTableHelper);
        queryParam.setSymmetricEncryptFactory(this.symmetricEncryptFactory);
        queryParam.setDataMaskingProcesser(this.dataMaskingProcesser);
        return queryParam;
    }
}

