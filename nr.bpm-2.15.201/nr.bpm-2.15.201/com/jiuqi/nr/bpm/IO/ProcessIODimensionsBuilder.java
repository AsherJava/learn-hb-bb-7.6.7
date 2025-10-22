/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.intf.IFmlExecEnvironment
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.cxt.DsContext
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionContext
 *  com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData
 *  com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.service.ITaskService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.IO;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IFmlExecEnvironment;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.internal.impl.RunTimeEntityViewDefineImpl;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.cxt.DsContext;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionContext;
import com.jiuqi.nr.dataservice.core.dimension.VariableDimensionValueProvider;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderData;
import com.jiuqi.nr.dataservice.core.dimension.provider.DimensionProviderFactory;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.env.ReportFmlExecEnvironment;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.service.ITaskService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.DimensionObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormGroupObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.FormObject;
import com.jiuqi.util.StringUtils;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProcessIODimensionsBuilder {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private ITaskService taskService;
    @Resource
    private IEntityMetaService entityMetaService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    private DimensionProviderFactory dimensionProviderFactory;
    @Autowired
    private IEntityDataService dataService;
    @Resource
    private com.jiuqi.nr.definition.controller.IRunTimeViewController viewController;
    @Autowired
    private IDataDefinitionRuntimeController tbRtCtl;
    @Resource
    private IEntityViewRunTimeController viewAdapter;
    @Autowired
    private IWorkflow workflow;

    public IBusinessObject buildBusinessObject(String taskKey, String period, DimensionValueSet dimensionValueSet, String formOrGroupKey) {
        DimensionCombination dimensionCombination = this.buildDimensionCombination(taskKey, period, dimensionValueSet);
        FormSchemeDefine formScheme = this.getFormScheme(taskKey, period);
        WorkFlowType workFlowType = this.workflow.queryStartType(formScheme.getKey());
        if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
            return new DimensionObject(dimensionCombination);
        }
        if (WorkFlowType.FORM.equals((Object)workFlowType)) {
            return new FormObject(dimensionCombination, formOrGroupKey);
        }
        if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
            return new FormGroupObject(dimensionCombination, formOrGroupKey);
        }
        return new DimensionObject(dimensionCombination);
    }

    public DimensionCombination buildDimensionCombination(String taskKey, String period, DimensionValueSet dimensionValueSet) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        DimensionCombinationImpl combination = new DimensionCombinationImpl();
        List reportDimensions = this.taskService.getDataDimension(taskKey);
        for (DataDimension dimension : reportDimensions) {
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                IEntityDefine dwEntityDefine = this.getProcessEntityDefinition(taskKey);
                Object value = dimensionValueSet.getValue(dimensionName);
                combination.setDWValue(dimensionName, dwEntityDefine.getId(), value);
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                combination.setValue(dimensionName, dimension.getDimKey(), (Object)period);
                continue;
            }
            if (this.isCorporate(taskDefine, dimension, dwEntityModel)) {
                String dwMainDimNameByTaskKey = this.getDwMainDimNameByTaskKey(taskKey);
                Object unitCode = dimensionValueSet.getValue(dwMainDimNameByTaskKey);
                DimensionValueSet dimensionNew = new DimensionValueSet();
                dimensionNew.setValue("DATATIME", (Object)period);
                dimensionNew.setValue(dwMainDimNameByTaskKey, unitCode);
                try {
                    IEntityTable entityTable = this.getIEntityTable(taskKey, period, dimensionValueSet);
                    IEntityRow targetRow = entityTable.findByEntityKey(unitCode.toString());
                    combination.setValue(dimensionName, dimension.getDimKey(), (Object)targetRow.getAsString(dimension.getDimAttribute()));
                    continue;
                }
                catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            Object value = dimensionValueSet.getValue(dimensionName);
            combination.setValue(dimensionName, dimension.getDimKey(), value);
        }
        return combination;
    }

    public DimensionCollection buildDimensionCollection(String taskKey, Map<String, DimensionValue> dimensionSet) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        IEntityModel dwEntityModel = this.entityMetaService.getEntityModel(taskDefine.getDw());
        List dataSchemeDimension = this.taskService.getDataDimension(taskKey);
        DimensionCollectionBuilder dimensionBuilder = new DimensionCollectionBuilder();
        DimensionContext dimensionContext = new DimensionContext(taskKey);
        dimensionBuilder.setContext(dimensionContext);
        for (DataDimension dimension : dataSchemeDimension) {
            DimensionValue dimensionValue;
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if ("ADJUST".equals(dimensionName)) {
                dimensionValue = dimensionSet.get(dimensionName);
                if (dimensionValue != null) {
                    dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{dimensionValue.getValue()});
                    continue;
                }
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{0});
                continue;
            }
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dimensionValue = dimensionSet.get(dimensionName);
                String value = dimensionValue.getValue();
                String dimId = this.getContextMainDimId(taskDefine.getDw());
                String dimName = this.getDimensionName(dimension.getDimKey());
                DimensionProviderData providerData = new DimensionProviderData();
                if (StringUtils.isEmpty((String)value)) {
                    providerData.setDataSchemeKey(taskDefine.getDataScheme());
                    providerData.setAuthorityType(AuthorityType.Read);
                } else {
                    String[] split = value.split(";");
                    List entityKeys = Arrays.stream(split).filter(element -> element != null && !element.trim().isEmpty()).collect(Collectors.toList());
                    providerData.setChoosedValues(entityKeys);
                }
                VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDWBYVERSION", providerData);
                dimensionBuilder.addVariableDW(dimName, dimId, dimensionProvider);
                continue;
            }
            if (DimensionType.PERIOD == dimension.getDimensionType()) {
                dimensionValue = dimensionSet.get(dimensionName);
                String periods = dimensionValue.getValue();
                if (periods.contains(";")) {
                    Object[] periodArr = periods.split(";");
                    dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), periodArr);
                    continue;
                }
                dimensionBuilder.setEntityValue(dimensionName, dimension.getDimKey(), new Object[]{periods});
                continue;
            }
            if (!this.isCorporate(taskDefine, dimension, dwEntityModel)) continue;
            DimensionProviderData providerData = new DimensionProviderData();
            providerData.setDataSchemeKey(taskDefine.getDataScheme());
            providerData.setAuthorityType(AuthorityType.Read);
            VariableDimensionValueProvider dimensionProvider = this.dimensionProviderFactory.getDimensionProvider("PROVIDER_FILTERDIMBYDW", providerData);
            dimensionBuilder.addVariableDimension(dimensionName, dimension.getDimKey(), dimensionProvider);
        }
        return dimensionBuilder.getCollection();
    }

    private boolean isCorporate(TaskDefine taskDefine, DataDimension dimension, IEntityModel dwEntityModel) {
        String dimAttribute = dimension.getDimAttribute();
        List reportDimension = this.taskService.getReportDimension(taskDefine.getKey());
        IEntityAttribute attribute = dwEntityModel.getAttribute(dimAttribute);
        DataDimension report = reportDimension.stream().filter(dataDimension -> dimension.getDimKey().equals(dataDimension.getDimKey())).findFirst().orElse(null);
        String dimReferAttr = report == null ? null : report.getDimAttribute();
        return DimensionType.DIMENSION == dimension.getDimensionType() && attribute != null && !attribute.isMultival() && StringUtils.isNotEmpty((String)dimReferAttr);
    }

    private String getDwMainDimNameByTaskKey(String taskKey) {
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        String contextMainDimId = this.getContextMainDimId(task.getDw());
        return this.getDimensionName(contextMainDimId);
    }

    private String getDimensionName(String entityKey) {
        String dimensionName = null;
        ContextExtension extension = NpContextHolder.getContext().getExtension("WORKFLOW_IO_DIMENISONNAME");
        Object object = extension.get(entityKey);
        if (object != null && !"".equals(object)) {
            dimensionName = object.toString();
            return dimensionName;
        }
        dimensionName = this.periodEntityAdapter.isPeriodEntity(entityKey) ? this.periodEntityAdapter.getPeriodDimensionName() : ("ADJUST".equals(entityKey) ? "ADJUST" : this.entityMetaService.getDimensionName(entityKey));
        extension.put(entityKey, (Serializable)((Object)dimensionName));
        return dimensionName;
    }

    private String getContextMainDimId(String dw) {
        DsContext dsContext = DsContextHolder.getDsContext();
        String entityId = dsContext.getContextEntityId();
        return StringUtils.isEmpty((String)entityId) ? dw : entityId;
    }

    private IEntityDefine getProcessEntityDefinition(String taskKey) {
        IEntityDefine entityDefine;
        DsContext dsContext = DsContextHolder.getDsContext();
        if (StringUtils.isNotEmpty((String)dsContext.getContextEntityId()) && (entityDefine = this.entityMetaService.queryEntity(dsContext.getContextEntityId())) != null) {
            return entityDefine;
        }
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        return this.entityMetaService.queryEntity(taskDefine.getDw());
    }

    private IEntityTable getIEntityTable(String taskKey, String period, DimensionValueSet dimensionValueSet) throws Exception {
        IEntityQuery entityQuery = this.makeIEntityQuery(taskKey, period);
        entityQuery.setMasterKeys(dimensionValueSet);
        ExecutorContext executorContext = this.makeExecuteContext(taskKey, period);
        executorContext.setVarDimensionValueSet(entityQuery.getMasterKeys());
        return this.getIEntityTable(entityQuery, executorContext);
    }

    private IEntityTable getIEntityTable(IEntityQuery entityQuery, ExecutorContext executorContext) throws Exception {
        return entityQuery.executeReader((IContext)executorContext);
    }

    private IEntityQuery makeIEntityQuery(String taskKey, String period) {
        DimensionValueSet masterKeys = this.getMasterKeys(taskKey, period);
        IEntityQuery query = this.dataService.newEntityQuery();
        query.sorted(true);
        query.setMasterKeys(masterKeys);
        query.setAuthorityOperations(AuthorityType.Read);
        query.markLeaf();
        query.lazyQuery();
        IEntityDefine entityDefine = this.getProcessEntityDefinition(taskKey);
        query.setEntityView(this.buildEntityView(taskKey, period, entityDefine.getId()));
        return query;
    }

    private ExecutorContext makeExecuteContext(String taskKey, String period) {
        ExecutorContext executorContext = new ExecutorContext(this.tbRtCtl);
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(task.getDateTime());
        executorContext.setPeriodView(periodEntity.getKey());
        FormSchemeDefine formScheme = this.getFormScheme(taskKey, period);
        IFmlExecEnvironment env = executorContext.getEnv();
        if (formScheme != null && null == env) {
            env = new ReportFmlExecEnvironment(this.viewController, this.tbRtCtl, this.viewAdapter, formScheme.getKey());
            executorContext.setEnv(env);
        }
        return executorContext;
    }

    private DimensionValueSet getMasterKeys(String taskKey, String period) {
        DimensionValueSet masterKeys = new DimensionValueSet();
        TaskDefine task = this.runTimeViewController.getTask(taskKey);
        IPeriodEntity periodEntity = this.periodEntityAdapter.getPeriodEntity(task.getDateTime());
        masterKeys.setValue(periodEntity.getDimensionName(), (Object)period);
        return masterKeys;
    }

    private FormSchemeDefine getFormScheme(String taskKey, String period) {
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskKey);
        if (schemePeriodLinkDefine != null) {
            return this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
        }
        return null;
    }

    private EntityViewDefine buildEntityView(String taskKey, String period, String entityDefinitionId) {
        FormSchemeDefine formScheme = this.getFormScheme(taskKey, period);
        if (null != formScheme) {
            String filterExpression = formScheme.getFilterExpression();
            RunTimeEntityViewDefineImpl entityViewDefine = new RunTimeEntityViewDefineImpl();
            entityViewDefine.setEntityId(entityDefinitionId);
            entityViewDefine.setRowFilterExpression(StringUtils.isNotEmpty((String)filterExpression) ? filterExpression : null);
            entityViewDefine.setFilterRowByAuthority(true);
            return entityViewDefine;
        }
        return null;
    }
}

