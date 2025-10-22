/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.dataengine.var.VariableManager
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.engine.var.ReferRelation
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityRefer
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils
 *  javax.annotation.Resource
 *  org.json.JSONObject
 */
package com.jiuqi.nr.lwtree.query;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.dataengine.var.VariableManager;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.engine.var.ReferRelation;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityRefer;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.lwtree.para.ITreeParamsInitializer;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.unit.treecommon.utils.JavaBeanUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IEntityTableQueryer {
    @Resource
    private IEntityDataService dataService;
    @Resource
    private IPeriodEntityAdapter periodAdapter;
    @Resource
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource
    private IEntityMetaService metaService;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Resource
    private IRunTimeViewController viewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IFormSchemeService formSchemeService;

    public ExecutorContext makeQueryContext(String periodEntityId) {
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        executorContext.setPeriodView(periodEntityId);
        return executorContext;
    }

    public ExecutorContext makeQueryContext() {
        return new ExecutorContext(this.tbRtCtl);
    }

    public String makeEntityDimName(String entityId) {
        String dimensionName = null;
        if (StringUtils.isNotEmpty((String)entityId)) {
            if (this.periodAdapter.isPeriodEntity(entityId)) {
                IPeriodEntity periodEntity = this.periodAdapter.getPeriodEntity(entityId);
                dimensionName = periodEntity.getDimensionName();
            } else {
                IEntityDefine entityDefine = this.metaService.queryEntity(entityId);
                dimensionName = entityDefine.getDimensionName();
            }
        }
        return dimensionName;
    }

    public IEntityQuery makeIEntityQuery(String entityId) {
        IEntityQuery query = this.dataService.newEntityQuery();
        query.setAuthorityOperations(AuthorityType.Read);
        IEntityDefine entityDefine = this.queryEntity(entityId);
        if (null != entityDefine) {
            EntityViewDefine entityView = this.viewAdapter.buildEntityView(entityDefine.getId());
            query.setEntityView(entityView);
        }
        return query;
    }

    public IEntityQuery makeIEntityQuery(String entityId, String rowFilterExpression) {
        IEntityQuery query = this.dataService.newEntityQuery();
        query.setAuthorityOperations(AuthorityType.Read);
        query.sorted(true);
        IEntityDefine entityDefine = this.queryEntity(entityId);
        if (null != entityDefine) {
            EntityViewDefine entityView = this.viewAdapter.buildEntityView(entityDefine.getId(), rowFilterExpression);
            query.setEntityView(entityView);
        }
        return query;
    }

    public IEntityQuery makeIEntityQuery(ITreeParamsInitializer loadInfo) {
        IEntityQuery query = this.dataService.newEntityQuery();
        query.setAuthorityOperations(AuthorityType.Read);
        query.sorted(true);
        FormSchemeDefine formScheme = loadInfo.getFormScheme();
        if (formScheme != null) {
            query.setEntityView(this.viewController.getViewByFormSchemeKey(formScheme.getKey()));
        } else {
            query.setEntityView(this.viewAdapter.buildEntityView(loadInfo.getEntityId()));
        }
        if (StringUtils.isNotEmpty((String)loadInfo.getExpression())) {
            query.setExpression(loadInfo.getExpression());
        }
        return query;
    }

    public IEntityTable makeIEntityTable(IEntityQuery query, String periodEntityId, JSONObject customVariable) {
        ExecutorContext context = this.makeQueryContext(periodEntityId);
        return this.makeIEntityTable(query, context);
    }

    public IEntityTable makeIEntityTable(IEntityQuery query, String periodEntityId) {
        ExecutorContext context = this.makeQueryContext(periodEntityId);
        return this.makeIEntityTable(query, context);
    }

    public IEntityTable makeIEntityTable(IEntityQuery query) {
        ExecutorContext context = this.makeQueryContext();
        return this.makeIEntityTable(query, context);
    }

    public IEntityTable makeIEntityTable(IEntityQuery query, ExecutorContext context) {
        try {
            return query.executeReader((IContext)context);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            return null;
        }
    }

    public IEntityTable makeFullTreeTable(IEntityQuery query, ExecutorContext context) {
        try {
            return query.executeFullBuild((IContext)context);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            return null;
        }
    }

    public IEntityTable makeIEntityTable(String entityId) {
        IEntityQuery query = this.makeIEntityQuery(entityId);
        return this.makeIEntityTable(query);
    }

    public IEntityTable makeIEntityTable(String entityId, String periodEntityId, DimensionValueSet masterKeys) {
        IEntityQuery query = this.makeIEntityQuery(entityId);
        query.setMasterKeys(masterKeys);
        return this.makeIEntityTable(query, periodEntityId);
    }

    public IEntityTable makeIEntityTable(ITreeParamsInitializer loadInfo) {
        String periodDimName = loadInfo.getPeriodDimName();
        String period = loadInfo.getPeriod();
        String periodEntityId = loadInfo.getPeriodEntityId();
        JSONObject customVariable = loadInfo.getCustomVariable();
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue(periodDimName, (Object)period);
        IEntityQuery query = this.makeIEntityQuery(loadInfo);
        query.setMasterKeys(masterKeys);
        ExecutorContext context = this.makeQueryContext(periodEntityId);
        context.setVarDimensionValueSet(masterKeys);
        String formSchemeKey = loadInfo.getFormSchemeKey();
        this.setCustomVariable(context, formSchemeKey, customVariable);
        return this.makeIEntityTable(query, context);
    }

    public IEntityTable makeIEntityTableAndFilterEntities(ITreeParamsInitializer loadInfo) {
        String periodDimName = loadInfo.getPeriodDimName();
        String period = loadInfo.getPeriod();
        String periodEntityId = loadInfo.getPeriodEntityId();
        JSONObject customVariable = loadInfo.getCustomVariable();
        DimensionValueSet masterKeys = new DimensionValueSet();
        masterKeys.setValue(periodDimName, (Object)period);
        IEntityQuery query = this.makeIEntityQuery(loadInfo);
        query.setMasterKeys(masterKeys);
        HashMap<String, IEntityRefer> iEntityReferMap = new HashMap<String, IEntityRefer>();
        List dwEntityReferList = this.entityMetaService.getEntityRefer(loadInfo.getCustomVariable().getString("UnitViewKey"));
        String dimAttributeByReportDim = this.formSchemeService.getDimAttributeByReportDim(loadInfo.getFormSchemeKey(), loadInfo.getViewKey());
        for (IEntityRefer iEntityReferInfo : dwEntityReferList) {
            if (!iEntityReferInfo.getOwnField().equals(dimAttributeByReportDim)) continue;
            iEntityReferMap.put(loadInfo.getViewKey(), iEntityReferInfo);
            break;
        }
        ArrayList<ReferRelation> referRelations = new ArrayList<ReferRelation>();
        if (StringUtils.isNotEmpty((String)loadInfo.getFormSchemeKey())) {
            FormSchemeDefine formSchemeDefine = null;
            try {
                formSchemeDefine = this.viewController.getFormScheme(loadInfo.getFormSchemeKey());
            }
            catch (Exception e) {
                LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            }
            if (formSchemeDefine != null && formSchemeDefine.getDims() != null) {
                String[] dimEntityIds;
                for (String dimEntityId : dimEntityIds = formSchemeDefine.getDims().split(";")) {
                    if (!dimEntityId.equals(loadInfo.getViewKey())) continue;
                    boolean entityRefer = this.metaService.estimateEntityRefer(loadInfo.getCustomVariable().getString("UnitViewKey"), loadInfo.getViewKey());
                    String dwID = loadInfo.getCustomVariable().getString("UnitViewId");
                    if (!entityRefer || !StringUtils.isNotEmpty((String)dwID)) continue;
                    EntityViewDefine dwEntityView = this.viewController.getViewByFormSchemeKey(formSchemeDefine.getKey());
                    ReferRelation referRelation = new ReferRelation();
                    referRelation.setViewDefine(dwEntityView);
                    ArrayList<String> range = new ArrayList<String>();
                    range.add(dwID);
                    referRelation.setRange(range);
                    if (!iEntityReferMap.containsKey(loadInfo.getViewKey())) continue;
                    referRelation.setRefer((IEntityRefer)iEntityReferMap.get(loadInfo.getViewKey()));
                    referRelations.add(referRelation);
                }
            }
            if (!referRelations.isEmpty()) {
                for (ReferRelation referRelation : referRelations) {
                    query.addReferRelation(referRelation);
                }
            }
        }
        ExecutorContext context = this.makeQueryContext(periodEntityId);
        context.setVarDimensionValueSet(masterKeys);
        String formSchemeKey = loadInfo.getFormSchemeKey();
        this.setCustomVariable(context, formSchemeKey, customVariable);
        return this.makeIEntityTable(query, context);
    }

    private void setCustomVariable(ExecutorContext context, String formSchemeKey, JSONObject customVariable) {
        if (StringUtils.isNotEmpty((String)formSchemeKey)) {
            VariableManager variableManager;
            IFmlExecEnvironment env = context.getEnv();
            if (env == null) {
                env = new ReportFmlExecEnvironment(this.viewController, this.tbRtCtl, this.viewAdapter, formSchemeKey);
                context.setEnv(env);
            }
            if (customVariable != null && (variableManager = env.getVariableManager()) != null) {
                Map customVariableMap = (Map)JavaBeanUtils.toJavaBean((String)customVariable.toString(), HashMap.class);
                for (Map.Entry entry : customVariableMap.entrySet()) {
                    variableManager.add(new Variable((String)entry.getKey(), (String)entry.getKey(), 6, entry.getValue()));
                }
            }
        }
    }

    public IEntityTable makeFullTreeTable(String entityId, String periodEntityId, DimensionValueSet masterKeys, String rowFilter) {
        IEntityQuery query = this.makeIEntityQuery(entityId, rowFilter);
        query.setMasterKeys(masterKeys);
        ExecutorContext context = this.makeQueryContext(periodEntityId);
        return this.makeFullTreeTable(query, context);
    }

    public IEntityTable makeIEntityTable(String entityId, String periodEntityId, DimensionValueSet masterKeys, String rowFilter, Date versionDate) {
        IEntityQuery query = this.makeIEntityQuery(entityId, rowFilter);
        query.setMasterKeys(masterKeys);
        query.setQueryVersionDate(versionDate);
        return this.makeIEntityTable(query, periodEntityId);
    }

    private IEntityDefine queryEntity(String entityId) {
        if (StringUtils.isNotEmpty((String)entityId)) {
            return this.metaService.queryEntity(entityId);
        }
        return null;
    }
}

