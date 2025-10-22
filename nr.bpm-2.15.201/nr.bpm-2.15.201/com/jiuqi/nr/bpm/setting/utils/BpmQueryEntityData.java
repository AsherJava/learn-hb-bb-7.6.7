/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.dataentity.service.DataEntityFullService
 *  com.jiuqi.nr.definition.common.IEntityUpgrader
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.service.IEntityDataService
 */
package com.jiuqi.nr.bpm.setting.utils;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.dataentity.service.DataEntityFullService;
import com.jiuqi.nr.definition.common.IEntityUpgrader;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.service.IEntityDataService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BpmQueryEntityData
implements IEntityUpgrader {
    private static final Logger logger = LoggerFactory.getLogger(BpmQueryEntityData.class);
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController = (IDataDefinitionRuntimeController)BeanUtil.getBean(IDataDefinitionRuntimeController.class);
    private IRunTimeViewController controller = (IRunTimeViewController)BeanUtil.getBean(IRunTimeViewController.class);
    private IEntityViewRunTimeController entityViewRunTimeController = (IEntityViewRunTimeController)BeanUtil.getBean(IEntityViewRunTimeController.class);
    private IEntityDataService entityDataService = (IEntityDataService)BeanUtil.getBean(IEntityDataService.class);
    private Map<String, IEntityTable> dataTabelMap;
    private DataEntityFullService dataEntityFullService = (DataEntityFullService)BeanUtil.getBean(DataEntityFullService.class);

    public BpmQueryEntityData() {
        this.dataTabelMap = new HashMap<String, IEntityTable>();
    }

    private IEntityTable getEntityDatatable(String formSchemeKey, String period) {
        String dataMapKey = formSchemeKey + "@" + period;
        if (!this.dataTabelMap.containsKey(dataMapKey)) {
            IEntityTable entityDataTable = this.entityQuerySet(formSchemeKey, period);
            this.dataTabelMap.put(dataMapKey, entityDataTable);
        }
        return this.dataTabelMap.get(dataMapKey);
    }

    public IEntityTable entityQuerySet(String formSchemeKey, String period) {
        DimensionValueSet dimSet = new DimensionValueSet();
        dimSet.setValue("DATATIME", (Object)period);
        EntityViewDefine entityViewDefine = this.getEntityViewDefine(formSchemeKey);
        IEntityQuery query = this.buildQuery(entityViewDefine, dimSet, formSchemeKey);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(dimSet);
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.controller, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return this.entityQuerySet(query, context, entityViewDefine, formScheme);
    }

    public IEntityTable entityQuerySet(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey) {
        IEntityQuery query = this.buildQuery(view, valueSet, formSchemeKey);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(valueSet);
        if (!StringUtils.isEmpty((String)formSchemeKey)) {
            context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.controller, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey));
        }
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return this.entityQuerySet(query, context, view, formScheme);
    }

    public IEntityTable entityQuerySet(EntityViewDefine view, String period, String formSchemeKey) {
        DimensionValueSet valueSet = new DimensionValueSet();
        if (period != null) {
            valueSet.setValue("DATATIME", (Object)period);
        }
        IEntityQuery query = this.buildQuery(view, valueSet, formSchemeKey);
        ExecutorContext context = this.executorContext(formSchemeKey, valueSet);
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return this.entityQuerySet(query, context, view, formScheme);
    }

    public IEntityRow getCurrentData(String formSchemeKey, String entityKey, String period) {
        IEntityRow entityRow = null;
        try {
            IEntityTable entityDatatable = this.getEntityDatatable(formSchemeKey, period);
            entityRow = entityDatatable.findByEntityKey(entityKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return entityRow;
    }

    public List<IEntityRow> getRootData(DimensionValueSet dim, String formSchemeKey) {
        List<Object> rootRows = new ArrayList<IEntityRow>();
        try {
            IEntityTable entityQuerySet = this.getEntityDatatable(formSchemeKey, dim.getValue("DATATIME").toString());
            rootRows = entityQuerySet.getRootRows();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return rootRows;
    }

    public List<IEntityRow> getDirectChildrenData(String formSchemeKey, String period, String entityKeyData) {
        List<Object> childrenRows = new ArrayList<IEntityRow>();
        try {
            IEntityTable entityQuerySet = this.getEntityDatatable(formSchemeKey, period);
            childrenRows = entityQuerySet.getChildRows(entityKeyData);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childrenRows;
    }

    public List<IEntityRow> getAllChildrenData(String formSchemeKey, String period, String entityKeyData) {
        List<Object> childrenRows = new ArrayList<IEntityRow>();
        try {
            IEntityTable entityQuerySet = this.getEntityDatatable(formSchemeKey, period);
            childrenRows = entityQuerySet.getAllChildRows(entityKeyData);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return childrenRows;
    }

    public String getParent(String formSchemeKey, String period, String entityKey) {
        String parentEntityKey = null;
        try {
            IEntityTable entityData = this.getEntityDatatable(formSchemeKey, period);
            parentEntityKey = entityData.findByEntityKey(entityKey).getParentEntityKey();
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return parentEntityKey;
    }

    public IEntityQuery buildQuery(EntityViewDefine view, DimensionValueSet valueSet, String formSchemeKey) {
        IEntityQuery query = this.entityDataService.newEntityQuery();
        query.setMasterKeys(valueSet);
        query.setAuthorityOperations(AuthorityType.Read);
        EntityViewDefine entityView = this.controller.getViewByFormSchemeKey(formSchemeKey);
        query.setEntityView(entityView);
        query.sorted(true);
        return query;
    }

    private IEntityTable entityQuerySet(IEntityQuery entityQuery, ExecutorContext context, EntityViewDefine entityView, FormSchemeDefine formScheme) {
        IEntityTable rsSet = null;
        try {
            rsSet = this.executeEntityReader(entityQuery, entityView, context, formScheme);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException("\u6570\u636e\u5f15\u64ce\u83b7\u53d6\u4e3b\u4f53\u96c6\u5408\u5931\u8d25\uff01", e.getCause());
        }
        return rsSet;
    }

    private ExecutorContext executorContext(String formSchemeKey, DimensionValueSet dim) {
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        context.setVarDimensionValueSet(dim);
        context.setEnv((IFmlExecEnvironment)new ReportFmlExecEnvironment(this.controller, this.dataDefinitionRuntimeController, this.entityViewRunTimeController, formSchemeKey));
        FormSchemeDefine formScheme = this.controller.getFormScheme(formSchemeKey);
        context.setPeriodView(formScheme.getDateTime());
        return context;
    }

    public List<IEntityRow> getEntityData(String formSchemeKey, String period) {
        List<Object> allRows = new ArrayList<IEntityRow>();
        try {
            IEntityTable entityQuerySet = this.getEntityDatatable(formSchemeKey, period);
            if (entityQuerySet != null) {
                allRows = entityQuerySet.getAllRows();
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return allRows;
    }

    public List<IEntityRow> getEntityData(String formSchemeKey, DimensionValueSet dim) {
        List<Object> allRows = new ArrayList<IEntityRow>();
        try {
            EntityViewDefine entityViewDefine = this.getEntityViewDefine(formSchemeKey);
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

    public List<String> getEntityIds(String formSchemeKey, String period) {
        ArrayList<String> ids = new ArrayList<String>();
        try {
            IEntityTable entityQuerySet = this.getEntityDatatable(formSchemeKey, period);
            if (entityQuerySet != null) {
                List allRows = entityQuerySet.getAllRows();
                for (IEntityRow iEntityRow : allRows) {
                    ids.add(iEntityRow.getEntityKeyData());
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return ids;
    }

    public EntityViewDefine getEntityViewDefine(String formSchemeKey) {
        try {
            return this.controller.getViewByFormSchemeKey(formSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
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
}

