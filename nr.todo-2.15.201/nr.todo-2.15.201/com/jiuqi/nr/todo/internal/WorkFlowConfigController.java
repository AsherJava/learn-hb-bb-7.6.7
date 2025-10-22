/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService
 *  com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.todo.internal;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeFormSchemeService;
import com.jiuqi.nr.definition.internal.runtime.controller.IRuntimeTaskService;
import com.jiuqi.nr.todo.bean.TodoConfig;
import com.jiuqi.nr.todo.internal.EntityQueryManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping
@RestController
public class WorkFlowConfigController {
    @Autowired
    private IDataentryFlowService dataentryFlowService;
    @Autowired
    private IRuntimeFormSchemeService formSchemeService;
    @Autowired
    private IRuntimeTaskService taskService;
    @Autowired
    private EntityQueryManager entityQueryManager;

    @PostMapping(value={"/todo/workflow/config"})
    public ActionParam getWorkFlowConfig(@RequestBody TodoConfig todoConfig) throws Exception {
        String taskKey;
        TaskDefine taskDefine;
        ActionParam actionParam = new ActionParam();
        WorkflowDataBean workflowDataBean = new WorkflowDataBean();
        workflowDataBean.setFormSchemeKey(todoConfig.getFormSchemeKey());
        String formKeyOrGroupKey = null;
        String reportIds = todoConfig.getReportId();
        if (reportIds != null && !reportIds.isEmpty()) {
            if (reportIds.contains("\u3001")) {
                String[] split = reportIds.split("\u3001");
                formKeyOrGroupKey = split[0];
            } else {
                formKeyOrGroupKey = todoConfig.getReportId();
            }
        }
        workflowDataBean.setFormKey(formKeyOrGroupKey);
        workflowDataBean.setFormGroupKey(formKeyOrGroupKey);
        DimensionValueSet dimensionSet = new DimensionValueSet();
        dimensionSet.setValue("DATATIME", (Object)todoConfig.getPeriod());
        FormSchemeDefine formSchemeDefine = this.formSchemeService.getFormScheme(todoConfig.getFormSchemeKey());
        if (formSchemeDefine != null && (taskDefine = this.taskService.queryTaskDefine(taskKey = formSchemeDefine.getTaskKey())) != null) {
            EntityViewDefine entityViewDefine = this.entityQueryManager.getEntityViewDefineByEntitiesKey(taskDefine.getMasterEntitiesKey());
            if (entityViewDefine != null) {
                dimensionSet.setValue(this.entityQueryManager.getMainDimName(entityViewDefine), (Object)todoConfig.getUnitId());
                workflowDataBean.setDimSet(dimensionSet);
                List workflowDataInfos = this.dataentryFlowService.queryWorkflowDataInfo(workflowDataBean);
                if (workflowDataInfos != null && workflowDataInfos.size() > 0) {
                    block0: for (WorkflowDataInfo workflowDataInfo : workflowDataInfos) {
                        List actions = workflowDataInfo.getActions();
                        if (actions == null || actions.size() <= 0) continue;
                        for (WorkflowAction workflowAction : actions) {
                            if (todoConfig.getActionCode() == null || !todoConfig.getActionCode().equals(workflowAction.getCode())) continue;
                            actionParam = workflowAction.getActionParam();
                            continue block0;
                        }
                    }
                }
            }
            actionParam.setDimensionName(this.entityQueryManager.getMainDimName(entityViewDefine));
        }
        return actionParam;
    }

    @GetMapping(value={"/getEntityDim"})
    public String getWorkFlowConfig(@RequestParam String formSchemeKey) throws Exception {
        String taskKey;
        TaskDefine taskDefine;
        FormSchemeDefine formSchemeDefine = this.formSchemeService.getFormScheme(formSchemeKey);
        if (formSchemeDefine != null && (taskDefine = this.taskService.queryTaskDefine(taskKey = formSchemeDefine.getTaskKey())) != null) {
            EntityViewDefine entityViewDefine = this.entityQueryManager.getEntityViewDefineByEntitiesKey(taskDefine.getMasterEntitiesKey());
            return this.entityQueryManager.getMainDimName(entityViewDefine);
        }
        return null;
    }
}

