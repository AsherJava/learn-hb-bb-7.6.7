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
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.time.setting.util;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.util.StringUtils;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TimeEntityUpgraderImpl
implements IEntityUpgrader {
    private static final Logger logger = LoggerFactory.getLogger(TimeEntityUpgraderImpl.class);
    private static final String SPLIT = ";";
    @Resource
    private IEntityDataService dataService;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Resource
    private IRunTimeViewController controller;

    protected IEntityQuery buildIEntityQuery(String formSchemeKey) {
        IEntityQuery query = this.dataService.newEntityQuery();
        query.setAuthorityOperations(AuthorityType.Read);
        EntityViewDefine entityView = this.controller.getViewByFormSchemeKey(formSchemeKey);
        query.setEntityView(entityView);
        return query;
    }

    protected IEntityDefine queryEntity(String entityId) {
        if (StringUtils.isNotEmpty((String)entityId)) {
            return this.metaService.queryEntity(entityId);
        }
        return null;
    }

    protected ExecutorContext executorContext(String formSchemeKey, DimensionValueSet dimValueSet) {
        ExecutorContext context = new ExecutorContext(this.tbRtCtl);
        context.setVarDimensionValueSet(dimValueSet);
        context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.controller, this.tbRtCtl, this.viewAdapter, formSchemeKey));
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return context;
    }

    protected IEntityTable entityQuerySet(IEntityQuery entityQuery, ExecutorContext context) {
        IEntityTable rsSet = null;
        try {
            rsSet = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rsSet;
    }

    protected IEntityTable entityFullQuerySet(IEntityQuery entityQuery, ExecutorContext context) {
        IEntityTable rsSet = null;
        try {
            rsSet = entityQuery.executeFullBuild((IContext)context);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rsSet;
    }
}

