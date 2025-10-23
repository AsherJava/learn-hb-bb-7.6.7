/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam
 *  com.jiuqi.nr.dataentry.bean.LogInfo
 *  com.jiuqi.nr.dataentry.internal.service.BatchWorkflowServiceImpl
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollection
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam
 *  com.jiuqi.nr.workflow2.service.IProcessExecuteService
 *  com.jiuqi.nr.workflow2.service.IProcessMetaDataService
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessEventExecuteAttrKeys
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult
 *  com.jiuqi.nr.workflow2.service.helper.ProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 *  com.jiuqi.nr.workflow2.service.para.ProcessExecutePara
 *  com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.dataentry.execute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.bpm.de.dataflow.util.IWorkFlowDimensionBuilder;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.LogInfo;
import com.jiuqi.nr.dataentry.internal.service.BatchWorkflowServiceImpl;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollection;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.workflow2.converter.dataentry.manager.DataEntryBatchExecResultManager;
import com.jiuqi.nr.workflow2.converter.dataentry.manager.dto.BatchExecManagerDTO;
import com.jiuqi.nr.workflow2.converter.dataentry.monitor.AsyncTaskMonitorConverter;
import com.jiuqi.nr.workflow2.converter.utils.ConverterUtil;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.defintion.IUserAction;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKeyCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessObjectCollection;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.IDimensionObjectMapping;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam;
import com.jiuqi.nr.workflow2.service.IProcessExecuteService;
import com.jiuqi.nr.workflow2.service.IProcessMetaDataService;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessEventExecuteAttrKeys;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult;
import com.jiuqi.nr.workflow2.service.helper.ProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessExecutePara;
import com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DataEntryWorkflowBatchExecuteConverter
extends BatchWorkflowServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(DataEntryWorkflowBatchExecuteConverter.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IProcessExecuteService processExecuteService;
    @Autowired
    private IProcessMetaDataService processMetaDataService;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private IWorkFlowDimensionBuilder workFlowDimensionBuilder;
    @Autowired
    private ProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private ConverterUtil converterUtil;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public LogInfo batchExecuteTask(BatchExecuteTaskParam param, AsyncTaskMonitor asyncTaskMonitor) {
        JtableContext context = param.getContext();
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskKey())) {
            return super.batchExecuteTask(param, asyncTaskMonitor);
        }
        Map dimensionSet = context.getDimensionSet();
        DimensionValue dimensionValue = (DimensionValue)dimensionSet.get("DATATIME");
        String period = dimensionValue.getValue();
        FormSchemeDefine formSchemeDefine = this.runTimeViewController.getFormScheme(context.getFormSchemeKey());
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(context.getTaskKey());
        DimensionCollection dimensionCollection = this.workFlowDimensionBuilder.buildDimensionCollection(param.getContext().getTaskKey(), context.getDimensionSet());
        BusinessKeyCollection businessKeyCollection = null;
        List<String> formKeys = param.getFormKeys();
        List formGroupKeys = param.getFormGroupKeys();
        JSONObject executeVariable = new JSONObject();
        executeVariable.put("COMMENT", (Object)param.getComment());
        executeVariable.put("RETURN_TYPE", (Object)param.getReturnType());
        executeVariable.put("FORCE_REPORT", param.isForceCommit());
        executeVariable.put(IProcessEventExecuteAttrKeys.ENV_DIMENSION_VALUE_MAP.attrKey, (Object)this.transferObjectToJsonString(context.getDimensionSet()));
        executeVariable.put(IProcessEventExecuteAttrKeys.BATCH_EXECUTE_TASK_PARAM.attrKey, (Object)this.transferObjectToJsonString(param));
        boolean singleRejectAction = param.getUserActionParam().isSingleRejectAction();
        executeVariable.put(IProcessFormRejectAttrKeys.is_form_reject_button.attrKey, singleRejectAction);
        if (singleRejectAction) {
            if (formKeys == null || formKeys.isEmpty()) {
                formKeys = this.runTimeViewController.listFormByFormScheme(context.getFormSchemeKey()).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            }
            FormRejectExecuteParam formRejectExecuteParam = new FormRejectExecuteParam();
            formRejectExecuteParam.setRejectFormIds((List)formKeys);
            formRejectExecuteParam.setComment(param.getComment());
            executeVariable.put(IProcessFormRejectAttrKeys.process_form_reject.attrKey, (Object)this.transferObjectToJsonString(formRejectExecuteParam));
        }
        executeVariable.put(IProcessEventExecuteAttrKeys.SEND_MAIL.attrKey, param.isSendEmail());
        executeVariable.put(IProcessEventExecuteAttrKeys.IS_TODO_ENABLED.attrKey, this.converterUtil.isSystemTodoEnabled() && this.workflowSettingsService.queryTaskTodoEnable(context.getTaskKey()));
        ProcessExecutePara processExecutePara = new ProcessExecutePara();
        processExecutePara.setTaskKey(context.getTaskKey());
        processExecutePara.setPeriod(period);
        processExecutePara.setTaskId(param.getTaskId());
        processExecutePara.setActionCode(param.getActionId());
        processExecutePara.setUserTaskCode(this.getTaskCode(param));
        processExecutePara.setEnvVariables(executeVariable);
        BatchExecManagerDTO execManagerDTO = new BatchExecManagerDTO();
        execManagerDTO.setFormSchemeDefine(formSchemeDefine);
        execManagerDTO.setPeriod(period);
        execManagerDTO.setWorkflowObjectType(workflowObjectType);
        execManagerDTO.setFormKeys(formKeys);
        execManagerDTO.setFormGroupKeys(formGroupKeys);
        if (workflowObjectType.equals((Object)WorkflowObjectType.MAIN_DIMENSION)) {
            businessKeyCollection = new BusinessKeyCollection(context.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newDimensionObjectCollection((DimensionCollection)dimensionCollection));
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            if (formKeys == null || formKeys.isEmpty()) {
                formKeys = this.runTimeViewController.listFormByFormScheme(context.getFormSchemeKey()).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            }
            IDimensionObjectMapping dimensionObjectMapping = this.processDimensionsBuilder.processDimToFormDefinesMap(formSchemeDefine, dimensionCollection, formKeys);
            businessKeyCollection = new BusinessKeyCollection(context.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)dimensionObjectMapping));
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            if (formGroupKeys == null || formGroupKeys.isEmpty()) {
                formGroupKeys = this.runTimeViewController.listFormGroupByFormScheme(context.getFormSchemeKey()).stream().sorted(Comparator.comparing(IBaseMetaItem::getOrder)).map(IBaseMetaItem::getKey).collect(Collectors.toList());
            }
            IDimensionObjectMapping dimensionObjectMapping = this.processDimensionsBuilder.processDimToGroupDefinesMap(formSchemeDefine, dimensionCollection, (Collection)formGroupKeys);
            businessKeyCollection = new BusinessKeyCollection(context.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newFormGroupObjectCollection((DimensionCollection)dimensionCollection, (IDimensionObjectMapping)dimensionObjectMapping));
        } else if (WorkflowObjectType.MD_WITH_SFR.equals((Object)workflowObjectType)) {
            businessKeyCollection = new BusinessKeyCollection(context.getTaskKey(), (IBusinessObjectCollection)BusinessObjectCollection.newDimensionObjectCollection((DimensionCollection)dimensionCollection));
        }
        if (businessKeyCollection == null) {
            LoggerFactory.getLogger(((Object)((Object)this)).getClass()).error("businessKeyCollection is null!");
            return new LogInfo();
        }
        IUserAction userAction = this.processMetaDataService.queryAction(context.getTaskKey(), param.getTaskCode(), param.getActionId());
        LogInfo logInfo = new LogInfo();
        logInfo.setActionName(userAction.getAlias());
        logInfo.setLogInfo("\u6267\u884c\u5b8c\u6bd5");
        execManagerDTO.setLogInfo(logInfo);
        DataEntryBatchExecResultManager resultManager = new DataEntryBatchExecResultManager(businessKeyCollection.getBusinessObjects(), asyncTaskMonitor, execManagerDTO);
        try {
            AsyncTaskMonitorConverter monitor = new AsyncTaskMonitorConverter(asyncTaskMonitor);
            IProcessExecuteResult executeResult = this.processExecuteService.executeProcess((IProcessExecutePara)processExecutePara, (IBusinessKeyCollection)businessKeyCollection, (IProcessAsyncMonitor)monitor, (IEventOperateResult)resultManager);
            execManagerDTO.setUserAction(executeResult.getUserAction());
            resultManager.setUserAction(executeResult.getUserAction());
            if (ProcessExecuteStatus.ENV_CHECK_ERROR == executeResult.getExecuteStatus()) {
                monitor.setJobResult(AsyncJobResult.FAILURE, executeResult.getExecuteMessage());
            } else if (ProcessExecuteStatus.PRE_EVENT_CHECK_ERROR == executeResult.getExecuteStatus()) {
                monitor.setJobResult(AsyncJobResult.FAILURE, resultManager.toResultMessage(), resultManager.toOutputDetail());
            } else if (ProcessExecuteStatus.SUCCESS == executeResult.getExecuteStatus()) {
                monitor.setJobResult(resultManager.toAsyncJobResult(), resultManager.toResultMessage(), resultManager.toOutputDetail());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return logInfo;
    }

    private String transferObjectToJsonString(Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(object);
        }
        catch (JsonProcessingException e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    private String getTaskCode(BatchExecuteTaskParam param) {
        String taskCode = param.getTaskCode();
        if (taskCode == null || taskCode.isEmpty()) {
            String actionId = param.getActionId();
            if (actionId.equals("act_upload")) {
                taskCode = "tsk_upload";
            } else if (actionId.equals("act_reject")) {
                taskCode = "tsk_audit";
            }
            param.setTaskCode(taskCode);
        }
        return taskCode;
    }
}

