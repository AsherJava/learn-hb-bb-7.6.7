/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.util.NpRollbackException
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionState
 *  com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg
 *  com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.SignBootModeParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.SignModeData
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean
 *  com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam
 *  com.jiuqi.nr.bpm.service.SingleFormRejectService
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.workflow2.todo.utils.TodoUtil
 *  com.jiuqi.util.StringUtils
 *  javax.annotation.Resource
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.util.NpRollbackException;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionState;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.DataEntryParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.ReadOnlyBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignBootModeParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignModeData;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowAction;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowConfig;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataBean;
import com.jiuqi.nr.bpm.de.dataflow.bean.WorkflowDataInfo;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.service.SingleFormRejectService;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.DWorkflowConfig;
import com.jiuqi.nr.dataentry.bean.DWorkflowData;
import com.jiuqi.nr.dataentry.bean.DWorkflowUserAction;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.WorkflowActionInfo;
import com.jiuqi.nr.dataentry.bean.WorkflowParam;
import com.jiuqi.nr.dataentry.service.IDataEntryParamService;
import com.jiuqi.nr.dataentry.service.IWorkflowService;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.definition.internal.controller.RunTimeAuthViewController;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.workflow2.todo.utils.TodoUtil;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WorkflowServiceImpl
implements IWorkflowService {
    private static final Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Autowired
    private IJtableParamService jtableParamService;
    @Resource
    private IDataEntryParamService dataEntryParamService;
    @Autowired
    private SingleFormRejectService singleFormRejectService;
    @Resource
    private RunTimeAuthViewController runTimeAuthViewController;
    @Autowired
    private IWorkflowService workflowService;
    @Resource
    private TodoUtil todoUtil;
    @Value(value="${jiuqi.nr.todo.version:2.0}")
    protected String todoVersion;

    @Override
    public DWorkflowConfig getWorkflowConfig(String formSchemeKey) {
        WorkflowConfig workflowConfig = this.dataFlowService.queryWorkflowConfig(formSchemeKey);
        DWorkflowConfig config = new DWorkflowConfig();
        config.setFlowsType(workflowConfig.getWorkFlowType());
        config.setFlowStarted(workflowConfig.isFlowStarted());
        config.setEntitys(this.jtableParamService.getEntityList(workflowConfig.getWorkflowEntities()));
        try {
            FormSchemeDefine formScheme = this.runTimeAuthViewController.getFormScheme(formSchemeKey);
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            String[] erroStatusArray = new String[]{};
            String[] promptStatus = new String[]{};
            if (flowsSetting.getErroStatus() != null && flowsSetting.getErroStatus().contains(";")) {
                erroStatusArray = flowsSetting.getErroStatus().split(";");
            }
            if (flowsSetting.getPromptStatus() != null && flowsSetting.getPromptStatus().contains(";")) {
                promptStatus = flowsSetting.getPromptStatus().split(";");
            }
            ArrayList<String> filterErrorStatus = new ArrayList<String>();
            for (String erro : erroStatusArray) {
                filterErrorStatus.add(erro);
            }
            for (String promp : promptStatus) {
                filterErrorStatus.add(promp);
            }
            ArrayList<String> array = new ArrayList<String>();
            AuditTypeDefineService auditTypeDefineService = (AuditTypeDefineService)BeanUtil.getBean(AuditTypeDefineService.class);
            try {
                List auditTypes = auditTypeDefineService.queryAllAuditType();
                if (auditTypes == null || auditTypes.size() == 0) {
                    throw new Exception();
                }
                for (AuditType auditType : auditTypes) {
                    array.add(auditType.getCode().toString());
                }
            }
            catch (Exception e) {
                array.add("1");
                array.add("2");
                array.add("4");
            }
            ArrayList filterErroStatusArray = new ArrayList();
            for (int i = 0; i < array.size(); ++i) {
                if (filterErrorStatus.contains(array.get(i))) continue;
                filterErroStatusArray.add(array.get(i));
            }
            ArrayList<Integer> erroStatus = new ArrayList<Integer>();
            for (String str : filterErroStatusArray) {
                if (StringUtils.isEmpty((String)str)) continue;
                erroStatus.add(Integer.parseInt(str));
            }
            if (erroStatus.size() == 0) {
                erroStatus.add(-1);
            }
            config.setErroStatus(erroStatus);
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        return config;
    }

    @Override
    public WorkflowActionInfo getRejectData(JtableContext context) {
        List<DWorkflowData> userActions = this.workflowService.getUserActions(context);
        for (DWorkflowData userAction : userActions) {
            for (DWorkflowUserAction action : userAction.getUserActions()) {
                if (action.getCode() != "act_reject") continue;
                WorkflowActionInfo workflowActionInfo = new WorkflowActionInfo();
                workflowActionInfo.setCode(action.getCode());
                workflowActionInfo.setTitle(action.getTitle());
                workflowActionInfo.setTaskCode(userAction.getTaskCode());
                workflowActionInfo.setTaskId(userAction.getTaskId());
                workflowActionInfo.setUserActionParam(action.getUserActionParam());
                return workflowActionInfo;
            }
        }
        return null;
    }

    @Override
    public List<DWorkflowData> getUserActions(JtableContext context) {
        WorkflowDataBean workflowData = new WorkflowDataBean();
        workflowData.setFormSchemeKey(context.getFormSchemeKey());
        workflowData.setFormKey(context.getFormKey());
        workflowData.setFormGroupKey(context.getFormGroupKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
        workflowData.setDimSet(dimensionValueSet);
        List workflowDataInfos = this.dataFlowService.queryWorkflowDataInfo(workflowData);
        ArrayList<DWorkflowData> dWorkflowDatas = new ArrayList<DWorkflowData>();
        for (WorkflowDataInfo wInfo : workflowDataInfos) {
            DWorkflowData dWorkflowData = new DWorkflowData();
            dWorkflowData.setTaskId(wInfo.getTaskId());
            dWorkflowData.setTaskCode(wInfo.getTaskCode());
            dWorkflowData.setDisabled(wInfo.isDisabled());
            ArrayList<DWorkflowUserAction> dWorkflowUserActions = new ArrayList<DWorkflowUserAction>();
            for (WorkflowAction workflowAction : wInfo.getActions()) {
                DWorkflowUserAction dWorkflowUserAction = new DWorkflowUserAction();
                dWorkflowUserAction.setCode(workflowAction.getCode());
                dWorkflowUserAction.setTitle(workflowAction.getTitle());
                dWorkflowUserAction.setIcon(workflowAction.getIcon());
                dWorkflowUserAction.setDesc(workflowAction.getDesc());
                DUserActionParam dUserActionParam = new DUserActionParam();
                dUserActionParam.setCheckFilter(workflowAction.getActionParam().getCheckFilter());
                dUserActionParam.setOpenForceCommit(workflowAction.getActionParam().isForceCommit());
                dUserActionParam.setNeedAutoCalculate(workflowAction.getActionParam().isNeedAutoCalculate());
                dUserActionParam.setNeedAutoCheck(workflowAction.getActionParam().isNeedAutoCheck());
                dUserActionParam.setNeedAutoNodeCheck(workflowAction.getActionParam().isNodeCheck());
                dUserActionParam.setNeedOptDesc(workflowAction.getActionParam().isNeedOptDesc());
                dUserActionParam.setNeedbuildVersion(workflowAction.getActionParam().isNeedbuildVersion());
                dUserActionParam.setStepByStepUpload(workflowAction.getActionParam().isStepByStep());
                dUserActionParam.setBatchOpt(workflowAction.getActionParam().isBatchOpt());
                dUserActionParam.setSysMsgShow(workflowAction.getActionParam().isSysMsgShow());
                dUserActionParam.setMailShow(workflowAction.getActionParam().isMailShow());
                dUserActionParam.setWorkFlowType(wInfo.getWorkFlowType());
                dUserActionParam.setSubmitAfterFormula(workflowAction.getActionParam().isSubmitAfterFormula());
                dUserActionParam.setSubmitAfterFormulaValue(workflowAction.getActionParam().getSubmitAfterFormulaValue());
                dUserActionParam.setCalculateFormulaValue(workflowAction.getActionParam().getCalcuteFormulaValue());
                dUserActionParam.setCheckFormulaValue(workflowAction.getActionParam().getCheckFormulaValue());
                dUserActionParam.setNeedAutoCheckAll(workflowAction.getActionParam().isNeedAutoAllCheck());
                dUserActionParam.setCheckCurrencyValue(workflowAction.getActionParam().getCheckCurrencyValue());
                dUserActionParam.setNodeCheckCurrencyValue(workflowAction.getActionParam().getNodeCheckCurrencyValue());
                dUserActionParam.setCheckCurrencyType(workflowAction.getActionParam().getCheckCurrencyType());
                dUserActionParam.setNodeCheckCurrencyType(workflowAction.getActionParam().getNodeCheckCurrencyType());
                dUserActionParam.setSingleRejectAction(workflowAction.getActionParam().isSingleRejectAction());
                ArrayList<Integer> formualTypes = new ArrayList<Integer>();
                try {
                    List auditTypes = this.auditTypeDefineService.queryAllAuditType();
                    if (auditTypes == null || auditTypes.size() == 0) {
                        throw new Exception();
                    }
                    for (AuditType auditType : auditTypes) {
                        formualTypes.add(auditType.getCode());
                    }
                }
                catch (Exception e) {
                    formualTypes.add(1);
                    formualTypes.add(2);
                    formualTypes.add(4);
                }
                ArrayList<Integer> erroStatus = new ArrayList<Integer>();
                for (int i = 0; i < formualTypes.size(); ++i) {
                    if (workflowAction.getActionParam().getIgnoreErrorStatus() != null && workflowAction.getActionParam().getIgnoreErrorStatus().contains(formualTypes.get(i))) continue;
                    erroStatus.add((Integer)formualTypes.get(i));
                }
                dUserActionParam.setErroStatus(erroStatus);
                dUserActionParam.setNeedCommentErrorStatus(workflowAction.getActionParam().getNeedCommentErrorStatus());
                dWorkflowUserAction.setUserActionParam(dUserActionParam);
                dWorkflowUserActions.add(dWorkflowUserAction);
            }
            dWorkflowData.setUserActions(dWorkflowUserActions);
            dWorkflowDatas.add(dWorkflowData);
        }
        return dWorkflowDatas;
    }

    @Override
    public CompleteMsg executeTask(ExecuteTaskParam param) {
        ExecuteParam executeParam = new ExecuteParam();
        executeParam.setActionId(param.getActionCode());
        executeParam.setComment(param.getComment());
        executeParam.setReturnType(param.getReturnType());
        executeParam.setFormSchemeKey(param.getContext().getFormSchemeKey());
        executeParam.setTaskId(param.getTaskId());
        ContextUser user = NpContextHolder.getContext().getUser();
        executeParam.setUserId(user.getId());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)param.getContext().getDimensionSet());
        executeParam.setDimSet(dimensionValueSet);
        executeParam.setForceUpload(param.isForceCommit());
        executeParam.setSendEmaill(param.isSendEmail());
        executeParam.setFormKey(param.getContext().getFormKey());
        executeParam.setGroupKey(param.getContext().getFormGroupKey());
        executeParam.setForceUpload(param.isForceCommit());
        executeParam.setNodeId(param.getTaskCode());
        executeParam.setSignBootModes(param.getSignBootMode());
        CompleteMsg executeTask = this.dataFlowService.executeTask(executeParam);
        return executeTask;
    }

    @Override
    public ActionState queryWorkflowState(WorkflowParam workflowParam) {
        JtableContext context = workflowParam.getContext();
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
        DataEntryParam dataEntryParam = new DataEntryParam();
        dataEntryParam.setDim(dimensionValueSet);
        dataEntryParam.setFormKey(context.getFormKey());
        dataEntryParam.setFormSchemeKey(context.getFormSchemeKey());
        dataEntryParam.setGroupKey(context.getFormGroupKey());
        dataEntryParam.setFormKeys(workflowParam.getFormKeys());
        dataEntryParam.setGroupKeys(workflowParam.getGroupKeys());
        ActionState actionState = this.dataFlowService.queryState(dataEntryParam);
        return actionState;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public void batchExecuteTask(BatchExecuteTaskParam param, AsyncTaskMonitor asyncTaskMonitor) {
    }

    @Override
    public ReadOnlyBean workflowReadOnly(JtableContext context) {
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
        DataEntryParam workflowParam = new DataEntryParam();
        workflowParam.setDim(dimensionValueSet);
        workflowParam.setFormKey(context.getFormKey());
        workflowParam.setGroupKey(context.getFormGroupKey());
        workflowParam.setFormSchemeKey(context.getFormSchemeKey());
        return this.dataFlowService.readOnly(workflowParam);
    }

    @Override
    public List<ReadOnlyBean> batchWorkflowReadOnly(JtableContext context) {
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)context.getDimensionSet());
        DataEntryParam workflowParam = new DataEntryParam();
        workflowParam.setDim(dimensionValueSet);
        workflowParam.setFormKey(context.getFormKey());
        workflowParam.setGroupKey(context.getFormGroupKey());
        workflowParam.setFormSchemeKey(context.getFormSchemeKey());
        List batchReadOnly = this.dataFlowService.batchReadOnly(workflowParam);
        return batchReadOnly;
    }

    @Override
    @Transactional(rollbackFor={NpRollbackException.class})
    public CompleteMsg executeFormRejectOrUpload(ExecuteTaskParam param) {
        List<String> formKeyids = param.getRejectFormKeys();
        Set formKeys = formKeyids.stream().collect(Collectors.toSet());
        DimensionValueSet dimensionSet = DimensionValueSetUtil.getDimensionValueSet((Map)param.getContext().getDimensionSet());
        String formSchemeKey = param.getContext().getFormSchemeKey();
        return this.singleFormRejectService.execute(formKeys, dimensionSet, formSchemeKey, param.getActionCode());
    }

    @Override
    public CompleteMsg applyReturn(BatchExecuteParam param) {
        if (this.todoVersion.equals("2.0")) {
            FormSchemeDefine formSchemeDefine = this.todoUtil.getFormSchemeDefine(param.getTaskId(), param.getPeriod());
            param.setFormSchemeKey(formSchemeDefine.getKey());
        }
        return this.dataFlowService.batchApplyReturnExecuteTask(param);
    }

    @Override
    public List<SignModeData> getSignBootModeDatas(SignBootModeParam signBootModeParam) {
        return this.dataFlowService.getSignModeData(signBootModeParam);
    }
}

