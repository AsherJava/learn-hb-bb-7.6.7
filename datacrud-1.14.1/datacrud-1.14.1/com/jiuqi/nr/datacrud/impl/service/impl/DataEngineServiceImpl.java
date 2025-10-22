/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.database.IDatabase
 *  com.jiuqi.np.authz2.service.EntityIdentityService
 *  com.jiuqi.np.dataengine.IConnectionProvider
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.QueryEnvironment
 *  com.jiuqi.np.dataengine.QueryParam
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.event.OperateRowEventListener
 *  com.jiuqi.np.dataengine.exception.ExpressionException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.EntityResetCacheService
 *  com.jiuqi.np.dataengine.intf.ICommonQuery
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IExpressionEvaluator
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.intf.IGroupingQuery
 *  com.jiuqi.np.dataengine.intf.SplitTableHelper
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.common.db.DatabaseInstance
 *  com.jiuqi.nr.data.engine.grouping.IGroupingAccessProvider
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 */
package com.jiuqi.nr.datacrud.impl.service.impl;

import com.jiuqi.bi.database.IDatabase;
import com.jiuqi.np.authz2.service.EntityIdentityService;
import com.jiuqi.np.dataengine.IConnectionProvider;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.QueryEnvironment;
import com.jiuqi.np.dataengine.QueryParam;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.event.OperateRowEventListener;
import com.jiuqi.np.dataengine.exception.ExpressionException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.EntityResetCacheService;
import com.jiuqi.np.dataengine.intf.ICommonQuery;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IExpressionEvaluator;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.intf.IGroupingQuery;
import com.jiuqi.np.dataengine.intf.SplitTableHelper;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionDesignTimeController;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.common.db.DatabaseInstance;
import com.jiuqi.nr.data.engine.grouping.IGroupingAccessProvider;
import com.jiuqi.nr.datacrud.impl.RegionRelation;
import com.jiuqi.nr.datacrud.impl.service.DataEngineService;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class DataEngineServiceImpl
implements DataEngineService {
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IGroupingAccessProvider groupingAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IRuntimeDataSchemeService dataSchemeService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IConnectionProvider connectionProvider;
    @Autowired(required=false)
    private IDataDefinitionDesignTimeController designTimeController;
    @Autowired
    private EntityIdentityService entityLinkService;
    @Autowired
    private OperateRowEventListener eventListener;
    @Autowired
    private EntityResetCacheService entityResetCacheService;
    @Autowired(required=false)
    private SplitTableHelper splitTableHelper;
    private static final Logger logger = LoggerFactory.getLogger(DataEngineServiceImpl.class);

    @Override
    public IDataQuery getDataQuery() {
        return this.dataAccessProvider.newDataQuery();
    }

    @Override
    public IDataQuery getDataQuery(RegionRelation regionRelation) {
        QueryEnvironment queryEnvironment = this.buildEnv(regionRelation);
        return this.dataAccessProvider.newDataQuery(queryEnvironment);
    }

    private QueryEnvironment buildEnv(RegionRelation regionRelation) {
        QueryEnvironment queryEnvironment = new QueryEnvironment();
        queryEnvironment.setFormSchemeKey(regionRelation.getFormSchemeDefine().getKey());
        queryEnvironment.setRegionKey(regionRelation.getRegionDefine().getKey());
        queryEnvironment.setFormulaSchemeKey(regionRelation.getFormulaSchemeKey());
        queryEnvironment.setFormKey(regionRelation.getFormDefine().getKey());
        queryEnvironment.setFormCode(regionRelation.getFormDefine().getFormCode());
        return queryEnvironment;
    }

    @Override
    public ExecutorContext getExecutorContext(RegionRelation regionRelation, DimensionCombination dimensionCombination) {
        return this.getExecutorContext(regionRelation, dimensionCombination == null ? null : dimensionCombination.toDimensionValueSet(), null);
    }

    @Override
    public ExecutorContext getExecutorContext(RegionRelation regionRelation, DimensionValueSet dimensionValueSet) {
        return this.getExecutorContext(regionRelation, dimensionValueSet, null);
    }

    public ExecutorContext getExecutorContext(RegionRelation regionRelation, DimensionValueSet dimensionCombination, Map<String, Object> var) {
        VariableManager variableManager;
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        FormDefine form = regionRelation.getFormDefine();
        if (form != null) {
            executorContext.setDefaultGroupName(form.getFormCode());
        }
        executorContext.setJQReportModel(true);
        FormSchemeDefine formSchemeDefine = regionRelation.getFormSchemeDefine();
        executorContext.setVarDimensionValueSet(dimensionCombination);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.iEntityViewRunTimeController, formSchemeDefine.getKey());
        executorContext.setEnv((IFmlExecEnvironment)environment);
        if (!CollectionUtils.isEmpty(var) && (variableManager = executorContext.getVariableManager()) != null) {
            for (Map.Entry<String, Object> variableEnt : var.entrySet()) {
                Variable variable = new Variable(variableEnt.getKey(), 6);
                variable.setVarValue(variableEnt.getValue());
                variableManager.add(variable);
            }
        }
        return executorContext;
    }

    @Override
    public AbstractData expressionEvaluate(String condition, ExecutorContext context, DimensionValueSet dimensionValueSet) {
        return this.expressionEvaluate(condition, context, dimensionValueSet, null);
    }

    @Override
    public AbstractData expressionEvaluate(String condition, ExecutorContext context, DimensionValueSet dimensionValueSet, RegionRelation regionRelation) {
        if (StringUtils.hasLength(condition)) {
            IExpressionEvaluator expressionEvaluator = this.dataAccessProvider.newExpressionEvaluator();
            try {
                QueryEnvironment queryEnvironment = new QueryEnvironment();
                if (regionRelation != null) {
                    queryEnvironment.setFormCode(regionRelation.getFormDefine().getFormCode());
                    queryEnvironment.setFormKey(regionRelation.getFormDefine().getKey());
                    queryEnvironment.setFormulaSchemeKey(regionRelation.getFormulaSchemeKey());
                    queryEnvironment.setRegionKey(regionRelation.getRegionDefine().getKey());
                    queryEnvironment.setFormSchemeKey(regionRelation.getFormSchemeDefine().getKey());
                }
                return expressionEvaluator.eval(condition, context, queryEnvironment, dimensionValueSet);
            }
            catch (ExpressionException e) {
                logger.error("\u516c\u5f0f\u3010" + condition + "\u3011\u6c42\u503c\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                return null;
            }
        }
        return null;
    }

    @Override
    public IGroupingQuery getGroupingQuery(RegionRelation regionRelation) {
        QueryEnvironment queryEnvironment = this.buildEnv(regionRelation);
        return this.groupingAccessProvider.newGroupingQuery(queryEnvironment);
    }

    @Override
    public IDataAssist getDataAssist(ExecutorContext context) {
        return this.dataAccessProvider.newDataAssist(context);
    }

    private void setDefaultGroupName(DataRegionDefine regionDefine, ICommonQuery query) {
        if (StringUtils.hasLength(regionDefine.getInputOrderFieldKey())) {
            FieldDefine inputOrderField = null;
            try {
                inputOrderField = this.dataDefinitionRuntimeController.queryFieldDefine(regionDefine.getInputOrderFieldKey());
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
            if (inputOrderField != null) {
                List deployInfos = this.dataSchemeService.getDeployInfoByDataFieldKeys(new String[]{inputOrderField.getKey()});
                query.setDefaultGroupName(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
            }
        }
    }

    @Override
    public QueryParam getQueryParam() {
        QueryParam queryParam = new QueryParam(this.connectionProvider, this.dataDefinitionRuntimeController, this.designTimeController, this.iEntityViewRunTimeController, this.getDatabase());
        queryParam.setEntityLinkService(this.entityLinkService);
        queryParam.setEventListener(this.eventListener);
        queryParam.setEntityResetCacheService(this.entityResetCacheService);
        queryParam.setSplitTableHelper(this.splitTableHelper);
        return queryParam;
    }

    @Override
    public IDatabase getDatabase() {
        return DatabaseInstance.getDatabase();
    }
}

