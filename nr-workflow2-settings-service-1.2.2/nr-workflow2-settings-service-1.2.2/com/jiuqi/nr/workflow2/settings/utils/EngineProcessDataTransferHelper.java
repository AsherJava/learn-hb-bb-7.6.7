/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityItem
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDOImpl
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType
 *  com.jiuqi.nr.workflow2.service.para.ProcessRangeDims
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.settings.utils;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityItem;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDO;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsDOImpl;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.para.EProcessRangeDimType;
import com.jiuqi.nr.workflow2.service.para.ProcessRangeDims;
import com.jiuqi.nr.workflow2.settings.dto.WorkflowSettingsManipulationContext;
import com.jiuqi.nr.workflow2.settings.service.WorkflowSettingsQueryService;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EngineProcessDataTransferHelper {
    public static final String TEMP_DIR_NAME = "workflow_transfer_temp";
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IEntityDataService entityDataService;
    @Autowired
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Autowired
    private IRuntimeDataSchemeService runtimeDataSchemeService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private WorkflowSettingsQueryService workflowSettingsQueryService;

    public Set<ProcessRangeDims> buildUnitWithAllFormOrFormGroupReportDimension(TaskDefine taskDefine, WorkflowObjectType workflowObjectType, List<IEntityRow> rangeEntityRows) {
        String entityId = this.getEntityCaliber(taskDefine.getKey());
        LinkedHashSet<ProcessRangeDims> rangeDims = new LinkedHashSet<ProcessRangeDims>();
        String dimensionName = this.entityMetaService.queryEntity(entityId).getDimensionName();
        ProcessRangeDims entityRangeDims = new ProcessRangeDims();
        entityRangeDims.setDimensionName(dimensionName);
        entityRangeDims.setDimensionKey(entityId);
        entityRangeDims.setRangeType(EProcessRangeDimType.RANGE);
        entityRangeDims.setRangeDims(rangeEntityRows.stream().map(IEntityItem::getEntityKeyData).collect(Collectors.toList()));
        rangeDims.add(entityRangeDims);
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM) || workflowObjectType.equals((Object)WorkflowObjectType.MD_WITH_SFR)) {
            ProcessRangeDims formRangeDims = new ProcessRangeDims();
            formRangeDims.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formRangeDims.setRangeType(EProcessRangeDimType.ALL);
            rangeDims.add(formRangeDims);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ProcessRangeDims formGroupRangeDims = new ProcessRangeDims();
            formGroupRangeDims.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupRangeDims.setRangeType(EProcessRangeDimType.ALL);
            rangeDims.add(formGroupRangeDims);
        }
        boolean isAdjustEnable = this.runtimeDataSchemeService.enableAdjustPeriod(taskDefine.getDataScheme());
        if (isAdjustEnable) {
            ProcessRangeDims adjustOneDims = new ProcessRangeDims();
            adjustOneDims.setDimensionName("ADJUST");
            adjustOneDims.setDimensionKey("ADJUST");
            adjustOneDims.setRangeType(EProcessRangeDimType.ALL);
            rangeDims.add(adjustOneDims);
        }
        return rangeDims;
    }

    public IEntityTable getEntityTable(String entityId, String period, String periodView, String filterExpression) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        executorContext.setPeriodView(periodView);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId, filterExpression));
        entityQuery.setMasterKeys(dimensionValueSet);
        entityQuery.sorted(true);
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getEntityCaliber(String taskKey) {
        String entityCaliber = DsContextHolder.getDsContext().getContextEntityId();
        if (entityCaliber == null || entityCaliber.isEmpty()) {
            TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
            entityCaliber = taskDefine.getDw();
        }
        return entityCaliber;
    }

    public WorkflowSettingsDO buildTargetSettingsDO(String configId, WorkflowSettingsManipulationContext context) {
        WorkflowSettingsDOImpl newSettingsDO = new WorkflowSettingsDOImpl();
        Object config = context.getWorkflowSettings();
        JSONObject workflowSettings = new JSONObject((Map)((HashMap)config));
        boolean isWorkFlowEnabled = workflowSettings.getBoolean("isWorkFlowEnabled");
        JSONObject todoSetting = workflowSettings.getJSONObject("todoSetting");
        boolean isTodoEnabled = todoSetting.getBoolean("isTodoEnabled");
        JSONObject basicSettings = workflowSettings.getJSONObject("basicSettings");
        String workflowObject = basicSettings.getString("submitMode");
        String workflowEngine = basicSettings.getString("workflowType");
        String workflowDefine = basicSettings.getString("workflowDefine");
        if (workflowDefine == null || workflowDefine.isEmpty()) {
            if (workflowEngine.equals("jiuqi.nr.customprocessengine")) {
                throw new RuntimeException("\u2014\u2014\u2014\u2014 \u672a\u9009\u62e9\u6d41\u7a0b\u5b9a\u4e49 \u2014\u2014\u2014\u2014");
            }
            String originWorkflowDefine = this.workflowSettingsService.queryTaskWorkflowDefine(context.getTaskId());
            workflowDefine = originWorkflowDefine == null || originWorkflowDefine.isEmpty() || originWorkflowDefine.equals("default-1.0") ? UUID.randomUUID().toString() : originWorkflowDefine;
        }
        newSettingsDO.setId(configId);
        newSettingsDO.setTaskId(context.getTaskId());
        newSettingsDO.setWorkflowEngine(workflowEngine);
        newSettingsDO.setWorkflowDefine(workflowDefine);
        newSettingsDO.setWorkflowEnable(isWorkFlowEnabled);
        newSettingsDO.setTodoEnable(isTodoEnabled);
        newSettingsDO.setWorkflowObjectType(WorkflowObjectType.valueOf((String)workflowObject));
        JSONObject otherConfig = new JSONObject((Map)((HashMap)context.getOtherSettings()));
        newSettingsDO.setOtherConfig(otherConfig.toString());
        return newSettingsDO;
    }

    public boolean isExecuteDataTransfer(WorkflowSettingsDO originalSettingsDO, WorkflowSettingsDO newSettingsDO) {
        boolean isExistInstance = this.workflowSettingsQueryService.isExistWorkflowInstance(originalSettingsDO.getTaskId());
        if (!isExistInstance) {
            return false;
        }
        if (originalSettingsDO.getWorkflowEngine().equals("jiuqi.nr.default-1.0") && newSettingsDO.getWorkflowEngine().equals("jiuqi.nr.default")) {
            return true;
        }
        if (originalSettingsDO.getWorkflowEngine().equals("jiuqi.nr.default") && newSettingsDO.getWorkflowEngine().equals("jiuqi.nr.default") && !originalSettingsDO.getWorkflowObjectType().equals((Object)newSettingsDO.getWorkflowObjectType())) {
            return !(originalSettingsDO.getWorkflowObjectType().equals((Object)WorkflowObjectType.MAIN_DIMENSION) && newSettingsDO.getWorkflowObjectType().equals((Object)WorkflowObjectType.MD_WITH_SFR) || originalSettingsDO.getWorkflowObjectType().equals((Object)WorkflowObjectType.MD_WITH_SFR) && newSettingsDO.getWorkflowObjectType().equals((Object)WorkflowObjectType.MAIN_DIMENSION));
        }
        return false;
    }

    public static void deleteDirectory(File file) {
        File[] files;
        if (file.isDirectory() && (files = file.listFiles()) != null) {
            for (File internalFile : files) {
                EngineProcessDataTransferHelper.deleteDirectory(internalFile);
            }
        }
        file.delete();
    }
}

