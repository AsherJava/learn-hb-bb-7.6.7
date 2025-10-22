/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext$CountModel
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.setting.OrderType
 *  com.jiuqi.nr.entity.engine.var.RangeQuery
 *  com.jiuqi.nr.entity.engine.var.TreeRangeQuery
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuiqi.nr.unit.treebase.entity.query;

import com.jiuiqi.nr.unit.treebase.context.IUnitTreeContext;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeSortField;
import com.jiuiqi.nr.unit.treebase.context.impl.UnitTreeSortParam;
import com.jiuiqi.nr.unit.treebase.entity.query.IEntityQueryPloy;
import com.jiuiqi.nr.unit.treebase.entity.query.IUnitTreeEntityDataQuery;
import com.jiuiqi.nr.unit.treebase.entity.query.ScenesEntityRowQuery;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.setting.OrderType;
import com.jiuqi.nr.entity.engine.var.RangeQuery;
import com.jiuqi.nr.entity.engine.var.TreeRangeQuery;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.unit.treecommon.exception.UnitTreeRuntimeException;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

@Component(value="unit-tree-entity-data-query")
public class UnitTreeEntityDataQuery
implements IUnitTreeEntityDataQuery {
    @Resource
    private IEntityDataService dataService;
    @Resource
    private IRunTimeViewController viewController;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource(name="dataEntityFullService")
    private DataEntityFullService entityDataService;
    @Resource
    private ScenesEntityRowQuery entityRowQuery;

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context) {
        if (IEntityQueryPloy.MAIN_DIM_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            EntityViewDefine entityViewDefine = this.buildEntityView(context);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.executeEntityReader(query, entityViewDefine, executorContext, context.getFormScheme());
        }
        return this.entityRowQuery.makeIEntityTable(context);
    }

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context, boolean isCountChaE, boolean isOnlyCountLeaf) {
        if (IEntityQueryPloy.MAIN_DIM_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            if (!isCountChaE) {
                query.setExpression(IUnitTreeEntityDataQuery.buildFilters(context, "BBLX != '1' OR BBLX IS NULL"));
            }
            EntityViewDefine entityViewDefine = this.buildEntityView(context);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            executorContext.setCountModel(isOnlyCountLeaf ? ExecutorContext.CountModel.LEAF_ONLY : ExecutorContext.CountModel.ALL);
            return this.executeEntityReader(query, entityViewDefine, executorContext, context.getFormScheme());
        }
        return this.entityRowQuery.makeIEntityTable(context, isCountChaE, isOnlyCountLeaf);
    }

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context, List<String> rowKeys) {
        if (IEntityQueryPloy.MAIN_DIM_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context, rowKeys);
            EntityViewDefine entityViewDefine = this.buildEntityView(context);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.executeEntityReader(query, entityViewDefine, executorContext, context.getFormScheme());
        }
        return this.entityRowQuery.makeIEntityTable(context, rowKeys);
    }

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context, String ... filters) {
        if (IEntityQueryPloy.MAIN_DIM_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context, filters);
            EntityViewDefine entityViewDefine = this.buildEntityView(context);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.executeEntityReader(query, entityViewDefine, executorContext, context.getFormScheme());
        }
        return this.entityRowQuery.makeIEntityTable(context, filters);
    }

    @Override
    public IEntityTable makeIEntityTable(IUnitTreeContext context, List<String> rowKeys, String ... filters) {
        if (IEntityQueryPloy.MAIN_DIM_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context, rowKeys, filters);
            EntityViewDefine entityViewDefine = this.buildEntityView(context);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.executeEntityReader(query, entityViewDefine, executorContext, context.getFormScheme());
        }
        return this.entityRowQuery.makeIEntityTable(context, rowKeys, filters);
    }

    @Override
    public IEntityTable makeFullTreeData(IUnitTreeContext context) {
        if (IEntityQueryPloy.MAIN_DIM_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            EntityViewDefine entityViewDefine = this.buildEntityView(context);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.executeEntityFullBuild(query, entityViewDefine, executorContext, context.getFormScheme());
        }
        return this.entityRowQuery.makeFullTreeData(context);
    }

    @Override
    public IEntityTable makeRangeFullTreeData(IUnitTreeContext context, List<String> parentKeys) {
        if (IEntityQueryPloy.MAIN_DIM_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            EntityViewDefine entityViewDefine = this.buildEntityView(context);
            TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
            treeRangeQuery.setParentKey(parentKeys);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.executeEntityRangeBuild(query, entityViewDefine, executorContext, context.getFormScheme(), (RangeQuery)treeRangeQuery);
        }
        return this.entityRowQuery.makeRangeFullTreeData(context, parentKeys);
    }

    @Override
    public IEntityTable makeRangeFullTreeData(IUnitTreeContext context, List<String> parentKeys, boolean sorted) {
        if (IEntityQueryPloy.MAIN_DIM_QUERY == context.getEntityQueryPloy()) {
            IEntityQuery query = this.makeIEntityQuery(context);
            query.sorted(sorted);
            EntityViewDefine entityViewDefine = this.buildEntityView(context);
            TreeRangeQuery treeRangeQuery = new TreeRangeQuery();
            treeRangeQuery.setParentKey(parentKeys);
            ExecutorContext executorContext = this.makeExecuteContext(context, query);
            return this.executeEntityRangeBuild(query, entityViewDefine, executorContext, context.getFormScheme(), (RangeQuery)treeRangeQuery);
        }
        return this.entityRowQuery.makeRangeFullTreeData(context, parentKeys);
    }

    public boolean isTreeData(IUnitTreeContext ctx) {
        IEntityTable entityTable = this.makeIEntityTable(ctx);
        int maxDepth = entityTable.getMaxDepth();
        return maxDepth > 1;
    }

    private IEntityQuery makeIEntityQuery(IUnitTreeContext context) {
        IEntityQuery query = this.dataService.newEntityQuery();
        query.sorted(true);
        query.setMasterKeys(this.getMasterKeys(context));
        query.setAuthorityOperations(AuthorityType.Read);
        query.setEntityView(this.buildEntityView(context));
        query.setQueryVersionDate(context.getVersionDate());
        query.setExpression(this.getRowFilterExpression(context));
        query.lazyQuery();
        query.markLeaf();
        this.setQueryOrderAttribute(query, context);
        return query;
    }

    private IEntityQuery makeIEntityQuery(IUnitTreeContext context, List<String> rowKeys) {
        IEntityQuery query = this.makeIEntityQuery(context);
        query.getMasterKeys().setValue(context.getEntityDefine().getDimensionName(), rowKeys);
        return query;
    }

    private IEntityQuery makeIEntityQuery(IUnitTreeContext context, String ... filters) {
        IEntityQuery query = this.makeIEntityQuery(context);
        query.setExpression(IUnitTreeEntityDataQuery.buildFilters(context, filters));
        return query;
    }

    private IEntityQuery makeIEntityQuery(IUnitTreeContext context, List<String> rowKeys, String ... filters) {
        IEntityQuery query = this.makeIEntityQuery(context);
        query.getMasterKeys().setValue(context.getEntityDefine().getDimensionName(), rowKeys);
        query.setExpression(IUnitTreeEntityDataQuery.buildFilters(context, filters));
        return query;
    }

    private EntityViewDefine buildEntityView(IUnitTreeContext context) {
        FormSchemeDefine formScheme = context.getFormScheme();
        if (null != formScheme) {
            return this.viewController.getViewByFormSchemeKey(formScheme.getKey());
        }
        IEntityDefine entityDefine = context.getEntityDefine();
        return this.viewAdapter.buildEntityView(entityDefine.getId());
    }

    private ExecutorContext makeExecuteContext(IUnitTreeContext context, IEntityQuery query) {
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        executorContext.setVarDimensionValueSet(query.getMasterKeys());
        IPeriodEntity periodEntity = context.getPeriodEntity();
        if (periodEntity != null) {
            executorContext.setPeriodView(periodEntity.getKey());
        }
        IFmlExecEnvironment env = executorContext.getEnv();
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null && null == env) {
            env = new ReportFmlExecEnvironment(this.viewController, this.tbRtCtl, this.viewAdapter, formScheme.getKey());
            this.setEnvCustomVariable(env, context.getCustomVariable());
            executorContext.setEnv(env);
        }
        return executorContext;
    }

    private void setEnvCustomVariable(IFmlExecEnvironment env, JSONObject customVariable) {
        VariableManager variableManager;
        if (customVariable != null && (variableManager = env.getVariableManager()) != null && !customVariable.isEmpty()) {
            Map customVariableMap = customVariable.toMap();
            for (Map.Entry entry : customVariableMap.entrySet()) {
                variableManager.add(new Variable((String)entry.getKey(), (String)entry.getKey(), 6, entry.getValue()));
            }
        }
    }

    private String getRowFilterExpression(IUnitTreeContext context) {
        String expression = context.getRowFilterExpression();
        return StringUtils.isNotEmpty((String)expression) ? expression : null;
    }

    private DimensionValueSet getMasterKeys(IUnitTreeContext context) {
        DimensionValueSet masterKeys = new DimensionValueSet();
        String period = context.getPeriod();
        IEntityDefine entityDefine = context.getEntityDefine();
        IPeriodEntity periodEntity = context.getPeriodEntity();
        if (periodEntity != null && StringUtils.isNotEmpty((String)period)) {
            masterKeys.setValue(periodEntity.getDimensionName(), (Object)period);
            Map<String, DimensionValue> dimValueSet = context.getDimValueSet();
            if (dimValueSet != null && !dimValueSet.isEmpty()) {
                for (Map.Entry<String, DimensionValue> entry : dimValueSet.entrySet()) {
                    if (entityDefine.getDimensionName().equals(entry.getKey()) || periodEntity.getDimensionName().equals(entry.getKey())) continue;
                    masterKeys.setValue(entry.getValue().getName(), (Object)entry.getValue().getValue());
                }
            }
        }
        return masterKeys;
    }

    private void setQueryOrderAttribute(IEntityQuery query, IUnitTreeContext context) {
        UnitTreeSortParam unitTreeSortParam = UnitTreeSortParam.translate2SortParam(context.getCustomVariable());
        if (null != unitTreeSortParam && unitTreeSortParam.getFields() != null) {
            List<UnitTreeSortField> fields = unitTreeSortParam.getFields();
            OrderType orderType = unitTreeSortParam.isAscOrder() ? OrderType.ASC : OrderType.DESC;
            for (UnitTreeSortField field : fields) {
                query.addOrderAttribute(field.getCode(), orderType);
            }
        }
    }

    private IEntityTable executeEntityReader(IEntityQuery entityQuery, EntityViewDefine entityView, ExecutorContext executorContext, FormSchemeDefine formScheme) {
        try {
            String formSchemeKey = null;
            if (formScheme != null) {
                formSchemeKey = formScheme.getKey();
            }
            return this.entityDataService.executeEntityReader(entityQuery, executorContext, entityView, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    private IEntityTable executeEntityFullBuild(IEntityQuery entityQuery, EntityViewDefine entityView, ExecutorContext executorContext, FormSchemeDefine formScheme) {
        try {
            String formSchemeKey = null;
            if (formScheme != null) {
                formSchemeKey = formScheme.getKey();
            }
            return this.entityDataService.executeEntityFullBuild(entityQuery, executorContext, entityView, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }

    private IEntityTable executeEntityRangeBuild(IEntityQuery entityQuery, EntityViewDefine entityView, ExecutorContext executorContext, FormSchemeDefine formScheme, RangeQuery rangeQuery) {
        try {
            String formSchemeKey = null;
            if (formScheme != null) {
                formSchemeKey = formScheme.getKey();
            }
            return this.entityDataService.executeEntityRangeBuild(entityQuery, executorContext, entityView, rangeQuery, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            throw new UnitTreeRuntimeException((Throwable)e);
        }
    }
}

