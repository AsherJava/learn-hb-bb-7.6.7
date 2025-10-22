/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.JsonMappingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.common.DataEngineConsts$FormulaType
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.dataengine.node.IParsedExpression
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg
 *  com.jiuqi.nr.bpm.de.dataflow.step.StepByStep
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckResult
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.access.util.DimCollectionBuildUtil
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckParam
 *  com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam
 *  com.jiuqi.nr.data.logic.facade.param.input.Mode
 *  com.jiuqi.nr.data.logic.facade.param.output.CheckResult
 *  com.jiuqi.nr.data.logic.facade.service.ICheckResultService
 *  com.jiuqi.nr.data.logic.facade.service.ICheckService
 *  com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.common.TaskGatherType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.engine.setting.AuthorityType
 *  com.jiuqi.nr.jtable.params.base.EntityViewData
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.service.IJtableParamService
 *  com.jiuqi.nr.jtable.uniformity.service.IDataStateCheckService
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.jtable.util.FormulaUtil
 *  com.jiuqi.nr.jtable.util.JsonUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.period.internal.adapter.IPeriodProvider
 *  com.jiuqi.nr.snapshot.input.CreateSnapshotContext
 *  com.jiuqi.nr.snapshot.service.SnapshotService
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.common.DataEngineConsts;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.step.StepByStep;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByOptParam;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.access.util.DimCollectionBuildUtil;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import com.jiuqi.nr.data.logic.facade.param.input.CheckParam;
import com.jiuqi.nr.data.logic.facade.param.input.CheckResultQueryParam;
import com.jiuqi.nr.data.logic.facade.param.input.Mode;
import com.jiuqi.nr.data.logic.facade.param.output.CheckResult;
import com.jiuqi.nr.data.logic.facade.service.ICheckResultService;
import com.jiuqi.nr.data.logic.facade.service.ICheckService;
import com.jiuqi.nr.data.logic.internal.util.DimensionCollectionUtil;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExternalUploadResult;
import com.jiuqi.nr.dataentry.bean.MultCheckLabel;
import com.jiuqi.nr.dataentry.bean.MultCheckReturnResult;
import com.jiuqi.nr.dataentry.bean.NodeCheckInfo;
import com.jiuqi.nr.dataentry.bean.NodeCheckResultInfo;
import com.jiuqi.nr.dataentry.bean.StepByStepCheckItem;
import com.jiuqi.nr.dataentry.bean.StepByStepCheckResult;
import com.jiuqi.nr.dataentry.bean.SubmitAfterCaculateEvent;
import com.jiuqi.nr.dataentry.bean.UploadVerifyType;
import com.jiuqi.nr.dataentry.internal.listener.BatchOptEvent;
import com.jiuqi.nr.dataentry.internal.service.util.CheckResultParamForReportUtil;
import com.jiuqi.nr.dataentry.internal.service.util.QueryLastOperateUtil;
import com.jiuqi.nr.dataentry.internal.service.util.UploadCheckFliterUtil;
import com.jiuqi.nr.dataentry.monitor.WorkFlowCheckProgressMonitor;
import com.jiuqi.nr.dataentry.monitor.WorkflowAsyncProgressMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchCalculateInfo;
import com.jiuqi.nr.dataentry.service.IBatchCalculateService;
import com.jiuqi.nr.dataentry.service.IBatchDataSumService;
import com.jiuqi.nr.dataentry.service.IDataentryWorkflowService;
import com.jiuqi.nr.dataentry.service.IExternalUploadFliter;
import com.jiuqi.nr.dataentry.service.IFinalaccountsAuditService;
import com.jiuqi.nr.dataentry.service.IForceUpload;
import com.jiuqi.nr.dataentry.service.IHBcheckExcute;
import com.jiuqi.nr.dataentry.service.IHBcheckReuslt;
import com.jiuqi.nr.dataentry.service.IMultcheckService;
import com.jiuqi.nr.dataentry.service.IStepByStepUpload;
import com.jiuqi.nr.dataentry.service.IWorkFlowHandler;
import com.jiuqi.nr.dataentry.service.IWorkflowService;
import com.jiuqi.nr.dataentry.util.DataEntryUtil;
import com.jiuqi.nr.dataentry.util.entityUtil.DataentryEntityUtils;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.common.TaskGatherType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.engine.setting.AuthorityType;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.uniformity.service.IDataStateCheckService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.jtable.util.FormulaUtil;
import com.jiuqi.nr.jtable.util.JsonUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.snapshot.input.CreateSnapshotContext;
import com.jiuqi.nr.snapshot.service.SnapshotService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataentryWorkflowServiceImpl
implements IDataentryWorkflowService {
    private static final Logger logger = LoggerFactory.getLogger(DataentryWorkflowServiceImpl.class);
    private static final String VERSION = "1.0";
    @Value(value="${jiuqi.nr.task.openFristMulCheck:false}")
    private boolean openFristMulcheck;
    @Value(value="${jiuqi.nr.workflow.mul-check.version:2.0}")
    private String mulCheckVersion;
    @Autowired
    private IWorkflowService workflowService;
    @Autowired
    private IBatchCalculateService batchCalculateService;
    @Autowired
    private StepByStep stepByStep;
    @Autowired
    private IBatchDataSumService batchDataSumService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IDataStateCheckService dataStateCheckService;
    @Autowired
    private IRunTimeViewController viewController;
    @Autowired(required=false)
    private List<IWorkFlowHandler> workflowHandlers;
    @Autowired
    private DataentryEntityUtils dataentryEntityUtils;
    @Autowired
    private IJtableParamService jtableParamService;
    @Autowired
    private IPeriodEntityAdapter periodEntityAdapter;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IDataDefinitionRuntimeController iDataDefinitionRuntimeController;
    @Autowired(required=false)
    private IExternalUploadFliter batchWorkflowHandlers;
    @Autowired
    private ICheckService iCheckService;
    @Autowired
    private ICheckResultService iCheckResultService;
    @Autowired
    private DimensionCollectionUtil dimensionCollectionUtil;
    @Autowired
    private CheckResultParamForReportUtil checkResultParamForReportUtil;
    @Autowired
    private UploadCheckFliterUtil uploadCheckFliterUtil;
    @Autowired
    private SnapshotService dataSnapshotService;
    @Autowired
    private DimCollectionBuildUtil dimCollectionBuildUtil;
    @Autowired(required=false)
    private IForceUpload forceUpload;
    @Autowired(required=false)
    private IStepByStepUpload stepByStepUpload;
    @Autowired(required=false)
    private IMultcheckService multcheckService;
    @Autowired
    private QueryLastOperateUtil queryLastOperateUtil;
    @Autowired
    private IFinalaccountsAuditService finalaccountsAuditService;
    @Autowired(required=false)
    private IHBcheckExcute hbCheckExcute;

    @Override
    public void dataentryExecuteTask(ExecuteTaskParam param, AsyncTaskMonitor asyncTaskMonitor) {
        ArrayList<String> list = new ArrayList<String>();
        DUserActionParam userActionParam = param.getUserActionParam();
        this.forceUpload(param);
        this.stepByStepUplaod(param);
        if (userActionParam.isStepByStepUpload()) {
            list.add("stepByStepUpload");
        }
        if (userActionParam.isNeedAutoCalculate()) {
            list.add("calc");
        }
        if (userActionParam.isNeedAutoCheck()) {
            list.add("check");
        }
        if (userActionParam.isNeedAutoNodeCheck()) {
            list.add("nodeCheck");
        }
        if (list.isEmpty()) {
            list.add(" ");
        }
        double scale = 0.9 / (double)list.size();
        ExternalUploadResult canUploadResult = this.canUpload(param);
        if (canUploadResult != null && !canUploadResult.isSuccess()) {
            asyncTaskMonitor.finish("extend", (Object)canUploadResult);
            return;
        }
        boolean leaf = this.isLeaf(param.getContext());
        if (userActionParam.isStepByStepUpload()) {
            double progress = 0.0;
            for (int i = 0; i < list.size(); ++i) {
                if (!"stepByStepUpload".equals(list.get(i))) continue;
                progress = (double)i * scale;
            }
            StepByOptParam stepByOptParam = new StepByOptParam();
            stepByOptParam.setActionId(param.getActionCode());
            stepByOptParam.setNodeId(param.getTaskCode());
            stepByOptParam.setTaskKey(param.getContext().getTaskKey());
            stepByOptParam.setFormSchemeKey(param.getContext().getFormSchemeKey());
            stepByOptParam.setFormKey(param.getContext().getFormKey());
            stepByOptParam.setGroupKey(param.getContext().getFormGroupKey());
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)param.getContext().getDimensionSet());
            stepByOptParam.setDimensionValue(dimensionValueSet);
            com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckResult stepByOpt = this.stepByStep.stepByOpt(stepByOptParam);
            StepByStepCheckResult result = new StepByStepCheckResult();
            result.setChild(stepByOpt.isChild());
            result.setActionStateTitle(stepByOpt.getActionStateTitle());
            result.setDirectActionStateTitle(stepByOpt.getDirectActionStateTitle());
            ArrayList<StepByStepCheckItem> items = new ArrayList<StepByStepCheckItem>();
            List bpmItems = stepByOpt.getItems();
            if (bpmItems != null) {
                for (int i = 0; i < bpmItems.size(); ++i) {
                    StepByStepCheckItem item = new StepByStepCheckItem();
                    com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem bpmItem = (com.jiuqi.nr.bpm.de.dataflow.step.bean.StepByStepCheckItem)bpmItems.get(i);
                    item.setUnitCode(bpmItem.getUnitCode());
                    item.setUnitId(bpmItem.getUnitId());
                    item.setUnitTitle(bpmItem.getUnitTitle());
                    item.setWorkflowState(bpmItem.getWorkflowState());
                    items.add(item);
                }
            }
            result.setItems(items);
            String stepCheck = "step_upload_check_success_info";
            asyncTaskMonitor.progressAndMessage(progress + scale, stepCheck);
            if (result.getItems().size() > 0) {
                asyncTaskMonitor.finish("stepByStepUpload", (Object)result);
                return;
            }
        }
        JtableContext context = param.getContext();
        Map<String, List<String>> formExps = this.getForms(context, param.getUserActionParam().getWorkFlowType());
        if (userActionParam.isNeedAutoCalculate() && !param.isForceCommit() && leaf) {
            String calculateFormulaSchemeKey = userActionParam.getCalculateFormulaValue();
            if (StringUtils.isEmpty((String)calculateFormulaSchemeKey)) {
                calculateFormulaSchemeKey = context.getFormulaSchemeKey();
            }
            BatchCalculateInfo batchCalculateInfo = new BatchCalculateInfo();
            batchCalculateInfo.setDimensionSet(context.getDimensionSet());
            batchCalculateInfo.setFormSchemeKey(context.getFormSchemeKey());
            batchCalculateInfo.setFormulaSchemeKey(calculateFormulaSchemeKey);
            batchCalculateInfo.setTaskKey(context.getTaskKey());
            batchCalculateInfo.setVariableMap(context.getVariableMap());
            batchCalculateInfo.setFormulas(formExps);
            batchCalculateInfo.setContext(context);
            double progress = 0.0;
            for (int i = 0; i < list.size(); ++i) {
                if (!"calc".equals(list.get(i))) continue;
                progress = (double)i * scale;
            }
            WorkflowAsyncProgressMonitor workflowAsyncProgressMonitor = new WorkflowAsyncProgressMonitor(asyncTaskMonitor, scale, progress);
            this.batchCalculateService.batchCalculateForm(batchCalculateInfo, workflowAsyncProgressMonitor);
        }
        TaskDefine taskDefine = this.viewController.queryTaskDefine(param.getContext().getTaskKey());
        TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
        if ("act_submit".equals(param.getActionCode()) || "act_upload".equals(param.getActionCode()) || "cus_upload".equals(param.getActionCode()) || "cus_submit".equals(param.getActionCode()) || "single_form_upload".equals(param.getActionCode())) {
            if (flowsSetting.getMulCheckBeforeCheck() && !param.isForceCommit()) {
                String finalaccountsAudit = this.finalaccountsAudit(param, flowsSetting, asyncTaskMonitor);
                if (this.openFristMulcheck || this.mulCheckVersion.equals(VERSION)) {
                    if (finalaccountsAudit != null) {
                        asyncTaskMonitor.finish("finalaccountsaudit1", (Object)finalaccountsAudit);
                        return;
                    }
                } else if (finalaccountsAudit != null) {
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        MultCheckReturnResult multCheckReturnResult = (MultCheckReturnResult)mapper.readValue(finalaccountsAudit, MultCheckReturnResult.class);
                        String errorMsg = multCheckReturnResult.getErrorMsg();
                        List<MultCheckLabel> failedList = multCheckReturnResult.getFailedList();
                        if (errorMsg != null || failedList.size() > 0) {
                            asyncTaskMonitor.finish("finalaccountsaudit2", (Object)finalaccountsAudit);
                            return;
                        }
                    }
                    catch (JsonMappingException e) {
                        logger.error("json\u89e3\u6790\u5f02\u5e38", e);
                    }
                    catch (JsonProcessingException e) {
                        logger.error("json\u89e3\u6790\u5f02\u5e38", e);
                    }
                }
            } else {
                if (userActionParam.isNeedAutoCheck() && !param.isForceCommit() && leaf) {
                    double progress = 0.0;
                    for (int i = 0; i < list.size(); ++i) {
                        if (!"check".equals(list.get(i))) continue;
                        progress = (double)i * scale;
                    }
                    WorkFlowCheckProgressMonitor workFlowCheckProgressMonitor = new WorkFlowCheckProgressMonitor(asyncTaskMonitor, scale, progress);
                    CheckResult allCheckResult = this.allCheckBeforeReport(param, workFlowCheckProgressMonitor, userActionParam.isNeedAutoCheckAll());
                    if (!this.isCheckSuccess(param, allCheckResult, asyncTaskMonitor)) {
                        return;
                    }
                }
                if (userActionParam.isNeedAutoNodeCheck() && !param.isForceCommit() && leaf) {
                    NodeCheckInfo nodeCheckInfo = new NodeCheckInfo();
                    JtableContext nodeCheckContext = new JtableContext(context);
                    Map dimensionSet = nodeCheckContext.getDimensionSet();
                    boolean judgeCurrentcyType = this.uploadCheckFliterUtil.judgeCurrentcyType(nodeCheckContext.getFormSchemeKey(), dimensionSet);
                    if (!judgeCurrentcyType) {
                        int nodeCheckCurrencyType = userActionParam.getNodeCheckCurrencyType();
                        String nodeCheckCurrencyValue = userActionParam.getNodeCheckCurrencyValue();
                        this.uploadCheckFliterUtil.setNodecheckConditions(nodeCheckContext.getTaskKey(), dimensionSet, nodeCheckCurrencyType, nodeCheckCurrencyValue);
                    }
                    nodeCheckInfo.setContext(nodeCheckContext);
                    StringBuilder formKeys = new StringBuilder();
                    if (formExps != null) {
                        for (String curForm : formExps.keySet()) {
                            formKeys.append(curForm);
                            formKeys.append(";");
                        }
                    }
                    nodeCheckInfo.setFormKeys(formKeys.toString());
                    double progress = 0.0;
                    for (int i = 0; i < list.size(); ++i) {
                        if (!"nodeCheck".equals(list.get(i))) continue;
                        progress = (double)i * scale;
                    }
                    WorkflowAsyncProgressMonitor workflowAsyncProgressMonitor = new WorkflowAsyncProgressMonitor(asyncTaskMonitor, scale, progress);
                    NodeCheckResultInfo nodeCheckResult = this.batchDataSumService.nodeCheck(nodeCheckInfo, workflowAsyncProgressMonitor);
                    if (nodeCheckResult != null && nodeCheckResult.getNodeCheckResult() != null && nodeCheckResult.getNodeCheckResult().size() > 0) {
                        asyncTaskMonitor.finish("nodeCheck", (Object)JsonUtil.objectToJson((Object)nodeCheckResult));
                        return;
                    }
                }
            }
        }
        if (this.workflowHandlers != null && this.workflowHandlers.size() > 0) {
            for (IWorkFlowHandler workFlowHandler : this.workflowHandlers) {
                boolean isBreak = workFlowHandler.beforeExecuteTask(param, asyncTaskMonitor);
                if (!isBreak) continue;
                return;
            }
        }
        CompleteMsg executeTask = null;
        List<String> formKeyids = param.getRejectFormKeys();
        executeTask = formKeyids != null && !formKeyids.isEmpty() ? this.workflowService.executeFormRejectOrUpload(param) : this.workflowService.executeTask(param);
        if (executeTask.isSucceed()) {
            CreateSnapshotContext dataSnapshotParam;
            if (userActionParam.isSubmitAfterFormula()) {
                String submitAfterFormulaValue = userActionParam.getSubmitAfterFormulaValue();
                NpContext npContext = NpContextHolder.getContext();
                if (StringUtils.isEmpty((String)submitAfterFormulaValue)) {
                    submitAfterFormulaValue = context.getFormulaSchemeKey();
                }
                BatchCalculateInfo submitAfterCalculateInfo = new BatchCalculateInfo();
                submitAfterCalculateInfo.setDimensionSet(context.getDimensionSet());
                submitAfterCalculateInfo.setFormSchemeKey(context.getFormSchemeKey());
                submitAfterCalculateInfo.setFormulaSchemeKey(submitAfterFormulaValue);
                submitAfterCalculateInfo.setTaskKey(context.getTaskKey());
                submitAfterCalculateInfo.setVariableMap(context.getVariableMap());
                submitAfterCalculateInfo.setFormulas(formExps);
                submitAfterCalculateInfo.setContext(context);
                submitAfterCalculateInfo.setIgnoreWorkFlow(true);
                SubmitAfterCaculateEvent submitAfterCaculateParam = new SubmitAfterCaculateEvent();
                submitAfterCaculateParam.setBatchCalculateInfo(submitAfterCalculateInfo);
                submitAfterCaculateParam.setNpContext(npContext);
                this.applicationEventPublisher.publishEvent(submitAfterCaculateParam);
            }
            this.dataStateCheckService.updateDimensionCache(context);
            if (userActionParam.isNeedbuildVersion() && (dataSnapshotParam = DataEntryUtil.getDataSnapshotParam(param, this.dataSnapshotService, this.dimCollectionBuildUtil, this.dimensionCollectionUtil, this.viewController)) != null) {
                try {
                    this.dataSnapshotService.createSnapshot(dataSnapshotParam, null);
                }
                catch (Exception e) {
                    logger.error("\u751f\u6210\u7cfb\u7edf\u5feb\u7167\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
                }
            }
            asyncTaskMonitor.finish("success", null);
        } else {
            asyncTaskMonitor.finish("fail", (Object)executeTask.getMsg());
        }
        this.sendOptEvent(param);
    }

    private CheckResult allCheckBeforeReport(ExecuteTaskParam param, WorkFlowCheckProgressMonitor workflowAsyncProgressMonitor, boolean specialAudit) {
        String checkFormulaSchemeKey;
        JtableContext context = param.getContext();
        JtableContext checkContext = new JtableContext(context);
        DUserActionParam userActionParam = param.getUserActionParam();
        double coefficient = workflowAsyncProgressMonitor.getCoefficient();
        Map<String, List<String>> formsMap = this.getForms(checkContext, param.getUserActionParam().getWorkFlowType());
        List<Object> formKeys = formsMap.keySet().stream().collect(Collectors.toList());
        if (specialAudit) {
            formKeys = this.queryLastOperateUtil.getlastOperateFormKeys(param.getContext(), param.getActionCode(), param.getTaskCode());
        }
        if (StringUtils.isEmpty((String)(checkFormulaSchemeKey = userActionParam.getCheckFormulaValue()))) {
            checkFormulaSchemeKey = checkContext.getFormulaSchemeKey();
        }
        String[] formulaSchemeKeys = checkFormulaSchemeKey.split(";");
        workflowAsyncProgressMonitor.setCount(formulaSchemeKeys.length);
        workflowAsyncProgressMonitor.setCoefficient(coefficient /= (double)formulaSchemeKeys.length);
        Map dimensionSet = checkContext.getDimensionSet();
        boolean judgeCurrentcyType = this.uploadCheckFliterUtil.judgeCurrentcyType(checkContext.getFormSchemeKey(), dimensionSet);
        if (!judgeCurrentcyType) {
            int checkCurrencyType = userActionParam.getCheckCurrencyType();
            String checkCurrencyValue = userActionParam.getCheckCurrencyValue();
            this.uploadCheckFliterUtil.setCheckConditions(checkContext.getTaskKey(), dimensionSet, checkCurrencyType, checkCurrencyValue);
        }
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSet);
        DimensionCollection dimensionCollection = this.dimensionCollectionUtil.getDimensionCollection(dimensionValueSet, checkContext.getFormSchemeKey());
        String actionID = workflowAsyncProgressMonitor.getTaskId();
        CheckParam checkParam = new CheckParam();
        checkParam.setActionId(actionID);
        checkParam.setVariableMap(param.getContext().getVariableMap());
        checkParam.setDimensionCollection(dimensionCollection);
        checkParam.setMode(Mode.FORM);
        checkParam.setRangeKeys(formKeys);
        checkParam.setFilterCondition(userActionParam.getCheckFilter());
        double process = workflowAsyncProgressMonitor.getProgress();
        for (String formulaSchemeKey : formulaSchemeKeys) {
            workflowAsyncProgressMonitor.setProgress(process);
            checkParam.setFormulaSchemeKey(formulaSchemeKey);
            this.iCheckService.allCheck(checkParam, (IFmlMonitor)workflowAsyncProgressMonitor);
            process += coefficient;
        }
        CheckResult allCheckResult = new CheckResult();
        EntityViewData dwEntity = this.jtableParamService.getDwEntity(checkContext.getFormSchemeKey());
        String dwDim = dwEntity.getDimensionName();
        String dwKey = (String)dimensionValueSet.getValue(dwDim);
        boolean enableCustomConfig = this.checkResultParamForReportUtil.enableCustomConfig(dwEntity, dwKey, checkContext.getTaskKey(), checkContext.getFormSchemeKey());
        List<Integer> erroStatus = userActionParam.getErroStatus();
        List<Integer> needCommentErrorStatus = userActionParam.getNeedCommentErrorStatus();
        if (enableCustomConfig) {
            Map<String, List<Integer>> customFormulaTypeMap = this.checkResultParamForReportUtil.getCustomFormulaTypeMap(checkContext.getTaskKey());
            erroStatus = customFormulaTypeMap.get("AFFECT");
            needCommentErrorStatus = customFormulaTypeMap.get("NEEDEXPLAIN");
        }
        if (erroStatus == null || erroStatus.size() == 0) {
            checkParam.setFormulaSchemeKey(checkFormulaSchemeKey);
            this.checkLog(checkContext, checkParam, formsMap, allCheckResult, param.getActionTitle());
            return allCheckResult;
        }
        HashMap<Integer, Boolean> checkTypes = new HashMap<Integer, Boolean>();
        for (Integer erroStatu : erroStatus) {
            checkTypes.put(erroStatu, null);
        }
        for (Integer needCommentErrorStatu : needCommentErrorStatus) {
            checkTypes.put(needCommentErrorStatu, false);
        }
        CheckResultQueryParam checkResultQueryParam = new CheckResultQueryParam();
        checkResultQueryParam.setVariableMap(param.getContext().getVariableMap());
        checkResultQueryParam.setDimensionCollection(dimensionCollection);
        checkResultQueryParam.setFormulaSchemeKeys(Arrays.asList(formulaSchemeKeys));
        checkResultQueryParam.setMode(Mode.FORM);
        checkResultQueryParam.setRangeKeys(formKeys);
        checkResultQueryParam.setFilterCondition(userActionParam.getCheckFilter());
        checkResultQueryParam.setCheckTypes(checkTypes);
        allCheckResult = this.iCheckResultService.queryAllCheckResult(checkResultQueryParam, actionID);
        checkParam.setFormulaSchemeKey(checkFormulaSchemeKey);
        this.checkLog(checkContext, checkParam, formsMap, allCheckResult, param.getActionTitle());
        return allCheckResult;
    }

    @Override
    public StepByStepCheckResult stepByStepUploadResult(String asyncTaskID) {
        Object object = this.asyncTaskManager.queryDetail(asyncTaskID);
        if (null != object) {
            return (StepByStepCheckResult)object;
        }
        return null;
    }

    private Map<String, List<String>> getForms(JtableContext context, WorkFlowType flowType) {
        HashMap<String, List<String>> formExps = new HashMap<String, List<String>>();
        if (flowType == WorkFlowType.FORM) {
            formExps.put(context.getFormKey(), new ArrayList());
            return formExps;
        }
        if (flowType == WorkFlowType.GROUP) {
            String groupKey = context.getFormGroupKey();
            try {
                List formDefines = this.viewController.getAllFormsInGroup(groupKey, true);
                for (FormDefine formDefine : formDefines) {
                    formExps.put(formDefine.getKey(), new ArrayList());
                }
            }
            catch (Exception e) {
                return null;
            }
            return formExps;
        }
        return formExps;
    }

    @EventListener
    @Transactional
    @Async
    public void submitAfterCaculateListener(SubmitAfterCaculateEvent event) {
        NpContext npContext = event.getNpContext();
        BatchCalculateInfo batchCalculateInfo = event.getBatchCalculateInfo();
        if (npContext != null) {
            NpContextHolder.setContext((NpContext)npContext);
        }
        this.batchCalculateService.batchCalculateForm(batchCalculateInfo);
    }

    private void checkLog(JtableContext context, CheckParam allCheckInfo, Map<String, List<String>> formExps, CheckResult allCheckResult, String actionTitle) {
        try {
            StringBuffer sb = new StringBuffer();
            TaskDefine taskDefine = this.viewController.queryTaskDefine(context.getTaskKey());
            sb.append("\u4efb\u52a1\u540d\u79f0\uff1a").append(taskDefine.getTitle() + "; ");
            FormSchemeDefine formScheme = this.viewController.getFormScheme(context.getFormSchemeKey());
            sb.append("\u62a5\u8868\u65b9\u6848\u540d\u79f0\uff1a").append(formScheme.getTitle() + "; ");
            Map dimensionSetMap = context.getDimensionSet();
            DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSetMap);
            EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
            EntityViewDefine dwEntityViewDefine = dwEntity.getEntityViewDefine();
            try {
                IEntityTable entDataQuerySet = this.dataentryEntityUtils.entDataQuerySet(formScheme, dwEntityViewDefine, dimensionValueSet, AuthorityType.None);
                List allRows = entDataQuerySet.getAllRows();
                if (allRows != null && allRows.size() > 0) {
                    for (IEntityRow entityRow : allRows) {
                        String code = entityRow.getCode();
                        String title = entityRow.getTitle();
                        sb.append("\u5355\u4f4dcode|\u5355\u4f4d\u540d\u79f0: " + code + "|" + title + "; ");
                    }
                }
            }
            catch (Exception e2) {
                logger.error(e2.getMessage(), e2);
            }
            IPeriodProvider periodProvider = this.periodEntityAdapter.getPeriodProvider(taskDefine.getDateTime());
            String periodTitle = periodProvider.getPeriodTitle(dimensionValueSet.getValue("DATATIME").toString());
            sb.append("\u65f6\u671f:" + periodTitle + "; ");
            List formDefines = this.viewController.queryAllFormDefinesByFormScheme(context.getFormSchemeKey());
            List formKeys = formDefines.stream().map(e -> e.getKey()).collect(Collectors.toList());
            ArrayList<String> formulaKeys = new ArrayList<String>();
            List formulaSchemeList = FormulaUtil.getFormulaSchemeList((String)context.getFormSchemeKey(), (String)allCheckInfo.getFormulaSchemeKey());
            for (int formulaIndex = 0; formulaIndex < formulaSchemeList.size(); ++formulaIndex) {
                FormulaSchemeDefine formulaSchemeDefine = (FormulaSchemeDefine)formulaSchemeList.get(formulaIndex);
                List formulaList = FormulaUtil.getFormulaList((String)formulaSchemeDefine.getKey(), formExps, formKeys, (String)context.getFormSchemeKey(), (DataEngineConsts.FormulaType)DataEngineConsts.FormulaType.CHECK);
                for (IParsedExpression formula : formulaList) {
                    formulaKeys.add(formula.getSource().getId());
                }
            }
            sb.append("\u5ba1\u6838\u516c\u5f0f\u6570\uff1a" + formulaKeys.size() + "; ");
            String checkInfo = "\u4e0a\u62a5\u524d\u5ba1\u6838\u4fe1\u606f";
            String msg = "\u4e0a\u62a5\u524d\u5ba1\u6838\u4fe1\u606f";
            if (actionTitle != null) {
                checkInfo = "\u5ba1\u6838\u5f71\u54cd" + actionTitle + "\u9519\u8bef\u6570:";
                msg = actionTitle + "\u524d\u5ba1\u6838\u4fe1\u606f";
            }
            sb.append(checkInfo + allCheckResult.getTotalCount());
            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)msg, (String)sb.toString());
        }
        catch (Exception e2) {
            logger.error(e2.getMessage(), e2);
        }
    }

    private void sendOptEvent(ExecuteTaskParam param) {
        try {
            ArrayList<String> units = new ArrayList<String>();
            WorkFlowType workFlowType = param.getUserActionParam().getWorkFlowType();
            BatchOptEvent batchOptEvent = new BatchOptEvent();
            batchOptEvent.setTaskKey(param.getContext().getTaskKey());
            batchOptEvent.setFromSchemeKey(param.getContext().getFormSchemeKey());
            Map dimensionSet = param.getContext().getDimensionSet();
            DimensionValue dimensionValue = (DimensionValue)dimensionSet.get("DATATIME");
            String period = dimensionValue.getValue();
            batchOptEvent.setPeriod(period);
            String mainDimName = this.getMainDimName(param.getContext().getFormSchemeKey());
            DimensionValue unitDim = (DimensionValue)dimensionSet.get(mainDimName);
            String unitId = unitDim.getValue();
            units.add(unitId);
            if (WorkFlowType.ENTITY.equals((Object)workFlowType)) {
                batchOptEvent.setUnits(units);
            } else if (WorkFlowType.FORM.equals((Object)workFlowType)) {
                ArrayList<String> forms = new ArrayList<String>();
                String formKey = param.getContext().getFormKey();
                forms.add(formKey);
                batchOptEvent.setUnits(units);
                batchOptEvent.setFormKeys(forms);
            } else if (WorkFlowType.GROUP.equals((Object)workFlowType)) {
                ArrayList<String> groups = new ArrayList<String>();
                String formGroupKey = param.getContext().getFormGroupKey();
                groups.add(formGroupKey);
                batchOptEvent.setUnits(units);
                batchOptEvent.setFromGroupKeys(groups);
            }
            String userId = NpContextHolder.getContext().getUserId();
            batchOptEvent.setOperator(userId);
            batchOptEvent.setActionCode(param.getActionCode());
            batchOptEvent.setContent(param.getComment());
            if (dimensionSet.containsKey("ADJUST")) {
                DimensionValue dimension = (DimensionValue)dimensionSet.get("ADJUST");
                String adjust = dimension.getValue();
                batchOptEvent.setAdjustPeriod(adjust);
            }
            this.applicationEventPublisher.publishEvent(batchOptEvent);
        }
        catch (Exception e) {
            logger.error("\u6279\u91cf\u64cd\u4f5c\u4e8b\u4ef6\u672a\u53d1\u9001\u6210\u529f");
        }
    }

    private String getMainDimName(String formSchemeKey) {
        EntityViewDefine entityViewDefine = this.viewController.getViewByFormSchemeKey(formSchemeKey);
        ExecutorContext context = new ExecutorContext(this.iDataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((com.jiuqi.np.dataengine.executors.ExecutorContext)context);
        return dataAssist.getDimensionName(entityViewDefine);
    }

    private ExternalUploadResult canUpload(ExecuteTaskParam param) {
        try {
            if (this.batchWorkflowHandlers != null) {
                return this.batchWorkflowHandlers.upload(param);
            }
        }
        catch (Exception e) {
            logger.error("\u653f\u5e9c\u6269\u5c55\u63a5\u53e3\u6267\u884c\u5f02\u5e38");
        }
        return null;
    }

    private void stepByStepUplaod(ExecuteTaskParam param) {
        try {
            if (this.stepByStepUpload != null) {
                boolean upload = this.stepByStepUpload.isUpload(param);
                DUserActionParam userActionParam = param.getUserActionParam();
                userActionParam.setStepByStepUpload(upload);
            }
        }
        catch (Exception e) {
            logger.error("\u653f\u5e9c\u6269\u5c55\u63a5\u53e3\u6269\u5c55\u5f02\u5e38\uff0c\u5c42\u5c42\u4e0a\u62a5");
        }
    }

    private void forceUpload(ExecuteTaskParam param) {
        try {
            List<UploadVerifyType> upload;
            if (param.isForceCommit() && this.forceUpload != null && (upload = this.forceUpload.isUpload(param)) != null && upload.size() > 0) {
                List types = upload.stream().map(e -> e.getCode()).collect(Collectors.toList());
                DUserActionParam userActionParam = param.getUserActionParam();
                if (types.contains(UploadVerifyType.CALCUTE.getCode())) {
                    userActionParam.setNeedAutoCalculate(true);
                } else {
                    userActionParam.setNeedAutoCalculate(false);
                }
                if (types.contains(UploadVerifyType.CHECK.getCode())) {
                    userActionParam.setNeedAutoCheck(true);
                } else {
                    userActionParam.setNeedAutoCheck(false);
                }
                if (types.contains(UploadVerifyType.NODECHECK.getCode())) {
                    userActionParam.setNeedAutoNodeCheck(true);
                } else {
                    userActionParam.setNeedAutoNodeCheck(false);
                }
                param.setForceCommit(false);
            }
        }
        catch (Exception e2) {
            logger.error("\u653f\u5e9c\u6269\u5c55\u63a5\u53e3\u6269\u5c55\u5f02\u5e38\uff0c\u5f3a\u5236\u4e0a\u62a5");
        }
    }

    private String finalaccountsAudit(ExecuteTaskParam param, TaskFlowsDefine flowsSetting, AsyncTaskMonitor asyncTaskMonitor) {
        try {
            if (flowsSetting.getMulCheckBeforeCheck() && !param.isForceCommit() && this.multcheckService != null && ("act_upload".equals(param.getActionCode()) || "cus_upload".equals(param.getActionCode()) || "act_submit".equals(param.getActionCode()) || "cus_submit".equals(param.getActionCode()) || "single_form_upload".equals(param.getActionCode()))) {
                if (this.openFristMulcheck || this.mulCheckVersion.equals(VERSION)) {
                    return this.finalaccountsAuditService.comprehensiveAudit(param, asyncTaskMonitor);
                }
                return this.multcheckService.comprehensiveAudit(param, asyncTaskMonitor);
            }
        }
        catch (Exception e) {
            logger.error("\u7efc\u5408\u5ba1\u6838\u6269\u5c55\u63a5\u53e3\u6267\u884c\u5f02\u5e38");
        }
        return null;
    }

    private boolean isCheckSuccess(ExecuteTaskParam executeTaskParam, CheckResult allCheckResult, AsyncTaskMonitor asyncTaskMonitor) {
        if (this.hbCheckExcute != null) {
            IHBcheckReuslt hbcheckReuslt = this.hbCheckExcute.excute(executeTaskParam);
            boolean result = hbcheckReuslt.isResult();
            if (!result || allCheckResult.getTotalCount() > 0) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    HashMap<String, String> checkResult = new HashMap<String, String>();
                    String value = mapper.writeValueAsString((Object)hbcheckReuslt);
                    checkResult.put("jihe", value);
                    if (allCheckResult.getTotalCount() > 0) {
                        checkResult.put("check", asyncTaskMonitor.getTaskId());
                    }
                    String checkResultStr = mapper.writeValueAsString(checkResult);
                    asyncTaskMonitor.finish("jiheCheck", (Object)checkResultStr);
                    return false;
                }
                catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        } else if (allCheckResult.getTotalCount() > 0) {
            asyncTaskMonitor.finish("check", null);
            return false;
        }
        return true;
    }

    private boolean isLeaf(JtableContext context) {
        try {
            FormSchemeDefine formScheme = this.viewController.getFormScheme(context.getFormSchemeKey());
            TaskDefine taskDefine = this.viewController.queryTaskDefine(formScheme.getTaskKey());
            if (taskDefine.getTaskGatherType() == TaskGatherType.TASK_GATHER_AUTO) {
                Map dimensionSetMap = context.getDimensionSet();
                DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet((Map)dimensionSetMap);
                EntityViewData dwEntity = this.jtableParamService.getDwEntity(context.getFormSchemeKey());
                EntityViewDefine dwEntityViewDefine = dwEntity.getEntityViewDefine();
                IEntityTable entityTable = this.dataentryEntityUtils.entDataQuerySet(formScheme, dwEntityViewDefine, dimensionValueSet, AuthorityType.None);
                List allRows = entityTable.getAllRows();
                if (allRows != null && allRows.size() > 0) {
                    return ((IEntityRow)allRows.get(0)).isLeaf();
                }
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage());
        }
        return true;
    }
}

