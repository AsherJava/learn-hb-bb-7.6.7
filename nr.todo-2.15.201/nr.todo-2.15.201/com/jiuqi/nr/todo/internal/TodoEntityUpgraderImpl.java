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
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.todo.internal;

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
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoEntityUpgraderImpl
implements IEntityUpgrader {
    private static final Logger logger = LoggerFactory.getLogger(TodoEntityUpgraderImpl.class);
    @Autowired
    IRuntimeTaskService taskService;
    @Autowired
    IRunTimeViewController runTimeViewController;
    @Autowired
    IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    IEntityMetaService entityMetaService;

    public IEntityTable entityQuerySet(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey) {
        IEntityQuery query = this.buildQuery(view, valueSet);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(valueSet);
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.runTimeViewController, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return this.entityQuerySet(query, context);
    }

    private IEntityTable entityQuerySet(IEntityQuery entityQuery, ExecutorContext context) {
        IEntityTable rsSet = null;
        try {
            rsSet = entityQuery.executeReader((IContext)context);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("\u6570\u636e\u5f15\u64ce\u83b7\u53d6\u4e3b\u4f53\u96c6\u5408\u5931\u8d25\uff01", e.getCause());
        }
        return rsSet;
    }

    public IEntityQuery buildQuery(EntityViewDefine view, DimensionValueSet valueSet) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setEntityView(view);
        query.setMasterKeys(valueSet);
        return query;
    }

    public List<IEntityRow> getEntityAllRows(String formSchemeKey, DimensionValueSet dim, EntityViewDefine entityViewDefine) {
        try {
            return this.getEntityData(formSchemeKey, dim, entityViewDefine);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public List<IEntityRow> getEntityData(String formSchemeKey, DimensionValueSet dim, EntityViewDefine entityViewDefine) {
        List<Object> allRows = new ArrayList<IEntityRow>();
        try {
            IEntityTable entityQuerySet = this.entityQuerySet(entityViewDefine, dim, formSchemeKey);
            if (entityQuerySet != null) {
                allRows = entityQuerySet.getAllRows();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return allRows;
    }

    public EntityViewDefine getEntityViewDefine(String entityViewKey) {
        if (entityViewKey != null) {
            return this.entityViewRunTimeController.buildEntityView(entityViewKey);
        }
        return null;
    }

    public EntityViewDefine getEntityViewDefineByFormSchemeKey(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String masterEntitiesKey = formScheme.getMasterEntitiesKey();
            String[] entitiesKeyArr = masterEntitiesKey.split(";");
            EntityViewDefine entityView = null;
            for (String entitiy : entitiesKeyArr) {
                entityView = this.getEntityViewDefine(entitiy);
                if (this.periodEntityAdapter.isPeriodEntity(entitiy)) continue;
                return entityView;
            }
            return entityView;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public EntityViewDefine getEntityViewDefineByEntitiesKey(String masterEntitiesKey) {
        try {
            String[] entitiesKeyArr = masterEntitiesKey.split(";");
            EntityViewDefine entityView = null;
            for (String entitiy : entitiesKeyArr) {
                entityView = this.getEntityViewDefine(entitiy);
                if (this.periodEntityAdapter.isPeriodEntity(entitiy)) continue;
                return entityView;
            }
            return entityView;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getFristEntity(String formSchemeKey) {
        try {
            FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
            String dw = formScheme.getDw();
            return dw;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}

