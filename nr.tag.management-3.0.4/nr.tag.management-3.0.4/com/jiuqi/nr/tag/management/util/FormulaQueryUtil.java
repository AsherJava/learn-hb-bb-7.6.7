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
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.tag.management.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.tag.management.intf.ITagQueryContext;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class FormulaQueryUtil {
    @Resource
    private IRunTimeViewController iRunTimeViewController;
    @Resource
    private IEntityDataService dataService;
    @Resource
    private com.jiuqi.nr.definition.controller.IRunTimeViewController viewController;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource(name="dataEntityFullService")
    private DataEntityFullService entityDataService;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IPeriodEntityAdapter periodAdapter;

    private FormulaQueryUtil() {
    }

    public List<String> getFilterEntityDatas(ITagQueryContext context, String formula) {
        FormSchemeDefine formScheme = this.iRunTimeViewController.getFormScheme(context.getFormScheme());
        IEntityQuery query = this.makeIEntityQuery(formScheme, context, formula);
        EntityViewDefine entityViewDefine = this.buildEntityView(formScheme, context);
        ExecutorContext executorContext = this.makeExecuteContext(formScheme, query);
        IEntityTable entityTable = null;
        try {
            entityTable = this.entityDataService.executeEntityReader(query, executorContext, entityViewDefine, context.getFormScheme()).getEntityTable();
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return entityTable.getAllRows().stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
    }

    public IEntityQuery makeIEntityQuery(FormSchemeDefine formScheme, ITagQueryContext context, String formula) {
        IEntityQuery query = this.dataService.newEntityQuery();
        query.sorted(true);
        query.setMasterKeys(this.getMasterKeys(formScheme, context));
        query.setAuthorityOperations(AuthorityType.Read);
        query.setEntityView(this.buildEntityView(formScheme, context));
        query.setExpression(formula);
        return query;
    }

    private DimensionValueSet getMasterKeys(FormSchemeDefine formScheme, ITagQueryContext context) {
        DimensionValueSet masterKeys = new DimensionValueSet();
        String period = context.getPeriod();
        IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(formScheme.getDateTime());
        if (periodEntity != null && StringUtils.isNotEmpty((String)period)) {
            masterKeys.setValue(periodEntity.getDimensionName(), (Object)period);
        }
        return masterKeys;
    }

    public ExecutorContext makeExecuteContext(FormSchemeDefine formScheme, IEntityQuery query) {
        IFmlExecEnvironment env;
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        executorContext.setVarDimensionValueSet(query.getMasterKeys());
        IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(formScheme.getDateTime());
        if (periodEntity != null) {
            executorContext.setPeriodView(periodEntity.getKey());
        }
        if (null == (env = executorContext.getEnv())) {
            env = new ReportFmlExecEnvironment(this.viewController, this.tbRtCtl, this.viewAdapter, formScheme.getKey());
            executorContext.setEnv(env);
        }
        return executorContext;
    }

    public EntityViewDefine buildEntityView(FormSchemeDefine formScheme, ITagQueryContext context) {
        IEntityDefine entityDefine = null;
        if (StringUtils.isNotEmpty((String)formScheme.getKey())) {
            return this.iRunTimeViewController.getViewByFormSchemeKey(formScheme.getKey());
        }
        entityDefine = this.metaService.queryEntity(context.getEntityId());
        return this.viewAdapter.buildEntityView(entityDefine.getId());
    }
}

