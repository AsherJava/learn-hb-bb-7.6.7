/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 */
package com.jiuqi.nr.bpm.de.dataflow.complete;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySet;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.common.ForceTaskContext;
import com.jiuqi.nr.bpm.de.dataflow.complete.BaseExecute;
import com.jiuqi.nr.bpm.de.dataflow.complete.CompleteAbstract;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.ISendMessage;
import com.jiuqi.nr.bpm.de.dataflow.service.IBpmWorkflowHander;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.bpm.upload.utils.ActionAndStateUtil;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RetrieveComplete
extends BaseExecute
implements CompleteAbstract {
    private static final Logger logger = LoggerFactory.getLogger(RetrieveComplete.class);
    @Autowired
    private ISendMessage sendMessage;
    @Autowired(required=false)
    private List<IBpmWorkflowHander> bpmWorkflowHander;
    @Autowired
    private ActionAndStateUtil actionAndStateUtil;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private ActionMethod actionMethod;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    @Override
    public boolean IsMatch(String actionCode, String taskKey) {
        return this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey) && "act_retrieve".equals(actionCode);
    }

    @Override
    public CompleteMsg executeTask(ExecuteParam executeParam) {
        CompleteMsg completeMsg = new CompleteMsg();
        FormSchemeDefine formScheme = this.getFormScheme(executeParam.getFormSchemeKey());
        DimensionValueSet filterDims = this.buildDimensionValueSet(formScheme, executeParam.getDimSet());
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), filterDims, executeParam.getFormKey(), executeParam.getGroupKey());
        boolean isDefault = this.isDefaultWorkflow(executeParam.getFormSchemeKey());
        Optional<ProcessEngine> processEngine = this.getProcessEngine(executeParam.getFormSchemeKey());
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        Optional<ProcessInstance> instance = runTimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
        try {
            CompleteMsg fillTime = this.isFillTime(executeParam.getFormSchemeKey(), executeParam.getDimSet());
            if (fillTime.isSucceed()) {
                return fillTime;
            }
            ForceTaskContext forceTaskContext = new ForceTaskContext();
            if (executeParam.isForceUpload()) {
                forceTaskContext.put(this.getForceKey(), executeParam.isForceUpload());
            }
            if (isDefault) {
                this.retrieveDefaultTask(deployService, runTimeService, filterDims, completeMsg, executeParam, formScheme, businessKey, executeParam.getTaskContext());
            } else {
                completeMsg = this.retrieveCustomTask(formScheme, filterDims, executeParam, businessKey, runTimeService, instance, processEngine, completeMsg, executeParam.getTaskContext());
            }
        }
        catch (UserActionException e1) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e1.getMessage());
            completeMsg.setMsg(e1.getLocalizedMessage());
            logger.error(e1.getMessage(), e1);
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
        CompleteMsg completeMsg = new CompleteMsg();
        try {
            String actionId = executeParam.getActionId();
            if (actionId.equals("act_retrieve")) {
                return this.batchRevert(executeParam);
            }
        }
        catch (UserActionException e1) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e1.getMessage());
            logger.error(e1.getMessage(), e1);
        }
        catch (Exception e) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg("batch_upload_fail");
            logger.error(e.getMessage(), e);
        }
        return completeMsg;
    }

    private CompleteMsg retrieveDefaultTask(DeployService deployService, RunTimeService runTimeService, DimensionValueSet filterDims, CompleteMsg completeMsg, ExecuteParam executeParam, FormSchemeDefine formScheme, BusinessKey businessKey, TaskContext context) {
        try {
            List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
            if (CollectionUtils.isEmpty(tasks)) {
                return this.errorMsg(completeMsg, "no_find_node");
            }
            boolean enableStep = true;
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            if (flowsSetting != null) {
                boolean stepByStepReport = flowsSetting.getStepByStepReport();
                boolean stepByStepBack = flowsSetting.getStepByStepBack();
                enableStep = stepByStepReport || stepByStepBack;
            }
            Map<Boolean, String> continueRetrieve = this.continueRetrieve(filterDims, formScheme, businessKey, enableStep);
            for (Map.Entry<Boolean, String> retrieve : continueRetrieve.entrySet()) {
                Boolean flag = retrieve.getKey();
                String msg = retrieve.getValue();
                if (flag.booleanValue()) continue;
                return this.errorMsg(completeMsg, "upload_ing");
            }
            Optional<UserTask> userTask = deployService.getUserTask(tasks.get(0).getProcessDefinitionId(), tasks.get(0).getUserTaskId(), formScheme.getKey());
            this.clearMessage1(formScheme.getKey(), filterDims, tasks.get(0).getId(), businessKey.getFormKey(), executeParam.getActionId(), executeParam.getNodeId());
            List<UploadRecordNew> queryUploadActionsNew = this.queryUploadActionsNew(businessKey);
            Optional<UserTask> targetTask = this.findTargetTask(false, queryUploadActionsNew, runTimeService, deployService, tasks.get(0), completeMsg, null, businessKey);
            if (!targetTask.isPresent()) {
                return this.errorMsg(completeMsg, "no_find_target_node");
            }
            String preEvent = this.getPrevent(false, queryUploadActionsNew, formScheme, tasks.get(0).getUserTaskId(), targetTask.get().getId());
            runTimeService.retrieveTask(tasks.get(0), targetTask.get(), preEvent, businessKey, context);
            this.sendRetrieveMsg(executeParam.getFormSchemeKey(), executeParam.getDimSet(), executeParam.getActionId(), businessKey, tasks.get(0), runTimeService, userTask);
            WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(formScheme.getKey());
            if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
                HashSet<BusinessKey> businessKeys = new HashSet<BusinessKey>();
                businessKeys.add(businessKey);
                this.nrParameterUtils.updateUnitState(businessKeys, workflowStartType, null);
            }
            return this.succMsg(completeMsg, "fetch_success");
        }
        catch (UserActionException e1) {
            return this.errorMsg(completeMsg, e1.getMessage());
        }
        catch (Exception e) {
            return this.errorMsg(completeMsg, "fetch_fail");
        }
    }

    private CompleteMsg retrieveCustomTask(FormSchemeDefine formScheme, DimensionValueSet filterDims, ExecuteParam executeParam, BusinessKey businessKey, RunTimeService runTimeService, Optional<ProcessInstance> instance, Optional<ProcessEngine> processEngine, CompleteMsg completeMsg, TaskContext context) {
        try {
            DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
            List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
            if (CollectionUtils.isEmpty(tasks)) {
                return this.errorMsg(completeMsg, "no_find_node");
            }
            List<UploadRecordNew> queryUploadActionsNew = this.queryUploadActionsNew(businessKey);
            boolean enableStep = true;
            Optional<UserTask> targetTask = this.findTargetTask(true, queryUploadActionsNew, runTimeService, deployService, tasks.get(0), completeMsg, instance.get(), businessKey);
            if (!targetTask.isPresent()) {
                return this.errorMsg(completeMsg, "no_find_target_node");
            }
            List<ActionParam> action = this.actionMethod.getCustomWorkflowAction(businessKey.getFormSchemeKey(), targetTask.get().getId());
            if (action != null && action.size() > 0) {
                for (ActionParam actionParam : action) {
                    boolean bl = actionParam.isStepByStep();
                    boolean stepByStepBack = actionParam.isStepByStepBack();
                    enableStep = bl || stepByStepBack;
                }
            }
            Map<Boolean, String> continueRetrieve = this.continueRetrieve(filterDims, formScheme, businessKey, enableStep);
            for (Map.Entry<Boolean, String> entry : continueRetrieve.entrySet()) {
                Boolean flag = entry.getKey();
                String msg = entry.getValue();
                if (flag.booleanValue()) continue;
                completeMsg.setSucceed(false);
                completeMsg.setMsg("upload_ing");
                return this.errorMsg(completeMsg, "upload_ing");
            }
            Optional<UserTask> optional = deployService.getUserTask(tasks.get(0).getProcessDefinitionId(), tasks.get(0).getUserTaskId(), formScheme.getKey());
            this.clearMessage1(formScheme.getKey(), filterDims, tasks.get(0).getId(), businessKey.getFormKey(), executeParam.getActionId(), (executeParam.getNodeId() == null || executeParam.getNodeId().isEmpty()) && optional.isPresent() ? optional.get().getId() : executeParam.getNodeId());
            String string = this.getPrevent(true, queryUploadActionsNew, formScheme, tasks.get(0).getUserTaskId(), targetTask.get().getId());
            runTimeService.retrieveTask(tasks.get(0), targetTask.get(), string, businessKey, context);
            this.succMsg(completeMsg, "fetch_success");
            this.sendRetrieveMsg(executeParam.getFormSchemeKey(), executeParam.getDimSet(), executeParam.getActionId(), businessKey, tasks.get(0), runTimeService, optional);
            WorkFlowType workflowStartType = this.nrParameterUtils.getWorkflowStartType(formScheme.getKey());
            if (WorkFlowType.FORM.equals((Object)workflowStartType) || WorkFlowType.GROUP.equals((Object)workflowStartType)) {
                HashSet<BusinessKey> businessKeys = new HashSet<BusinessKey>();
                businessKeys.add(businessKey);
                this.nrParameterUtils.updateUnitState(businessKeys, workflowStartType, null);
            }
        }
        catch (UserActionException e1) {
            logger.error(e1.getMessage(), e1);
            this.errorMsg(completeMsg, e1.getMessage());
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            this.errorMsg(completeMsg, "fetch_fail");
        }
        return completeMsg;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Map<Boolean, String> continueRetrieve(DimensionValueSet filterDims, FormSchemeDefine formScheme, BusinessKey businessKey, boolean enableStep) {
        HashMap<Boolean, String> actionMap;
        block14: {
            ActionStateBean queryUploadState;
            block13: {
                actionMap = new HashMap<Boolean, String>();
                boolean ignoreCheck = this.ignoreCheck(filterDims, formScheme, businessKey);
                if (ignoreCheck) {
                    actionMap.put(true, "");
                    return actionMap;
                }
                if (!enableStep) {
                    actionMap.put(true, "");
                    return actionMap;
                }
                String mainDimName = this.getMinName(formScheme.getKey());
                String period = filterDims.getValue("DATATIME").toString();
                String parentId = this.deEntityHelper.getParentId(filterDims.getValue(mainDimName).toString(), formScheme.getKey(), period);
                if (parentId == null) {
                    actionMap.put(true, "");
                    return actionMap;
                }
                DimensionValueSet parentDim = new DimensionValueSet();
                parentDim.setValue("DATATIME", filterDims.getValue("DATATIME"));
                parentDim.setValue(mainDimName, (Object)parentId);
                queryUploadState = this.queryUploadState(parentDim, businessKey.getFormKey(), formScheme);
                if (queryUploadState == null) {
                    actionMap.put(true, "");
                    return actionMap;
                }
                if (!this.isDefaultWorkflow(formScheme.getKey())) break block13;
                String stepReportType = formScheme.getFlowsSetting().getStepReportType();
                if ("1".equals(stepReportType) && (UploadState.UPLOADED.toString().equals(queryUploadState.getCode()) || UploadState.CONFIRMED.toString().equals(queryUploadState.getCode()))) {
                    String stateCode;
                    if (UploadState.UPLOADED.toString().equals(queryUploadState.getCode())) {
                        stateCode = this.actionAndStateUtil.getStateNameByStateCode(formScheme.getKey(), UploadState.UPLOADED.toString());
                        actionMap.put(false, stateCode);
                    }
                    if (UploadState.CONFIRMED.toString().equals(queryUploadState.getCode())) {
                        stateCode = this.actionAndStateUtil.getStateNameByStateCode(formScheme.getKey(), UploadState.CONFIRMED.toString());
                        actionMap.put(false, stateCode);
                    }
                }
                break block14;
            }
            if (null != queryUploadState) {
                String code = queryUploadState.getCode();
                if (UploadState.UPLOADED.toString().equals(code) || UploadState.SUBMITED.toString().equals(code)) {
                    String stateCode;
                    if (UploadState.UPLOADED.toString().equals(queryUploadState.getCode())) {
                        stateCode = this.actionAndStateUtil.getStateNameByStateCode(formScheme.getKey(), UploadState.UPLOADED.toString());
                        actionMap.put(false, stateCode);
                    }
                    if (UploadState.SUBMITED.toString().equals(queryUploadState.getCode())) {
                        stateCode = this.actionAndStateUtil.getStateNameByStateCode(formScheme.getKey(), UploadState.SUBMITED.toString());
                        actionMap.put(false, stateCode);
                    }
                } else if (UploadState.CONFIRMED.toString().equals(code)) {
                    String stateCode = this.actionAndStateUtil.getStateNameByStateCode(formScheme.getKey(), UploadState.CONFIRMED.toString());
                    actionMap.put(false, stateCode);
                }
            }
        }
        actionMap.put(true, "");
        return actionMap;
    }

    private Optional<UserTask> findTargetTask(boolean isWorkFlow, List<UploadRecordNew> queryUploadActionsNew, RunTimeService runTimeService, DeployService deployService, Task currentTask, CompleteMsg completeMsg, ProcessInstance instance, BusinessKey businessKey) {
        if (queryUploadActionsNew.isEmpty()) {
            this.errorMsg(completeMsg, "no_find_process");
            return Optional.empty();
        }
        if (isWorkFlow) {
            String userTaskId = this.queryRevertTaskId(queryUploadActionsNew, currentTask, businessKey);
            return Optional.of(runTimeService.getRetrievableTask(instance.getId(), Actor.fromNpContext(), userTaskId));
        }
        String userTaskId = null;
        if (currentTask.getUserTaskId().equals("tsk_audit")) {
            userTaskId = "tsk_upload";
        } else if (currentTask.getUserTaskId().equals("tsk_upload")) {
            userTaskId = "tsk_submit";
        }
        return deployService.getUserTask(null, userTaskId, businessKey.getFormSchemeKey());
    }

    private CompleteMsg batchRevert(BatchExecuteParam executeParam) {
        CompleteMsg completeMsg = new CompleteMsg();
        ExecuteParam param = new ExecuteParam();
        BusinessKeySet businessKeySet = executeParam.getBusinessKeySet();
        Set<String> formKeys = businessKeySet.getFormKey();
        MasterEntitySet entitySet = businessKeySet.getMasterEntitySet();
        try {
            if (CollectionUtils.isEmpty(formKeys)) {
                while (entitySet.next()) {
                    MasterEntityInfo entity = entitySet.getCurrent();
                    DimensionValueSet dim = this.dimensionUtil.buildUploadMasterKey(entity, null, businessKeySet.getPeriod());
                    param.setActionId(executeParam.getActionId());
                    param.setFormSchemeKey(executeParam.getFormSchemeKey());
                    param.setDimSet(dim);
                    param.setFormSchemeKey(executeParam.getFormSchemeKey());
                    param.setUserId(executeParam.getUserId());
                    this.executeRevert(param, this.businessGenerator, executeParam.getTaskContext());
                }
            } else {
                while (entitySet.next()) {
                    MasterEntityInfo entity = entitySet.getCurrent();
                    for (String formKey : formKeys) {
                        DimensionValueSet dim = this.dimensionUtil.buildUploadMasterKey(entity, formKey, businessKeySet.getPeriod());
                        param.setActionId(executeParam.getActionId());
                        param.setFormSchemeKey(executeParam.getFormSchemeKey());
                        param.setDimSet(dim);
                        param.setFormKey(formKey);
                        param.setFormSchemeKey(executeParam.getFormSchemeKey());
                        param.setGroupKey(formKey);
                        param.setUserId(executeParam.getUserId());
                        param.setTaskId(executeParam.getTaskId());
                        param.setNodeId(executeParam.getNodeId());
                        completeMsg = this.executeRevert(param, this.businessGenerator, executeParam.getTaskContext());
                    }
                }
            }
        }
        catch (UserActionException e1) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e1.getMessage());
        }
        catch (Exception e) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg("batch_fetch_fail");
        }
        return completeMsg;
    }

    public CompleteMsg executeRevert(ExecuteParam executeParam, BusinessGenerator businessGenerator, TaskContext context) {
        CompleteMsg completeMsg = new CompleteMsg();
        DimensionValueSet dimset = executeParam.getDimSet();
        BusinessKey businessKey = businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), dimset, executeParam.getFormKey(), executeParam.getGroupKey());
        FormSchemeDefine formScheme = this.getFormScheme(executeParam.getFormSchemeKey());
        boolean isDefault = this.isDefaultWorkflow(executeParam.getFormSchemeKey());
        Optional<ProcessEngine> processEngine = this.getProcessEngine(executeParam.getFormSchemeKey());
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        if (isDefault) {
            DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
            this.retrieveDefaultTask(deployService, runTimeService, dimset, completeMsg, executeParam, formScheme, businessKey, context);
        } else {
            Optional<ProcessInstance> instance = runTimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
            this.retrieveCustomTask(formScheme, dimset, executeParam, businessKey, runTimeService, instance, processEngine, completeMsg, context);
        }
        return completeMsg;
    }

    private void sendRetrieveMsg(String fromSchemeKey, DimensionValueSet dim, String actionCode, BusinessKey businessKey, Task task, RunTimeService runTimeService, Optional<UserTask> userTask) {
        List<Task> queryTask;
        if ("act_retrieve".equals(actionCode) && (queryTask = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false)).size() > 0) {
            String fullname = NpContextHolder.getContext().getUser().getFullname();
            this.sendMessage.sendRetrieveMessage(task, userTask, businessKey, dim, fromSchemeKey, queryTask.get(0), fullname);
        }
    }

    @Override
    public CompleteMsg executeRevert(BusinessKey businessKey) {
        CompleteMsg completeMsg = new CompleteMsg();
        try {
            FormSchemeDefine formScheme = this.getFormScheme(businessKey.getFormSchemeKey());
            boolean isDefault = this.isDefaultWorkflow(businessKey.getFormSchemeKey());
            Optional<ProcessEngine> processEngine = this.getProcessEngine(businessKey.getFormSchemeKey());
            RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            String preEvent = null;
            List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
            if (CollectionUtils.isEmpty(tasks)) {
                return this.errorMsg(completeMsg, "no_find_node");
            }
            List<UploadRecordNew> queryUploadActionsNew = this.queryUploadActionsNew(businessKey);
            Optional<Object> targetTask = Optional.empty();
            if (isDefault) {
                DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
                targetTask = this.findTargetTask(false, queryUploadActionsNew, runTimeService, deployService, tasks.get(0), completeMsg, null, businessKey);
                if (!targetTask.isPresent()) {
                    return this.errorMsg(completeMsg, "no_find_target_node");
                }
                preEvent = this.getPrevent(false, queryUploadActionsNew, formScheme, tasks.get(0).getUserTaskId(), ((UserTask)targetTask.get()).getId());
            } else {
                Optional<ProcessInstance> instance = runTimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
                targetTask = this.findTargetTask(true, queryUploadActionsNew, runTimeService, null, tasks.get(0), completeMsg, instance.get(), businessKey);
                if (!targetTask.isPresent()) {
                    return this.errorMsg(completeMsg, "no_find_target_node");
                }
                preEvent = this.getPrevent(true, queryUploadActionsNew, formScheme, tasks.get(0).getUserTaskId(), ((UserTask)targetTask.get()).getId());
            }
            runTimeService.retrieveTask(tasks.get(0), (UserTask)targetTask.get(), preEvent, businessKey);
            completeMsg.setSucceed(true);
            completeMsg.setMsg("fetch_success");
        }
        catch (UserActionException e1) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(e1.getMessage());
        }
        catch (Exception e) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg("fetch_fail");
        }
        return completeMsg;
    }

    @Override
    public CompleteMsg executeRevert(BusinessKey businessKey, TaskContext context) {
        CompleteMsg completeMsg = new CompleteMsg();
        try {
            FormSchemeDefine formScheme = this.getFormScheme(businessKey.getFormSchemeKey());
            boolean isDefault = this.isDefaultWorkflow(businessKey.getFormSchemeKey());
            Optional<ProcessEngine> processEngine = this.getProcessEngine(businessKey.getFormSchemeKey());
            RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            String preEvent = null;
            List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
            if (CollectionUtils.isEmpty(tasks)) {
                return this.errorMsg(completeMsg, "no_find_node");
            }
            List<UploadRecordNew> queryUploadActionsNew = this.queryUploadActionsNew(businessKey);
            Optional<Object> targetTask = Optional.empty();
            if (isDefault) {
                DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
                targetTask = this.findTargetTask(false, queryUploadActionsNew, runTimeService, deployService, tasks.get(0), completeMsg, null, businessKey);
                if (!targetTask.isPresent()) {
                    return this.errorMsg(completeMsg, "no_find_target_node");
                }
                preEvent = this.getPrevent(false, queryUploadActionsNew, formScheme, tasks.get(0).getUserTaskId(), ((UserTask)targetTask.get()).getId());
            } else {
                Optional<ProcessInstance> instance = runTimeService.queryInstanceByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
                targetTask = this.findTargetTask(true, queryUploadActionsNew, runTimeService, null, tasks.get(0), completeMsg, instance.get(), businessKey);
                if (!targetTask.isPresent()) {
                    return this.errorMsg(completeMsg, "no_find_target_node");
                }
                preEvent = this.getPrevent(true, queryUploadActionsNew, formScheme, tasks.get(0).getUserTaskId(), ((UserTask)targetTask.get()).getId());
            }
            runTimeService.retrieveTask(tasks.get(0), (UserTask)targetTask.get(), preEvent, businessKey, context);
            completeMsg.setSucceed(true);
            completeMsg.setMsg("fetch_success");
        }
        catch (UserActionException ae) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg(ae.getMessage());
        }
        catch (Exception e) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg("fetch_fail");
        }
        return completeMsg;
    }

    private boolean ignoreCheck(DimensionValueSet filterDims, FormSchemeDefine formScheme, BusinessKey businessKey) {
        boolean ignoreChecked = false;
        if (this.bpmWorkflowHander != null && this.bpmWorkflowHander.size() > 0) {
            for (IBpmWorkflowHander workflowHandler : this.bpmWorkflowHander) {
                ignoreChecked = workflowHandler.isApproval(filterDims, formScheme, businessKey);
            }
        }
        return ignoreChecked;
    }
}

