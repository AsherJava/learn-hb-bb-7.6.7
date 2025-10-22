/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.todo.service.TodoManipulationServiceImpl
 */
package com.jiuqi.nr.bpm.setting.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.ProcessEngineProvider;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.dataflow.service.IQueryUploadStateService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.upload.modeling.ProcessBuilderUtils;
import com.jiuqi.nr.bpm.service.HistoryService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.setting.service.IDeleteProcess;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.upload.WorkflowStatus;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationServiceImpl;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DeleteProcess
implements IDeleteProcess {
    private static final Logger logger = LoggerFactory.getLogger(DeleteProcess.class);
    @Autowired
    private ProcessEngineProvider processEngineProvider;
    @Autowired
    private IQueryUploadStateService queryUploadStateService;
    @Autowired
    private WorkflowSettingService workflowSettingService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private TodoManipulationServiceImpl todoManipulationServiceImpl;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;

    @Override
    public boolean deleteProcess(String formSchemeId, Map<BusinessKey, String> businessKeys, WorkflowStatus flowType, boolean bindFlag, boolean selectUnitAll, boolean selectReportAll, String adjust) {
        WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(formSchemeId);
        FormSchemeDefine formScheme = this.viewController.getFormScheme(formSchemeId);
        Optional<ProcessEngine> processEngine = null;
        try {
            if (WorkflowStatus.DEFAULT.equals((Object)flowType)) {
                processEngine = this.processEngineProvider.getProcessEngine(ProcessType.DEFAULT);
            } else if (WorkflowStatus.WORKFLOW.equals((Object)flowType)) {
                processEngine = this.processEngineProvider.getProcessEngine(ProcessType.COMPLETED_ACTIVIT);
            }
            RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            HashSet<BusinessKey> businessKeyList = new HashSet<BusinessKey>();
            for (Map.Entry<BusinessKey, String> object : businessKeys.entrySet()) {
                DimensionValueSet dim;
                String task;
                String actionCode;
                BusinessKey businessKey = object.getKey();
                businessKeyList.add(businessKey);
                String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), businessKey);
                this.clearMessage(businessKey, workflowStartType, adjust, corporateValue);
                Optional<ProcessInstance> instance = runTimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
                if (!Optional.empty().equals(instance)) {
                    runTimeService.deleteProcessInstanceById(instance.get().getId());
                    HistoryService historyService = processEngine.map(engine -> engine.getHistoryService()).orElse(null);
                    historyService.deleteHistoricProcessInstance(BusinessKeyFormatter.formatToString(businessKey), instance.get().getId());
                }
                this.queryUploadStateService.deleteUploadState(businessKey);
                this.queryUploadStateService.deleteUploadRecord(businessKey);
                if (bindFlag) {
                    // empty if block
                }
                if (bindFlag) continue;
                if (WorkFlowType.ENTITY.equals((Object)workflowStartType)) {
                    actionCode = "stop";
                    task = this.getTask(businessKey);
                    dim = this.nrParameterUtils.convertDimensionName(businessKey);
                    this.nrParameterUtils.commitStateQuery(formScheme, dim, actionCode, task, null);
                    continue;
                }
                if (!WorkFlowType.FORM.equals((Object)workflowStartType) && !WorkFlowType.GROUP.equals((Object)workflowStartType)) continue;
                actionCode = "";
                actionCode = selectReportAll ? "stop" : "act_other_start";
                task = this.getTask(businessKey);
                dim = this.nrParameterUtils.convertDimensionName(businessKey);
                this.nrParameterUtils.addFormKeyToMasterKeys(dim, null, "11111111-1111-1111-1111-111111111111");
                this.nrParameterUtils.commitStateQuery(formScheme, dim, actionCode, task, null);
            }
            if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
                this.nrParameterUtils.updateUnitState(businessKeyList, workflowStartType, null);
            }
            return true;
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    private String getTask(BusinessKey businessKey) {
        List<Task> tasks = this.workflow.queryTasks(businessKey.getFormSchemeKey(), businessKey);
        if (tasks != null && tasks.size() > 0) {
            Task task = tasks.get(0);
            return task.getId();
        }
        return null;
    }

    private void clearMessage(BusinessKey businessKey, WorkFlowType workflowStartType, String adjust, String corporateValue) {
        String dwMainDim = this.dimensionUtil.getDwTableNameByFormSchemeKey(businessKey.getFormSchemeKey());
        String masterEntityKey = businessKey.getMasterEntity().getMasterEntityKey(dwMainDim);
        String formKey = businessKey.getFormKey();
        List<Task> tasks = this.workflow.queryTasks(businessKey.getFormSchemeKey(), businessKey);
        String userTaskId = null;
        if (tasks != null && tasks.size() > 0) {
            Task task = tasks.get(0);
            userTaskId = task.getUserTaskId();
        }
        String messageId = this.workflow.getMessageId(businessKey.getFormSchemeKey(), businessKey.getPeriod(), masterEntityKey, adjust, formKey, formKey, workflowStartType, userTaskId, corporateValue);
        ArrayList<String> ids = new ArrayList<String>();
        ids.add(ProcessBuilderUtils.produceUUIDKey(messageId));
        this.todoManipulationServiceImpl.batchClearTodo(ids);
    }
}

