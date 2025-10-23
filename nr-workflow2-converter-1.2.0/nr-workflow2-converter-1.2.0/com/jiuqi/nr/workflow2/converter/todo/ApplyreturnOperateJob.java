/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.JsonProcessingException
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob
 *  com.jiuqi.bi.core.jobs.realtime.RealTimeJob
 *  com.jiuqi.nr.definition.internal.BeanUtil
 *  com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask
 *  com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey
 *  com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey
 *  com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil
 *  com.jiuqi.nr.workflow2.engine.dflt.process.runtime.BusinessKeyUtil
 *  com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoBeanUtils
 *  com.jiuqi.nr.workflow2.service.IProcessExecuteService
 *  com.jiuqi.nr.workflow2.service.IProcessQueryService
 *  com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys
 *  com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus
 *  com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult
 *  com.jiuqi.nr.workflow2.service.execute.runtime.ProcessAsyncMonitor
 *  com.jiuqi.nr.workflow2.service.para.IProcessExecutePara
 *  com.jiuqi.nr.workflow2.service.para.IProcessRunPara
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneExecutePara
 *  com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara
 *  com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult
 *  com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoConsumeInfoImpl
 *  com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.converter.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.RealTimeJob;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.workflow2.converter.dataentry.manager.DataEntrySingleExecResultManager;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.core.event.runtime.IProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessRuntimeException;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessInstance;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IProcessTask;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.BusinessKey;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.common.RuntimeBusinessKey;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.ActorStrategyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.BusinessKeyUtil;
