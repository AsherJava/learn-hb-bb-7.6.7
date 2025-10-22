/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 */
package com.jiuqi.nr.bpm.de.dataflow.complete;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySet;
import com.jiuqi.nr.bpm.common.ConcurrentTaskContext;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.dataflow.serviceImpl.BatchQueryTaskUtil;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.common.ForceTaskContext;
import com.jiuqi.nr.bpm.de.dataflow.complete.AutoComplete;
import com.jiuqi.nr.bpm.de.dataflow.complete.BaseExecute;
import com.jiuqi.nr.bpm.de.dataflow.complete.CompleteAbstract;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.ISendMessage;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.BatchMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageData;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.SingleMessageEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.thread.BatchMessageThreadLocalStrategy;
import com.jiuqi.nr.bpm.de.dataflow.service.IOtherAction;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.CounterParamBuilder;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.exception.NotSupportOperation;
import com.jiuqi.nr.bpm.exception.TaskNotFound;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.countersign.CountSignStartMode;
import com.jiuqi.nr.bpm.impl.countersign.group.CounterSignConst;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class ExecuteTask
extends BaseExecute
implements CompleteAbstract {
    private static final Logger logger = LoggerFactory.getLogger(ExecuteTask.class);
    public static final String KEY_OF_RETURN_TYPE = "returnType";
    @Autowired
    NrParameterUtils nrParameterUtils;
    @Autowired
    private ISendMessage sendMessage;
    @Autowired
    AutoComplete autoExecute;
    @Autowired(required=false)
    Map<String, IOtherAction> otherActionResult;
    @Autowired
    CounterParamBuilder counterParamBuilder;
    @Autowired
    BusinessGenerator businessGenerator;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    @Autowired
    private BatchQueryTaskUtil batchQueryTaskUtil;
    @Autowired
    private DimensionUtil dimensionUtil;

    @Override
    public boolean IsMatch(String actionCode, String taskKey) {
        return this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey) && ("act_upload".equals(actionCode) || "start".equals(actionCode) || "act_reject".equals(actionCode) || "act_confirm".equals(actionCode) || "act_submit".equals(actionCode) || "act_return".equals(actionCode) || "act_cancel_confirm".equals(actionCode) || actionCode.startsWith("cus_"));
    }

    @Override
    public CompleteMsg executeTask(ExecuteParam executeParam) {
        NpContext context = NpContextHolder.getContext();
        ContextExtension signExtension = context.getExtension(CounterSignConst.NR_WORKFLOW_SING_MODE);
        CountSignStartMode countSignStartMode = new CountSignStartMode();
        List<String> signBootModes = executeParam.getSignBootModes();
        if (signBootModes != null && signBootModes.size() > 0) {
            countSignStartMode.setActors(new HashSet<String>(signBootModes));
            signExtension.put(CounterSignConst.WORKFLOW_SIGN_START_MODE_VALUE, (Serializable)countSignStartMode);
        }
        CompleteMsg completeMsg = new CompleteMsg();
        FormSchemeDefine formScheme = this.getFormScheme(executeParam.getFormSchemeKey());
        DimensionValueSet filterDims = this.buildDimensionValueSet(formScheme, executeParam.getDimSet());
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), filterDims, executeParam.getFormKey(), executeParam.getGroupKey());
        boolean isDefault = this.isDefaultWorkflow(executeParam.getFormSchemeKey());
        Optional<ProcessEngine> processEngine = this.getProcessEngine(executeParam.getFormSchemeKey());
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        try {
            CompleteMsg fillTime = this.isFillTime(executeParam.getFormSchemeKey(), executeParam.getDimSet());
            if (fillTime.isSucceed()) {
                return fillTime;
            }
            ForceTaskContext forceTaskContext = new ForceTaskContext();
            if (executeParam.isForceUpload()) {
                forceTaskContext.put(this.nrParameterUtils.getForceMapKey(), executeParam.isForceUpload());
            }
            if (isDefault) {
                this.clearMessage(executeParam.getMessageId(), executeParam.getFormSchemeKey(), executeParam.getDimSet(), executeParam.getTaskId(), executeParam.getFormKey(), executeParam.getGroupKey(), executeParam.getActionId(), executeParam.getNodeId());
                Optional<Task> userTask = runTimeService.getTaskById(executeParam.getTaskId(), businessKey);
                if (!userTask.isPresent()) {
                    logger.info("no_find_node executeParam = {} businessKey = {} filterDims = {} ", executeParam, businessKey, filterDims);
                    throw new TaskNotFound("no_find_node");
                }
                boolean canExe = this.nrParameterUtils.canExecuteTask(userTask.get().getId(), executeParam.getActionId(), executeParam.getFormSchemeKey());
                if (!canExe) {
                    completeMsg.setSucceed(false);
                    completeMsg.setMsg("node_action_diff");
                    throw new NotSupportOperation(executeParam.getActionId(), userTask.get().getId());
                }
                if ("act_cancel_confirm".equals(executeParam.getActionId()) && !this.continueCancelConfirm(filterDims, formScheme, executeParam)) {
                    completeMsg.setSucceed(false);
                    completeMsg.setMsg("upload_ing");
                    return completeMsg;
                }
                HashMap<String, Object> variables = new HashMap<String, Object>();
                TaskContext taskContext = executeParam.getTaskContext();
                String returnType = executeParam.getReturnType();
                if (taskContext == null) {
                    taskContext = new ConcurrentTaskContext();
                    executeParam.setTaskContext(taskContext);
                }
                taskContext.put(KEY_OF_RETURN_TYPE, returnType == null ? "" : returnType);
                Map<Boolean, Task> autoCompelete = this.autoExecute.autoCompelete(runTimeService, deployService, businessKey, executeParam.getFormSchemeKey(), executeParam.getActionId(), executeParam.getComment(), executeParam.isForceUpload(), variables, executeParam.getTaskContext());
                for (Map.Entry<Boolean, Task> autoCom : autoCompelete.entrySet()) {
                    if (autoCom.getKey().booleanValue()) {
                        completeMsg.setSucceed(true);
                        completeMsg.setMsg("node_complete");
                        continue;
                    }
                    completeMsg.setSucceed(autoCom.getKey());
                    completeMsg.setMsg("node_no_complete");
                }
                if (SendMessageTaskConfig.canSendMessage() && autoCompelete.containsKey(true)) {
                    this.sendTodo(userTask.get(), businessKey, executeParam.getActionId(), executeParam.getComment(), executeParam.isSendEmail(), signBootModes);
                }
            } else {
                String returnType;
                this.clearMessage(executeParam.getMessageId(), executeParam.getFormSchemeKey(), executeParam.getDimSet(), executeParam.getTaskId(), executeParam.getFormKey(), executeParam.getGroupKey(), executeParam.getActionId(), executeParam.getNodeId());
                if ("act_cancel_confirm".equals(executeParam.getActionId()) && !this.continueCancelConfirm(filterDims, formScheme, executeParam)) {
                    completeMsg.setSucceed(false);
                    completeMsg.setMsg("upload_ing");
                    return completeMsg;
                }
                Optional<Task> currentTask = runTimeService.getTaskById(executeParam.getTaskId(), businessKey);
                if (!currentTask.isPresent()) {
                    throw new TaskNotFound("no_find_node");
                }
                HashMap<String, Object> variables = new HashMap<String, Object>();
                TaskContext taskContext = executeParam.getTaskContext();
                if (taskContext == null) {
                    taskContext = new ConcurrentTaskContext();
                }
                taskContext.put(KEY_OF_RETURN_TYPE, (returnType = executeParam.getReturnType()) == null ? "" : returnType);
                Map<Boolean, Task> autoCompelete = this.autoExecute.autoCompelete(runTimeService, deployService, businessKey, executeParam.getFormSchemeKey(), executeParam.getActionId(), executeParam.getComment(), executeParam.isForceUpload(), variables, executeParam.getTaskContext());
                for (Map.Entry<Boolean, Task> autoCom : autoCompelete.entrySet()) {
                    if (autoCom.getKey().booleanValue()) {
                        completeMsg.setSucceed(true);
                        completeMsg.setMsg("node_complete");
                        continue;
                    }
                    completeMsg.setSucceed(autoCom.getKey());
                    completeMsg.setMsg("node_no_complete");
                }
                if (SendMessageTaskConfig.canSendMessage() && autoCompelete.containsKey(true)) {
                    Iterator<Map.Entry<Boolean, Task>> iterator = autoCompelete.entrySet().iterator();
                    Map.Entry<Boolean, Task> tail = null;
                    while (iterator.hasNext()) {
                        tail = iterator.next();
                    }
                    Task task = (Task)tail.getValue();
                    this.sendTodo(task, businessKey, executeParam.getActionId(), executeParam.getComment(), executeParam.isSendEmail(), signBootModes);
                }
            }
        }
        catch (UserActionException e2) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e2.getMessage());
            logger.error(e2.getMessage(), e2);
        }
        catch (Exception e) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e.getMessage());
            completeMsg.setMsg(e.getLocalizedMessage());
            logger.error(e.getMessage(), e);
        }
        return completeMsg;
    }

    @Override
    public CompleteMsg batchExecuteTask(BatchExecuteParam executeParam) throws UserActionException {
        NpContext context = NpContextHolder.getContext();
        ContextExtension signExtension = context.getExtension(CounterSignConst.NR_WORKFLOW_SING_MODE);
        CountSignStartMode countSignStartMode = new CountSignStartMode();
        List<String> signBootModes = executeParam.getSignBootModes();
        if (signBootModes != null && signBootModes.size() > 0) {
            countSignStartMode.setActors(new HashSet<String>(signBootModes));
            signExtension.put(CounterSignConst.WORKFLOW_SIGN_START_MODE_VALUE, (Serializable)countSignStartMode);
        }
        CompleteMsg completeMsg = new CompleteMsg();
        HashSet<String> fliterkeys = new HashSet<String>();
        try {
            DimensionValueSet filterDims;
            FormSchemeDefine formScheme = this.getFormScheme(executeParam.getFormSchemeKey());
            String actionId = executeParam.getActionId();
            if ("act_cancel_confirm".equals(actionId) && !this.continueBatchCancelConfirm(filterDims = this.buildDimensionValueSet(formScheme, executeParam.getDimSet()), formScheme, executeParam, fliterkeys)) {
                completeMsg.setSucceed(false);
                completeMsg.setMsg("upload_ing");
                return completeMsg;
            }
            this.batchClearMessage(executeParam);
            Optional<ProcessEngine> processEngine = this.getProcessEngine(executeParam.getFormSchemeKey());
            RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
            BusinessKeySet businessKeySet = executeParam.getBusinessKeySet();
            String formSchemeKey = executeParam.getFormSchemeKey();
            String formGroupKey = null;
            if (businessKeySet.getFormKey() != null && businessKeySet.getFormKey().size() > 0) {
                formGroupKey = businessKeySet.getFormKey().stream().findAny().orElse(null);
            }
            BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, executeParam.getDimSet(), formGroupKey, formGroupKey);
            ForceTaskContext taskContext = new ForceTaskContext();
            taskContext.put(this.nrParameterUtils.getForceMapKey(), executeParam.isForceUpload());
            HashMap<String, Object> variables = new HashMap<String, Object>();
            HashMap autoCompelete = new HashMap();
            autoCompelete = executeParam.getConditionCache() != null ? this.autoExecute.batchAutoCompelete(runTimeService, deployService, businessKey, executeParam.getFormSchemeKey(), businessKeySet, executeParam.getActionId(), executeParam.getComment(), executeParam.isForceUpload(), variables, executeParam.getConditionCache(), executeParam.getTaskContext()) : this.autoExecute.batchAutoCompelete(runTimeService, deployService, businessKey, executeParam.getFormSchemeKey(), businessKeySet, executeParam.getActionId(), executeParam.getComment(), executeParam.isForceUpload(), variables, executeParam.getTaskContext());
            for (Map.Entry autoCom : autoCompelete.entrySet()) {
                if (((Boolean)autoCom.getKey()).booleanValue()) {
                    completeMsg.setSucceed(true);
                    completeMsg.setMsg("node_complete");
                    continue;
                }
                completeMsg.setSucceed((Boolean)autoCom.getKey());
                completeMsg.setMsg("node_no_complete");
            }
            if (executeParam.getMessageIds() != null && executeParam.getMessageIds().size() > 0) {
                executeParam.getMessageIds().forEach(e -> {
                    if (this.todoVersion.equals("1.0")) {
                        this.completeMsgVersion1_0((String)e, executeParam.getActionId());
                    } else if (this.todoVersion.equals("2.0")) {
                        this.completeMsg((String)e, executeParam.getActionId());
                    }
                });
            }
            if (SendMessageTaskConfig.canSendMessage() && autoCompelete.containsKey(true)) {
                Iterator iterator = autoCompelete.entrySet().iterator();
                Map.Entry tail = null;
                while (iterator.hasNext()) {
                    tail = iterator.next();
                }
                Task task = (Task)tail.getValue();
                this.sendTodo(task, businessKey, executeParam.getActionId(), executeParam.getComment(), executeParam.isSendEmail(), executeParam.getCanUploadDimensionSets(), executeParam.getBusinessKeySet().getFormKey(), signBootModes);
            }
            if (fliterkeys.size() > 0) {
                completeMsg.setSucceed(false);
                completeMsg.setMsg("upload_ing");
            } else {
                completeMsg.setSucceed(true);
                completeMsg.setMsg("batch_upload_success");
            }
        }
        catch (UserActionException e2) {
            completeMsg.setSucceed(false);
            if (e2.getMessage().contains("batch_sign_info")) {
                completeMsg.setMsg("batch_sign_info");
            } else {
                completeMsg.setMsg(e2.getMessage());
            }
            logger.error(e2.getMessage(), e2);
        }
        catch (Exception e2) {
            if (fliterkeys.size() > 0) {
                completeMsg.setSucceed(false);
                completeMsg.setMsg("upload_ing");
            } else {
                completeMsg.setSucceed(false);
                completeMsg.setMsg("batch_upload_fail");
            }
            logger.error(e2.getMessage(), e2);
        }
        return completeMsg;
    }

    private void batchClearMessage(BatchExecuteParam executeParam) {
        WorkFlowType startType = this.workflow.queryStartType(executeParam.getFormSchemeKey());
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(executeParam.getFormSchemeKey());
        Set<String> formKeySet = executeParam.getBusinessKeySet().getFormKey();
        ArrayList<String> formKeys = new ArrayList<String>();
        if (formKeySet != null && formKeySet.size() > 0) {
            for (String formKey : formKeySet) {
                formKeys.add(formKey);
            }
        }
        Map<String, List<Task>> taskMap = this.batchQueryTaskUtil.getTasks(executeParam.getFormSchemeKey(), executeParam.getDimSet(), formKeys, formKeys, startType, dwMainDimName);
        DimensionValueSet dimSet = executeParam.getDimSet();
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            BusinessKey businessKey = this.businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), dimSet, null, null);
            List<Task> tasks = taskMap.get(BusinessKeyFormatter.formatToString(businessKey));
            if (tasks != null && tasks.size() > 0) {
                String userTaskId = tasks.get(0).getUserTaskId();
                this.clearMessage(executeParam.getFormSchemeKey(), executeParam.getDimSet(), executeParam.getTaskId(), executeParam.getBusinessKeySet().getFormKey(), executeParam.getActionId(), userTaskId);
            }
        } else {
            for (String formKey : formKeySet) {
                BusinessKey businessKey = this.businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), dimSet, formKey, formKey);
                List<Task> tasks = taskMap.get(BusinessKeyFormatter.formatToString(businessKey));
                if (tasks == null || tasks.size() <= 0) continue;
                String userTaskId = tasks.get(0).getUserTaskId();
                this.clearMessage(executeParam.getFormSchemeKey(), executeParam.getDimSet(), executeParam.getTaskId(), executeParam.getBusinessKeySet().getFormKey(), executeParam.getActionId(), userTaskId);
            }
        }
    }

    private void sendTodo(Task task, BusinessKey businessKey, String actionId, String comment, boolean sendMail, List<String> signBootModes) {
        String fullname = NpContextHolder.getContext().getUser().getFullname();
        SingleMessageEvent singleMessageEvent = new SingleMessageEvent();
        MessageData messageData = new MessageData();
        messageData.setId(task.getId());
        messageData.setName(task.getName());
        messageData.setProcessDefinitionId(task.getProcessDefinitionId());
        messageData.setProcessInstanceId(task.getProcessInstanceId());
        messageData.setUserTaskId(task.getUserTaskId());
        singleMessageEvent.setTask(messageData);
        singleMessageEvent.setBusinessKey(businessKey);
        singleMessageEvent.setActionCode(actionId);
        singleMessageEvent.setContent(comment);
        singleMessageEvent.setSendMail(sendMail);
        singleMessageEvent.setOperator(fullname);
        if (signBootModes != null && signBootModes.size() > 0) {
            singleMessageEvent.setSignRoles(new HashSet<String>(signBootModes));
        }
        this.applicationContext.publishEvent(singleMessageEvent);
    }

    private void sendTodo(Task task, BusinessKey businessKey, String actionId, String comment, boolean sendEmail, List<DimensionValueSet> canUploadDimensionSets, Set<String> formKeys, List<String> signBootModes) {
        String fullname = NpContextHolder.getContext().getUser().getFullname();
        BatchMessageEvent batchMessageEvent = new BatchMessageEvent();
        MessageData messageData = new MessageData();
        messageData.setId(task.getId());
        messageData.setName(task.getName());
        messageData.setProcessDefinitionId(task.getProcessDefinitionId());
        messageData.setProcessInstanceId(task.getProcessInstanceId());
        messageData.setUserTaskId(task.getUserTaskId());
        batchMessageEvent.setTask(messageData);
        batchMessageEvent.setBusinessKey(businessKey);
        batchMessageEvent.setActionCode(actionId);
        batchMessageEvent.setContent(comment);
        batchMessageEvent.setSendMail(sendEmail);
        batchMessageEvent.setOperator(fullname);
        batchMessageEvent.setFormOrGroupKeys(formKeys);
        batchMessageEvent.setCanUploadUnitSize(canUploadDimensionSets.size());
        if (signBootModes != null && signBootModes.size() > 0) {
            batchMessageEvent.setSignRoles(new HashSet<String>(signBootModes));
        }
        BatchMessageThreadLocalStrategy.addMessageBody(batchMessageEvent);
    }

    private boolean continueCancelConfirm(DimensionValueSet filterDims, FormSchemeDefine formScheme, ExecuteParam executeParam) {
        String mainDimName = this.getMinName(formScheme.getKey());
        String parentId = this.getParentId(filterDims, formScheme.getKey());
        if (parentId != null) {
            DimensionValueSet parentDim = new DimensionValueSet();
            parentDim.setValue("DATATIME", filterDims.getValue("DATATIME"));
            parentDim.setValue(mainDimName, (Object)parentId);
            ActionStateBean queryUploadState = this.queryUploadState(parentDim, executeParam.getFormKey(), executeParam.getGroupKey(), formScheme);
            if (this.isDefaultWorkflow(formScheme.getKey())) {
                String stepReportType = formScheme.getFlowsSetting().getStepReportType();
                if (!"1".equals(stepReportType) && queryUploadState != null && (UploadState.UPLOADED.toString().equals(queryUploadState.getCode()) || UploadState.CONFIRMED.toString().equals(queryUploadState.getCode()))) {
                    return false;
                }
            } else if (null != queryUploadState) {
                String code = queryUploadState.getCode();
                if (UploadState.UPLOADED.toString().equals(code)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean continueBatchCancelConfirm(DimensionValueSet filterDims, FormSchemeDefine formScheme, BatchExecuteParam executeParam, Set<String> fliterkeys) {
        String mainDimName = this.getMinName(formScheme.getKey());
        String parentId = this.getParentId(filterDims, formScheme.getKey());
        if (parentId != null) {
            DimensionValueSet parentDim = new DimensionValueSet();
            parentDim.setValue("DATATIME", filterDims.getValue("DATATIME"));
            parentDim.setValue(mainDimName, (Object)parentId);
            BusinessKeySet businessKeySet = executeParam.getBusinessKeySet();
            Set<String> formKeys = businessKeySet.getFormKey();
            ArrayList<String> formKeysOrGroupKeys = formKeys != null && formKeys.size() > 0 ? new ArrayList<String>(formKeys) : new ArrayList<String>();
            List<UploadStateNew> uploadStates = this.queryUploadStates(formScheme.getKey(), parentDim, formKeysOrGroupKeys, formKeysOrGroupKeys);
            for (UploadStateNew uploadStateNew : uploadStates) {
                String stepReportType;
                if (uploadStateNew == null || uploadStateNew.getActionStateBean() == null) continue;
                ActionStateBean actionStateBean = uploadStateNew.getActionStateBean();
                if (!this.isDefaultWorkflow(formScheme.getKey()) || "1".equals(stepReportType = formScheme.getFlowsSetting().getStepReportType()) || !UploadState.UPLOADED.toString().equals(actionStateBean.getCode()) && !UploadState.CONFIRMED.toString().equals(actionStateBean.getCode())) continue;
                fliterkeys.add(uploadStateNew.getFormId());
            }
            if (fliterkeys.size() > 0) {
                MasterEntitySet masterEntitySet = businessKeySet.getMasterEntitySet();
                Set<String> formKey = businessKeySet.getFormKey();
                if (formKey != null) {
                    formKey.removeAll(fliterkeys);
                    businessKeySet.setFormKey(formKey);
                }
                return false;
            }
        }
        return true;
    }
}

