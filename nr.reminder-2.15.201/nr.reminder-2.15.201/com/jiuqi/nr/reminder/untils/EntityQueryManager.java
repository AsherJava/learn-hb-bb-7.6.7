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
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.reminder.untils;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={NpRollbackException.class})
@Service(value="reminder.entityManager")
public class EntityQueryManager {
    private static final Logger log = LoggerFactory.getLogger(EntityQueryManager.class);
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

    public IEntityTable entityQuerySet(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey) {
        IEntityQuery query = this.buildQuery(view, valueSet, formSchemeKey);
        ExecutorContext context = this.executorContext(formSchemeKey);
        return this.entityQuerySet(query, context);
    }

    public IEntityTable entityQuerySet(String entityViewKey, DimensionValueSet valueSet, String formSchemeKey) {
        if (!StringUtils.isEmpty((String)entityViewKey)) {
            EntityViewDefine view = this.controller.getViewByFormSchemeKey(formSchemeKey);
            IEntityQuery query = this.buildQuery(view, valueSet, formSchemeKey);
            ExecutorContext context = this.executorContext(formSchemeKey);
            return this.entityQuerySet(query, context);
        }
        return null;
    }

    public IEntityQuery buildQuery(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(view);
        query.setMasterKeys(valueSet);
        return query;
    }

    private IEntityTable entityQuerySet(IEntityQuery entityQuery, ExecutorContext context) {
        IEntityTable rsSet = null;
        try {
            rsSet = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return rsSet;
    }

    public IEntityTable entityQuerySet(EntityViewDefine view, String formSchemeKey) throws Exception {
        IEntityQuery iEntityQuery = this.entityDataService.newEntityQuery();
        iEntityQuery.setEntityView(view);
        iEntityQuery.setIgnoreViewFilter(true);
        return iEntityQuery.executeReader((IContext)this.executorContext(formSchemeKey));
    }

    private ExecutorContext executorContext(String formSchemeKey) {
        ExecutorContext context = new ExecutorContext(this.tbRtCtl);
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.controller, this.tbRtCtl, this.viewRtCtl, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return context;
    }
}

