/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.bpm.impl.process.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.exception.BpmException;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.process.ProcessTask;
import com.jiuqi.nr.bpm.impl.process.ProcessUserTask;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessAction;
import com.jiuqi.nr.bpm.impl.process.consts.ProcessType;
import com.jiuqi.nr.bpm.impl.process.consts.TaskEnum;
import com.jiuqi.nr.bpm.impl.process.dao.ProcessStateHistoryDao;
import com.jiuqi.nr.bpm.impl.process.service.ProcessTaskBuilder;
import com.jiuqi.nr.bpm.impl.process.util.ProcessUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DefaultProcessTaskBuilder
implements ProcessTaskBuilder {
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private ProcessStateHistoryDao processStateHistoryDao;
    @Autowired
    private ProcessUtil processUtil;
    @Autowired
    private BusinessGenerator businessGenerator;

    @Override
    public List<Task> queryTaskByBusinessKey(BusinessKey businessKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        DimensionValueSet dimensionValueSet = this.processUtil.buildUploadMasterKey(businessKey);
        List<Task> task = this.queryTasks(dimensionValueSet, businessKey.getFormKey(), formScheme);
        return task;
    }

    @Override
    public List<Task> queryTasks(DimensionValueSet dimensionValueSet, String formKey, FormSchemeDefine formScheme) {
        ArrayList<Task> userTasks = new ArrayList<Task>();
        UploadStateNew uploadState = this.processStateHistoryDao.queryUploadState(dimensionValueSet, formKey, formScheme);
        if (uploadState == null) {
            return Collections.emptyList();
        }
        List<TaskEnum> taskEnums = Arrays.asList(TaskEnum.values());
        List tasks = taskEnums.stream().filter(e -> e.getTaskId().equals(uploadState.getTaskId())).collect(Collectors.toList());
        TaskEnum task = (TaskEnum)((Object)tasks.stream().findFirst().get());
        userTasks.add(new ProcessTask(task, this.businessGenerator.buildBusinessKey(formScheme.getKey(), dimensionValueSet, formKey, formKey)));
        return userTasks;
    }

    @Override
    public Optional<UserTask> queryUserTask(String userTaskId, String formSchemeKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(formSchemeKey);
        List<TaskEnum> taskEnums = Arrays.asList(TaskEnum.values());
        List tasks = taskEnums.stream().filter(e -> e.getTaskId().equals(userTaskId)).collect(Collectors.toList());
        TaskEnum task = (TaskEnum)((Object)tasks.stream().findFirst().get());
        return Optional.of(new ProcessUserTask(userTaskId, task.getName(), this.buildProcessAction(task, formScheme)));
    }

    @Override
    public Optional<Task> queryTaskById(String userTaskId, BusinessKey businessKey) {
        DimensionValueSet dimensionValueSet = this.processUtil.buildUploadMasterKey(businessKey);
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(businessKey.getFormSchemeKey());
        UploadStateNew instance = this.processStateHistoryDao.queryUploadState(dimensionValueSet, businessKey.getFormKey(), formScheme);
        if (instance != null && instance.getTaskId().equals(userTaskId)) {
            List<TaskEnum> taskEnums = Arrays.asList(TaskEnum.values());
            List tasks = taskEnums.stream().filter(e -> e.getTaskId().equals(userTaskId)).collect(Collectors.toList());
            TaskEnum task = (TaskEnum)((Object)tasks.stream().findFirst().get());
            return Optional.of(new ProcessTask(task, businessKey));
        }
        return Optional.empty();
    }

    @Override
    public boolean canStartProcess(String businessKey) {
        return true;
    }

    @Override
    public ProcessType getProcessType() {
        return ProcessType.DEFAULT;
    }

    @Override
    public String nextUserTaskId(String taskId, String actionId, String businessKey) {
        FormSchemeDefine formScheme = this.nrParameterUtils.getFormScheme(BusinessKeyFormatter.parsingFromString(businessKey).getFormSchemeKey());
        boolean isSubmit = formScheme.getFlowsSetting().getDesignFlowSettingDefine().isUnitSubmitForCensorship();
        if (taskId.equals("start")) {
            return isSubmit ? "tsk_submit" : "tsk_upload";
        }
        if (taskId.equals("tsk_submit")) {
            return "tsk_upload";
        }
        if (taskId.equals("tsk_upload")) {
            if (actionId.equals("act_return")) {
                return "tsk_submit";
            }
            return "tsk_audit";
        }
        if (taskId.equals("tsk_audit")) {
            if (actionId.equals("act_reject")) {
                if (isSubmit) {
                    return "tsk_submit";
                }
                return "tsk_upload";
            }
            return "tsk_audit_after_confirm";
        }
        if (taskId.equals("tsk_audit_after_confirm")) {
            if (actionId.equals("act_reject")) {
                if (isSubmit) {
                    return "tsk_submit";
                }
                return "tsk_upload";
            }
            return "tsk_audit";
        }
        throw new BpmException(String.format("%s action:%s task:%s", "\u53c2\u6570\u9519\u8bef\uff01", actionId, taskId));
    }

    private List<ProcessAction> buildProcessAction(TaskEnum task, FormSchemeDefine formSchemeDefine) {
        ArrayList<ProcessAction> processActions = new ArrayList<ProcessAction>();
        boolean isSubmit = formSchemeDefine.getFlowsSetting().getDesignFlowSettingDefine().isUnitSubmitForCensorship();
        boolean allowConfirm = formSchemeDefine.getFlowsSetting().getDesignFlowSettingDefine().isDataConfirm();
        String taskId = task.getTaskId();
        if (taskId.equals("tsk_upload")) {
            processActions.add(ProcessAction.UPLOAD);
            if (isSubmit) {
                processActions.add(ProcessAction.RETURN);
            }
        } else if (taskId.equals("tsk_submit")) {
            processActions.add(ProcessAction.SUBMIT);
        } else if (taskId.equals("tsk_audit")) {
            processActions.add(ProcessAction.REJECT);
            if (allowConfirm) {
                processActions.add(ProcessAction.CONFIRM);
            }
        } else if (taskId.equals("tsk_audit_after_confirm")) {
            processActions.add(ProcessAction.CANCEL_CONFIRM);
            processActions.add(ProcessAction.REJECT);
        }
        return processActions;
    }
}

