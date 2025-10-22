/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.syntax.parser.IContext
 *  com.jiuqi.np.asynctask.AsyncTaskManager
 *  com.jiuqi.np.asynctask.AsyncThreadExecutor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskInfo
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.controller.IEntityViewRunTimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg
 *  com.jiuqi.nr.bpm.de.dataflow.bean.SignBootModeParam
 *  com.jiuqi.nr.bpm.de.dataflow.bean.SignModeData
 *  com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam
 *  com.jiuqi.nr.bpm.de.dataflow.util.WorkFlowDimensionBuilder2
 *  com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.context.annotation.NRContextBuild
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.controller.ITaskOptionController
 *  com.jiuqi.nr.definition.facade.AuditType
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.definition.internal.service.AuditTypeDefineService
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.entity.engine.intf.IEntityQuery
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.entity.engine.intf.IEntityTable
 *  com.jiuqi.nr.entity.model.IEntityDefine
 *  com.jiuqi.nr.entity.service.IEntityDataService
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.annotation.JLoggable
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.uniformity.annotation.JUniformity
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector
 *  com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt
 *  com.jiuqi.util.StringUtils
 *  io.swagger.annotations.Api
 *  io.swagger.annotations.ApiOperation
 *  javax.annotation.Resource
 *  javax.servlet.http.HttpServletRequest
 *  javax.validation.Valid
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestMapping
 *  org.springframework.web.bind.annotation.RequestParam
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.nr.dataentry.web;

import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.syntax.parser.IContext;
import com.jiuqi.np.asynctask.AsyncTaskManager;
import com.jiuqi.np.asynctask.AsyncThreadExecutor;
import com.jiuqi.np.asynctask.NpRealTimeTaskInfo;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.controller.IEntityViewRunTimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.CompleteMsg;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignBootModeParam;
import com.jiuqi.nr.bpm.de.dataflow.bean.SignModeData;
import com.jiuqi.nr.bpm.de.dataflow.service.IDataentryFlowService;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.step.bean.BatchExecuteParam;
import com.jiuqi.nr.bpm.de.dataflow.util.WorkFlowDimensionBuilder2;
import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.context.annotation.NRContextBuild;
import com.jiuqi.nr.dataentry.asynctask.BatchUploadAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.asynctask.WorkflowAsyncTaskExecutor;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.DWorkflowData;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ReturnType;
import com.jiuqi.nr.dataentry.bean.ReturnTypeInfo;
import com.jiuqi.nr.dataentry.bean.StepByStepCheckResult;
import com.jiuqi.nr.dataentry.bean.WorkflowActionInfo;
import com.jiuqi.nr.dataentry.bean.WorkflowExecuteTodoParam;
import com.jiuqi.nr.dataentry.service.IDataentryWorkflowService;
import com.jiuqi.nr.dataentry.service.IWorkflowService;
import com.jiuqi.nr.dataentry.web.WorkFlowExecuteParamTransfer;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.service.IRuntimeDataSchemeService;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.FixedDimensionValue;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.controller.ITaskOptionController;
import com.jiuqi.nr.definition.facade.AuditType;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.definition.internal.service.AuditTypeDefineService;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.entity.engine.intf.IEntityQuery;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IEntityTable;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.service.IEntityDataService;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.annotation.JLoggable;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.uniformity.annotation.JUniformity;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.nr.period.internal.adapter.IPeriodEntityAdapter;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleCollector;
import com.jiuqi.nr.workflow2.engine.core.settings.compatible.Workflow2EngineCompatibleExtend;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nvwa.sf.adapter.spring.encrypt.SFDecrypt;
import com.jiuqi.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value={"/api/v1/work-flow"})
@Api(tags={"\u6570\u636e\u5f55\u5165\u5de5\u4f5c\u6d41\uff0c\u5305\u62ec\uff1a\u4e0a\u62a5\u6d41\u7a0b"})
public class WorkFlowController {
    private static final Logger logger = LoggerFactory.getLogger(WorkFlowController.class);
    @Autowired
    private IWorkflowService workFlowService;
    @Autowired
    private IDataentryWorkflowService dataentryWorkflowService;
    @Autowired
    private AsyncTaskManager asyncTaskManager;
    @Autowired
    private IDataentryFlowService dataFlowService;
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private AsyncThreadExecutor asyncThreadExecutor;
    @Autowired
    private IEntityMetaService entityMetaService;
    @Autowired
    private AuditTypeDefineService auditTypeDefineService;
    @Resource
    private IFormulaRunTimeController formulaRunTimeController;
    @Resource
    private IRuntimeDataSchemeService dataSchemeService;
    @Resource
    public IPeriodEntityAdapter periodEntityAdapter;
    @Resource
    private WorkFlowExecuteParamTransfer workFlowExecuteParamTransfer;
    @Resource
    private ITaskOptionController taskOptionController;
    @Resource
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Resource
    private IEntityDataService entityDataService;
    @Resource
    private IEntityViewRunTimeController entityViewRunTimeController;
    @Resource
    private WorkFlowDimensionBuilder2 workFlowDimensionBuilder;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private Workflow2EngineCompatibleCollector workflow2EngineCompatibleCollector;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    @PostMapping(value={"/user-actions"})
    @ApiOperation(value="\u4e0a\u62a5\u6d41\u7a0b\uff1a\u5f97\u5230\u7528\u6237\u6240\u6709\u7684\u4efb\u52a1")
    @NRContextBuild
    public List<DWorkflowData> getUserActions(@Valid @RequestBody JtableContext context) {
        return this.workFlowService.getUserActions(context);
    }

