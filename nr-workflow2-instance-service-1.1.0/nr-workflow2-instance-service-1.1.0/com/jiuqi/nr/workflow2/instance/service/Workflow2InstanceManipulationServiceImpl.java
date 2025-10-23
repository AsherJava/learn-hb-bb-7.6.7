/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.instance.async.OperateWorkflowTaskExecutor
 *  com.jiuqi.nr.bpm.instance.async.RefreshParticipantTaskExecutor
 *  com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam
 *  com.jiuqi.nr.bpm.instance.bean.StartStateParam
 *  com.jiuqi.nr.bpm.instance.bean.StartStateParam$Type
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.exception.FormSchemeNotFoundException
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.jobs.ClearInstancesTaskExecutor
 *  com.jiuqi.nr.workflow2.service.jobs.RefreshActorsTaskExecutor
 *  com.jiuqi.nr.workflow2.service.jobs.StartInstancesTaskExecutor
 *  com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType
 *  com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessRangeDims
 */
package com.jiuqi.nr.workflow2.instance.service;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.instance.async.OperateWorkflowTaskExecutor;
import com.jiuqi.nr.bpm.instance.async.RefreshParticipantTaskExecutor;
import com.jiuqi.nr.bpm.instance.bean.QueryGridDataParam;
import com.jiuqi.nr.bpm.instance.bean.StartStateParam;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.engine.core.exception.FormSchemeNotFoundException;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.JavaBeanUtils;
import com.jiuqi.nr.workflow2.instance.context.InstanceBaseContext;
import com.jiuqi.nr.workflow2.instance.context.InstanceOperateContext;
import com.jiuqi.nr.workflow2.instance.entity.EntityInfo;
import com.jiuqi.nr.workflow2.instance.enumeration.InstanceOperateType;
import com.jiuqi.nr.workflow2.instance.service.Workflow2InstanceManipulationService;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.jobs.ClearInstancesTaskExecutor;
import com.jiuqi.nr.workflow2.service.jobs.RefreshActorsTaskExecutor;
import com.jiuqi.nr.workflow2.service.jobs.StartInstancesTaskExecutor;
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.ProcessBatchRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.LoggerFactory;

