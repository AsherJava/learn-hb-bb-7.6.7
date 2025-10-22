/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.data.access.util.DimensionValueSetUtil
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoConsumeInfoImpl
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.bpm.de.dataflow.complete;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.Actor.Actor;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.businesskey.BusinessKeySet;
import com.jiuqi.nr.bpm.businesskey.MasterEntityInfo;
import com.jiuqi.nr.bpm.businesskey.MasterEntitySetInfo;
import com.jiuqi.nr.bpm.common.ProcessInstance;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.common.ForceTaskContext;
import com.jiuqi.nr.bpm.de.dataflow.complete.BaseExecute;
import com.jiuqi.nr.bpm.de.dataflow.complete.CompleteAbstract;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.ISendMessage;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.SendMessageTaskConfig;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.bpm.exception.NotSupportOperation;
import com.jiuqi.nr.bpm.exception.TaskNotFound;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.impl.common.BusinessKeyFormatter;
import com.jiuqi.nr.bpm.impl.upload.modeling.ProcessBuilderUtils;
import com.jiuqi.nr.bpm.service.DeployService;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.data.access.util.DimensionValueSetUtil;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoConsumeInfoImpl;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class ApplyReturnComplete
extends BaseExecute
implements CompleteAbstract {
    private static final Logger logger = LoggerFactory.getLogger(ApplyReturnComplete.class);
    private static final String ACT_DISAGREEID = "act_dis_apply_return";
    private static final String ACT_DISAGREENAME = "\u6570\u636e\u9000\u56de\u7533\u8bf7\u9a73\u56de";
    private static final String ACT_APPLY_RETURN = "act_apply_return";
    private static final String ACT_APPLY_RETURN_NAME = "\u6570\u636e\u9000\u56de\u7533\u8bf7";
    @Autowired
    private ISendMessage sendMessage;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Resource
    public IRunTimeViewController rtViewService;
    @Autowired
    private VaMessageClient messageClient;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    @Override
    public boolean IsMatch(String actionCode, String taskKey) {
        return this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey) && ACT_APPLY_RETURN.equals(actionCode);
    }

    @Override
    public CompleteMsg executeTask(ExecuteParam executeParam) {
        CompleteMsg completeMsg = new CompleteMsg();
        FormSchemeDefine formScheme = this.commonUtil.getFormScheme(executeParam.getFormSchemeKey());
        DimensionValueSet filterDims = this.buildDimensionValueSet(formScheme, executeParam.getDimSet());
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), filterDims, executeParam.getFormKey(), executeParam.getGroupKey());
        boolean isDefault = this.isDefaultWorkflow(executeParam.getFormSchemeKey());
        Optional<ProcessEngine> processEngine = this.getProcessEngine(executeParam.getFormSchemeKey());
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        try {
            if (isDefault) {
                List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
                if (tasks == null || tasks.size() <= 0) {
                    throw new TaskNotFound("no_find_node");
                }
                String userTaskId = tasks.get(0).getUserTaskId();
                boolean canExe = this.nrParameterUtils.canExecuteTask(userTaskId, executeParam.getActionId(), executeParam.getFormSchemeKey());
                if (!canExe) {
                    completeMsg.setSucceed(false);
                    completeMsg.setMsg("node_action_diff");
                    throw new NotSupportOperation(executeParam.getActionId(), userTaskId);
                }
                CompleteMsg fillTime = this.isFillTime(executeParam.getFormSchemeKey(), executeParam.getDimSet());
                if (fillTime.isSucceed()) {
                    return fillTime;
                }
                DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
                this.applyReturnTask(deployService, runTimeService, filterDims, completeMsg, executeParam, formScheme, businessKey, isDefault);
            }
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
        BusinessKeySet businessKeySet = executeParam.getBusinessKeySet();
        ArrayList<String> formKeys = new ArrayList<String>(businessKeySet.getFormKey());
        ArrayList<String> units = new ArrayList<String>();
        FormSchemeDefine formScheme = this.commonUtil.getFormScheme(executeParam.getFormSchemeKey());
        businessKeySet.getMasterEntitySet().reset();
        MasterEntitySetInfo masterEntitySetInfo = businessKeySet.getMasterEntitySetInfo();
        if (masterEntitySetInfo.next()) {
            MasterEntityInfo current = masterEntitySetInfo.getCurrent();
            String tableModelName = this.dimensionUtil.getDwTableNameByFormSchemeKey(formScheme.getKey());
            String masterEntityKey = current.getMasterEntityKey(tableModelName);
            units.add(masterEntityKey);
        }
        boolean isDefault = this.isDefaultWorkflow(executeParam.getFormSchemeKey());
        Optional<ProcessEngine> processEngine = this.getProcessEngine(executeParam.getFormSchemeKey());
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        try {
            CompleteMsg fillTime = this.isFillTime(executeParam.getFormSchemeKey(), executeParam.getDimSet());
            if (fillTime.isSucceed()) {
                return fillTime;
            }
            if (isDefault) {
                DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
                DimensionValueSet filterDims = this.dimensionUtil.buildDimension(businessKeySet);
                for (String formKey : formKeys) {
                    BusinessKey businessKey = this.businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), filterDims, formKey, formKey);
                    this.applyReturnTask(deployService, runTimeService, filterDims, completeMsg, executeParam, formScheme, businessKey, isDefault);
                }
            }
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
    public CompleteMsg batchApplyReturnExecute(BatchExecuteParam executeParam) {
        CompleteMsg completeMsg = new CompleteMsg();
        try {
            String actionId;
            boolean isDefault = this.isDefaultWorkflow(this.getFormSchemeKey(executeParam));
            if (isDefault && (actionId = executeParam.getActionId()).equals(ACT_APPLY_RETURN)) {
                return this.batchApplyReturn(executeParam);
            }
        }
        catch (Exception e) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg("apply_return_fail");
            logger.error(e.getMessage(), e);
        }
        return completeMsg;
    }

    private String getFormSchemeKey(BatchExecuteParam executeParam) {
        if (StringUtils.isNotEmpty((String)executeParam.getFormSchemeKey())) {
            return executeParam.getFormSchemeKey();
        }
        try {
            FormSchemeDefine formScheme;
            SchemePeriodLinkDefine schemePeriodLinkDefine = this.rtViewService.querySchemePeriodLinkByPeriodAndTask(executeParam.getPeriod(), executeParam.getTaskId());
            if (schemePeriodLinkDefine != null && (formScheme = this.rtViewService.getFormScheme(schemePeriodLinkDefine.getSchemeKey())) != null) {
                return formScheme.getKey();
            }
        }
        catch (Exception e) {
            LoggerFactory.getLogger(this.getClass()).error(e.getMessage(), e.getCause());
        }
        return "";
    }

    private CompleteMsg applyReturnTask(DeployService deployService, RunTimeService runTimeService, DimensionValueSet filterDims, CompleteMsg completeMsg, ExecuteParam executeParam, FormSchemeDefine formScheme, BusinessKey businessKey, boolean isDefault) {
        if (isDefault && !this.continueRetrieve(filterDims, formScheme, businessKey)) {
            return this.errorMsg(completeMsg, "upload_ing");
        }
        List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey));
        Optional<UserTask> userTask = deployService.getUserTask(tasks.get(0).getProcessDefinitionId(), tasks.get(0).getUserTaskId(), formScheme.getKey());
        if (CollectionUtils.isEmpty(tasks)) {
            return this.errorMsg(completeMsg, "no_find_node");
        }
        List<UploadRecordNew> queryUploadActionsNew = this.queryUploadActionsNew(businessKey);
        Optional<UserTask> targetTask = this.findTargetTask(false, queryUploadActionsNew, runTimeService, deployService, tasks.get(0), completeMsg, null, businessKey);
        if (!targetTask.isPresent()) {
            return this.errorMsg(completeMsg, "no_find_target_node");
        }
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("content", executeParam.getComment());
        param.put("todoType", TodoNodeType.REQUEST_REJECT.title);
        param.put("action", ACT_APPLY_RETURN_NAME);
        param.put("actionId", ACT_APPLY_RETURN);
        param.put("currentActionId", ACT_APPLY_RETURN);
        String fullname = NpContextHolder.getContext().getUser().getFullname();
        if (SendMessageTaskConfig.canSendReturnMessage()) {
            this.sendMessage.sendApplyReturnTodo(tasks.get(0), userTask, businessKey, executeParam.getFormSchemeKey(), param, fullname);
        }
        return this.succMsg(completeMsg, "apply_return_success");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean continueRetrieve(DimensionValueSet filterDims, FormSchemeDefine formScheme, BusinessKey businessKey) {
        String mainDimName = this.dimensionUtil.getDwMainDimName(formScheme.getKey());
        String parentId = this.getParentId(filterDims, formScheme.getKey());
        if (parentId == null) return true;
        DimensionValueSet parentDim = new DimensionValueSet();
        parentDim.setValue("DATATIME", filterDims.getValue("DATATIME"));
        parentDim.setValue(mainDimName, (Object)parentId);
        ActionStateBean queryUploadState = this.queryUploadState(parentDim, businessKey.getFormKey(), businessKey.getFormKey(), formScheme);
        if (queryUploadState == null) return true;
        if (this.isDefaultWorkflow(formScheme.getKey())) {
            boolean stepByStepBack = formScheme.getFlowsSetting().getStepByStepBack();
            if (!stepByStepBack || !UploadState.UPLOADED.toString().equals(queryUploadState.getCode()) && !UploadState.CONFIRMED.toString().equals(queryUploadState.getCode())) return true;
            return false;
        }
        if (null == queryUploadState) return true;
        String code = queryUploadState.getCode();
        if (UploadState.UPLOADED.toString().equals(code) || UploadState.SUBMITED.toString().equals(code)) {
            return false;
        }
        if (!UploadState.CONFIRMED.toString().equals(code)) return true;
        return false;
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
        String userTaskId = queryUploadActionsNew.get(0).getTaskId();
        if (userTaskId.equals("tsk_audit_after_confirm")) {
            userTaskId = "tsk_upload";
        }
        return deployService.getUserTask(null, userTaskId, businessKey.getFormSchemeKey());
    }

    private CompleteMsg batchApplyReturn(BatchExecuteParam executeParam) {
        CompleteMsg completeMsg = new CompleteMsg();
        List<String> formKeys = executeParam.getFormKeys();
        List<String> groupKeys = executeParam.getGroupKeys();
        List<String> units = executeParam.getUnits();
        String period = executeParam.getPeriod();
        ExecuteParam param = new ExecuteParam();
        try {
            List<String> messageIds;
            if (CollectionUtils.isEmpty(formKeys) && CollectionUtils.isEmpty(groupKeys)) {
                for (String unit : units) {
                    DimensionValueSet dimensionValueSet = new DimensionValueSet();
                    dimensionValueSet.setValue(this.dimensionUtil.getDwMainDimName(executeParam.getFormSchemeKey()), (Object)unit);
                    dimensionValueSet.setValue("DATATIME", (Object)period);
                    param.setActionId(executeParam.getActionId());
                    param.setFormSchemeKey(executeParam.getFormSchemeKey());
                    param.setDimSet(dimensionValueSet);
                    param.setFormSchemeKey(executeParam.getFormSchemeKey());
                    param.setUserId(executeParam.getUserId());
                    if (!executeParam.isAgreed()) continue;
                    this.executeRevert(param, this.businessGenerator);
                }
            } else {
                for (String unit : units) {
                    List<String> formIds = formKeys;
                    if (formIds.isEmpty()) {
                        formIds = groupKeys;
                    }
                    for (String formId : formIds) {
                        DimensionValueSet dimensionValueSet = new DimensionValueSet();
                        dimensionValueSet.setValue(this.dimensionUtil.getDwMainDimName(executeParam.getFormSchemeKey()), (Object)unit);
                        dimensionValueSet.setValue("DATATIME", (Object)period);
                        if (formId != null) {
                            dimensionValueSet.setValue("FORMID", (Object)formId);
                        }
                        param.setActionId(executeParam.getActionId());
                        param.setFormSchemeKey(executeParam.getFormSchemeKey());
                        param.setDimSet(dimensionValueSet);
                        param.setFormKey(formId);
                        param.setFormSchemeKey(executeParam.getFormSchemeKey());
                        param.setGroupKey(formId);
                        param.setUserId(executeParam.getUserId());
                        if (!executeParam.isAgreed()) continue;
                        completeMsg = this.executeRevert(param, this.businessGenerator);
                    }
                }
            }
            if (this.todoVersion.equals("1.0")) {
                messageIds = executeParam.getMessageIds();
                if (messageIds != null) {
                    for (String msgId : messageIds) {
                        this.completeMsg(msgId, executeParam.getActionId());
                    }
                }
                completeMsg.setSucceed(true);
                completeMsg.setMsg("batch_apply_return_success");
                if (!executeParam.isAgreed()) {
                    this.sendMsgVersion1_0(executeParam);
                }
            } else if (this.todoVersion.equals("2.0")) {
                messageIds = executeParam.getMessageIds();
                if (messageIds != null) {
                    for (String msgId : messageIds) {
                        this.completeReturnMsg(msgId, executeParam.getActionId());
                    }
                }
                if (executeParam.isAgreed()) {
                    this.clearAuditTodo(executeParam);
                }
                this.sendMsg(executeParam);
                completeMsg.setSucceed(true);
                completeMsg.setMsg("batch_apply_return_success");
            }
        }
        catch (Exception e) {
            completeMsg.setSucceed(false);
            completeMsg.setMsg("batch_apply_return_fail");
        }
        return completeMsg;
    }

    private void sendMsgVersion1_0(BatchExecuteParam executeParam) {
        Optional<ProcessEngine> processEngine = this.getProcessEngine(executeParam.getFormSchemeKey());
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("DATATIME", (Object)executeParam.getPeriod());
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(executeParam.getFormSchemeKey());
        dim.setValue(dwMainDimName, (Object)executeParam.getUnits().get(0));
        WorkFlowType startType = this.commonUtil.workflowType(executeParam.getFormSchemeKey());
        String key = null;
        key = executeParam.getFormKeys() != null && executeParam.getFormKeys().size() > 0 ? executeParam.getFormKeys().get(0) : executeParam.getFormKey();
        String formOrGroupKey = startType == WorkFlowType.FORM ? key : (startType == WorkFlowType.GROUP ? key : this.nrParameterUtils.getDefaultFormId(executeParam.getFormSchemeKey()));
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), dim, formOrGroupKey, formOrGroupKey);
        List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
        List<UploadRecordNew> queryUploadActionsNew = this.queryUploadActionsNew(businessKey);
        Optional<UserTask> targetTask = this.findTargetTask(false, queryUploadActionsNew, runTimeService, deployService, tasks.get(0), new CompleteMsg(), null, businessKey);
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("content", executeParam.getComment());
        param.put("action", ACT_DISAGREENAME);
        param.put("actionId", ACT_DISAGREEID);
        param.put("currentActionId", ACT_DISAGREEID);
        String fullname = NpContextHolder.getContext().getUser().getFullname();
        this.sendMessage.sendApplyReturnMessage(tasks.get(0), targetTask, businessKey, executeParam.getFormSchemeKey(), param, fullname);
    }

    private void sendMsg(BatchExecuteParam executeParam) {
        Optional<ProcessEngine> processEngine = this.getProcessEngine(executeParam.getFormSchemeKey());
        RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
        DimensionValueSet dim = new DimensionValueSet();
        dim.setValue("DATATIME", (Object)executeParam.getPeriod());
        String dwMainDimName = this.dimensionUtil.getDwMainDimName(executeParam.getFormSchemeKey());
        dim.setValue(dwMainDimName, (Object)executeParam.getUnits().get(0));
        WorkFlowType startType = this.commonUtil.workflowType(executeParam.getFormSchemeKey());
        String formOrGroupKey = startType == WorkFlowType.FORM ? executeParam.getFormKeys().get(0) : (startType == WorkFlowType.GROUP ? executeParam.getGroupKeys().get(0) : this.nrParameterUtils.getDefaultFormId(executeParam.getFormSchemeKey()));
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), dim, formOrGroupKey, formOrGroupKey);
        List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
        HashMap<String, String> param = new HashMap<String, String>();
        param.put("content", executeParam.getComment());
        param.put("todoType", TodoNodeType.REQUEST_REJECT.title);
        param.put("action", ACT_DISAGREENAME);
        param.put("actionId", ACT_DISAGREEID);
        param.put("currentActionId", ACT_DISAGREEID);
        String fullname = NpContextHolder.getContext().getUser().getFullname();
        if (executeParam.isAgreed()) {
            Optional<UserTask> userTask = deployService.getUserTask(tasks.get(0).getProcessDefinitionId(), tasks.get(0).getUserTaskId(), executeParam.getFormSchemeKey());
            if (SendMessageTaskConfig.canSendMessage()) {
                this.sendMessage.sendApplyReturnTodoDisAgreed(tasks.get(0), userTask, businessKey, executeParam.getFormSchemeKey(), param, fullname);
            }
        } else {
            List<UploadRecordNew> queryUploadActionsNew = this.queryUploadActionsNew(businessKey);
            Optional<UserTask> targetTask = this.findTargetTask(false, queryUploadActionsNew, runTimeService, deployService, tasks.get(0), new CompleteMsg(), null, businessKey);
            this.sendMessage.sendApplyReturnMessage(tasks.get(0), targetTask, businessKey, executeParam.getFormSchemeKey(), param, fullname);
        }
    }

    public void sendMessage(String content, List<String> participants, Map<String, String> param) {
        String actionName = param.get("action");
        VaMessageSendDTO dto = new VaMessageSendDTO();
        dto.setGrouptype("\u6d88\u606f");
        dto.setMsgtype(actionName + "\u901a\u77e5");
        dto.setReceiveUserIds(participants);
        dto.setMsgChannel(VaMessageOption.MsgChannel.PC);
        dto.setContent(content);
        dto.setTitle(actionName + "\u901a\u77e5");
        this.messageClient.addMsg(dto);
    }

    public CompleteMsg executeRevert(ExecuteParam executeParam, BusinessGenerator businessGenerator) {
        CompleteMsg completeMsg = new CompleteMsg();
        DimensionValueSet dimset = executeParam.getDimSet();
        FormSchemeDefine schemeDefine = this.rtViewService.getFormScheme(executeParam.getFormSchemeKey());
        List<DimensionValueSet> reportDimension = this.dimensionUtil.buildDimensionValueSets(executeParam.getDimSet(), schemeDefine.getKey());
        if (reportDimension != null && reportDimension.size() > 0) {
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.mergeDimensionValueSet(reportDimension);
            BusinessKey businessKey = businessGenerator.buildBusinessKey(executeParam.getFormSchemeKey(), dimensionValueSet, executeParam.getFormKey(), executeParam.getGroupKey());
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(executeParam.getFormSchemeKey());
            boolean isDefault = this.isDefaultWorkflow(executeParam.getFormSchemeKey());
            Optional<ProcessEngine> processEngine = this.getProcessEngine(executeParam.getFormSchemeKey());
            RunTimeService runTimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
            if (isDefault) {
                DeployService deployService = processEngine.map(engine -> engine.getDeployService()).orElse(null);
                this.retrieveDefaultTask(runTimeService, dimensionValueSet, completeMsg, formScheme, businessKey);
            }
        }
        return completeMsg;
    }

    private CompleteMsg retrieveDefaultTask(RunTimeService runTimeService, DimensionValueSet filterDims, CompleteMsg completeMsg, FormSchemeDefine formScheme, BusinessKey businessKey) {
        if (!this.continueRetrieve(filterDims, formScheme, businessKey)) {
            return this.errorMsg(completeMsg, "upload_ing");
        }
        List<Task> tasks = runTimeService.queryTaskByBusinessKey(BusinessKeyFormatter.formatToString(businessKey), false);
        if (CollectionUtils.isEmpty(tasks)) {
            return this.errorMsg(completeMsg, "no_find_node");
        }
        Actor candicateActor = Actor.fromNpContext();
        runTimeService.completeProcessTask(businessKey, tasks.get(0).getId(), candicateActor.getIdentityId(), "act_reject", null, new ForceTaskContext());
        return this.succMsg(completeMsg, "apply_return_success");
    }

    @Override
    public void completeReturnMsg(String msgid, String actionCode) {
        if (SendMessageTaskConfig.canSendReturnMessage()) {
            TodoConsumeInfoImpl todoConsumeInfoImpl = new TodoConsumeInfoImpl();
            todoConsumeInfoImpl.setBusinessKey(msgid);
            todoConsumeInfoImpl.setWorkflowInstance(msgid);
            todoConsumeInfoImpl.setWorkflowNodeTask(TodoNodeType.REQUEST_REJECT.title);
            this.todoManipulationService.consumeTodo((TodoConsumeInfo)todoConsumeInfoImpl);
        }
    }

    private void clearAuditTodo(BatchExecuteParam executeParam) {
        if (SendMessageTaskConfig.canSendReturnMessage()) {
            String formSchemeKey = executeParam.getFormSchemeKey();
            String period = executeParam.getPeriod();
            String unit = executeParam.getUnits() != null && !executeParam.getUnits().isEmpty() ? executeParam.getUnits().get(0) : "";
            String formKey = executeParam.getFormKeys() != null && !executeParam.getFormKeys().isEmpty() ? executeParam.getFormKeys().get(0) : "";
            String groupKey = executeParam.getGroupKeys() != null && !executeParam.getGroupKeys().isEmpty() ? executeParam.getGroupKeys().get(0) : "";
            DimensionValueSet dimensionValueSet = executeParam.getDimSet();
            Object adjustObj = dimensionValueSet != null ? dimensionValueSet.getValue("ADJUST") : null;
            String adjust = adjustObj == null ? "" : adjustObj.toString();
            WorkFlowType workflowType = this.workflow.queryStartType(formSchemeKey);
            String workflowNodeTask = "tsk_audit";
            FormSchemeDefine formScheme = this.rtViewService.getFormScheme(executeParam.getFormSchemeKey());
            String corporateValue = this.workFlowDimensionBuilder.getCorporateValue(formScheme.getTaskKey(), dimensionValueSet);
            String msgId = this.workflow.getMessageId(formSchemeKey, period, unit, adjust, formKey, groupKey, workflowType, workflowNodeTask, corporateValue);
            TodoConsumeInfoImpl todoConsumeInfoImpl = new TodoConsumeInfoImpl();
            todoConsumeInfoImpl.setBusinessKey(msgId);
            todoConsumeInfoImpl.setWorkflowInstance(ProcessBuilderUtils.produceUUIDKey(msgId));
            todoConsumeInfoImpl.setWorkflowNodeTask(workflowNodeTask);
            this.todoManipulationService.consumeTodo((TodoConsumeInfo)todoConsumeInfoImpl);
        }
    }
}