    @JLoggable(value="\u6267\u884c\u4e0a\u62a5")
    @PostMapping(value={"/actions"})
    @JUniformity
    @ApiOperation(value="\u4e0a\u62a5\u6d41\u7a0b\uff1a\u6267\u884c\u4e0a\u62a5\u6d41\u7a0b\u52a8\u4f5c")
    @NRContextBuild
    public AsyncTaskInfo executeTask(@RequestBody @SFDecrypt ExecuteTaskParam param) throws InterruptedException {
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)param)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new WorkflowAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @PostMapping(value={"/batch-actions"})
    @ApiOperation(value="\u4e0a\u62a5\u6d41\u7a0b\uff1a\u6279\u91cf\u6267\u884c\u4e0a\u62a5\u6d41\u7a0b\u52a8\u4f5c")
    @NRContextBuild
    public AsyncTaskInfo batchExecuteTask(@RequestBody @SFDecrypt BatchExecuteTaskParam param) {
        this.workFlowExecuteParamTransfer.checkBatchExecuteTaskParam(param);
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)param)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchUploadAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @PostMapping(value={"/batch-user-actions"})
    @ApiOperation(value="\u4e0a\u62a5\u6d41\u7a0b\uff1a\u6279\u91cf\u6267\u884c\u4e0a\u62a5\u6d41\u7a0b\u52a8\u4f5c")
    @NRContextBuild
    public Boolean batchUserAction(@Valid @RequestBody BatchExecuteTaskParam param) {
        BatchExecuteParam executeParam = new BatchExecuteParam();
        executeParam.setActionId(param.getActionId());
        executeParam.setFormSchemeKey(param.getContext().getFormSchemeKey());
        executeParam.setFormKey(param.getContext().getFormKey());
        executeParam.setGroupKey(param.getContext().getFormGroupKey());
        Map dimensionSet = param.getContext().getDimensionSet();
        ActionParam actionParam = this.dataFlowService.actionParam(executeParam, dimensionSet);
        if (actionParam != null && actionParam.isNeedOptDesc()) {
            return true;
        }
        return false;
    }

    @PostMapping(value={"/reject-info"})
    @ApiOperation(value="\u4e0a\u62a5\u6d41\u7a0b\uff1a\u67e5\u8be2\u9000\u56de\u5355\u4f4d\u914d\u7f6e")
    @NRContextBuild
    public WorkflowActionInfo queryRejectConfig(@Valid @RequestBody JtableContext context) {
        return this.workFlowService.getRejectData(context);
    }

    @GetMapping(value={"/stepByStep-upload-result"})
    @ApiOperation(value="\u9010\u7ea7\u6d41\u7a0b\u68c0\u67e5\u7ed3\u679c", notes="\u9010\u7ea7\u6d41\u7a0b\u68c0\u67e5\u7ed3\u679c")
    @NRContextBuild
    public StepByStepCheckResult nodecheckResult(HttpServletRequest request) {
        String id = request.getParameter("id");
        return this.dataentryWorkflowService.stepByStepUploadResult(id);
    }

    @PostMapping(value={"/apply-return"})
    @ApiOperation(value="\u7533\u8bf7\u9000\u56de")
    @NRContextBuild
    public CompleteMsg applyReturn(@Valid @RequestBody BatchExecuteParam context) {
        return this.workFlowService.applyReturn(context);
    }

    @PostMapping(value={"/batch-user-actionParam"})
    @ApiOperation(value="\u83b7\u53d6\u6279\u91cf\u4e0a\u62a5\u53c2\u6570")
    @NRContextBuild
    public TaskFlowsDefine getActionParam(@Valid @RequestBody BatchExecuteTaskParam param) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(param.getContext().getFormSchemeKey());
        TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
        return flowsSetting;
    }

    @PostMapping(value={"/getActionsFlowType"})
    @ApiOperation(value="\u83b7\u53d6\u4e0a\u62a5\u6d41\u7a0b\u7c7b\u578b")
    public String getActionsFlowType(@Valid @RequestBody JtableContext jtableContext) {
        if (this.workflow.isDefaultWorkflow(jtableContext.getFormSchemeKey())) {
            return "act_upload";
        }
        return "cus_upload";
    }

    @PostMapping(value={"/getReturnFlowType"})
    @ApiOperation(value="\u83b7\u53d6\u9000\u56de\u6d41\u7a0b\u7c7b\u578b")
    public String getReturnFlowType(@Valid @RequestBody JtableContext jtableContext) {
        if (this.workflow.isDefaultWorkflow(jtableContext.getFormSchemeKey())) {
            return "act_reject";
        }
        return "cus_reject";
    }

    @JLoggable(value="\u6267\u884c\u4e0a\u62a5")
    @PostMapping(value={"/actions-todo"})
    @JUniformity
    @ApiOperation(value="\u4e0a\u62a5\u6d41\u7a0b\uff1a\u6267\u884c\u4e0a\u62a5\u6d41\u7a0b\u52a8\u4f5c")
    @NRContextBuild
    public AsyncTaskInfo executeTaskTodo(@RequestBody WorkflowExecuteTodoParam wParam) throws InterruptedException {
        List<String> rangeGroups;
        ExecuteTaskParam executeTaskParam = new ExecuteTaskParam();
        JtableContext context = new JtableContext();
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        List<String> rangeUnits = wParam.getRangeUnits();
        if (rangeUnits != null && !rangeUnits.isEmpty()) {
            String dwDimName = this.getDwDimName(wParam.getTaskKey());
            dimensionValueSet.setValue(dwDimName, (Object)rangeUnits.get(0));
        }
        String period = wParam.getPeriod();
        String adjust = wParam.getAdjust();
        dimensionValueSet.setValue("DATATIME", (Object)period);
        if (StringUtils.isNotEmpty((String)adjust)) {
            dimensionValueSet.setValue("ADJUST", (Object)adjust);
        }
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        context.setDimensionSet(dimensionSet);
        List<String> rangeForms = wParam.getRangeForms();
        if (rangeForms != null && !rangeForms.isEmpty()) {
            context.setFormKey(rangeForms.get(0));
        }
        if ((rangeGroups = wParam.getRangeGroups()) != null && !rangeGroups.isEmpty()) {
            context.setFormGroupKey(rangeGroups.get(0));
        }
        context.setTaskKey(wParam.getTaskKey());
        String formSchemeKey = this.queryFormSchemeKey(wParam.getTaskKey(), wParam.getPeriod());
        context.setFormSchemeKey(formSchemeKey);
        context.setFormulaSchemeKey(this.queryFormulaSchemeKey(formSchemeKey));
        executeTaskParam.setContext(context);
        executeTaskParam.setTaskCode(wParam.getNodeTaskCode());
        executeTaskParam.setTaskId(wParam.getNodeTaskId());
        executeTaskParam.setActionCode(wParam.getActionCode());
        executeTaskParam.setActionTitle(wParam.getActionTitle());
        executeTaskParam.setReturnType(wParam.getReturnType());
        executeTaskParam.setComment(wParam.getComment());
        executeTaskParam.setForceCommit(wParam.isForceCommit());
        executeTaskParam.setSendEmail(wParam.isSendEmail());
        ActionParam actionParam = wParam.getActionParam();
        DUserActionParam dUserActionParam = new DUserActionParam();
        if (actionParam != null) {
            dUserActionParam.setCheckFilter(actionParam.getCheckFilter());
            dUserActionParam.setOpenForceCommit(actionParam.isForceCommit());
            dUserActionParam.setNeedAutoCalculate(actionParam.isNeedAutoCalculate());
            dUserActionParam.setNeedAutoCheck(actionParam.isNeedAutoCheck());
            dUserActionParam.setNeedAutoNodeCheck(actionParam.isNodeCheck());
            dUserActionParam.setNeedOptDesc(actionParam.isNeedOptDesc());
            dUserActionParam.setNeedbuildVersion(actionParam.isNeedbuildVersion());
            dUserActionParam.setStepByStepUpload(actionParam.isStepByStep());
            dUserActionParam.setBatchOpt(actionParam.isBatchOpt());
            dUserActionParam.setSysMsgShow(actionParam.isSysMsgShow());
            dUserActionParam.setMailShow(actionParam.isMailShow());
            dUserActionParam.setWorkFlowType(WorkFlowType.valueOf((String)wParam.getWorkflowType()));
            dUserActionParam.setSubmitAfterFormula(actionParam.isSubmitAfterFormula());
            dUserActionParam.setSubmitAfterFormulaValue(actionParam.getSubmitAfterFormulaValue());
            dUserActionParam.setCalculateFormulaValue(actionParam.getCalcuteFormulaValue());
            dUserActionParam.setCheckFormulaValue(actionParam.getCheckFormulaValue());
            dUserActionParam.setCheckCurrencyValue(actionParam.getCheckCurrencyValue());
            dUserActionParam.setNodeCheckCurrencyValue(actionParam.getNodeCheckCurrencyValue());
            dUserActionParam.setCheckCurrencyType(actionParam.getCheckCurrencyType());
            dUserActionParam.setNodeCheckCurrencyType(actionParam.getNodeCheckCurrencyType());
            dUserActionParam.setSingleRejectAction(actionParam.isSingleRejectAction());
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
                if (actionParam.getIgnoreErrorStatus() != null && actionParam.getIgnoreErrorStatus().contains(formualTypes.get(i))) continue;
                erroStatus.add((Integer)formualTypes.get(i));
            }
            dUserActionParam.setErroStatus(erroStatus);
            dUserActionParam.setNeedCommentErrorStatus(actionParam.getNeedCommentErrorStatus());
            executeTaskParam.setUserActionParam(dUserActionParam);
        }
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)executeTaskParam)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new WorkflowAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncThreadExecutor.executeTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @PostMapping(value={"/batch-actions-todo"})
    @ApiOperation(value="\u4e0a\u62a5\u6d41\u7a0b\uff1a\u6279\u91cf\u6267\u884c\u4e0a\u62a5\u6d41\u7a0b\u52a8\u4f5c")
    @NRContextBuild
    public AsyncTaskInfo batchExecuteTaskTodo(@RequestBody WorkflowExecuteTodoParam wParam) {
        List<String> rangeGroups;
        BatchExecuteTaskParam batchExecuteTaskParam = new BatchExecuteTaskParam();
        String formSchemeKey = this.queryFormSchemeKey(wParam.getTaskKey(), wParam.getPeriod());
        JtableContext context = new JtableContext();
        DimensionValueSet dimensionValueSet = this.buildDimensionValueSet(formSchemeKey, wParam);
        Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
        context.setDimensionSet(dimensionSet);
        List<String> rangeForms = wParam.getRangeForms();
        if (rangeForms != null && !rangeForms.isEmpty()) {
            context.setFormKey(rangeForms.get(0));
        }
        if ((rangeGroups = wParam.getRangeGroups()) != null && !rangeGroups.isEmpty()) {
            context.setFormGroupKey(rangeGroups.get(0));
        }
        context.setTaskKey(wParam.getTaskKey());
        context.setFormSchemeKey(formSchemeKey);
        context.setFormulaSchemeKey(this.queryFormulaSchemeKey(formSchemeKey));
        batchExecuteTaskParam.setContext(context);
        batchExecuteTaskParam.setFormKeys(wParam.getRangeForms());
        batchExecuteTaskParam.setFormGroupKeys(wParam.getRangeGroups());
        batchExecuteTaskParam.setTaskCode(wParam.getNodeTaskCode());
        batchExecuteTaskParam.setActionId(wParam.getActionCode());
        batchExecuteTaskParam.setActionTitle(wParam.getActionTitle());
        batchExecuteTaskParam.setWorkFlowType(WorkFlowType.valueOf((String)wParam.getWorkflowType()));
        batchExecuteTaskParam.setSendEmail(wParam.isSendEmail());
        batchExecuteTaskParam.setForceCommit(wParam.isForceCommit());
        batchExecuteTaskParam.setReturnType(wParam.getReturnType());
        batchExecuteTaskParam.setComment(wParam.getComment());
        ActionParam actionParam = wParam.getActionParam();
        DUserActionParam dUserActionParam = new DUserActionParam();
        dUserActionParam.setCheckFilter(actionParam.getCheckFilter());
        dUserActionParam.setOpenForceCommit(actionParam.isForceCommit());
        dUserActionParam.setNeedAutoCalculate(actionParam.isNeedAutoCalculate());
        dUserActionParam.setNeedAutoCheck(actionParam.isNeedAutoCheck());
        dUserActionParam.setNeedAutoCheckAll(actionParam.isNeedAutoAllCheck());
        dUserActionParam.setNeedAutoNodeCheck(actionParam.isNodeCheck());
        dUserActionParam.setNeedOptDesc(actionParam.isNeedOptDesc());
        dUserActionParam.setNeedbuildVersion(actionParam.isNeedbuildVersion());
        dUserActionParam.setStepByStepUpload(actionParam.isStepByStep());
        dUserActionParam.setBatchOpt(actionParam.isBatchOpt());
        dUserActionParam.setSysMsgShow(actionParam.isSysMsgShow());
        dUserActionParam.setMailShow(actionParam.isMailShow());
        dUserActionParam.setWorkFlowType(WorkFlowType.valueOf((String)wParam.getWorkflowType()));
        dUserActionParam.setSubmitAfterFormula(actionParam.isSubmitAfterFormula());
        dUserActionParam.setSubmitAfterFormulaValue(actionParam.getSubmitAfterFormulaValue());
        dUserActionParam.setCheckFormulaValue(actionParam.getCheckFormulaValue());
        dUserActionParam.setCalculateFormulaValue(actionParam.getCalcuteFormulaValue());
        dUserActionParam.setCheckCurrencyValue(actionParam.getCheckCurrencyValue());
        dUserActionParam.setNodeCheckCurrencyValue(actionParam.getNodeCheckCurrencyValue());
        dUserActionParam.setCheckCurrencyType(actionParam.getCheckCurrencyType());
        dUserActionParam.setNodeCheckCurrencyType(actionParam.getNodeCheckCurrencyType());
        dUserActionParam.setSingleRejectAction(actionParam.isSingleRejectAction());
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
            if (actionParam.getIgnoreErrorStatus() != null && actionParam.getIgnoreErrorStatus().contains(formualTypes.get(i))) continue;
            erroStatus.add((Integer)formualTypes.get(i));
        }
        dUserActionParam.setErroStatus(erroStatus);
        dUserActionParam.setNeedCommentErrorStatus(actionParam.getNeedCommentErrorStatus());
        batchExecuteTaskParam.setUserActionParam(dUserActionParam);
        NpRealTimeTaskInfo npRealTimeTaskInfo = new NpRealTimeTaskInfo();
        npRealTimeTaskInfo.setArgs(SimpleParamConverter.SerializationUtils.serializeToString((Object)((Object)batchExecuteTaskParam)));
        npRealTimeTaskInfo.setAbstractRealTimeJob((AbstractRealTimeJob)new BatchUploadAsyncTaskExecutor());
        AsyncTaskInfo asyncTaskInfo = new AsyncTaskInfo();
        String asynTaskID = this.asyncTaskManager.publishTask(npRealTimeTaskInfo);
        asyncTaskInfo.setId(asynTaskID);
        asyncTaskInfo.setUrl("/api/asynctask/query?asynTaskID=");
        return asyncTaskInfo;
    }

    @GetMapping(value={"/return-type-info"})
    @ApiOperation(value="\u83b7\u53d6\u4efb\u52a1\u9000\u56de\u7c7b\u578b\u4fe1\u606f")
    @NRContextBuild
    public ReturnTypeInfo getReturnTypeInfo(@RequestParam String taskId) {
        String baseDataEntityId;
        boolean returnTypeEnable;
        ReturnTypeInfo returnTypeInfo = new ReturnTypeInfo();
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskId)) {
            TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskId);
            TaskFlowsDefine flowsSetting = taskDefine.getFlowsSetting();
            returnTypeEnable = flowsSetting.isOpenBackType();
            baseDataEntityId = flowsSetting.getBackTypeEntity();
        } else {
            String workflowEngine = this.workflowSettingsService.queryTaskWorkflowEngine(taskId);
            Workflow2EngineCompatibleExtend extensionByEngine = this.workflow2EngineCompatibleCollector.getExtensionByEngine(workflowEngine);
            String returnType = extensionByEngine.getReturnType(taskId);
            returnTypeEnable = returnType != null;
            baseDataEntityId = returnType;
        }
        List<ReturnType> returnTypeSource = new ArrayList<ReturnType>();
        if (returnTypeEnable) {
            IEntityDefine entityDefine = this.entityMetaService.queryEntity(baseDataEntityId);
            IEntityTable entityTable = this.getBaseDataEntityTable(entityDefine.getId());
            List allRows = entityTable.getAllRows();
            returnTypeSource = this.buildReturnTypeSource(allRows);
        }
        returnTypeInfo.setReturnTypeEnable(returnTypeEnable);
        returnTypeInfo.setReturnTypeSource(returnTypeSource);
        return returnTypeInfo;
    }

    private String queryFormSchemeKey(String taskKey, String period) {
        try {
            SchemePeriodLinkDefine schemePeriodLink = this.runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
            return schemePeriodLink.getSchemeKey();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    private String getDwDimName(String taskKey) {
        EntityViewDefine viewDefine = this.runTimeViewController.getViewByTaskDefineKey(taskKey);
        return this.entityMetaService.getDimensionName(viewDefine.getEntityId());
    }

    private String queryFormulaSchemeKey(String formSchemeKey) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        if (formScheme != null) {
            List<Object> formulaSchemesInformScheme = new ArrayList();
            List allRPTFormulaSchemeDefines = this.formulaRunTimeController.getAllRPTFormulaSchemeDefinesByFormScheme(formSchemeKey);
            if (allRPTFormulaSchemeDefines != null) {
                formulaSchemesInformScheme = allRPTFormulaSchemeDefines.stream().map(IBaseMetaItem::getKey).collect(Collectors.toList());
            }
            ArrayList<Object> formulaSchemesInSetting = new ArrayList();
            TaskFlowsDefine flowsSetting = formScheme.getFlowsSetting();
            String settingFormulaSchemesOfCheck = flowsSetting.getReportBeforeAuditValue();
            if (StringUtils.isNotEmpty((String)settingFormulaSchemesOfCheck)) {
                formulaSchemesInSetting = new ArrayList<String>(Arrays.asList(settingFormulaSchemesOfCheck.split(";")));
            }
            formulaSchemesInSetting.retainAll(formulaSchemesInformScheme);
            if (!formulaSchemesInSetting.isEmpty()) {
                return String.join((CharSequence)";", formulaSchemesInSetting);
            }
            FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
            if (formulaSchemeDefine != null) {
                return formulaSchemeDefine.getKey();
            }
        }
        return null;
    }

    private DimensionValueSet buildDimensionValueSet(String formSchemeKey, WorkflowExecuteTodoParam wParam) {
        FormSchemeDefine formScheme = this.runTimeViewController.getFormScheme(formSchemeKey);
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(formScheme.getTaskKey());
        HashMap<String, Object> dimensionSet = new HashMap<String, Object>();
        List dataSchemeDimension = this.dataSchemeService.getDataSchemeDimension(taskDefine.getDataScheme());
        for (DataDimension dimension : dataSchemeDimension) {
            Object dimensionValue;
            String dimensionName = this.getDimensionName(dimension.getDimKey());
            if ("ADJUST".equals(dimensionName)) {
                dimensionValue = new DimensionValue();
                dimensionValue.setName(dimensionName);
                dimensionValue.setType(0);
                dimensionValue.setValue(wParam.getAdjust());
                dimensionSet.put(dimensionName, dimensionValue);
                continue;
            }
            if (DimensionType.UNIT == dimension.getDimensionType()) {
                dimensionValue = new DimensionValue();
                dimensionValue.setName(dimensionName);
                dimensionValue.setType(0);
                dimensionValue.setValue(String.join((CharSequence)";", wParam.getRangeUnits()));
                dimensionSet.put(dimensionName, dimensionValue);
                continue;
            }
            if (DimensionType.PERIOD != dimension.getDimensionType()) continue;
            dimensionValue = new DimensionValue();
            dimensionValue.setName(dimensionName);
            dimensionValue.setType(4);
            dimensionValue.setValue(String.join((CharSequence)";", wParam.getPeriod()));
            dimensionSet.put(dimensionName, dimensionValue);
        }
        DimensionCollection dimensionCollection = this.workFlowDimensionBuilder.buildDimensionCollection(taskDefine.getKey(), dimensionSet);
        List dimensionCombinations = dimensionCollection.getDimensionCombinations();
        if (dimensionCombinations == null || dimensionCombinations.isEmpty()) {
            throw new IllegalArgumentException("\u6ca1\u6709\u53ef\u4ee5\u6267\u884c\u7684\u5b9e\u4f8b");
        }
        HashMap<String, Set> mergeList = new HashMap<String, Set>();
        for (DimensionCombination dimensionCombination : dimensionCombinations) {
            Collection dimensionValues = dimensionCombination.getDimensionValues();
            for (FixedDimensionValue fixedDimensionValue : dimensionValues) {
                Set objects = mergeList.computeIfAbsent(fixedDimensionValue.getName(), k -> new HashSet());
                if (fixedDimensionValue.getValue() == null) continue;
                objects.add(fixedDimensionValue.getValue());
            }
        }
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        for (Map.Entry entry : mergeList.entrySet()) {
            Set values = (Set)entry.getValue();
            if (values == null || values.isEmpty()) continue;
            if (values.size() == 1) {
                Object[] array = values.toArray();
                dimensionValueSet.setValue((String)entry.getKey(), array[0]);
                continue;
            }
            dimensionValueSet.setValue((String)entry.getKey(), (Object)values);
        }
        return dimensionValueSet;
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

    private IEntityTable getBaseDataEntityTable(String entityId) {
        ExecutorContext executorContext = new ExecutorContext(this.dataDefinitionRuntimeController);
        IEntityQuery entityQuery = this.entityDataService.newEntityQuery();
        entityQuery.setEntityView(this.entityViewRunTimeController.buildEntityView(entityId));
        try {
            return entityQuery.executeReader((IContext)executorContext);
        }
        catch (Exception var8) {
            throw new RuntimeException(var8);
        }
    }

    private List<ReturnType> buildReturnTypeSource(List<IEntityRow> rows) {
        ArrayList<ReturnType> returnTypeList = new ArrayList<ReturnType>();
        for (IEntityRow row : rows) {
            ReturnType returnType = new ReturnType();
            returnType.setCode(row.getCode());
            returnType.setTitle(row.getTitle());
            returnTypeList.add(returnType);
        }
        return returnTypeList;
    }

    @PostMapping(value={"/signmode"})
    @ApiOperation(value="\u4f1a\u7b7e\u542f\u52a8\u6a21\u5f0f\u96c6\u5408")
    @NRContextBuild
    public List<SignModeData> getSignBootMode(@Valid @RequestBody SignBootModeParam signBootModeParam) {
        return this.workFlowService.getSignBootModeDatas(signBootModeParam);
    }
}

