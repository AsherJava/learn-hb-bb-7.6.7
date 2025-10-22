/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.datacheckcommon.helper;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityQueryHelper {
    private static final String PERIOD_DIM = "DATATIME";
    @Resource
    private IEntityDataService entityDataService;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IDataDefinitionRuntimeController dataCtrl;
    @Resource
    private IRunTimeViewController viewCtrl;
    @Resource
    private IEntityViewRunTimeController entityViewCtrl;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;

    public IEntityQuery getEntityQuery(EntityViewDefine viewDefine, String period, String filterEntityList, String rowFilter, AuthorityType filterAuth, boolean ignoreViewCondition) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(viewDefine);
        DimensionValueSet valueSet = new DimensionValueSet();
        boolean filterSetted = false;
        if (StringUtils.isNotEmpty((String)period)) {
            valueSet.setValue(PERIOD_DIM, (Object)period);
            filterSetted = true;
        }
        if (StringUtils.isNotEmpty((String)filterEntityList)) {
            IEntityDefine entityDefine = this.metaService.queryEntity(viewDefine.getEntityId());
            valueSet.setValue(entityDefine.getDimensionName(), (Object)filterEntityList);
            filterSetted = true;
        }
        if (filterSetted) {
            query.setMasterKeys(valueSet);
        }
        if (StringUtils.isNotEmpty((String)rowFilter)) {
            query.setRowFilter(rowFilter);
        }
        query.setAuthorityOperations(filterAuth);
        query.setIgnoreViewFilter(ignoreViewCondition);
        return query;
    }

    public IEntityQuery getEntityQuery(String formSchemaKey, String period, String filterEntityList, String rowFilter, AuthorityType filterAuth, boolean ignoreViewCondition) {
        return this.getEntityQuery(this.getDwEntityView(formSchemaKey), period, filterEntityList, rowFilter, filterAuth, ignoreViewCondition);
    }

    public IEntityQuery getEntityQuery(String formSchemaKey, String period) {
        return this.getEntityQuery(formSchemaKey, period, null, null, AuthorityType.Read, false);
    }

    public IEntityQuery getEntityQuery(EntityViewDefine viewDefine, String period) {
        return this.getEntityQuery(viewDefine, period, null, null, AuthorityType.Read, false);
    }

    public IEntityQuery getEntityQuery(String formSchemaKey, String period, AuthorityType filterAuth) {
        return this.getEntityQuery(formSchemaKey, period, null, null, filterAuth, false);
    }

    public IEntityQuery getDimEntityQuery(String viewKey, String period) {
        return this.getEntityQuery(this.entityViewCtrl.buildEntityView(viewKey), period, null, null, AuthorityType.Read, false);
    }

    public IEntityTable buildEntityTable(IEntityQuery entityQuery, String formSchemeKey, boolean lessUse) throws Exception {
        ExecutorContext context = new ExecutorContext(this.dataCtrl);
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            ReportFmlExecEnvironment environment = new ReportFmlExecEnvironment(this.viewCtrl, this.dataCtrl, this.entityViewCtrl, formSchemeKey);
            context.setEnv((IFmlExecEnvironment)environment);
        }
        context.setVarDimensionValueSet(entityQuery.getMasterKeys());
        if (lessUse) {
            return entityQuery.executeReader((IContext)context);
        }
        return entityQuery.executeFullBuild((IContext)context);
    }

    public IEntityTable buildEntityTable(EntityViewDefine entityView, String period, String formSchemeKey, boolean lessUse) throws Exception {
        return this.buildEntityTable(this.getEntityQuery(entityView, period), formSchemeKey, lessUse);
    }

    public EntityViewDefine getDwEntityView(String formSchemeKey) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        String filterExpression = dsContext.getContextFilterExpression();
        EntityViewDefine entityViewDefine = this.viewCtrl.getViewByFormSchemeKey(formSchemeKey);
        if (StringUtils.isNotEmpty((String)entityId)) {
            if (StringUtils.isEmpty((String)filterExpression)) {
                filterExpression = entityViewDefine.getRowFilterExpression();
            }
            return this.entityViewCtrl.buildEntityView(entityId, filterExpression, entityViewDefine.getFilterRowByAuthority());
        }
        return entityViewDefine;
    }

    public String getTableNameByTableCode(String tableCode) {
        DataTable dataTable = this.runtimeDataSchemeService.getDataTableByCode(tableCode);
        DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)this.runtimeDataSchemeService.getDeployInfoByDataTableKey(dataTable.getKey()).get(0);
        return deployInfo.getTableName();
    }

    public String getFieldNameByFieldKey(String fieldKey) {
        DataFieldDeployInfo deployInfo = (DataFieldDeployInfo)this.runtimeDataSchemeService.getDeployInfoByDataFieldKeys(new String[]{fieldKey}).get(0);
        return deployInfo.getFieldName();
    }

    public EntityViewDefine getEntityView(String formSchemeKey, String viewKey) {
        EntityViewDefine dwEntityView = this.getDwEntityView(formSchemeKey);
        if (viewKey.equals(dwEntityView.getEntityId())) {
            return dwEntityView;
        }
        return this.entityViewCtrl.buildEntityView(viewKey);
    }

    public EntityViewDefine getEnumViewByEntityId(String entityId) {
        return this.entityViewCtrl.buildEntityView(entityId);
    }

    public HashMap<String, Integer> entityOrderByKey(IEntityTable entityTable) {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        ArrayList<IEntityRow> rootRows = new ArrayList<IEntityRow>(entityTable.getRootRows());
        rootRows.sort(new Comparator<IEntityRow>(){

            @Override
            public int compare(IEntityRow o1, IEntityRow o2) {
                if (o1 == null || o2 == null || o1.getEntityOrder() == null || o2.getEntityOrder() == null) {
                    return -1;
                }
                String l1 = o1.getEntityOrder().toString();
                String l2 = o2.getEntityOrder().toString();
                return l1.compareTo(l2);
            }
        });
        this.recursiveEntity(result, entityTable, rootRows);
        return result;
    }

    private void recursiveEntity(HashMap<String, Integer> result, IEntityTable entityTable, List<IEntityRow> rootRows) {
        for (IEntityRow row : rootRows) {
            result.put(row.getEntityKeyData(), result.size());
            this.recursiveEntity(result, entityTable, entityTable.getChildRows(row.getEntityKeyData()));
        }
    }

    public HashMap<String, Integer> formOrderByKey(String formSchemaKey) {
        HashMap<String, Integer> result = new HashMap<String, Integer>();
        int idx = 0;
        for (FormDefine form : this.viewCtrl.queryAllFormDefinesByFormScheme(formSchemaKey)) {
            result.put(form.getKey(), idx++);
        }
        return result;
    }
}

