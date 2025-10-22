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
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.bpm.de.dataflow.util;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={NpRollbackException.class})
@Service
public class DeEntityQueryManager
implements IEntityUpgrader {
    private static final Logger logger = LoggerFactory.getLogger(DeEntityQueryManager.class);
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource
    private IEntityViewRunTimeController viewRtCtl;
    @Resource
    private IRunTimeViewController controller;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private DataEntityFullService dataEntityFullService;

    public IEntityTable buildEntityTable(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey) {
        IEntityQuery query = this.buildQuery(view, valueSet, formSchemeKey);
        ExecutorContext context = this.executorContext(formSchemeKey, valueSet);
        return this.entityQuerySet(view, query, context, formSchemeKey);
    }

    public IEntityTable buildFullEntityTable(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey) {
        IEntityTable rsSet = null;
        IEntityQuery query = this.buildQuery(view, valueSet, formSchemeKey);
        ExecutorContext context = this.executorContext(formSchemeKey, valueSet);
        try {
            FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
            rsSet = this.executeEntityFullBuild(query, view, context, formScheme);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rsSet;
    }

    public IEntityQuery buildQuery(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setMasterKeys(valueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        query.setEntityView(view);
        return query;
    }

    private ExecutorContext executorContext(String formSchemeKey, DimensionValueSet dim) {
        ExecutorContext context = new ExecutorContext(this.tbRtCtl);
        context.setVarDimensionValueSet(dim);
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.controller, this.tbRtCtl, this.entityViewRunTimeController, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return context;
    }

    public IEntityTable entityQuerySet(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey, AuthorityType authorityType) {
        IEntityQuery query = this.buildQuery(view, valueSet, formSchemeKey);
        query.setAuthorityOperations(authorityType);
        ExecutorContext context = this.executorContext(formSchemeKey, valueSet);
        return this.entityQuerySet(view, query, context, formSchemeKey);
    }

    private IEntityTable entityQuerySet(EntityViewDefine view, IEntityQuery entityQuery, ExecutorContext context, String formSchemeKey) {
        IEntityTable rsSet = null;
        try {
            FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
            return this.executeEntityReader(entityQuery, view, context, formScheme);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return rsSet;
        }
    }

    private IEntityTable entityQuerySetByEntityView(EntityViewDefine view, IEntityQuery entityQuery, ExecutorContext context, String formSchemeKey) {
        IEntityTable rsSet = null;
        try {
            FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
            rsSet = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rsSet;
    }

    public IEntityTable entityQuerySet(EntityViewDefine view, String formSchemeKey, String period) throws Exception {
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("DATATIME", (Object)period);
        IEntityQuery buildQuery = this.buildQuery(view, dim, formSchemeKey);
        ExecutorContext context = this.executorContext(formSchemeKey, dim);
        return this.entityQuerySet(view, buildQuery, context, formSchemeKey);
    }

    public IEntityTable entityQuerySet(String entityId, String period, String formSchemeKey) throws Exception {
        EntityViewDefine view = this.viewRtCtl.buildEntityView(entityId);
        IEntityTable rs = this.entityQuerySet(view, formSchemeKey, period);
        return rs;
    }

    public IEntityTable entityQueryByViewKey(String entityViewKey, String formSchemeKey, String period) throws Exception {
        if (!StringUtils.isEmpty((String)entityViewKey)) {
            EntityViewDefine view = this.viewRtCtl.buildEntityView(entityViewKey);
            return view != null ? this.entityQuerySet(view, formSchemeKey, period) : null;
        }
        return null;
    }

    public IEntityTable entityQuerySetByEntityView(EntityViewDefine view, String formSchemeKey, String period, AuthorityType authorityType) throws Exception {
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("DATATIME", (Object)period);
        IEntityQuery buildQuery = this.entityDataService.newEntityQuery();
        buildQuery.setMasterKeys(dim);
        buildQuery.setAuthorityOperations(authorityType);
        buildQuery.setEntityView(view);
        ExecutorContext context = this.executorContext(formSchemeKey, dim);
        return this.entityQuerySetByEntityView(view, buildQuery, context, formSchemeKey);
    }

    public EntityViewDefine getEntityViewDefine(String entityViewKey) {
        if (entityViewKey != null) {
            return this.viewRtCtl.buildEntityView(entityViewKey);
        }
        return null;
    }

    public IEntityQuery buildQuery(String entityId) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setMasterKeys(new DimensionValueSet());
        query.setAuthorityOperations(AuthorityType.Read);
        EntityViewDefine view = this.viewRtCtl.buildEntityView(entityId);
        query.setEntityView(view);
        return query;
    }

    private IEntityTable executeEntityReader(IEntityQuery entityQuery, EntityViewDefine entityView, ExecutorContext executorContext, FormSchemeDefine formScheme) {
        try {
            String formSchemeKey = null;
            if (formScheme != null) {
                formSchemeKey = formScheme.getKey();
            }
            return this.dataEntityFullService.executeEntityReader(entityQuery, executorContext, entityView, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private IEntityTable executeEntityFullBuild(IEntityQuery entityQuery, EntityViewDefine entityView, ExecutorContext executorContext, FormSchemeDefine formScheme) {
        try {
            String formSchemeKey = null;
            if (formScheme != null) {
                formSchemeKey = formScheme.getKey();
            }
            return this.dataEntityFullService.executeEntityFullBuild(entityQuery, executorContext, entityView, formSchemeKey).getEntityTable();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public IEntityTable entityQuerySetByTask(EntityViewDefine view, String taskKey, String period) throws Exception {
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("DATATIME", (Object)period);
        IEntityQuery buildQuery = this.buildQueryByTask(view, dim, taskKey);
        ExecutorContext context = this.executorContextByTask(taskKey, dim);
        return this.entityQuerySet(buildQuery, context);
    }

    private IEntityTable entityQuerySet(IEntityQuery entityQuery, ExecutorContext context) {
        IEntityTable rsSet = null;
        try {
            rsSet = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rsSet;
    }

    public IEntityQuery buildQueryByTask(EntityViewDefine view, DimensionValueSet valueSet, String taskKey) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setMasterKeys(valueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        query.setEntityView(view);
        return query;
    }

    private ExecutorContext executorContextByTask(String taskKey, DimensionValueSet dim) {
        ExecutorContext context = new ExecutorContext(this.tbRtCtl);
        context.setVarDimensionValueSet(dim);
        TaskDefine taskDefine = this.controller.queryTaskDefine(taskKey);
        context.setPeriodView(taskDefine.getDateTime());
        return context;
    }
}

