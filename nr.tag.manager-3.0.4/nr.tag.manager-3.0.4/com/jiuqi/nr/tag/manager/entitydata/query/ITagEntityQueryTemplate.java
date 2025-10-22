/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.manager.entitydata.query;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.tag.manager.entitydata.query.TagQueryParam;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;

@Component
public class ITagEntityQueryTemplate {
    @Resource
    private IEntityDataService dataService;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IRunTimeViewController viewController;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;

    public IEntityQuery makeIEntityQuery(String entityId) {
        IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
        EntityViewDefine entityView = this.viewAdapter.buildEntityView(entityDefine.getId());
        IEntityQuery query = this.dataService.newEntityQuery();
        query.sorted(true);
        query.setEntityView(entityView);
        query.setAuthorityOperations(AuthorityType.Read);
        return query;
    }

    public IEntityQuery makeIEntityQuery(String entityId, List<String> rowKeys) {
        IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue(entityDefine.getDimensionName(), rowKeys);
        IEntityQuery query = this.makeIEntityQuery(entityId);
        query.setMasterKeys(masterKeys);
        return query;
    }

    public IEntityQuery makeIEntityQuery(TagQueryParam context) {
        IEntityQuery query = this.dataService.newEntityQuery();
        query.sorted(true);
        query.setMasterKeys(this.getMasterKeys(context));
        query.setAuthorityOperations(AuthorityType.Read);
        query.setEntityView(this.buildEntityView(context));
        query.setQueryVersionDate(context.getVersionDate());
        query.setExpression(this.getRowFilterExpression(context));
        return query;
    }

    public IEntityQuery makeIEntityQuery(TagQueryParam context, List<String> rowKeys) {
        IEntityQuery query = this.makeIEntityQuery(context);
        query.getMasterKeys().setValue(context.getEntityDefine().getDimensionName(), rowKeys);
        return query;
    }

    public IEntityQuery makeIEntityQuery(TagQueryParam context, String ... filters) {
        IEntityQuery query = this.makeIEntityQuery(context);
        query.setExpression(this.getRowQueryFilters(filters));
        return query;
    }

    public IEntityQuery makeIEntityQuery(TagQueryParam context, List<String> rowKeys, String ... filters) {
        IEntityQuery query = this.makeIEntityQuery(context);
        query.getMasterKeys().setValue(context.getEntityDefine().getDimensionName(), rowKeys);
        query.setExpression(this.getRowQueryFilters(filters));
        return query;
    }

    public ExecutorContext makeExecuteContext() {
        return new ExecutorContext(this.tbRtCtl);
    }

    public ExecutorContext makeExecuteContext(TagQueryParam context, IEntityQuery query) {
        ExecutorContext executorContext = this.makeExecuteContext();
        executorContext.setVarDimensionValueSet(query.getMasterKeys());
        IPeriodEntity periodEntity = context.getPeriodEntity();
        if (periodEntity != null) {
            executorContext.setPeriodView(periodEntity.getKey());
        }
        IFmlExecEnvironment env = executorContext.getEnv();
        FormSchemeDefine formScheme = context.getFormScheme();
        if (formScheme != null && null == env) {
            env = new ReportFmlExecEnvironment(this.viewController, this.tbRtCtl, this.viewAdapter, formScheme.getKey());
            executorContext.setEnv(env);
        }
        return executorContext;
    }

    public EntityViewDefine buildEntityView(TagQueryParam context) {
        IEntityDefine entityDefine = context.getEntityDefine();
        FormSchemeDefine formScheme = context.getFormScheme();
        if (null != formScheme) {
            return this.viewController.getViewByFormSchemeKey(formScheme.getKey());
        }
        return this.viewAdapter.buildEntityView(entityDefine.getId());
    }

    private String getRowFilterExpression(TagQueryParam context) {
        String expression = context.getRowFilterExpression();
        return StringUtils.isNotEmpty((String)expression) ? expression : null;
    }

    private String getRowQueryFilters(String ... filters) {
        if (filters != null && filters.length > 0) {
            StringBuilder expression = new StringBuilder("(" + filters[0] + ")");
            for (int i = 1; i < filters.length; ++i) {
                expression.append(" AND (").append(filters[i]).append(") ");
            }
            return expression.toString();
        }
        return null;
    }

    private DimensionValueSet getMasterKeys(TagQueryParam context) {
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
}

