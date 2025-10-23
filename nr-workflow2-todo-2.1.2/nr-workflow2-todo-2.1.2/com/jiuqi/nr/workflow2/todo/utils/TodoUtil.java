/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.definition.api.IDesignTimeViewController
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DesignTaskDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.todocat.TodoCatConfigDO
 *  com.jiuqi.va.domain.todocat.TodoCatGroupDO
 *  com.jiuqi.va.domain.todocat.TodoCatInfoDO
 *  com.jiuqi.va.domain.todocat.TodoCatParamDTO
 *  com.jiuqi.va.feign.client.TodoClient
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.workflow2.todo.utils;

import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.definition.api.IDesignTimeViewController;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DesignTaskDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationService;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.todocat.TodoCatConfigDO;
import com.jiuqi.va.domain.todocat.TodoCatGroupDO;
import com.jiuqi.va.domain.todocat.TodoCatInfoDO;
import com.jiuqi.va.domain.todocat.TodoCatParamDTO;
import com.jiuqi.va.feign.client.TodoClient;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TodoUtil {
    private static final String ROOT_GROUP_NAME = "-";
    private static final String TODO_GROUP_NAME = "NR_WORKFLOW_TODO";
    private static final String TODO_MODEL_NAME = "NR-WORKFLOW-TODO";
    @Resource
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDesignTimeViewController designTimeViewController;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IEntityDataService entityDataService;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    private IRuntimeDataSchemeService dataSchemeService;
    @Resource
    private TodoClient todoClient;
    @Resource
    public IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    private IEntityMetaService entityMetaService;
    @Resource
    private TodoManipulationService todoManipulationService;
    @Resource
    private WorkflowSettingsService workflowSettingsService;

    public IEntityTable getEntityTable(String entityId, String period, String periodView) {
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

    public FormSchemeDefine getFormSchemeDefine(String taskId, String period) {
        SchemePeriodLinkDefine schemePeriodLinkDefine;
        try {
            schemePeriodLinkDefine = this.runTimeViewController.getSchemePeriodLinkByPeriodAndTask(period, taskId);
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
            throw new RuntimeException(e);
        }
        return schemePeriodLinkDefine == null ? null : this.runTimeViewController.getFormScheme(schemePeriodLinkDefine.getSchemeKey());
    }

    public boolean isAdjust(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.getTask(formScheme.getTaskKey());
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dimension : dataSchemeDimension) {
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if (!"ADJUST".equals(dimensionName)) continue;
            return true;
        }
        return false;
    }

    public String getDimensionName(String entityId) {
        if (this.periodEntityAdapter.isPeriodEntity(entityId)) {
            return this.periodEntityAdapter.getPeriodEntity(entityId).getDimensionName();
        }
        if ("ADJUST".equals(entityId)) {
            return "ADJUST";
        }
        return this.entityMetaService.queryEntity(entityId).getDimensionName();
    }

    public void establishTodoGroup() {
        TodoCatGroupDO groupDO = new TodoCatGroupDO();
        groupDO.setName(TODO_GROUP_NAME);
        R groupResult = this.todoClient.listTodoCatGroup(groupDO);
        List todoDefineGroupList = (List)groupResult.get((Object)"data");
        if (todoDefineGroupList == null || todoDefineGroupList.isEmpty()) {
            groupDO.setTitle("\u62a5\u8868\u4e0a\u62a5\u6d41\u7a0b\u5f85\u529e");
            groupDO.setParentName(ROOT_GROUP_NAME);
            R response = this.todoClient.saveEditTodoCatGroup(groupDO);
            if ((Integer)response.get((Object)"code") != 0) {
                LoggerFactory.getLogger(this.getClass()).error("\u2014\u2014\u2014\u2014\u5f85\u529e\u5206\u7c7b\u5b9a\u4e49\u5206\u7ec4\u5df2\u5b58\u5728 \u521b\u5efa\u5931\u8d25\u2014\u2014\u2014\u2014");
            }
        }
    }

    public void establishTodoDefine(String taskId) {
        R saveResponse;
        boolean isWorkflowEnable;
        DesignTaskDefine taskDefine = this.designTimeViewController.getTask(taskId);
        if (this.isTaskVersion_2_0(taskId)) {
            isWorkflowEnable = this.workflowSettingsService.queryTaskWorkflowEnable(taskId);
        } else {
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            FlowsType flowsType = flowsSetting.getFlowsType();
            boolean bl = isWorkflowEnable = !flowsType.equals((Object)FlowsType.NOSTARTUP);
        }
        if (!isWorkflowEnable) {
            this.deleteTodoDefine(taskDefine.getTaskCode());
            return;
        }
        TodoCatParamDTO todoCatParamDTO = new TodoCatParamDTO();
        todoCatParamDTO.setName(TODO_GROUP_NAME);
        R result = this.todoClient.listInfoListByGroupId(todoCatParamDTO);
        List todoDefineList = (List)result.get((Object)"data");
        List todoDefineNames = todoDefineList.stream().map(todoDefine -> (String)todoDefine.get("name")).collect(Collectors.toList());
        TodoCatInfoDO catInfoDO = new TodoCatInfoDO();
        catInfoDO.setName(taskDefine.getTaskCode());
        catInfoDO.setTitle(taskDefine.getTitle());
        catInfoDO.setGroupName(TODO_GROUP_NAME);
        catInfoDO.setModelName(TODO_MODEL_NAME);
        UUID id = null;
        if (!todoDefineNames.contains(taskDefine.getTaskCode())) {
            saveResponse = this.todoClient.saveEditTodoCatInfo(catInfoDO);
            if ((Integer)saveResponse.get((Object)"code") != 0) {
                LoggerFactory.getLogger(this.getClass()).error("\u2014\u2014\u2014\u2014\u5f85\u529e\u5206\u7c7b\u5b9a\u4e49\u5df2\u5b58\u5728 \u521b\u5efa\u5931\u8d25\u2014\u2014\u2014\u2014");
                return;
            }
            id = UUID.fromString(saveResponse.get((Object)"id").toString());
        }
        if (id == null) {
            Optional<Map> targetTodoDefine = todoDefineList.stream().filter(arg_0 -> TodoUtil.lambda$establishTodoDefine$1((TaskDefine)taskDefine, arg_0)).findFirst();
            if (!targetTodoDefine.isPresent()) {
                return;
            }
            id = UUID.fromString((String)targetTodoDefine.get().get("id"));
        }
        catInfoDO.setId(id);
        saveResponse = this.todoClient.saveEditTodoCatInfo(catInfoDO);
        if ((Integer)saveResponse.get((Object)"code") != 0) {
            LoggerFactory.getLogger(this.getClass()).error("\u2014\u2014\u2014\u2014\u5f85\u529e\u5206\u7c7b\u5b9a\u4e49\u76f8\u5173\u53c2\u6570\u66f4\u65b0\u5931\u8d25\u2014\u2014\u2014\u2014");
        }
        TodoCatConfigDO configDO = new TodoCatConfigDO();
        configDO.setId(id);
        configDO.setConfig("{\"taskId\": \"" + taskId + "\"}");
        R configResponse = this.todoClient.saveTodoCatConfig(configDO);
        if ((Integer)configResponse.get((Object)"code") != 0) {
            LoggerFactory.getLogger(this.getClass()).error("\u2014\u2014\u2014\u2014\u5f85\u529e\u5206\u7c7b\u5b9a\u4e49\u914d\u7f6e\u6dfb\u52a0\u5931\u8d25\u2014\u2014\u2014\u2014");
        }
    }

    public void verifyTaskWorkflowEnable(String taskKey) {
        boolean isWorkflowEnable;
        TaskDefine taskDefine = this.runTimeViewController.getTask(taskKey);
        if (this.isTaskVersion_2_0(taskKey)) {
            isWorkflowEnable = this.workflowSettingsService.queryTaskWorkflowEnable(taskKey);
        } else {
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            FlowsType flowsType = flowsSetting.getFlowsType();
            boolean bl = isWorkflowEnable = !flowsType.equals((Object)FlowsType.NOSTARTUP);
        }
        if (!isWorkflowEnable) {
            this.deleteTodoDefine(taskDefine.getTaskCode());
        }
    }

    public void deleteTodoDefine(String taskCode) {
        TodoCatParamDTO todoCatParamDTO = new TodoCatParamDTO();
        todoCatParamDTO.setName(TODO_GROUP_NAME);
        R result = this.todoClient.listInfoListByGroupId(todoCatParamDTO);
        List todoDefineList = (List)result.get((Object)"data");
        UUID id = null;
        for (Map todoDefine : todoDefineList) {
            String name = (String)todoDefine.get("name");
            if (!name.equals(taskCode)) continue;
            id = UUID.fromString((String)todoDefine.get("id"));
            break;
        }
        if (id != null) {
            TodoCatInfoDO todoCatInfoDO = new TodoCatInfoDO();
            todoCatInfoDO.setId(id);
            R response = this.todoClient.deleteTodoCatInfo(todoCatInfoDO);
            if ((Integer)response.get((Object)"code") != 0) {
                LoggerFactory.getLogger(this.getClass()).error("\u2014\u2014\u2014\u2014\u5f85\u529e\u5206\u7c7b\u5b9a\u4e49\u5220\u9664\u5931\u8d25\u2014\u2014\u2014\u2014");
            }
        }
    }

    public boolean isTaskVersion_2_0(String taskKey) {
        DesignTaskDefine designTaskDefine = this.designTimeViewController.getTask(taskKey);
        return designTaskDefine.getVersion().equals("2.0");
    }

    private static /* synthetic */ boolean lambda$establishTodoDefine$1(TaskDefine taskDefine, Map todoDefine) {
        return ((String)todoDefine.get("name")).equals(taskDefine.getTaskCode());
    }
}