import com.jiuqi.nr.workflow2.engine.dflt.process.runtime.todo.TodoBeanUtils;
import com.jiuqi.nr.workflow2.service.IProcessExecuteService;
import com.jiuqi.nr.workflow2.service.IProcessQueryService;
import com.jiuqi.nr.workflow2.service.enumeration.IProcessFormRejectAttrKeys;
import com.jiuqi.nr.workflow2.service.enumeration.ProcessExecuteStatus;
import com.jiuqi.nr.workflow2.service.execute.runtime.IEventOperateResult;
import com.jiuqi.nr.workflow2.service.execute.runtime.ProcessAsyncMonitor;
import com.jiuqi.nr.workflow2.service.para.IProcessExecutePara;
import com.jiuqi.nr.workflow2.service.para.IProcessRunPara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneExecutePara;
import com.jiuqi.nr.workflow2.service.para.ProcessOneRunPara;
import com.jiuqi.nr.workflow2.service.result.IProcessExecuteResult;
import com.jiuqi.nr.workflow2.todo.entity.TodoConsumeInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoConsumeInfoImpl;
import com.jiuqi.nr.workflow2.todo.enumeration.TodoNodeType;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RealTimeJob(group="NR_WF_TODO_APPLYRETURNOPERATE", groupTitle="\u62a5\u8868\u4e0a\u62a5\u6d41\u7a0b\u7533\u8bf7\u9000\u56de\u5f85\u529e\u64cd\u4f5c")
public class ApplyreturnOperateJob
extends AbstractRealTimeJob {
    private static final long serialVersionUID = -6151657823277495483L;
    private static final String PARAMETERNAME = "PARAM";
    public static final ObjectMapper OBJECTMAPPER = new ObjectMapper();
    private static final String APPLYRETURN_MSG_GROUP = "\u6d88\u606f";
    private static final String APPLYRETURN_MSG_TYPE = "\u7533\u8bf7\u9000\u56de\u901a\u77e5";

    public ApplyreturnOperateJob() {
    }

    public ApplyreturnOperateJob(ApplyreturnOperateJobParameter parameter) {
        String paramJson;
        HashMap<String, String> params = new HashMap<String, String>();
        try {
            paramJson = OBJECTMAPPER.writeValueAsString((Object)parameter);
        }
        catch (JsonProcessingException e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u5f85\u529e\u4e8b\u9879\u5bf9\u8c61\u5e8f\u5217\u5316\u9519\u8bef\u3002");
        }
        params.put(PARAMETERNAME, paramJson);
        this.setParams(params);
    }

    private static ApplyreturnOperateJobParameter getParameter(JobContext context) {
        String paramJson = context.getParameterValue(PARAMETERNAME);
        try {
            return (ApplyreturnOperateJobParameter)OBJECTMAPPER.readValue(paramJson, ApplyreturnOperateJobParameter.class);
        }
        catch (Exception e) {
            throw new ProcessRuntimeException("jiuqi.nr.default", "\u5f85\u529e\u4e8b\u9879\u5bf9\u8c61\u53cd\u5e8f\u5217\u5316\u9519\u8bef\u3002");
        }
    }

    public void execute(JobContext context) throws JobExecutionException {
        try {
            ApplyreturnOperateJobParameter parameter = ApplyreturnOperateJob.getParameter(context);
            BusinessKey businessKey = new BusinessKey(parameter.getTaskKey(), parameter.getBusinessObject());
            if (parameter.isAgree()) {
                this.argee((IBusinessKey)businessKey, context);
            } else {
                this.noargee((IBusinessKey)businessKey, parameter.getComment(), context);
            }
        }
        catch (Exception e) {
            context.setResult(4, e.getMessage());
        }
    }

    private void argee(IBusinessKey businessKey, JobContext context) {
        IProcessQueryService processQueryService = (IProcessQueryService)BeanUtil.getBean(IProcessQueryService.class);
        ProcessOneRunPara runPara = new ProcessOneRunPara();
        runPara.setTaskKey(businessKey.getTask());
        IProcessInstance instance = processQueryService.queryInstances((IProcessRunPara)runPara, businessKey);
        if (instance == null) {
            context.setResult(4, "\u6d41\u7a0b\u5b9e\u4f8b\u4e0d\u5b58\u5728\u3002");
            return;
        }
        if (!instance.getCurrentUserTask().equals("tsk_audit")) {
            context.setResult(4, "\u6d41\u7a0b\u5df2\u6d41\u8f6c\uff0c\u4e0d\u652f\u6301\u9000\u56de\u64cd\u4f5c\u3002");
            return;
        }
        List tasks = processQueryService.queryCurrentTask((IProcessRunPara)runPara, businessKey);
        if (tasks == null || tasks.isEmpty()) {
            context.setResult(4, "\u60a8\u6ca1\u6709\u6743\u9650\u6267\u884c\u8be5\u64cd\u4f5c\u3002");
            return;
        }
        IProcessTask processTask = (IProcessTask)tasks.get(0);
        if (processTask.getActions().stream().noneMatch(o -> "act_reject".equals(o.getCode()))) {
            context.setResult(4, "\u6d41\u7a0b\u5df2\u6d41\u8f6c\uff0c\u4e0d\u652f\u6301\u9000\u56de\u64cd\u4f5c\u3002");
            return;
        }
        JSONObject executeVariables = new JSONObject();
        executeVariables.put(IProcessFormRejectAttrKeys.is_form_reject_button.attrKey, false);
        ProcessOneExecutePara executePara = new ProcessOneExecutePara();
        executePara.setActionCode("act_reject");
        executePara.setUserTaskCode(instance.getCurrentUserTask());
        executePara.setTaskId(processTask.getId());
        executePara.setEnvVariables(executeVariables);
        executePara.setTaskKey(businessKey.getTask());
        executePara.setPeriod((String)businessKey.getBusinessObject().getDimensions().getPeriodDimensionValue().getValue());
        IProcessExecuteService processExecuteService = (IProcessExecuteService)BeanUtil.getBean(IProcessExecuteService.class);
        DataEntrySingleExecResultManager resultManager = new DataEntrySingleExecResultManager(businessKey.getBusinessObject());
        Logger logger = LoggerFactory.getLogger(ApplyreturnOperateJob.class);
        ProcessAsyncMonitor monitor = new ProcessAsyncMonitor(context, logger, 100);
        try {
            IProcessExecuteResult execResul = processExecuteService.executeProcess((IProcessExecutePara)executePara, businessKey, (IProcessAsyncMonitor)monitor, (IEventOperateResult)resultManager);
            ProcessExecuteStatus executeStatus = execResul.getExecuteStatus();
            if (executeStatus.equals((Object)ProcessExecuteStatus.SUCCESS)) {
                context.setResult(100, null);
            } else if (executeStatus.equals((Object)ProcessExecuteStatus.ENV_CHECK_ERROR) || executeStatus.equals((Object)ProcessExecuteStatus.PRE_EVENT_CHECK_ERROR)) {
                context.setResult(-100, resultManager.toResultMessage());
            }
            if (executeStatus.equals((Object)ProcessExecuteStatus.SUCCESS)) {
                this.sendApplyReturnMessage(businessKey, instance.getProcessDefinitionId(), true, null);
            }
        }
        catch (Exception e) {
            context.setResult(4, e.getMessage());
        }
    }

    private void noargee(IBusinessKey businessKey, String comment, JobContext context) {
        ProcessOneRunPara runPara;
        IProcessQueryService processQueryService = (IProcessQueryService)BeanUtil.getBean(IProcessQueryService.class);
        IProcessInstance instance = processQueryService.queryInstances((IProcessRunPara)(runPara = new ProcessOneRunPara()), businessKey);
        if (instance == null) {
            context.setResult(4, "\u6d41\u7a0b\u5b9e\u4f8b\u4e0d\u5b58\u5728\u3002");
            return;
        }
        if (!instance.getCurrentUserTask().equals("tsk_audit")) {
            context.setResult(4, "\u6d41\u7a0b\u5df2\u6d41\u8f6c\uff0c\u4e0d\u652f\u6301\u9000\u56de\u64cd\u4f5c\u3002");
            return;
        }
        TodoConsumeInfoImpl consumeInfo = new TodoConsumeInfoImpl();
        consumeInfo.setWorkflowInstance(instance.getId());
        consumeInfo.setWorkflowNodeTask(TodoNodeType.REQUEST_REJECT.name());
        TodoBeanUtils.getTodoManipulationService().consumeTodo((TodoConsumeInfo)consumeInfo);
        this.sendApplyReturnMessage(businessKey, instance.getProcessDefinitionId(), false, comment);
        context.setResult(100, null);
    }

    private void sendApplyReturnMessage(IBusinessKey businessKey, String processDefinitionId, boolean isAgree, String comment) {
        UserTask reportUserTask = (UserTask)TodoBeanUtils.getProcessDefinitionService().getUserTask(processDefinitionId, "tsk_upload");
        if (reportUserTask == null) {
            return;
        }
        BusinessKeyUtil businessKeyUtil = (BusinessKeyUtil)BeanUtil.getBean(BusinessKeyUtil.class);
        RuntimeBusinessKey rtBusinessKey = businessKeyUtil.buildRuntimeBusinessKey(businessKey);
        List participants = ActorStrategyUtil.getInstance().getActors((Collection)reportUserTask.getTodoReceivers(), rtBusinessKey);
        VaMessageSendDTO dto = new VaMessageSendDTO();
        dto.setGrouptype(APPLYRETURN_MSG_GROUP);
        dto.setMsgtype(APPLYRETURN_MSG_TYPE);
        dto.setMsgChannel(VaMessageOption.MsgChannel.PC);
        dto.setReceiveUserIds(participants);
        if (isAgree) {
            dto.setTitle("\u7533\u8bf7\u9000\u56de\u901a\u8fc7");
            dto.setContent(businessKeyUtil.getTitle(rtBusinessKey) + "\u7533\u8bf7\u9000\u56de\u5df2\u901a\u8fc7.\u3002");
        } else {
            dto.setTitle("\u7533\u8bf7\u9000\u56de\u9a73\u56de");
            if (comment == null || comment == "") {
                dto.setContent(businessKeyUtil.getTitle(rtBusinessKey) + "\u7533\u8bf7\u9000\u56de\u88ab\u9a73\u56de.\u3002");
            } else {
                dto.setContent(businessKeyUtil.getTitle(rtBusinessKey) + "\u7533\u8bf7\u9000\u56de\u88ab\u9a73\u56de\uff0c\u9a73\u56de\u539f\u56e0\u662f" + comment);
            }
        }
        ((VaMessageClient)BeanUtil.getBean(VaMessageClient.class)).addMsg(dto);
    }

    public static class ApplyreturnOperateJobParameter {
        private String taskKey;
        private IBusinessObject businessObject;
        private boolean agree;
        private String comment;

        public String getTaskKey() {
            return this.taskKey;
        }

        public void setTaskKey(String taskKey) {
            this.taskKey = taskKey;
        }

        public IBusinessObject getBusinessObject() {
            return this.businessObject;
        }

        public void setBusinessObject(IBusinessObject businessObject) {
            this.businessObject = businessObject;
        }

        public boolean isAgree() {
            return this.agree;
        }

        public void setAgree(boolean agree) {
            this.agree = agree;
        }

        public String getComment() {
            return this.comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}

