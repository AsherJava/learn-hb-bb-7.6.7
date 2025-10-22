/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 *  com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor
 *  com.jiuqi.np.asynctask.util.SimpleParamConverter$SerializationUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.np.log.BeanUtils
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.entity.service.IEntityMetaService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo
 *  com.jiuqi.nr.jtable.params.output.UploadBeforeCheck
 *  com.jiuqi.nr.jtable.params.output.UploadBeforeNodeCheck
 *  com.jiuqi.nr.jtable.params.output.UploadReturnInfo
 *  com.jiuqi.nr.jtable.util.DimensionValueSetUtil
 *  com.jiuqi.util.StringUtils
 */
package com.jiuqi.nr.dataentry.asynctask;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.np.asynctask.impl.RealTimeTaskMonitor;
import com.jiuqi.np.asynctask.util.SimpleParamConverter;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.np.log.BeanUtils;
import com.jiuqi.np.log.LogHelper;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.dataentry.bean.TodoResult;
import com.jiuqi.nr.dataentry.bean.TodoSelect;
import com.jiuqi.nr.dataentry.bean.WorkflowExecuteTodoParam;
import com.jiuqi.nr.dataentry.service.IBatchWorkflowService;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.SchemePeriodLinkDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.entity.service.IEntityMetaService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo;
import com.jiuqi.nr.jtable.params.output.UploadBeforeCheck;
import com.jiuqi.nr.jtable.params.output.UploadBeforeNodeCheck;
import com.jiuqi.nr.jtable.params.output.UploadReturnInfo;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import com.jiuqi.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="ASYNCTASK_BATCHUPLOAD_MULT_PERIOD", groupTitle="\u591a\u65f6\u671f\u6279\u91cf\u4e0a\u62a5", subject="\u62a5\u8868", tags={"\u957f\u4efb\u52a1"})
public class BatchUploadMultPeriodAsyncTaskExecutor
extends NpRealTimeTaskExecutor {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(BatchUploadMultPeriodAsyncTaskExecutor.class);
    public static final String ASYNCTASK_BATCHUPLOAD_MULT_PERIOD = "ASYNCTASK_BATCHUPLOAD_MULT_PERIOD";

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void executeWithNpContext(JobContext jobContext) throws JobExecutionException {
        String errorInfo = "task_error_info";
        String cancelInfo = "task_cancel_info";
        IBatchWorkflowService batchCommitFlowService = (IBatchWorkflowService)BeanUtils.getBean(IBatchWorkflowService.class);
        String taskId = jobContext.getInstanceId();
        AbstractRealTimeJob job = jobContext.getRealTimeJob();
        Map params = job.getParams();
        RealTimeTaskMonitor asyncTaskMonitor = new RealTimeTaskMonitor(taskId, ASYNCTASK_BATCHUPLOAD_MULT_PERIOD, jobContext);
        StringBuilder logInfoSb = new StringBuilder();
        String actionName = null;
        try {
            String args = (String)params.get("NR_ARGS");
            if (Objects.nonNull(args)) {
                TodoResult todoResult = new TodoResult();
                WorkflowExecuteTodoParam wParam = (WorkflowExecuteTodoParam)SimpleParamConverter.SerializationUtils.deserialize((String)args);
                List<BatchExecuteTaskParam> batchExecuteTaskParams = this.buildBatchExecuteTaskParam(wParam);
                for (BatchExecuteTaskParam batchExecuteTaskParam2 : batchExecuteTaskParams) {
                    LogInfo batchExecuteTask = batchCommitFlowService.batchExecuteTask(batchExecuteTaskParam2, (AsyncTaskMonitor)asyncTaskMonitor);
                    actionName = batchExecuteTask.getActionName();
                    logInfoSb.append(batchExecuteTask.getLogInfo()).append(";").append("\n");
                    BatchUploadRetrunInfo batchUploadRetrunInfo = batchExecuteTask.getBatchUploadRetrunInfo();
                    this.bulidTodoResult(todoResult, batchUploadRetrunInfo);
                    asyncTaskMonitor.progressAndMessage(10.0, "\u6b63\u5728\u6267\u884c...");
                }
                boolean hasError = todoResult.getErrorEntityNum() + todoResult.getErrorFormNums() + todoResult.getErrorFromGroupNums() + todoResult.getOtherErrorNum() + todoResult.getOtherErrorFromNum() + todoResult.getOtherErrorGroupNum() > 0;
                ObjectMapper mapper = new ObjectMapper();
                String retStr = mapper.writeValueAsString((Object)todoResult);
                if (hasError) {
                    asyncTaskMonitor.finish("batch_todo_upload_fail", (Object)retStr);
                } else {
                    asyncTaskMonitor.finish("batch_todo_upload_success", (Object)retStr);
                }
            }
        }
        catch (Exception e) {
            asyncTaskMonitor.error(errorInfo, (Throwable)e);
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        finally {
            LogHelper.info((String)"\u6570\u636e\u5f55\u5165", (String)("\u6267\u884c\u6279\u91cf" + actionName), (String)logInfoSb.toString());
        }
    }

    private List<BatchExecuteTaskParam> buildBatchExecuteTaskParam(WorkflowExecuteTodoParam wParam) {
        ArrayList<BatchExecuteTaskParam> batchExecuteTaskParamList = new ArrayList<BatchExecuteTaskParam>();
        String formSchemeKey = this.queryFormSchemeKey(wParam.getTaskKey(), wParam.getPeriod());
        IWorkflow workflow = (IWorkflow)BeanUtils.getBean(IWorkflow.class);
        WorkFlowType workflowType = workflow.queryStartType(formSchemeKey);
        List<TodoSelect> rangeSelect = wParam.getRangeSelect();
        for (TodoSelect todoSelect : rangeSelect) {
            BatchExecuteTaskParam batchExecuteTaskParam = new BatchExecuteTaskParam();
            String rangeUnit = todoSelect.getRangeUnit();
            ArrayList rangeForms = todoSelect.getRangeForms();
            ArrayList rangeGroups = todoSelect.getRangeGroups();
            JtableContext context = new JtableContext();
            DimensionValueSet dimensionValueSet = new DimensionValueSet();
            String dwDimName = this.getDwDimName(wParam.getTaskKey());
            dimensionValueSet.setValue(dwDimName, (Object)rangeUnit);
            String period = wParam.getPeriod();
            String adjust = wParam.getAdjust();
            dimensionValueSet.setValue("DATATIME", (Object)period);
            if (StringUtils.isNotEmpty((String)adjust)) {
                dimensionValueSet.setValue("ADJUST", (Object)adjust);
            }
            Map dimensionSet = DimensionValueSetUtil.getDimensionSet((DimensionValueSet)dimensionValueSet);
            context.setDimensionSet(dimensionSet);
            context.setTaskKey(wParam.getTaskKey());
            context.setFormSchemeKey(formSchemeKey);
            batchExecuteTaskParam.setContext(context);
            batchExecuteTaskParam.setFormKeys(WorkFlowType.FORM.equals((Object)workflowType) ? rangeForms : new ArrayList());
            batchExecuteTaskParam.setFormGroupKeys(WorkFlowType.GROUP.equals((Object)workflowType) ? rangeGroups : new ArrayList());
            batchExecuteTaskParam.setTaskCode(wParam.getNodeTaskCode());
            batchExecuteTaskParam.setActionId(wParam.getActionCode());
            batchExecuteTaskParam.setActionTitle(wParam.getActionTitle());
            batchExecuteTaskParam.setWorkFlowType(WorkFlowType.valueOf((String)wParam.getWorkflowType()));
            batchExecuteTaskParam.setSendEmail(wParam.isSendEmail());
            batchExecuteTaskParam.setForceCommit(wParam.isForceCommit());
            batchExecuteTaskParam.setComment(wParam.getComment());
            ActionParam actionParam = wParam.getActionParam();
            DUserActionParam dUserActionParam = new DUserActionParam();
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
            batchExecuteTaskParam.setUserActionParam(dUserActionParam);
            batchExecuteTaskParamList.add(batchExecuteTaskParam);
        }
        return batchExecuteTaskParamList;
    }

    private String queryFormSchemeKey(String taskKey, String period) {
        try {
            IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtils.getBean(IRunTimeViewController.class);
            SchemePeriodLinkDefine schemePeriodLink = runTimeViewController.querySchemePeriodLinkByPeriodAndTask(period, taskKey);
            return schemePeriodLink.getSchemeKey();
        }
        catch (Exception e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            return null;
        }
    }

    private String getDwDimName(String taskKey) {
        IRunTimeViewController runTimeViewController = (IRunTimeViewController)BeanUtils.getBean(IRunTimeViewController.class);
        IEntityMetaService entityMetaService = (IEntityMetaService)BeanUtils.getBean(IEntityMetaService.class);
        EntityViewDefine viewDefine = runTimeViewController.getViewByTaskDefineKey(taskKey);
        return entityMetaService.getDimensionName(viewDefine.getEntityId());
    }

    private void bulidTodoResult(TodoResult todoResult, BatchUploadRetrunInfo batchUploadRetrunInfo) {
        UploadReturnInfo uploadReturnInfo = batchUploadRetrunInfo.getUploadReturnInfo();
        UploadBeforeCheck uploadBeforeCheck = batchUploadRetrunInfo.getUploadBeforeCheck();
        UploadBeforeNodeCheck uploadBeforeNodeCheck = batchUploadRetrunInfo.getUploadBeforeNodeCheck();
        List levelUploadInfo = batchUploadRetrunInfo.getLevelUploadInfo();
        if (uploadReturnInfo != null) {
            todoResult.addAllEntityNums(uploadReturnInfo.getAllEntityNums());
            todoResult.addAllFormNums(uploadReturnInfo.getAllFormNums());
            todoResult.addAllFormGroupNums(uploadReturnInfo.getAllFormGroupNums());
            todoResult.addSuccessEntityNum(uploadReturnInfo.getSuccessEntityNum());
            todoResult.addSuccessFormNums(uploadReturnInfo.getSuccessFormNums());
            todoResult.addSuccessFormNums(uploadReturnInfo.getSuccessFromGroupNums());
            int errorEntityNum = uploadReturnInfo.getErrorEntityNum();
            int errorFormNums = uploadReturnInfo.getErrorFormNums();
            int errorFromGroupNums = uploadReturnInfo.getErrorFromGroupNums();
            int otherErrorNum = uploadReturnInfo.getOtherErrorNum();
            int otherErrorFromNum = uploadReturnInfo.getOtherErrorFromNum();
            int otherErrorFromGroupNum = uploadReturnInfo.getOtherErrorFromGroupNum();
            int errorEntityNumTemp = errorEntityNum + otherErrorNum;
            int errorFormNumTemp = errorFormNums + otherErrorFromNum;
            int errorGroupNumTemp = errorFromGroupNums + otherErrorFromGroupNum;
            todoResult.addErrorEntityNum(errorEntityNumTemp);
            todoResult.addErrorEntityNum(errorFormNumTemp);
            todoResult.addErrorFromGroupNums(errorGroupNumTemp);
            todoResult.addOtherErrorNum(otherErrorNum);
            todoResult.addOtherErrorFromNum(otherErrorFromNum);
            todoResult.addOtherErrorGroupNum(otherErrorFromGroupNum);
        }
        if (uploadBeforeCheck != null) {
            todoResult.addCheckNoPassUnitNum(uploadBeforeCheck.getUnPassEntityNum());
            todoResult.addCheckNoPassFormNum(uploadBeforeCheck.getUnPassFormsNum());
            todoResult.addCheckNoPassGroupNum(uploadBeforeCheck.getUnPassFormGroupNum());
        }
        if (uploadBeforeNodeCheck != null) {
            todoResult.addNodeCheckNoPassUnitNum(uploadBeforeNodeCheck.getUnPassEntityNum());
            todoResult.addNodeCheckNoPassFormNum(uploadBeforeNodeCheck.getUnPassFormNum());
            todoResult.addNodeCheckNoPassGroupNum(uploadBeforeNodeCheck.getUnPassFormGroupNum());
        }
        if (levelUploadInfo != null) {
            int size = levelUploadInfo.size();
            todoResult.addLevelUploadInfoNum(size);
        }
    }

    public String getTaskPoolType() {
        return AsynctaskPoolType.ASYNCTASK_BATCHUPLOAD.getName();
    }
}

