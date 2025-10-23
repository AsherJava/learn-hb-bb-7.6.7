/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.IDataAccessProvider
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController
 *  com.jiuqi.np.definition.facade.EntityViewDefine
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.dataentry.bean.ExecuteTaskParam
 *  com.jiuqi.nr.dataentry.bean.StepByStepCheckResult
 *  com.jiuqi.nr.dataentry.internal.service.DataentryWorkflowServiceImpl
 *  com.jiuqi.nr.definition.api.IRunTimeViewController
 *  com.jiuqi.nr.entity.engine.executors.ExecutorContext
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService
 *  com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType
 *  com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge
 *  com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam
 *  com.jiuqi.nr.workflow2.service.IProcessExecuteService
 *  com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessEventExecuteAttrKeys
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult
 *  com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneDim
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneExecutePara
 *  com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.dataentry.execute;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.IDataAccessProvider;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.controller.IDataDefinitionRuntimeController;
import com.jiuqi.np.definition.facade.EntityViewDefine;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.StepByStepCheckResult;
import com.jiuqi.nr.dataentry.internal.service.DataentryWorkflowServiceImpl;
import com.jiuqi.nr.definition.api.IRunTimeViewController;
import com.jiuqi.nr.entity.engine.executors.ExecutorContext;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.workflow2.converter.dataentry.manager.DataEntrySingleExecResultManager;
import com.jiuqi.nr.workflow2.converter.dataentry.monitor.AsyncTaskMonitorConverter;
import com.jiuqi.nr.workflow2.converter.utils.ConverterUtil;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.settings.WorkflowSettingsService;
import com.jiuqi.nr.workflow2.engine.core.settings.enumeration.WorkflowObjectType;
import com.jiuqi.nr.workflow2.engine.core.settings.utils.DefaultEngineVersionJudge;
import com.jiuqi.nr.workflow2.form.reject.ext.service.FormRejectExecuteParam;
import com.jiuqi.nr.workflow2.service.IProcessExecuteService;
import com.jiuqi.nr.workflow2.service.dimension.EProcessDimensionName;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessEventExecuteAttrKeys;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult;
import com.jiuqi.nr.workflow2.service.helper.IProcessDimensionsBuilder;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneDim;
import com.jiuqi.nr.workflow2.service.para.ProcessOneExecutePara;
import com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DataEntryWorkflowSingleExecuteConverter
extends DataentryWorkflowServiceImpl {
    private static final Logger logger = LoggerFactory.getLogger(DataEntryWorkflowSingleExecuteConverter.class);
    @Autowired
    private IRunTimeViewController runTimeViewController;
    @Autowired
    private IDataDefinitionRuntimeController dataDefinitionRuntimeController;
    @Autowired
    private IDataAccessProvider dataAccessProvider;
    @Autowired
    private IProcessExecuteService processExecuteService;
    @Autowired
    private IProcessDimensionsBuilder processDimensionsBuilder;
    @Autowired
    private WorkflowSettingsService workflowSettingsService;
    @Autowired
    private ConverterUtil converterUtil;
    @Autowired
    private DefaultEngineVersionJudge defaultEngineVersionJudge;

    public void dataentryExecuteTask(ExecuteTaskParam param, AsyncTaskMonitor asyncTaskMonitor) {
        JtableContext context = param.getContext();
        if (this.defaultEngineVersionJudge.isTaskAndEngineVersion_1_0(context.getTaskKey())) {
            super.dataentryExecuteTask(param, asyncTaskMonitor);
            return;
        }
        WorkflowObjectType workflowObjectType = this.workflowSettingsService.queryTaskWorkflowObjectType(context.getTaskKey());
        Map dimensionSet = param.getContext().getDimensionSet();
        DimensionValue dimensionValue = (DimensionValue)dimensionSet.get("DATATIME");
        String period = dimensionValue.getValue();
        String dimensionName = this.getMainDimName(param.getContext().getFormSchemeKey());
        DimensionValue unitDim = (DimensionValue)dimensionSet.get(dimensionName);
        String unitKey = unitDim.getValue();
        HashSet<ProcessOneDim> oneDims = new HashSet<ProcessOneDim>();
        ProcessOneDim unitOneDim = new ProcessOneDim();
        unitOneDim.setDimensionName(dimensionName);
        unitOneDim.setDimensionKey(param.getContextEntityId());
        unitOneDim.setDimensionValue(unitKey);
        oneDims.add(unitOneDim);
        if (workflowObjectType.equals((Object)WorkflowObjectType.FORM)) {
            ProcessOneDim formOneDim = new ProcessOneDim();
            formOneDim.setDimensionName(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionKey(EProcessDimensionName.PROCESS_FORM.dimName);
            formOneDim.setDimensionValue(context.getFormKey());
            oneDims.add(formOneDim);
        } else if (workflowObjectType.equals((Object)WorkflowObjectType.FORM_GROUP)) {
            ProcessOneDim formGroupOneDim = new ProcessOneDim();
            formGroupOneDim.setDimensionName(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionKey(EProcessDimensionName.PROCESS_GROUP.dimName);
            formGroupOneDim.setDimensionValue(context.getFormGroupKey());
            oneDims.add(formGroupOneDim);
        }
        JSONObject executeVariable = new JSONObject();
        executeVariable.put("COMMENT", (Object)param.getComment());
        executeVariable.put("RETURN_TYPE", (Object)param.getReturnType());
        executeVariable.put("FORCE_REPORT", param.isForceCommit());
        executeVariable.put(IProcessEventExecuteAttrKeys.ENV_DIMENSION_VALUE_MAP.attrKey, (Object)this.transferObjectToJsonString(context.getDimensionSet()));
        executeVariable.put(IProcessEventExecuteAttrKeys.EXECUTE_TASK_PARAM.attrKey, (Object)this.transferObjectToJsonString(param));
        boolean singleRejectAction = param.getUserActionParam().isSingleRejectAction();
        executeVariable.put(IProcessFormRejectAttrKeys.is_form_reject_button.attrKey, singleRejectAction);
        if (singleRejectAction) {
            FormRejectExecuteParam formRejectExecuteParam = new FormRejectExecuteParam();
            List rejectFormKeys = param.getRejectFormKeys();
            if (rejectFormKeys != null && rejectFormKeys.size() == 1) {
                formRejectExecuteParam.setFormId((String)rejectFormKeys.get(0));
            } else {
                formRejectExecuteParam.setFormId(context.getFormKey());
            }
            formRejectExecuteParam.setComment(param.getComment());
            executeVariable.put(IProcessFormRejectAttrKeys.process_form_reject.attrKey, (Object)this.transferObjectToJsonString(formRejectExecuteParam));
        }
        executeVariable.put(IProcessEventExecuteAttrKeys.SEND_MAIL.attrKey, param.isSendEmail());
        executeVariable.put(IProcessEventExecuteAttrKeys.IS_TODO_ENABLED.attrKey, this.converterUtil.isSystemTodoEnabled() && this.workflowSettingsService.queryTaskTodoEnable(context.getTaskKey()));
        ProcessOneExecutePara oneExecutePara = new ProcessOneExecutePara();
        oneExecutePara.setTaskKey(context.getTaskKey());
        oneExecutePara.setPeriod(period);
        oneExecutePara.setTaskId(param.getTaskId());
        oneExecutePara.setUserTaskCode(param.getTaskCode());
        oneExecutePara.setActionCode(param.getActionCode());
        oneExecutePara.setReportDimensions(oneDims);
        oneExecutePara.setEnvVariables(executeVariable);
        IBusinessKey businessKey = this.processDimensionsBuilder.buildBusinessKey(oneExecutePara);
        try {
            AsyncTaskMonitorConverter monitor = new AsyncTaskMonitorConverter(asyncTaskMonitor);
            DataEntrySingleExecResultManager resultManager = new DataEntrySingleExecResultManager(businessKey.getBusinessObject());
            IProcessExecuteResult executeResult = this.processExecuteService.executeProcess((IProcessExecutePara)oneExecutePara, businessKey, (IProcessAsyncMonitor)monitor, (IEventOperateResult)resultManager);
            if (ProcessExecuteStatus.ENV_CHECK_ERROR == executeResult.getExecuteStatus()) {
                monitor.setJobResult(AsyncJobResult.FAILURE, executeResult.getExecuteMessage(), executeResult.getExecuteMessage());
            } else if (ProcessExecuteStatus.PRE_EVENT_CHECK_ERROR == executeResult.getExecuteStatus()) {
                monitor.setJobResult(AsyncJobResult.FAILURE, resultManager.toResultMessage(), resultManager.toOutputDetail());
            } else if (ProcessExecuteStatus.SUCCESS == executeResult.getExecuteStatus()) {
                monitor.setJobResult(AsyncJobResult.SUCCESS, "success", resultManager.toOutputDetail());
            }
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    public StepByStepCheckResult stepByStepUploadResult(String asyncTaskID) {
        return super.stepByStepUploadResult(asyncTaskID);
    }

    private String getMainDimName(String formSchemeKey) {
        EntityViewDefine entityViewDefine = this.runTimeViewController.getViewByFormSchemeKey(formSchemeKey);
        ExecutorContext context = new ExecutorContext(this.dataDefinitionRuntimeController);
        IDataAssist dataAssist = this.dataAccessProvider.newDataAssist((com.jiuqi.np.dataengine.executors.ExecutorContext)context);
        return dataAssist.getDimensionName(entityViewDefine);
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
}

