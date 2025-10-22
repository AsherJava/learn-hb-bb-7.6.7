/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.bi.dataset.DataSet
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory
 */
package com.jiuqi.np.dataengine.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.bi.dataset.DataSet;
import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.IDataQueryFactory;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.config.DataEngineProperties;
import com.jiuqi.np.dataengine.definitions.FormulaCallBack;
import com.jiuqi.np.dataengine.event.OperateRowEventListener;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataChangeListener;
import com.jiuqi.np.dataengine.intf.IDataMaskingProcesser;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.IEncryptDecryptProcesser;
import com.jiuqi.np.dataengine.intf.IEntityQuery;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFieldDefineFinder;
import com.jiuqi.np.dataengine.intf.IFormulaRunner;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.IMainDimFilter;
import com.jiuqi.np.dataengine.intf.IValueValidateHandler;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.intf.impl.DataAssistImpl;
import com.jiuqi.np.dataengine.intf.impl.DataQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.DataSetExprEvaluator;
import com.jiuqi.np.dataengine.intf.impl.ExpressionEvaluatorImpl;
import com.jiuqi.np.dataengine.intf.impl.FormulaRunnerImpl;
import com.jiuqi.np.dataengine.intf.impl.GroupingQueryImpl;
import com.jiuqi.np.dataengine.intf.impl.MainDimFilterImpl;
import com.jiuqi.np.dataengine.multi.IMultiDimAdapter;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.encryption.crypto.SymmetricEncryptFactory;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataAccessProviderImpl
implements IDataAccessProvider {
    private static Logger logger = LoggerFactory.getLogger(DataAccessProviderImpl.class);
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IConnectionProvider connectionProvider;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired(required=false)
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
    @Autowired
    private IFieldDefineFinder fieldDefineFinder;
    @Autowired(required=false)
    List<IValueValidateHandler> valueValidateHandlers;
    @Autowired(required=false)
    private List<IDataChangeListener> dataChangeListeners;
    @Autowired(required=false)
    private SplitTableHelper splitTableHelper;
    @Autowired
    private NrdbHelper nrdbHelper;
    @Autowired
    private SymmetricEncryptFactory symmetricEncryptFactory;
    @Autowired(required=false)
    private DataEngineProperties dataEngineProperties;
    @Autowired(required=false)
    private IDataMaskingProcesser dataMaskingProcesser;
    @Autowired(required=false)
    private IMultiDimAdapter multiDimAdapter;

    @Override
    public IDataAssist newDataAssist(ExecutorContext context) {
        DataAssistImpl dataAssistImpl = new DataAssistImpl(context);
        QueryParam queryParam = this.getQueryParam();
        dataAssistImpl.setQueryParam(queryParam);
        return dataAssistImpl;
    }

    @Override
    public IDataQuery newDataQuery() {
        DataQueryImpl dataQueryImpl = new DataQueryImpl();
        QueryParam queryParam = this.getQueryParam();
        dataQueryImpl.setQueryParam(queryParam);
        return dataQueryImpl;
    }

    @Override
    public IDataQuery newDataQuery(QueryEnvironment queryEnvironment) {
        IDataQuery dataQuery;
        IDataQuery iDataQuery = dataQuery = this.dataQueryFactory == null ? null : this.dataQueryFactory.getDataQuery(queryEnvironment);
        if (dataQuery == null) {
            return this.newDataQuery();
        }
        dataQuery.setQueryParam(this.getQueryParam());
        return dataQuery;
    }

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

    @Override
    public IEntityQuery newEntityQuery() {
        return null;
    }

    @Override
    public IFormulaRunner newFormulaRunner(FormulaCallBack callBack) {
        FormulaRunnerImpl formulaRunnerImpl = new FormulaRunnerImpl(callBack);
        QueryParam queryParam = this.getFormulaParam();
        formulaRunnerImpl.setFormulaParam(queryParam);
        return formulaRunnerImpl;
    }

    @Override
    public QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController, this.designTimeController, this.entityViewRunTimeController, this.getDatabase());
        queryParam.setEntityLinkService(this.entityLinkService);
        queryParam.setEventListener(this.eventListener);
        queryParam.setCacheManager(this.cacheManager);
        queryParam.setEntityResetCacheService(this.entityResetCacheService);
        queryParam.setEncryptDecryptProcesser(this.encryptDecryptProcesser);
        queryParam.setValueValidateHandlers(this.valueValidateHandlers);
        queryParam.setDataChangeListeners(this.dataChangeListeners);
        queryParam.setSplitTableHelper(this.splitTableHelper);
        queryParam.setNrdbHelper(this.nrdbHelper);
        queryParam.setSymmetricEncryptFactory(this.symmetricEncryptFactory);
        queryParam.setProperties(this.dataEngineProperties);
        queryParam.setDataMaskingProcesser(this.dataMaskingProcesser);
        queryParam.setMultiDimAdapter(this.multiDimAdapter);
        return queryParam;
    }

    @Override
    public QueryParam getFormulaParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.runtimeController, this.getDatabase());
        queryParam.setEntityLinkService(this.entityLinkService);
        queryParam.setEncryptDecryptProcesser(this.encryptDecryptProcesser);
        queryParam.setDataChangeListeners(this.dataChangeListeners);
        queryParam.setSplitTableHelper(this.splitTableHelper);
        queryParam.setNrdbHelper(this.nrdbHelper);
        queryParam.setSymmetricEncryptFactory(this.symmetricEncryptFactory);
        queryParam.setProperties(this.dataEngineProperties);
        queryParam.setDataMaskingProcesser(this.dataMaskingProcesser);
        queryParam.setMultiDimAdapter(this.multiDimAdapter);
        return queryParam;
    }

    @Override
    public IExpressionEvaluator newExpressionEvaluator() {
        return new ExpressionEvaluatorImpl(this.getQueryParam());
    }

    @Override
    public IDataSetExprEvaluator newDataSetExprEvaluator(DataSet<FieldDefine> dataSet) {
        DataSetExprEvaluator dataSetExprEvaluator = new DataSetExprEvaluator(dataSet, this);
        dataSetExprEvaluator.setQueryParam(this.getQueryParam());
        return dataSetExprEvaluator;
    }

    @Override
    public void registerEncryptDecryptProcesser(IEncryptDecryptProcesser encryptDecryptProcesser) {
        this.encryptDecryptProcesser = encryptDecryptProcesser;
    }

    @Override
    public IEncryptDecryptProcesser getEncryptDecryptProcesser() {
        return this.encryptDecryptProcesser;
    }

    @Override
    public IMainDimFilter newMainDimFilter() {
        return new MainDimFilterImpl(this.getFormulaParam());
    }

    @Override
    public IDatabase getDatabase() {
        return DatabaseInstance.getDatabase();
    }
}

