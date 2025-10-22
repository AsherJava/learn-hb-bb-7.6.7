/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.setting.OrderType
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.dataentity.service;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.dataentity.entity.IDataEntity;
import com.jiuqi.nr.dataentity.entity.data.DataEntity;
import com.jiuqi.nr.dataentity.param.DataEntityQueryParam;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.setting.OrderType;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service(value="dataEntityFullService")
public class DataEntityFullService {
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired
    private IRunTimeViewController runtimeView;

    public IDataEntity executeEntityReader(IEntityQuery entityQuery, ExecutorContext executorContext, EntityViewDefine entityView, String formSchemeKey) throws Exception {
        DimensionValueSet masterKeys = entityQuery.getMasterKeys();
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (masterKeys.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(formSchemeKey, masterKeys.getValue("DATATIME").toString());
            }
            return this.queryAdjust(formSchemeKey);
        }
        entityView = this.getNrContextEntityId(entityView, formSchemeKey);
        entityQuery.setEntityView(entityView);
        if (executorContext == null) {
            executorContext = this.getExecutorContext(formSchemeKey, masterKeys);
        }
        executorContext.setOrgEntityId(entityView.getEntityId());
        IEntityTable entityTables = entityQuery.executeReader((IContext)executorContext);
        return new DataEntity(entityTables, DataEntityType.DataEntity);
    }

    public IDataEntity executeEntityFullBuild(IEntityQuery entityQuery, ExecutorContext executorContext, EntityViewDefine entityView, String formSchemeKey) throws Exception {
        DimensionValueSet masterKeys = entityQuery.getMasterKeys();
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (masterKeys.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(formSchemeKey, masterKeys.getValue("DATATIME").toString());
            }
            return this.queryAdjust(formSchemeKey);
        }
        entityView = this.getNrContextEntityId(entityView, formSchemeKey);
        entityQuery.setEntityView(entityView);
        if (executorContext == null) {
            executorContext = this.getExecutorContext(formSchemeKey, masterKeys);
        }
        executorContext.setOrgEntityId(entityView.getEntityId());
        IEntityTable entityTables = entityQuery.executeFullBuild((IContext)executorContext);
        return new DataEntity(entityTables, DataEntityType.DataEntity);
    }

    public IDataEntity executeEntityRangeBuild(IEntityQuery entityQuery, ExecutorContext executorContext, EntityViewDefine entityView, RangeQuery rangeQuery, String formSchemeKey) throws Exception {
        DimensionValueSet masterKeys = entityQuery.getMasterKeys();
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (masterKeys.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(formSchemeKey, masterKeys.getValue("DATATIME").toString());
            }
            return this.queryAdjust(formSchemeKey);
        }
        entityView = this.getNrContextEntityId(entityView, formSchemeKey);
        entityQuery.setEntityView(entityView);
        if (executorContext == null) {
            executorContext = this.getExecutorContext(formSchemeKey, masterKeys);
        }
        executorContext.setOrgEntityId(entityView.getEntityId());
        IEntityTable entityTables = entityQuery.executeRangeBuild((IContext)executorContext, rangeQuery);
        return new DataEntity(entityTables, DataEntityType.DataEntity);
    }

    public IDataEntity executeEntityReader(DataEntityQueryParam dataEntityQueryParam) throws Exception {
        EntityViewDefine entityView = dataEntityQueryParam.getEntityView();
        DimensionValueSet masterKeys = dataEntityQueryParam.getMasterKeys();
        String formSchemeKey = dataEntityQueryParam.getFormSchemeKey();
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (masterKeys.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(formSchemeKey, masterKeys.getValue("DATATIME").toString());
            }
            return this.queryAdjust(formSchemeKey);
        }
        IEntityQuery entityQuery = this.getEntityQuery(dataEntityQueryParam);
        ExecutorContext context = dataEntityQueryParam.getContext();
        if (context == null) {
            context = this.getExecutorContext(formSchemeKey, masterKeys);
        }
        IEntityTable entityTables = entityQuery.executeReader((IContext)context);
        return new DataEntity(entityTables, DataEntityType.DataEntity);
    }

    public IDataEntity executeEntityFullBuild(DataEntityQueryParam dataEntityQueryParam) throws Exception {
        EntityViewDefine entityView = dataEntityQueryParam.getEntityView();
        DimensionValueSet masterKeys = dataEntityQueryParam.getMasterKeys();
        String formSchemeKey = dataEntityQueryParam.getFormSchemeKey();
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (masterKeys.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(formSchemeKey, masterKeys.getValue("DATATIME").toString());
            }
            return this.queryAdjust(formSchemeKey);
        }
        IEntityQuery entityQuery = this.getEntityQuery(dataEntityQueryParam);
        ExecutorContext context = dataEntityQueryParam.getContext();
        if (context == null) {
            context = this.getExecutorContext(formSchemeKey, masterKeys);
        }
        IEntityTable entityTables = entityQuery.executeFullBuild((IContext)context);
        return new DataEntity(entityTables, DataEntityType.DataEntity);
    }

    public IDataEntity executeEntityRangeBuild(DataEntityQueryParam dataEntityQueryParam) throws Exception {
        EntityViewDefine entityView = dataEntityQueryParam.getEntityView();
        DimensionValueSet masterKeys = dataEntityQueryParam.getMasterKeys();
        String formSchemeKey = dataEntityQueryParam.getFormSchemeKey();
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getEntityId())) {
            return this.queryPeriodEntity(entityView.getEntityId());
        }
        if (entityView.getEntityId().equals("ADJUST")) {
            if (masterKeys.getValue("DATATIME") != null) {
                return this.queryAdjustPeriods(formSchemeKey, masterKeys.getValue("DATATIME").toString());
            }
            return this.queryAdjust(formSchemeKey);
        }
        IEntityQuery entityQuery = this.getEntityQuery(dataEntityQueryParam);
        ExecutorContext context = dataEntityQueryParam.getContext();
        if (context == null) {
            context = this.getExecutorContext(formSchemeKey, masterKeys);
        }
        RangeQuery rangeQuery = dataEntityQueryParam.getRangeQuery();
        IEntityTable entityTables = entityQuery.executeRangeBuild((IContext)context, rangeQuery);
        return new DataEntity(entityTables, DataEntityType.DataEntity);
    }

    protected IDataEntity queryAdjust(String formSchemeKey) {
        List queryAdjustPeriods = this.formSchemeService.queryAdjustPeriods(formSchemeKey);
        return new DataEntity(null, queryAdjustPeriods, null, DataEntityType.DataEntityAdjust);
    }

    protected IDataEntity queryAdjustPeriods(String formSchemeKey, String period) {
        List queryAdjustPeriods = this.formSchemeService.queryAdjustPeriods(formSchemeKey, period);
        return new DataEntity(null, queryAdjustPeriods, null, DataEntityType.DataEntityAdjust);
    }

    protected IDataEntity queryPeriodEntity(String entityId) {
        IPeriodEntityAdapter periodAdapter = this.periodEngineService.getPeriodAdapter();
        EntityViewDefine viewDefine = this.entityViewRunTimeController.buildEntityView(entityId);
        IPeriodEntity periodEntity = periodAdapter.getPeriodEntity(viewDefine.getEntityId());
        DataEntity dataEntry = new DataEntity(DataEntityType.DataEntityPeriod);
        dataEntry.setPeriodEntity(periodEntity);
        return dataEntry;
    }

    private IEntityQuery getEntityQuery(DataEntityQueryParam dataEntityQueryParam) {
        EntityViewDefine entityView = dataEntityQueryParam.getEntityView();
        DimensionValueSet masterKeys = dataEntityQueryParam.getMasterKeys();
        String formSchemeKey = dataEntityQueryParam.getFormSchemeKey();
        boolean sorted = dataEntityQueryParam.isSorted();
        String isSolateCondition = dataEntityQueryParam.getEntityIsolateCondition();
        String expresion = dataEntityQueryParam.getExpression();
        Map<String, Boolean> orderAttributes = dataEntityQueryParam.getOrderAttribute();
        Date queryVersionDate = dataEntityQueryParam.getQueryVersionDate();
        String rowFilter = dataEntityQueryParam.getRowFilter();
        boolean ignoreViewFilter = dataEntityQueryParam.isIgnoreViewFiler();
        boolean querySorted = dataEntityQueryParam.isQuerySorted();
        boolean readAuth = dataEntityQueryParam.isReadAuth();
        entityView = this.getNrContextEntityId(entityView, formSchemeKey);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(entityView);
        if (StringUtils.isNotEmpty((String)expresion)) {
            entityQuery.setExpression(expresion);
        }
        if (StringUtils.isNotEmpty((String)isSolateCondition)) {
            entityQuery.setIsolateCondition(isSolateCondition);
        }
        if (Objects.nonNull(queryVersionDate)) {
            entityQuery.setQueryVersionDate(queryVersionDate);
        }
        if (StringUtils.isNotEmpty((String)rowFilter)) {
            entityQuery.setRowFilter(rowFilter);
        }
        if (!CollectionUtils.isEmpty(orderAttributes)) {
            Set<String> keys = orderAttributes.keySet();
            for (String key : keys) {
                entityQuery.addOrderAttribute(key, orderAttributes.get(key) != false ? OrderType.ASC : OrderType.DESC);
            }
        }
        entityQuery.setAuthorityOperations(readAuth ? AuthorityType.Read : AuthorityType.None);
        entityQuery.sorted(sorted);
        entityQuery.setIgnoreViewFilter(ignoreViewFilter);
        entityQuery.sortedByQuery(querySorted);
        entityQuery.setMasterKeys(masterKeys);
        return entityQuery;
    }

    private ExecutorContext getExecutorContext(String formSchemeKey, DimensionValueSet dimensionValueSet) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.runtimeView, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey);
        executorContext.setEnv((IFmlExecEnvironment)environment);
        executorContext.setJQReportModel(true);
        if (dimensionValueSet == null) {
            dimensionValueSet = new DimensionValueSet();
        }
        DimensionValueSet varDimensionValueSet = new DimensionValueSet();
        for (int i = 0; i < dimensionValueSet.size(); ++i) {
            String dimensionName = dimensionValueSet.getName(i);
            Object dimensionValue = dimensionValueSet.getValue(i);
            if (dimensionValue instanceof List) continue;
            varDimensionValueSet.setValue(dimensionName, dimensionValue);
        }
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        executorContext.setPeriodView(formScheme.getDateTime());
        executorContext.setVarDimensionValueSet(varDimensionValueSet);
        return executorContext;
    }

    private EntityViewDefine getNrContextEntityId(EntityViewDefine entityView, String formSchemeKey) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        String filterExpression = dsContext.getContextFilterExpression();
        if (StringUtils.isNotEmpty((String)entityId) && this.isQueryDw(entityView, formSchemeKey)) {
            if (StringUtils.isEmpty((String)filterExpression)) {
                filterExpression = entityView.getRowFilterExpression();
            }
            return this.entityViewRunTimeController.buildEntityView(entityId, filterExpression, entityView.getFilterRowByAuthority());
        }
        return entityView;
    }

    private boolean isQueryDw(EntityViewDefine entityView, String formSchemeKey) {
        FormSchemeDefine formScheme = this.runtimeView.getFormScheme(formSchemeKey);
        if (formScheme != null) {
            return formScheme.getDw().equals(entityView.getEntityId());
        }
        return false;
    }
}

