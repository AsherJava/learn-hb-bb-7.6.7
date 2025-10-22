/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.IDataQueryFactory
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.event.OperateRowEventListener
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory
 */
package com.jiuqi.nr.data.engine.grouping;

import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.IDataQueryFactory;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.event.OperateRowEventListener;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.data.engine.grouping.GroupingQueryImpl;
import com.jiuqi.nr.data.engine.grouping.IGroupingAccessProvider;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GroupingAccessProviderImpl
implements IGroupingAccessProvider {
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
    private IDataQueryFactory dataQueryFactory;
    @Autowired
    private NedisCacheManager cacheManager;
    @Autowired
    private EntityResetCacheService entityResetCacheService;
    private IEncryptDecryptProcesser encryptDecryptProcesser;
    @Autowired(required=false)
    private SplitTableHelper splitTableHelper;
    @Autowired
    private SymmetricEncryptFactory symmetricEncryptFactory;

    @Override
    public void registerDataQuery(IDataQueryFactory dataQueryFactory) {
        this.dataQueryFactory = dataQueryFactory;
    }

    @Override
    public IGroupingQuery newGroupingQuery() {
        GroupingQueryImpl groupingQueryImpl = new GroupingQueryImpl();
        QueryParam queryParam = this.getQueryParam();
        groupingQueryImpl.setQueryParam(queryParam);
        return groupingQueryImpl;
    }

    @Override
    public IGroupingQuery newGroupingQuery(QueryEnvironment queryEnvironment) {
        IGroupingQuery groupingQuery;
        IGroupingQuery iGroupingQuery = groupingQuery = this.dataQueryFactory == null ? null : this.dataQueryFactory.getGroupingQuery(queryEnvironment);
        if (groupingQuery == null) {
            return this.newGroupingQuery();
        }
        groupingQuery.setQueryParam(this.getQueryParam());
        return groupingQuery;
    }

    private QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController, this.designTimeController, this.entityViewRunTimeController);
        queryParam.setEntityLinkService(this.entityLinkService);
        queryParam.setEventListener(this.eventListener);
        queryParam.setCacheManager(this.cacheManager);
        queryParam.setEntityResetCacheService(this.entityResetCacheService);
        queryParam.setEncryptDecryptProcesser(this.encryptDecryptProcesser);
        queryParam.setSplitTableHelper(this.splitTableHelper);
        queryParam.setSymmetricEncryptFactory(this.symmetricEncryptFactory);
        return queryParam;
    }
}

