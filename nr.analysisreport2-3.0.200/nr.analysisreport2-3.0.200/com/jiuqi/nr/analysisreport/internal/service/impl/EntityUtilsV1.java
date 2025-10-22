/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.common.exception.JQException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.entity.bo.EntitySearchBO
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.analysisreport.internal.service.impl;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.common.exception.JQException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.analysisreport.facade.DimensionObj;
import com.jiuqi.nr.analysisreport.internal.service.IEntityUtils;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.entity.bo.EntitySearchBO;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Primary
@Service
public class EntityUtilsV1
implements IEntityUtils {
    private static final Logger log = LoggerFactory.getLogger(EntityUtilsV1.class);
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired
    private IEntityViewRunTimeController iEntityViewRunTimeController;
    @Autowired
    private IDataDefinitionRuntimeController runtimeController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    private static final int DIMENSION_FLAG_BASEDATA = 0;
    private static final int DIMENSION_FLAG_ORGANIZATION = 1;

    @Override
    public IEntityTable buildQueryer(EntityViewDefine entityView, DimensionValueSet masterKeys, boolean showLinkEntity) {
        try {
            if (entityView != null) {
                ExecutorContext context = this.createExecutorContext();
                IEntityQuery query = this.buildQuery(entityView, masterKeys, !showLinkEntity);
                return query.executeReader((IContext)context);
            }
        }
        catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
        return null;
    }

    @Override
    public IEntityTable queryEntityTable(EntityViewDefine entityView, DimensionValueSet masterKeys) throws Exception {
        ExecutorContext context = this.createExecutorContext();
        IEntityQuery query = this.buildQuery(entityView, masterKeys, true);
        IEntityTable result = query.executeReader((IContext)context);
        return result;
    }

    private ExecutorContext createExecutorContext() {
        return this.createExecutorContext(null);
    }

    private ExecutorContext createExecutorContext(String formSchemeKey) {
        ExecutorContext newExecutorContext = new ExecutorContext(this.runtimeController);
        ReportFmlExecEnvironment env = new ReportFmlExecEnvironment(this.runTimeViewController, this.iDataDefinitionRuntimeController, this.iEntityViewRunTimeController, formSchemeKey);
        newExecutorContext.setEnv((IFmlExecEnvironment)env);
        return newExecutorContext;
    }

    private IEntityQuery buildQuery(EntityViewDefine entityView, DimensionValueSet masterKeys, boolean ignoreParentView) {
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        if (null != masterKeys) {
            entityQuery.setMasterKeys(masterKeys);
        }
        entityQuery.setEntityView(entityView);
        entityQuery.setAuthorityOperations(AuthorityType.Read);
        return entityQuery;
    }

    @Override
    public IEntityTable buildIEntityTable(String tableKey, FieldDefine fieldDefine) throws Exception {
        EntityViewDefine entityView = this.entityViewRunTimeController.buildEntityView(tableKey);
        return this.buildIEntityTable(entityView, fieldDefine, false);
    }

    @Override
    public IEntityTable buildIEntityTable(EntityViewDefine entityView, FieldDefine fieldDefine, Boolean ignoreViewFilter) throws Exception {
        ExecutorContext context = this.createExecutorContext();
        IEntityQuery query = this.buildQuery(entityView, null, false);
        query.setIgnoreViewFilter(ignoreViewFilter.booleanValue());
        return query.executeReader((IContext)context);
    }

    @Override
    public EntityViewDefine getDefaultEntityViewByTable(String tableKey) throws JQException {
        return this.entityViewRunTimeController.buildEntityView(tableKey);
    }

    @Override
    public String getTableKey(String viewKey) throws JQException {
        IEntityDefine entityDefine = this.entityMetaService.queryEntity(viewKey);
        return entityDefine == null ? null : entityDefine.getId();
    }

    @Override
    public List<IEntityDefine> getAllEntityDefinesByDimensionFlag(int dimensionFlag) {
        List entities = this.entityMetaService.getEntities(1);
        if (CollectionUtils.isEmpty(entities)) {
            return entities;
        }
        String category = dimensionFlag == 1 ? "ORG" : "BASE";
        entities.removeIf(e -> !category.equals(e.getCategory()));
        return entities;
    }

    @Override
    public List<IEntityDefine> fuzzySearchEntity(String search, int dimensionFlag) {
        EntitySearchBO searchBO = new EntitySearchBO();
        searchBO.setKeyWords(search);
        searchBO.setDimensionFlag(dimensionFlag);
        return this.entityMetaService.fuzzySearchEntity(searchBO);
    }

    @Override
    public List<IEntityDefine> getAllEntityDefines() {
        ArrayList<IEntityDefine> res = new ArrayList<IEntityDefine>();
        List organizations = this.entityMetaService.getEntities(1);
        List basedatas = this.entityMetaService.getEntities(0);
        res.addAll(organizations);
        res.addAll(basedatas);
        return res;
    }

    @Override
    public List<IPeriodEntity> getAllPeriodEntitys() {
        List res = this.periodEntityAdapter.getPeriodEntity();
        return res;
    }

    @Override
    public List<IEntityDefine> getEntityDefinesInRanges(List<String> keys) {
        ArrayList<IEntityDefine> res = new ArrayList<IEntityDefine>();
        for (String key : keys) {
            res.add(this.entityMetaService.queryEntity(key));
        }
        return res;
    }

    @Override
    public IEntityDefine getEntityDefine(String key) {
        IEntityDefine res = this.entityMetaService.queryEntity(key);
        return res;
    }

    @Override
    public List<IPeriodEntity> getPeriodEntitysInRanges(List<String> keys) {
        ArrayList<IPeriodEntity> res = new ArrayList<IPeriodEntity>();
        for (String key : keys) {
            res.add(this.periodEntityAdapter.getPeriodEntity(key));
        }
        return res;
    }

    @Override
    public IPeriodEntity getPeriodEntity(String key) {
        IPeriodEntity res = this.periodEntityAdapter.getPeriodEntity(key);
        return res;
    }

    @Override
    public EntityViewDefine getDefaultEntityViewByEntityId(String entityId) throws JQException {
        return this.entityViewRunTimeController.buildEntityView(entityId);
    }

    @Override
    public EntityViewDefine getPeriodViewByEntityKey(String entityId) {
        return this.entityViewRunTimeController.buildEntityView(entityId);
    }

    @Override
    public IPeriodProvider getPeriodProvider(String entityId) {
        return this.periodEntityAdapter.getPeriodProvider(entityId);
    }

    @Override
    public void setPeriodEntityType(DimensionObj dimension) {
        switch (dimension.getKey()) {
            case "N": {
                dimension.getConfig().setPeriodType(PeriodType.YEAR.type());
                break;
            }
            case "H": {
                dimension.getConfig().setPeriodType(PeriodType.HALFYEAR.type());
                break;
            }
            case "J": {
                dimension.getConfig().setPeriodType(PeriodType.SEASON.type());
                break;
            }
            case "Y": {
                dimension.getConfig().setPeriodType(PeriodType.MONTH.type());
                break;
            }
            case "X": {
                dimension.getConfig().setPeriodType(PeriodType.TENDAY.type());
                break;
            }
            case "R": {
                dimension.getConfig().setPeriodType(PeriodType.DAY.type());
                break;
            }
            case "Z": {
                dimension.getConfig().setPeriodType(PeriodType.WEEK.type());
                break;
            }
            case " ": {
                dimension.getConfig().setPeriodType(PeriodType.DEFAULT.type());
                break;
            }
            default: {
                dimension.getConfig().setPeriodType(PeriodType.CUSTOM.type());
            }
        }
    }
}

