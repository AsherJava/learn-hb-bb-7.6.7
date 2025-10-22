/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.RoleDTO
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.message.TodoActionEvent
 *  com.jiuqi.np.message.TodoBatchActionEvent
 *  com.jiuqi.np.message.TodoCompleteEvent
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DimensionValueSetUtil
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.FlowsType
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.service.IFormSchemeService
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.bpm.de.dataflow.service.impl;

import com.jiuqi.np.authz2.RoleDTO;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.message.TodoActionEvent;
import com.jiuqi.np.message.TodoBatchActionEvent;
import com.jiuqi.np.message.TodoCompleteEvent;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.TaskContext;
import com.jiuqi.nr.bpm.common.UploadRecordNew;
import com.jiuqi.nr.bpm.common.UploadStateNew;
import com.jiuqi.nr.bpm.custom.bean.WorkFlowNodeSet;
import com.jiuqi.nr.bpm.dataflow.service.IDataentryQueryStateService;
import com.jiuqi.nr.bpm.dataflow.service.IReadOnlyService;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.DeWorkflowBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignBootModeParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignModeData;
import com.jiuqi.nr.bpm.de.dataflow.bean.UploadStateBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.common.CustomDesignWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.complete.AutoComplete;
import com.jiuqi.nr.bpm.de.dataflow.complete.CompleteAbstract;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.QueryParticipants;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.UploadStateUtil;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchWorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.util.BusinessGenerator;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.de.dataflow.util.DimensionUtil;
import com.jiuqi.nr.bpm.exception.UserActionException;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.service.IBatchQueryUploadStateService;
import com.jiuqi.nr.bpm.setting.bean.WorkflowSettingDefine;
import com.jiuqi.nr.bpm.setting.service.WorkflowSettingService;
import com.jiuqi.nr.bpm.setting.utils.SettingUtil;
import com.jiuqi.nr.bpm.upload.UploadState;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DimensionValueSetUtil;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.FlowsType;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.service.IFormSchemeService;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class DataentryFlowService
implements IDataentryFlowService {
    private static final Logger logger = LoggerFactory.getLogger(DataentryFlowService.class);
    @Autowired
    private UploadStateUtil uploadStateUtil;
    @Autowired
    private CommonUtil commonUtil;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    AutoComplete autoExecute;
    @Autowired(required=false)
    Map<String, CompleteAbstract> completeTask;
    @Autowired
    QueryParticipants queryParticipants;
    @Autowired
    private DimensionUtil dimensionUtil;
    @Autowired
    private BusinessGenerator businessGenerator;
    @Autowired
    private IDataentryQueryStateService dataentryQueryStateService;
    @Autowired
    private IReadOnlyService readOnlyService;
    @Autowired
    private IFormSchemeService formSchemeService;
    @Autowired(required=false)
    private Map<String, CustomDesignWorkflow> customDesignProvider;
    @Autowired
    private WorkflowSettingService flowService;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IBatchQueryUploadStateService batchQueryUploadStateService;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private RoleService roleService;
    @Autowired
    private IRunTimeViewController runTimeViewController;

    @Override
    public WorkflowConfig queryWorkflowConfig(String formSchemeKey) {
        WorkflowConfig workflowConfig = new WorkflowConfig();
        try {
            FormSchemeDefine formScheme = this.commonUtil.getFormScheme(formSchemeKey);
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            FlowsType flowsType = flowsSetting.getFlowsType();
            WorkFlowType wordFlowType = flowsSetting.getWordFlowType();
            if (FlowsType.DEFAULT.equals((Object)flowsType)) {
                workflowConfig.setWorkFlowType(wordFlowType);
                workflowConfig.setFlowStarted(true);
            } else if (FlowsType.NOSTARTUP.equals((Object)flowsType)) {
                workflowConfig.setFlowStarted(false);
            } else if (FlowsType.WORKFLOW.equals((Object)flowsType)) {
                workflowConfig.setWorkFlowType(wordFlowType);
                workflowConfig.setFlowStarted(true);
            }
            List reportEntityKeys = this.formSchemeService.getReportEntityKeys(formSchemeKey);
            workflowConfig.setWorkflowEntities(reportEntityKeys);
            workflowConfig.setCalculateBefor(flowsSetting.getReportBeforeOperation());
            workflowConfig.setCalculateFormulaSchemeKey(this.getCheckFormulaSchemeKey(formSchemeKey, flowsSetting.getReportBeforeOperationValue()));
            workflowConfig.setCheckBefor(flowsSetting.getReportBeforeAudit());
            String checkFormulaSchemeKey = this.getCheckFormulaSchemeKey(formSchemeKey, flowsSetting.getReportBeforeAuditValue());
            workflowConfig.setCheckFormulaSchemeKey(checkFormulaSchemeKey);
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return workflowConfig;
    }

    public String getCheckFormulaSchemeKey(String formSchemeKey, String reportBeforeAuditValue) {
        List<Object> formulaByFormschemeKey = new ArrayList();
        List allRPTFormulaSchemeDefines = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
        if (allRPTFormulaSchemeDefines != null && allRPTFormulaSchemeDefines.size() > 0) {
            formulaByFormschemeKey = allRPTFormulaSchemeDefines.stream().map((? super T e) -> e.getKey()).collect(Collectors.toList());
        }
        ArrayList allFormula = new ArrayList();
        if (StringUtils.isNotEmpty((String)reportBeforeAuditValue)) {
            String[] split = reportBeforeAuditValue.split(";");
            Collections.addAll(allFormula, split);
        }
        allFormula.retainAll(formulaByFormschemeKey);
        return allFormula.stream().collect(Collectors.joining(";"));
    }

    @Override
    public List<WorkflowDataInfo> queryWorkflowDataInfo(WorkflowDataBean workflowData) {
        return this.dataentryQueryStateService.queryWorkflowDataInfo(workflowData);
    }

    @Override
    public Map<DimensionValueSet, LinkedHashMap<String, List<WorkflowDataInfo>>> batchWorkflowDataInfo(BatchWorkflowDataBean workflowData) {
        return this.dataentryQueryStateService.batchWorkflowDataInfo(workflowData);
    }

    @Override
    public ActionStateBean queryReportState(DataEntryParam dataEntryParam) {
        return this.dataentryQueryStateService.queryResourceState(dataEntryParam);
    }

    @Override
    public ActionStateBean queryUnitState(DataEntryParam dataEntryParam) {
        return this.dataentryQueryStateService.queryUnitState(dataEntryParam);
    }

    @Override
    public Map<DimensionValueSet, Map<String, Boolean>> batchReadOnlyMap(DataEntryParam dataEntryParam) {
        return this.readOnlyService.batchReadOnlyMap(dataEntryParam);
    }

    @Override
    public ReadOnlyBean readOnly(DataEntryParam dataEntryParam) {
        return this.readOnlyService.readOnly(dataEntryParam);
    }

    @Override
    public CompleteMsg executeRevert(BusinessKey businessKey) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        CompleteMsg completeMsg = new CompleteMsg();
        if (this.completeTask != null && this.completeTask.size() > 0) {
            for (Map.Entry<String, CompleteAbstract> completeItem : this.completeTask.entrySet()) {
                CompleteAbstract complete = completeItem.getValue();
                if (!complete.IsMatch("act_retrieve", formSchemeDefine.getTaskKey())) continue;
                completeMsg = complete.executeRevert(businessKey);
            }
        }
        return completeMsg;
    }

    @Override
    public CompleteMsg executeRevert(BusinessKey businessKey, TaskContext context) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(businessKey.getFormSchemeKey());
        CompleteMsg completeMsg = new CompleteMsg();
        if (this.completeTask != null && this.completeTask.size() > 0) {
            for (Map.Entry<String, CompleteAbstract> completeItem : this.completeTask.entrySet()) {
                CompleteAbstract complete = completeItem.getValue();
                if (!complete.IsMatch("act_retrieve", formSchemeDefine.getTaskKey())) continue;
                completeMsg = complete.executeRevert(businessKey, context);
            }
        }
        return completeMsg;
    }

    @Override
    public CompleteMsg executeTask(ExecuteParam executeParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        CompleteMsg completeMsg = new CompleteMsg();
        if (this.completeTask != null && this.completeTask.size() > 0) {
            for (Map.Entry<String, CompleteAbstract> completeItem : this.completeTask.entrySet()) {
                CompleteAbstract complete = completeItem.getValue();
                if (!complete.IsMatch(executeParam.getActionId(), formSchemeDefine.getTaskKey())) continue;
                completeMsg = complete.executeTask(executeParam);
            }
        }
        return completeMsg;
    }

    @Override
    public CompleteMsg batchExecuteTask(BatchExecuteParam executeParam) throws UserActionException {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        CompleteMsg completeMsg = new CompleteMsg();
        if (this.completeTask != null && this.completeTask.size() > 0) {
            for (Map.Entry<String, CompleteAbstract> completeItem : this.completeTask.entrySet()) {
                CompleteAbstract complete = completeItem.getValue();
                if (!complete.IsMatch(executeParam.getActionId(), formSchemeDefine.getTaskKey())) continue;
                completeMsg = complete.batchExecuteTask(executeParam);
            }
        }
        return completeMsg;
    }

    @EventListener
    public void onCompleteTask(TodoActionEvent event) {
        String actionId = event.getActionId();
        String messageId = event.getMessageId();
        String userId = event.getUserId();
        Map extendParams = event.getExtendParams();
        Object object = extendParams.get("formSchemeId");
        String mainDimName = this.dimensionUtil.getDwMainDimName(object.toString());
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        dimensionValueSet.setValue("DATATIME", extendParams.get("period"));
        dimensionValueSet.setValue(mainDimName, extendParams.get("unitId"));
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setActionId(actionId);
        executeParam.setDimSet(dimensionValueSet);
        executeParam.setFormSchemeKey((String)extendParams.get("formSchemeId"));
        executeParam.setMessageId(messageId);
        Object taskObj = extendParams.get("taskNodeId");
        if (taskObj != null) {
            executeParam.setTaskId(taskObj.toString());
        }
        executeParam.setUserId(userId);
        this.executeTask(executeParam);
    }

    @EventListener
    public void batchOnCompleteTask(TodoBatchActionEvent todoBatchAction) {
        List messageId = todoBatchAction.getMessageId();
        if (messageId != null) {
            for (String msgId : messageId) {
                this.completeMsg(msgId, todoBatchAction.getActionId());
            }
        }
    }

    public void completeMsg(String msgid, String actionCode) {
        try {
            this.applicationContext.publishEvent(new TodoCompleteEvent(msgid, SettingUtil.getCurrentUserId(), actionCode));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public WorkFlowType queryStartType(String formSchemeKey) {
        return this.workflow.queryStartType(formSchemeKey);
    }

    @Override
    public List<UploadStateBean> queryAllactionCode(UploadState state, String formSchemeKey) {
        return this.uploadStateUtil.queryAllactionCode(state);
    }

    @Override
    public List<ReadOnlyBean> batchReadOnly(DataEntryParam dataEntryParam) {
        return this.readOnlyService.batchReadOnly(dataEntryParam);
    }

    @Override
    public ActionState queryState(DataEntryParam dataEntryParam) {
        ActionState actionState = new ActionState();
        WorkFlowType startType = this.queryStartType(dataEntryParam.getFormSchemeKey());
        ActionStateBean resourceState = this.dataentryQueryStateService.queryResourceState(dataEntryParam);
        ActionStateBean unitState = this.dataentryQueryStateService.queryUnitState(dataEntryParam);
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            actionState.setUnitState(unitState);
        } else if (WorkFlowType.FORM.equals((Object)startType)) {
            actionState.setFormState(resourceState);
            actionState.setUnitState(unitState);
        } else if (WorkFlowType.GROUP.equals((Object)startType)) {
            actionState.setGroupState(resourceState);
            actionState.setUnitState(unitState);
        }
        return actionState;
    }

    @Override
    public ActionParam actionParam(BatchExecuteParam executeParam, Map<String, DimensionValue> dimensionSet) {
        return this.commonUtil.actionParam(executeParam.getFormSchemeKey(), executeParam.getActionId());
    }

    @Override
    public Set<String> getTaskActors(FormSchemeDefine formScheme, DimensionValueSet dim, String formKey, String groupKey) {
        Set<String> actors = new HashSet<String>();
        ActionStateBean uploadState = this.dataentryQueryStateService.queryUploadState(formScheme.getKey(), dim, formKey, groupKey);
        if (UploadState.ORIGINAL_SUBMIT.toString().equals(uploadState.getCode()) || UploadState.ORIGINAL_UPLOAD.toString().equals(uploadState.getCode()) || UploadState.RETURNED.toString().equals(uploadState.getCode()) || UploadState.REJECTED.toString().equals(uploadState.getCode())) {
            BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formScheme.getKey(), dim, formKey, groupKey);
            Optional<ProcessEngine> processEngine = this.workflow.getProcessEngine(formScheme.getKey());
            List<Task> tasks = this.workflow.queryTasks(formScheme.getKey(), formKey, groupKey, dim, businessKey, false);
            if (tasks == null || tasks.size() == 0) {
                return actors;
            }
            actors = this.queryParticipants.getCurrenActor(processEngine, tasks.get(0), businessKey);
        }
        return actors;
    }

    @Override
    public CompleteMsg batchApplyReturnExecuteTask(BatchExecuteParam executeParam) {
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(executeParam.getFormSchemeKey());
        CompleteMsg completeMsg = new CompleteMsg();
        if (this.completeTask != null && this.completeTask.size() > 0) {
            for (Map.Entry<String, CompleteAbstract> completeItem : this.completeTask.entrySet()) {
                CompleteAbstract complete = completeItem.getValue();
                if (!complete.IsMatch(executeParam.getActionId(), formSchemeDefine.getTaskKey())) continue;
                completeMsg = complete.batchApplyReturnExecute(executeParam);
            }
        }
        return completeMsg;
    }

    @Override
    public Map<DimensionValueSet, ActionStateBean> getStateMap(String formSchemeKey, DimensionValueSet dim) {
        return this.dataentryQueryStateService.getWorkflowUploadState(dim, null, null, formSchemeKey);
    }

    @Override
    public DeWorkflowBean getDeWorkflow(DataEntryParam dataEntryParam) {
        DeWorkflowBean deWorkflowBean = new DeWorkflowBean();
        WorkflowDataBean workflowDataBean = new WorkflowDataBean();
        workflowDataBean.setFormSchemeKey(dataEntryParam.getFormSchemeKey());
        workflowDataBean.setDimSet(dataEntryParam.getDim());
        workflowDataBean.setFormKey(dataEntryParam.getFormKey());
        workflowDataBean.setFormGroupKey(dataEntryParam.getGroupKey());
        workflowDataBean.setFormKeys(dataEntryParam.getFormKeys());
        workflowDataBean.setFormGroupKeys(dataEntryParam.getGroupKeys());
        List<WorkflowDataInfo> workflowDataInfos = this.queryWorkflowDataInfo(workflowDataBean);
        deWorkflowBean.setWorkflowDataInfoList(workflowDataInfos);
        ActionState actionState = new ActionState();
        WorkFlowType startType = this.queryStartType(dataEntryParam.getFormSchemeKey());
        ActionState actionStateOther = this.getActionState(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim(), dataEntryParam.getFormKey());
        if (actionStateOther != null) {
            actionState = actionStateOther;
            ReadOnlyBean readOnlyBean = new ReadOnlyBean();
            readOnlyBean.setReadOnly(true);
            readOnlyBean.setMsg("");
            deWorkflowBean.setReadOnlyBean(readOnlyBean);
        } else {
            UploadStateNew unitUploadState = this.dataentryQueryStateService.queryUnitStateInfo(dataEntryParam);
            ActionStateBean unitState = unitUploadState.getActionStateBean();
            if (WorkFlowType.ENTITY.equals((Object)startType)) {
                actionState.setUnitState(unitState);
                ReadOnlyBean readOnlyBean = this.dataentryQueryStateService.readOnly(dataEntryParam, unitUploadState);
                deWorkflowBean.setReadOnlyBean(readOnlyBean);
            } else if (WorkFlowType.FORM.equals((Object)startType)) {
                UploadStateNew uploadStateNew = this.dataentryQueryStateService.queryUploadStateList(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim(), dataEntryParam.getFormKey(), dataEntryParam.getGroupKey());
                actionState.setFormState(uploadStateNew.getActionStateBean());
                actionState.setUnitState(unitState);
                ReadOnlyBean readOnlyBean = this.dataentryQueryStateService.readOnly(dataEntryParam, uploadStateNew);
                deWorkflowBean.setReadOnlyBean(readOnlyBean);
            } else if (WorkFlowType.GROUP.equals((Object)startType)) {
                UploadStateNew uploadStateNew = this.dataentryQueryStateService.queryUploadStateList(dataEntryParam.getFormSchemeKey(), dataEntryParam.getDim(), dataEntryParam.getFormKey(), dataEntryParam.getGroupKey());
                actionState.setGroupState(uploadStateNew.getActionStateBean());
                actionState.setUnitState(unitState);
                ReadOnlyBean readOnlyBean = this.dataentryQueryStateService.readOnly(dataEntryParam, uploadStateNew);
                deWorkflowBean.setReadOnlyBean(readOnlyBean);
            }
        }
        deWorkflowBean.setActionState(actionState);
        return deWorkflowBean;
    }

    private ActionState getActionState(String formSchemeKey, DimensionValueSet dimension, String formKey) {
        CustomDesignWorkflow map;
        WorkflowSettingDefine workflowSetting = this.flowService.getWorkflowDefineByFormSchemeKey(formSchemeKey);
        if (workflowSetting != null && workflowSetting.getDataId() != null && (map = this.map(workflowSetting.getWorkflowId())) != null) {
            ActionState state = map.getState(formSchemeKey, dimension, formKey);
            return state;
        }
        return null;
    }

    private CustomDesignWorkflow map(String processDefineKey) {
        if (this.customDesignProvider != null && this.customDesignProvider.size() > 0) {
            for (Map.Entry<String, CustomDesignWorkflow> customDesign : this.customDesignProvider.entrySet()) {
                CustomDesignWorkflow value = customDesign.getValue();
                if (!value.isApply(processDefineKey)) continue;
                return value;
            }
        }
        return null;
    }

    @Override
    public boolean isExistData(String taskKey) {
        TaskDefine taskDefine = this.commonUtil.getTaskDefine(taskKey);
        if (taskDefine == null) {
            logger.info("\u8fd0\u884c\u671f\u7684\u4efb\u52a1\u4e0d\u5b58\u5728,taskKey = " + taskKey);
            return false;
        }
        List<FormSchemeDefine> formSchemeDefines = this.commonUtil.getFormSchemeDefineByTaskKey(taskKey);
        if (formSchemeDefines != null && formSchemeDefines.size() > 0) {
            for (FormSchemeDefine formSchemeDefine : formSchemeDefines) {
                int stateSize = this.batchQueryUploadStateService.queryStateData(formSchemeDefine);
                int historyStateSize = this.batchQueryUploadStateService.queryHistoryStateData(formSchemeDefine);
                if (stateSize <= 0 && historyStateSize <= 0) continue;
                return true;
            }
        }
        return false;
    }

    @Override
    public List<SignModeData> getSignModeData(SignBootModeParam signBootModeParam) {
        ArrayList<SignModeData> signModeDatas = new ArrayList<SignModeData>();
        WorkFlowNodeSet workFlowNodeSet = this.nrParameterUtils.queryNodeNode(signBootModeParam.getFormSchemeKey(), signBootModeParam.getNodeId(), signBootModeParam.getActionCode());
        if (workFlowNodeSet != null && workFlowNodeSet.getId() != null) {
            List roleDTOS;
            List<String> roleKeys = this.nrParameterUtils.queryNodeActors(signBootModeParam.getFormSchemeKey(), workFlowNodeSet);
            Map<String, DimensionValue> dimensionSet = signBootModeParam.getDimensionValueSet();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(dimensionSet);
            List<String> actors = this.queryActors(signBootModeParam.getFormSchemeKey(), dimensionValueSet, roleKeys, workFlowNodeSet.getId());
            if (roleKeys != null && roleKeys.size() > 0 && (roleDTOS = this.roleService.findByIds(roleKeys)) != null && roleDTOS.size() > 0) {
                for (RoleDTO roleDTO : roleDTOS) {
                    SignModeData signData = new SignModeData();
                    signData.setKey(roleDTO.getId());
                    signData.setTitle(roleDTO.getTitle());
                    if (actors.contains(roleDTO.getId())) {
                        signData.setSelected(false);
                    } else {
                        signData.setSelected(true);
                    }
                    signModeDatas.add(signData);
                }
            }
        }
        return signModeDatas;
    }

    private List<String> queryActors(String formSchemeKey, DimensionValueSet dimensionValueSet, List<String> roleKeys, String nodeCode) {
        BusinessKey businessKey = this.businessGenerator.buildBusinessKey(formSchemeKey, dimensionValueSet, null, null);
        List<UploadRecordNew> historyState = this.nrParameterUtils.queryHistoryState(businessKey, nodeCode, roleKeys);
        ArrayList<String> temp = new ArrayList<String>();
        if (historyState != null && historyState.size() > 0) {
            List<UploadRecordNew> uploadRecordNews = historyState;
            if (historyState.size() > roleKeys.size()) {
                uploadRecordNews = historyState.subList(0, roleKeys.size());
            }
            for (UploadRecordNew uploadRecordNew : uploadRecordNews) {
                if (!"cus_submit".equals(uploadRecordNew.getAction()) && !"cus_upload".equals(uploadRecordNew.getAction()) && !"cus_confirm".equals(uploadRecordNew.getAction()) || uploadRecordNew.getRoleKey() == null || uploadRecordNew.getRoleKey().isEmpty()) continue;
                temp.add(uploadRecordNew.getRoleKey());
            }
        }
        return temp;
    }
}

