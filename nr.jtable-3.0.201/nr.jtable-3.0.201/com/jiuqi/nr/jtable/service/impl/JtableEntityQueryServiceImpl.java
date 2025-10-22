/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.internal.service.PeriodEngineService
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.jtable.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.jtable.exception.DataEngineQueryException;
import com.jiuqi.nr.jtable.exception.JtableExceptionCodeCost;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IEntityIsolateConditionProvider;
import com.jiuqi.nr.jtable.service.IJtableDataEngineService;
import com.jiuqi.nr.jtable.service.IJtableEntityQueryService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.internal.service.PeriodEngineService;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JtableEntityQueryServiceImpl
implements IJtableEntityQueryService {
    private static final Logger logger = LoggerFactory.getLogger(JtableEntityQueryServiceImpl.class);
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IJtableDataEngineService jtableDataEngineService;
    @Autowired(required=false)
    private List<IEntityIsolateConditionProvider> entityIsolateConditionProviders;
    @Autowired
    private PeriodEngineService periodEngineService;
    @Autowired
    private DataEntityFullService dataEntityFullService;
    @Resource
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;

    @Override
    public IEntityTable queryEntity(EntityViewData entityView, List<ReferRelation> referRelations, JtableContext jtableContext, boolean readAuth, boolean queryByKey, boolean ignoreIsolate, boolean desensitized) {
        return this.queryEntityTable(entityView, referRelations, jtableContext, readAuth, queryByKey, false, false, null, false, ignoreIsolate, desensitized);
    }

    @Override
    public IEntityTable queryEntityWithDimVal(EntityViewData entityView, List<ReferRelation> referRelations, JtableContext jtableContext, boolean readAuth, boolean queryByKey, DimensionValueSet dimensionValueSet) {
        return this.queryEntityTable(entityView, referRelations, jtableContext, readAuth, queryByKey, false, false, dimensionValueSet, false, false, false);
    }

    @Override
    public IEntityTable queryEntity(EntityViewData entityView, List<ReferRelation> referRelations, JtableContext jtableContext, boolean readAuth, boolean queryByKey, boolean sorted, boolean isFMDMform, boolean ignoreIsolate, boolean desensitized) {
        return this.queryEntityTable(entityView, referRelations, jtableContext, readAuth, queryByKey, sorted, false, null, isFMDMform, ignoreIsolate, desensitized);
    }

    @Override
    public IEntityTable queryDimEntity(EntityViewData entityView, List<ReferRelation> referRelations, JtableContext jtableContext, boolean readAuth, boolean queryByKey, boolean sorted) {
        return this.queryEntityTable(entityView, referRelations, jtableContext, readAuth, queryByKey, sorted, true, null, false, false, false);
    }

    private IEntityTable queryEntityTable(EntityViewData entityView, List<ReferRelation> referRelations, JtableContext jtableContext, boolean readAuth, boolean queryByKey, boolean sorted, boolean queryDim, DimensionValueSet dimensionValueSet, boolean isFMDMform, boolean ignoreIsolate, boolean desensitized) {
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getKey())) {
            return null;
        }
        if (jtableContext == null) {
            jtableContext = new JtableContext();
        }
        ExecutorContext oldContext = this.jtableDataEngineService.getExecutorContext(jtableContext, dimensionValueSet);
        com.jiuqi.nr.entity.engine.executors.ExecutorContext executorContext = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(oldContext.getRuntimeController());
        executorContext.setDefaultGroupName(oldContext.getDefaultGroupName());
        executorContext.setJQReportModel(oldContext.isJQReportModel());
        executorContext.setVarDimensionValueSet(oldContext.getVarDimensionValueSet());
        executorContext.setEnv(oldContext.getEnv());
        EntityViewData dataTimeEntity = this.jtableParamService.getDataTimeEntity(jtableContext.getFormSchemeKey());
        if (dataTimeEntity != null) {
            executorContext.setPeriodView(dataTimeEntity.getKey());
        }
        for (Variable variable : oldContext.getVariableManager().getAllVars()) {
            executorContext.getVariableManager().add(variable);
        }
        EntityViewDefine entityViewDefine = entityView.getEntityViewDefine();
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        if (desensitized) {
            entityQuery.maskedData();
        }
        entityQuery.sorted(sorted);
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setAuthorityOperations(readAuth ? AuthorityType.Read : AuthorityType.None);
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(jtableContext.getFormSchemeKey(), false);
        if (referRelations != null && !referRelations.isEmpty()) {
            for (ReferRelation referRelation : referRelations) {
                EntityViewDefine viewDefine = referRelation.getViewDefine();
                if (viewDefine.getEntityId().equals(dwEntity.getKey()) && isFMDMform) continue;
                entityQuery.addReferRelation(referRelation);
            }
        }
        entityQuery.setMasterKeys(new DimensionValueSet());
        String unitKey = "";
        if (StringUtils.isNotEmpty((String)jtableContext.getFormSchemeKey()) && jtableContext.getDimensionSet().containsKey(dwEntity.getDimensionName())) {
            unitKey = jtableContext.getDimensionSet().get(dwEntity.getDimensionName()).getValue();
            EntityViewDefine dwEntityViewDefine = this.entityViewRunTimeController.buildEntityView(dwEntity.getKey());
            executorContext.setMainView(dwEntityViewDefine);
        }
        boolean hasExtIsolation = false;
        if (this.entityIsolateConditionProviders != null && this.entityIsolateConditionProviders.size() > 0) {
            JtableContext isolateJtableContext = new JtableContext(jtableContext);
            for (IEntityIsolateConditionProvider entityIsolateConditionProvider : this.entityIsolateConditionProviders) {
                String entityIsolateCondition = entityIsolateConditionProvider.getEntityIsolateCondition(entityView.getKey(), isolateJtableContext);
                if (!StringUtils.isNotEmpty((String)entityIsolateCondition)) continue;
                entityQuery.setIsolateCondition(entityIsolateCondition);
                hasExtIsolation = true;
                break;
            }
        }
        if (!ignoreIsolate && StringUtils.isNotEmpty((String)unitKey) && StringUtils.isEmpty((String)executorContext.getIsolateCondition()) && !hasExtIsolation) {
            entityQuery.setIsolateCondition(unitKey);
        }
        if (jtableContext.getDimensionSet() != null && jtableContext.getDimensionSet().containsKey("DATATIME")) {
            String period = jtableContext.getDimensionSet().get("DATATIME").getValue();
            IPeriodProvider periodProvider = this.periodEngineService.getPeriodAdapter().getPeriodProvider(dataTimeEntity.getKey());
            Date versionDate = null;
            try {
                versionDate = periodProvider.getPeriodDateRegion(period)[1];
            }
            catch (ParseException e) {
                logger.error("\u83b7\u53d6\u65f6\u671f\u5931\u8d25" + e.getMessage());
            }
            entityQuery.setQueryVersionDate(versionDate);
        }
        IEntityTable entityTable = null;
        try {
            entityTable = queryByKey ? (!queryDim && jtableContext.getFormSchemeKey() != null ? this.dataEntityFullService.executeEntityReader(entityQuery, executorContext, entityViewDefine, jtableContext.getFormSchemeKey()).getEntityTable() : entityQuery.executeReader((IContext)executorContext)) : (!queryDim && jtableContext.getFormSchemeKey() != null ? this.dataEntityFullService.executeEntityFullBuild(entityQuery, executorContext, entityViewDefine, jtableContext.getFormSchemeKey()).getEntityTable() : entityQuery.executeFullBuild((IContext)executorContext));
        }
        catch (Exception e) {
            StringBuilder logInfo = this.getEntityQueryErrorInfo(entityView.getTitle(), e.getMessage());
            logger.error(logInfo.toString(), e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u5b9e\u4f53\u67e5\u8be2\u51fa\u9519"});
        }
        return entityTable;
    }

    private StringBuilder getEntityQueryErrorInfo(String entityTitle, String message) {
        StringBuilder errorInfo = new StringBuilder();
        errorInfo.append("\u5b9e\u4f53[").append(entityTitle).append("]\u67e5\u8be2\u51fa\u9519\uff0c\u51fa\u9519\u539f\u56e0\uff1a").append(message);
        return errorInfo;
    }

    @Override
    public IEntityTable querySimplEntityTable(EntityViewData entityView) {
        if (this.periodEntityAdapter.isPeriodEntity(entityView.getKey())) {
            return null;
        }
        com.jiuqi.nr.entity.engine.executors.ExecutorContext executorContext = new com.jiuqi.nr.entity.engine.executors.ExecutorContext(this.iDataDefinitionRuntimeController);
        EntityViewDefine entityViewDefine = entityView.getEntityViewDefine();
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.maskedData();
        entityQuery.setEntityView(entityViewDefine);
        entityQuery.setMasterKeys(new DimensionValueSet());
        entityQuery.setAuthorityOperations(AuthorityType.None);
        IEntityTable entityTable = null;
        try {
            entityTable = entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            StringBuilder logInfo = this.getEntityQueryErrorInfo(entityView.getTitle(), e.getMessage());
            logger.error(logInfo.toString(), e);
            throw new DataEngineQueryException(JtableExceptionCodeCost.DATAENGINE_QUERY_EXCEPTION, new String[]{"\u5b9e\u4f53\u67e5\u8be2\u51fa\u9519"});
        }
        return entityTable;
    }
}

