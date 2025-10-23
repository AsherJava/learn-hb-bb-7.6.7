/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.definition.common.UUIDUtils
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow
 *  com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.data.common.service.ImportResultDisplayCollector
 *  com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam
 *  com.jiuqi.nr.dataentry.bean.DUserActionParam
 *  com.jiuqi.nr.dataentry.bean.LogInfo
 *  com.jiuqi.nr.dataentry.service.IBatchWorkflowService
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormulaSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.io.record.bean.ImportLog
 *  com.jiuqi.nr.io.record.bean.ImportState
 *  com.jiuqi.nr.io.record.service.ImportHistoryService
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo
 *  com.jiuqi.nr.jtable.params.output.UploadReturnInfo
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 */
package com.jiuqi.nr.nrdx.adapter.listener.handler;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.definition.common.UUIDUtils;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.ActionMethod;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.common.service.ImportResultDisplayCollector;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.dataentry.service.IBatchWorkflowService;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.io.record.bean.ImportLog;
import com.jiuqi.nr.io.record.bean.ImportState;
import com.jiuqi.nr.io.record.service.ImportHistoryService;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.output.BatchUploadRetrunInfo;
import com.jiuqi.nr.jtable.params.output.UploadReturnInfo;
import com.jiuqi.nr.nrdx.adapter.listener.dto.HandlerParam;
import com.jiuqi.nr.nrdx.adapter.listener.dto.WorkflowUploadResult;
import com.jiuqi.nr.nrdx.adapter.listener.monitor.NrdxImportListenerMonitor;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowHandler
implements ImportResultDisplayCollector {
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private IWorkflow workflow;
    @Autowired
    private ActionMethod actionMethod;
    @Autowired
    private IBatchWorkflowService batchWorkflowService;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;
    @Autowired
    private ImportHistoryService importHistoryService;
    private static final String WORKFLOW_HANDLER_CODE = "WORKFLOW_HANDLER";
    private static final Logger logger = LoggerFactory.getLogger(WorkflowHandler.class);

    public String getCode() {
        return WORKFLOW_HANDLER_CODE;
    }

    public String getName() {
        return "\u5bfc\u5165\u540e\u4e0a\u62a5";
    }

    public String getDescription() {
        return "";
    }

    public void doWorkflowUpload(HandlerParam handlerParam) throws TransferException {
        WorkflowUploadResult workflowUploadResult = new WorkflowUploadResult();
        String recordKey = handlerParam.getRecordKey();
        Map<String, DimensionValue> dimensionValueMap = handlerParam.getDimensionValueMap();
        if (dimensionValueMap.isEmpty()) {
            return;
        }
        String taskKey = handlerParam.getTaskKey();
        String formSchemeKey = handlerParam.getFormSchemeKey();
        TaskDefine taskDefine = this.runTimeViewController.queryTaskDefine(taskKey);
        FormulaSchemeDefine formulaSchemeDefine = this.formulaRunTimeController.getDefaultFormulaSchemeInFormScheme(formSchemeKey);
        List<String> forms = handlerParam.getForms();
        TaskFlowsDefine taskFlowsDefine = taskDefine.getFlowsSetting();
        WorkFlowType workflowType = taskFlowsDefine.getWordFlowType();
        String uploadActionCode = this.workflow.getUploadActionCode(formSchemeKey);
        ActionParam actionParam = this.actionMethod.actionParam(formSchemeKey, uploadActionCode);
        IProgressMonitor progressMonitor = handlerParam.getProgressMonitor();
        NrdxImportListenerMonitor nrdxImportListenerMonitor = new NrdxImportListenerMonitor(progressMonitor, taskKey);
        JtableContext jtableContext = new JtableContext();
        jtableContext.setTaskKey(taskKey);
        jtableContext.setFormSchemeKey(formSchemeKey);
        jtableContext.setFormulaSchemeKey(formulaSchemeDefine.getKey());
        jtableContext.setDimensionSet(dimensionValueMap);
        BatchExecuteTaskParam executeTaskparam = new BatchExecuteTaskParam();
        executeTaskparam.setContext(jtableContext);
        executeTaskparam.setUserActionParam(WorkflowHandler.getDUserActionParam(actionParam));
        executeTaskparam.setActionId(uploadActionCode);
        executeTaskparam.setComment(handlerParam.getUploadDes());
        executeTaskparam.setWorkFlowType(workflowType);
        executeTaskparam.setFormulaSchemeKeys(formulaSchemeDefine.getKey());
        if (handlerParam.isAllowForceUpload() && taskFlowsDefine.isUnitSubmitForForce()) {
            executeTaskparam.setForceCommit(true);
        }
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(taskKey)) {
            String taskCode = this.workflow.getTaskCode(formSchemeKey, uploadActionCode);
            executeTaskparam.setTaskCode(taskCode);
        }
        try {
            if (WorkFlowType.FORM.equals((Object)workflowType)) {
                if (forms.isEmpty()) {
                    this.recordUploadResult(recordKey, false, "\u6570\u636e\u5bfc\u5165\u62a5\u8868\u4e2a\u6570\u4e3a0\uff0c\u4e0d\u6267\u884c\u4e0a\u62a5\uff01");
                }
                executeTaskparam.setFormKeys(forms);
            } else if (WorkFlowType.GROUP.equals((Object)workflowType)) {
                HashSet formGroupKeys = new HashSet();
                forms.forEach(formKey -> this.runTimeViewController.getFormGroupsByFormKey(formKey).stream().map(IBaseMetaItem::getKey).forEach(formGroupKeys::add));
                if (formGroupKeys.isEmpty()) {
                    this.recordUploadResult(recordKey, false, "\u6570\u636e\u5bfc\u5165\u62a5\u8868\u5206\u7ec4\u4e2a\u6570\u4e3a0\uff0c\u4e0d\u6267\u884c\u4e0a\u62a5\uff01");
                }
                executeTaskparam.setFormGroupKeys(new ArrayList(formGroupKeys));
            }
            LogInfo logInfo = this.batchWorkflowService.batchExecuteTask(executeTaskparam, (AsyncTaskMonitor)nrdxImportListenerMonitor);
            workflowUploadResult.setUploadSuccess(true);
            String resultMessage = this.buildUploadResult(logInfo);
            this.recordUploadResult(recordKey, true, resultMessage);
        }
        catch (Exception e) {
            this.recordUploadResult(recordKey, false, "\u6267\u884c\u4e0a\u62a5\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff0c\u4e0a\u62a5\u5931\u8d25\uff01");
            logger.error("\u5bfc\u5165\u540e\u6267\u884c\u4e0a\u62a5\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a{}", (Object)e.getMessage(), (Object)e);
            throw new TransferException("\u5bfc\u5165\u540e\u6267\u884c\u4e0a\u62a5\u8fc7\u7a0b\u4e2d\u53d1\u751f\u5f02\u5e38\uff1a" + e.getMessage(), (Throwable)e);
        }
    }

    private static DUserActionParam getDUserActionParam(ActionParam actionParam) {
        DUserActionParam userActionParam = new DUserActionParam();
        userActionParam.setCheckFormulaValue(actionParam.getCheckFormulaValue());
        userActionParam.setCalculateFormulaValue(actionParam.getCalcuteFormulaValue());
        userActionParam.setCheckCurrencyType(actionParam.getCheckCurrencyType());
        userActionParam.setCheckCurrencyValue(actionParam.getCheckCurrencyValue());
        userActionParam.setNodeCheckCurrencyType(actionParam.getNodeCheckCurrencyType());
        userActionParam.setNodeCheckCurrencyValue(actionParam.getNodeCheckCurrencyValue());
        return userActionParam;
    }

    private void recordUploadResult(String recKey, boolean success, String resultMessage) {
        ImportLog importLog = new ImportLog();
        importLog.setKey(UUIDUtils.getKey());
        importLog.setRecKey(recKey);
        importLog.setFactoryId(WORKFLOW_HANDLER_CODE);
        importLog.setDesc(resultMessage);
        if (success) {
            importLog.setState(ImportState.FINISHED.getCode());
        } else {
            importLog.setState(ImportState.FAILED.getCode());
        }
        this.importHistoryService.addImportLog(importLog);
    }

    private String buildUploadResult(LogInfo logInfo) {
        String errorMessage = "\u4e0a\u62a5\u5931\u8d25\uff01";
        if (logInfo == null) {
            return errorMessage;
        }
        BatchUploadRetrunInfo batchUploadRetrunInfo = logInfo.getBatchUploadRetrunInfo();
        UploadReturnInfo uploadReturnInfo = Optional.ofNullable(batchUploadRetrunInfo).map(BatchUploadRetrunInfo::getUploadReturnInfo).orElse(null);
        if (uploadReturnInfo == null) {
            return errorMessage;
        }
        int allEntityNums = uploadReturnInfo.getAllEntityNums();
        int successEntityNum = uploadReturnInfo.getSuccessEntityNum();
        int errorEntityNum = uploadReturnInfo.getErrorEntityNum();
        int otherErrorNum = uploadReturnInfo.getOtherErrorNum();
        return "\u4e0a\u62a5\u5b8c\u6210\uff01\u5171\u4e0a\u62a5" + allEntityNums + "\u5bb6\u5355\u4f4d\u3002\u5176\u4e2d\uff0c\u4e0a\u62a5\u6210\u529f" + successEntityNum + "\u5bb6\u5355\u4f4d\uff1b\u4e0a\u62a5\u5931\u8d25" + (errorEntityNum + otherErrorNum) + "\u5bb6\u5355\u4f4d\u3002";
    }
}