public class Workflow2InstanceManipulationServiceImpl
implements Workflow2InstanceManipulationService {
    private IRunTimeViewController runTimeViewController;
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    private IEntityDataService entityDataService;
    private IEntityViewRunTimeController entityViewRunTimeController;
    private IEntityMetaService entityMetaService;
    private WorkflowSettingsService workflowSettingsService;
    private AsyncTaskManager asyncTaskManager;
    private IProcessDimensionsBuilder processDimensionsBuilder;
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    private String todoVersion;

    public void setRunTimeViewController(IRunTimeViewController runTimeViewController) {
        this.runTimeViewController = runTimeViewController;
    }

    public void setDataDefinitionRuntimeController(IDataDefinitionRuntimeController dataDefinitionRuntimeController) {
        this.dataDefinitionRuntimeController = dataDefinitionRuntimeController;
    }

    public void setEntityDataService(IEntityDataService entityDataService) {
        this.entityDataService = entityDataService;
    }

    public void setEntityViewRunTimeController(IEntityViewRunTimeController entityViewRunTimeController) {
        this.entityViewRunTimeController = entityViewRunTimeController;
    }

    public void setEntityMetaService(IEntityMetaService entityMetaService) {
        this.entityMetaService = entityMetaService;
    }

    public void setWorkflowSettingsService(WorkflowSettingsService workflowSettingsService) {
        this.workflowSettingsService = workflowSettingsService;
    }

    public void setAsyncTaskManager(AsyncTaskManager asyncTaskManager) {
        this.asyncTaskManager = asyncTaskManager;
    }

    public void setProcessDimensionsBuilder(IProcessDimensionsBuilder processDimensionsBuilder) {
        this.processDimensionsBuilder = processDimensionsBuilder;
    }

    public void setTodoVersion(String todoVersion) {
        this.todoVersion = todoVersion;
    }

    public void setDefaultEngineVersionUtil(DefaultEngineVersionJudge defaultEngineVersionJudge) {
        this.defaultEngineVersionJudge = defaultEngineVersionJudge;
    }

    @Override
    public AsyncTaskInfo operateWorkflowInstance(InstanceOperateContext context) {
        InstanceOperateType operateType = context.getOperateType();
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskKey()) || operateType.equals((Object)InstanceOperateType.STOP)) {
            return this.operateWorkflowInstance_1_0(context);
        }
        ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
        batchRunPara.setTaskKey(context.getTaskKey());
        batchRunPara.setPeriod(context.getPeriod());
        batchRunPara.setReportDimensions(this.buildOperateProcessRangeDims(context));
        if (operateType.equals((Object)InstanceOperateType.START)) {
            return this.startInstancesExecute(batchRunPara);
        }
        if (operateType.equals((Object)InstanceOperateType.CLEAR)) {
            return this.clearInstancesExecute(batchRunPara);
        }
        return new AsyncTaskInfo();
    }

    @Override
    public AsyncTaskInfo refreshParticipant(InstanceBaseContext context) {
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskKey()) && this.todoVersion.equals("1.0")) {
            return this.refreshParticipantExecute(context);
        }
        ProcessBatchRunPara batchRunPara = new ProcessBatchRunPara();
        batchRunPara.setTaskKey(context.getTaskKey());
        batchRunPara.setPeriod(context.getPeriod());
        batchRunPara.setReportDimensions(this.buildRefreshProcessRangeDims(context));
        return this.refreshParticipantExecute(batchRunPara);
    }

    private Set<ProcessRangeDims> buildOperateProcessRangeDims(InstanceOperateContext context) {
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskKey());
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        String entityId = entityCaliber != null && !entityCaliber.isEmpty() ? entityCaliber : taskDefine.getDw();
        String unitDimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(context.getTaskKey());
        if (workflowObjectType == null) {
            LoggerFactory.getLogger(this.getClass()).error("workflowObjectType is null");
            return new LinkedHashSet<ProcessRangeDims>();
        }
        String formSchemeKey = this.getFormSchemeKey(context.getTaskKey(), context.getPeriod());
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        EntityInfo unitInfo = context.getUnitInfo();
        boolean isOperateAllUnit = unitInfo.isCheckAll();
        List<String> rangeUnitKeys = unitInfo.getValueList();
        if (isOperateAllUnit) {
            IEntityTable entityTable = this.getEntityTable(entityId, context.getPeriod(), taskDefine.getDateTime());
            rangeUnitKeys = entityTable.getAllRows().stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        }
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(unitDimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        entityRangeDims.setRangeDims(rangeUnitKeys);
        rangeDims.add(entityRangeDims);
        String adjust = context.getAdjust();
        if (adjust != null && !adjust.isEmpty()) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(context.getAdjust());
            rangeDims.add(adjustOneDims);
        }
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            EntityInfo formInfo = context.getFormOrFormGroupInfo();
            boolean isOperateAllForm = formInfo.isCheckAll();
            List<String> rangeFormKeys = formInfo.getValueList();
            if (isOperateAllForm) {
                rangeFormKeys = this.runTimeViewController.listFormByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            }
            ProcessRangeDims formRangeDims = new ProcessRangeDims();
            formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
            formRangeDims.setRangeDims(rangeFormKeys);
            rangeDims.add(formRangeDims);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            EntityInfo formGroupInfo = context.getFormOrFormGroupInfo();
            boolean isOperateAllFormGroup = formGroupInfo.isCheckAll();
            List<String> rangeFormGroupKeys = formGroupInfo.getValueList();
            if (isOperateAllFormGroup) {
                rangeFormGroupKeys = this.runTimeViewController.listFormGroupByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            }
            ProcessRangeDims formGroupRangeDims = new ProcessRangeDims();
            formGroupRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setRangeType(EProcessRangeDimType.RANGE);
            formGroupRangeDims.setRangeDims(rangeFormGroupKeys);
            rangeDims.add(formGroupRangeDims);
        }
        return rangeDims;
    }

    private Set<ProcessRangeDims> buildRefreshProcessRangeDims(InstanceBaseContext context) {
        String formSchemeKey;
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(context.getTaskKey());
        TaskDefine taskDefine = this.runTimeViewController.getTask(context.getTaskKey());
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        String entityId = entityCaliber != null && !entityCaliber.isEmpty() ? entityCaliber : taskDefine.getDw();
        String unitDimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        IEntityTable entityTable = this.getEntityTable(entityId, context.getPeriod(), taskDefine.getDateTime());
        List rangeUnitKeys = entityTable.getAllRows().stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList());
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(unitDimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        entityRangeDims.setRangeDims(rangeUnitKeys);
        rangeDims.add(entityRangeDims);
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            formSchemeKey = this.getFormSchemeKey(context.getTaskKey(), context.getPeriod());
            List rangeFormKeys = this.runTimeViewController.listFormByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            ProcessRangeDims formRangeDims = new ProcessRangeDims();
            formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setRangeType(EProcessRangeDimType.RANGE);
            formRangeDims.setRangeDims(rangeFormKeys);
            rangeDims.add(formRangeDims);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            formSchemeKey = this.getFormSchemeKey(context.getTaskKey(), context.getPeriod());
            List rangeFormGroupKeys = this.runTimeViewController.listFormGroupByFormScheme(formSchemeKey).stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            ProcessRangeDims formGroupRangeDims = new ProcessRangeDims();
            formGroupRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setRangeType(EProcessRangeDimType.RANGE);
            formGroupRangeDims.setRangeDims(rangeFormGroupKeys);
            rangeDims.add(formGroupRangeDims);
        }
        String adjust = context.getAdjust();
        if (adjust != null && !adjust.isEmpty()) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ONE);
            adjustOneDims.setDimensionValue(context.getAdjust());
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    private String getFormSchemeKey(String taskId, String period) {
        SchemePeriodLinkDefine schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskId);
        if (schemePeriodLinkDefine == null) {
            LoggerFactory.getLogger(this.getClass()).error("formSchemeKey is null");
            throw new FormSchemeNotFoundException(taskId, period);
        }
        return schemePeriodLinkDefine.getSchemeKey();
    }

    private IEntityTable getEntityTable(String entityId, String period, String periodView) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setPeriodView(periodView);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        entityQuery.setMasterKeys(dimensionValueSet);
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private AsyncTaskInfo startInstancesExecute(ProcessBatchRunPara batchRunPara) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(JavaBeanUtils.toJSONStr((Object)batchRunPara));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new StartInstancesTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asyncTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    private AsyncTaskInfo clearInstancesExecute(ProcessBatchRunPara batchRunPara) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(JavaBeanUtils.toJSONStr((Object)batchRunPara));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new ClearInstancesTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asyncTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    private AsyncTaskInfo refreshParticipantExecute(ProcessBatchRunPara batchRunPara) {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(JavaBeanUtils.toJSONStr((Object)batchRunPara));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new RefreshActorsTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asyncTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asyncTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    private AsyncTaskInfo operateWorkflowInstance_1_0(InstanceOperateContext context) {
        StartStateParam startStateParam = new StartStateParam();
        startStateParam.setTaskKey(context.getTaskKey());
        startStateParam.setPeriod(context.getPeriod());
        startStateParam.setAdjust(context.getAdjust());
        List<String> formOrFormGroupKeys = context.getFormOrFormGroupInfo().getValueList();
        startStateParam.setFormOrGroupKeys(formOrFormGroupKeys != null ? new HashSet<String>(formOrFormGroupKeys) : new HashSet());
        QueryGridDataParam queryGridDataParam = new QueryGridDataParam();
        queryGridDataParam.setTaskKey(context.getTaskKey());
        queryGridDataParam.setPeriod(context.getPeriod());
        queryGridDataParam.setAdjust(context.getAdjust());
        EntityInfo unitInfo = context.getUnitInfo();
        if (unitInfo.getValueList() != null && !unitInfo.getValueList().isEmpty()) {
            queryGridDataParam.setCurrentUnitKey(unitInfo.getValueList().get(0));
        }
        queryGridDataParam.setAllChecked(unitInfo.isCheckAll());
        queryGridDataParam.setSelectKeys(unitInfo.getValueList());
        startStateParam.setQueryGridDataParam(queryGridDataParam);
        InstanceOperateType operateType = context.getOperateType();
        if (operateType.equals((Object)InstanceOperateType.START)) {
            startStateParam.setOperateType(StartStateParam.Type.START.getType());
        } else if (operateType.equals((Object)InstanceOperateType.CLEAR)) {
            startStateParam.setOperateType(StartStateParam.Type.CLEAR.getType());
        } else if (operateType.equals((Object)InstanceOperateType.STOP)) {
            startStateParam.setOperateType(StartStateParam.Type.STOP.getType());
        }
        startStateParam.setContextEntityId(context.getContextEntityId());
        startStateParam.setContextFilterExpression(context.getContextFilterExpression());
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)startStateParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new OperateWorkflowTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    private AsyncTaskInfo refreshParticipantExecute(InstanceBaseContext context) {
        StartStateParam startStateParam = new StartStateParam();
        startStateParam.setTaskKey(context.getTaskKey());
        startStateParam.setPeriod(context.getPeriod());
        startStateParam.setAdjust(context.getAdjust());
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)startStateParam));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new RefreshParticipantTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }
}

