/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.message.constants.HandleModeEnum
 *  com.jiuqi.np.message.constants.MessageTypeEnum
 *  com.jiuqi.np.message.constants.ParticipantTypeEnum
 *  com.jiuqi.np.message.pojo.MessageDTO
 *  com.jiuqi.np.message.service.MessagePipelineService
 *  com.jiuqi.nr.context.cxt.DsContextHolder
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.workflow2.todo.entity.TodoInfo
 *  com.jiuqi.nr.workflow2.todo.entityimpl.TodoInfoImpl
 *  com.jiuqi.nr.workflow2.todo.service.TodoManipulationServiceImpl
 *  com.jiuqi.va.message.domain.VaMessageOption$MsgChannel
 *  com.jiuqi.va.message.domain.VaMessageSendDTO
 *  com.jiuqi.va.message.feign.client.VaMessageClient
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg;

import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.message.constants.HandleModeEnum;
import com.jiuqi.np.message.constants.MessageTypeEnum;
import com.jiuqi.np.message.constants.ParticipantTypeEnum;
import com.jiuqi.np.message.pojo.MessageDTO;
import com.jiuqi.np.message.service.MessagePipelineService;
import com.jiuqi.nr.bpm.ProcessEngine;
import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.common.Task;
import com.jiuqi.nr.bpm.common.UserTask;
import com.jiuqi.nr.bpm.de.dataflow.common.TodoMsgToOtherEvent;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.WfSendTodoConfig;
import com.jiuqi.nr.bpm.de.dataflow.service.IWorkflow;
import com.jiuqi.nr.bpm.de.dataflow.service.impl.QueryParticipants;
import com.jiuqi.nr.bpm.de.dataflow.util.CommonUtil;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.countersign.CountSignStartMode;
import com.jiuqi.nr.bpm.impl.countersign.group.CounterSignConst;
import com.jiuqi.nr.bpm.impl.upload.modeling.ProcessBuilderUtils;
import com.jiuqi.nr.bpm.service.RunTimeService;
import com.jiuqi.nr.context.cxt.DsContextHolder;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.workflow2.todo.entity.TodoInfo;
import com.jiuqi.nr.workflow2.todo.entityimpl.TodoInfoImpl;
import com.jiuqi.nr.workflow2.todo.service.TodoManipulationServiceImpl;
import com.jiuqi.va.message.domain.VaMessageOption;
import com.jiuqi.va.message.domain.VaMessageSendDTO;
import com.jiuqi.va.message.feign.client.VaMessageClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class SendBaseParam {
    private static final Logger logger = LoggerFactory.getLogger(SendBaseParam.class);
    @Autowired
    MessagePipelineService messagePipelineService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    QueryParticipants queryParticipants;
    @Autowired
    CommonUtil commonUtil;
    @Autowired
    IWorkflow workflow;
    @Autowired
    private VaMessageClient messageClient;
    @Autowired
    private TodoManipulationServiceImpl todoManipulationService;
    @Value(value="${jiuqi.nr.todo.version:2.0}")
    protected String todoVersion;
    @Autowired
    private WfSendTodoConfig sendTodoConfig;
    @Value(value="${jiuqi.nr.todo.use.gd.gzw.mode:false}")
    private boolean useGD_GZW_MODE;
    @Autowired
    private NrParameterUtils nrParameterUtils;
    @Autowired
    private RoleService roleService;

    public Optional<ProcessEngine> processEngine(String formSchemeKey) {
        return this.workflow.getProcessEngine(formSchemeKey);
    }

    public Task getTasks(Optional<ProcessEngine> processEngine, BusinessKey businessKey) {
        RunTimeService runtimeService = processEngine.map(engine -> engine.getRunTimeService()).orElse(null);
        Task afterTask = null;
        List<Task> tasks = runtimeService.queryTaskByBusinessKey(businessKey.toString());
        if (tasks.size() > 0) {
            afterTask = tasks.get(0);
        }
        return afterTask;
    }

    public Set<String> getSuperiorActor(Optional<ProcessEngine> processEngine, Task task, BusinessKey businessKey) {
        return this.queryParticipants.getSuperiorActor(processEngine, task, businessKey);
    }

    public Set<String> getCurrenActor(Optional<ProcessEngine> processEngine, Task afterTask, BusinessKey businessKey) {
        return this.queryParticipants.getCurrenActor(processEngine, afterTask, businessKey);
    }

    public Set<String> getActors(UserTask userTask, BusinessKey businessKey, Task task) {
        return this.queryParticipants.getActors(userTask, businessKey, task);
    }

    public void sendMessage(Task task, String taskId, String content, List<String> participants, Map<String, String> param) {
        if (this.todoVersion.equals("1.0")) {
            String actionName = param.get("action");
            MessageDTO messageDTO = MessageDTO.buildDefaultTodoMessage((String)(actionName + "\u901a\u77e5"), (String)"dataentry", param, (ParticipantTypeEnum)ParticipantTypeEnum.USER, participants);
            messageDTO.setContent(content);
            messageDTO.setTodoType(actionName + "\u901a\u77e5");
            messageDTO.setActionName(actionName);
            messageDTO.setType(MessageTypeEnum.NOTICE.getCode());
            ArrayList<Integer> handleMode = new ArrayList<Integer>();
            handleMode.add(HandleModeEnum.SYSTEM.getCode());
            messageDTO.setHandleMode(handleMode);
            String msgid = this.msgId(taskId, param);
            messageDTO.setId(msgid);
            this.messagePipelineService.send(messageDTO);
        } else if (this.todoVersion.equals("2.0")) {
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
    }

    public void sendDaiBan(Set<String> formOrGroupKeys, String taskId, String taskCode, String defaultContent, Map<String, String> commonInfo, List<String> currenActor) {
        if (formOrGroupKeys != null && formOrGroupKeys.size() > 0) {
            for (String formOrGroupKey : formOrGroupKeys) {
                this.sendDaiBan(taskId, taskCode, defaultContent, currenActor, commonInfo, formOrGroupKey);
            }
        } else {
            this.sendDaiBan(taskId, taskCode, defaultContent, currenActor, commonInfo, null);
        }
    }

    public void sendDaiBan(String taskId, String taskCode, String content, List<String> participants, Map<String, String> param) {
        List<String> participantList;
        String unit;
        String period;
        String taskKey;
        boolean send;
        if (this.useGD_GZW_MODE && !(send = this.sendTodoConfig.get(taskKey = param.get("taskId"), period = param.get("period"), unit = param.get("unitId")))) {
            return;
        }
        String msgid = this.msgId(taskCode, param);
        if (this.todoVersion.equals("1.0")) {
            String actionName = param.get("action");
            participantList = this.fliterParticipant(participants, param.get("formSchemeId"), taskCode);
            MessageDTO messageDTO = MessageDTO.buildDefaultTodoMessage((String)(actionName + "\u901a\u77e5"), (String)"dataentry", param, (ParticipantTypeEnum)ParticipantTypeEnum.USER, participantList);
            messageDTO.setContent(content);
            messageDTO.setTodoType(actionName + "\u901a\u77e5");
            messageDTO.setActionName(actionName);
            messageDTO.setType(MessageTypeEnum.TODO.getCode());
            ArrayList<Integer> handleMode = new ArrayList<Integer>();
            handleMode.add(HandleModeEnum.SYSTEM.getCode());
            messageDTO.setHandleMode(handleMode);
            messageDTO.setId(msgid);
            this.messagePipelineService.send(messageDTO);
        } else if (this.todoVersion.equals("2.0")) {
            TodoInfoImpl todoInfo = new TodoInfoImpl();
            todoInfo.setTaskId(param.get("taskId"));
            todoInfo.setBusinessKey(msgid);
            todoInfo.setBusinessTitle(content);
            todoInfo.setWorkflowNodeTask(taskId);
            participantList = this.fliterParticipant(participants, param.get("formSchemeId"), taskCode);
            todoInfo.setParticipants(participantList);
            todoInfo.setWorkflowNode(param.get("todoType"));
            todoInfo.setWorkflowInstance(ProcessBuilderUtils.produceUUIDKey(msgid));
            todoInfo.setFormSchemeKey(param.get("formSchemeId"));
            todoInfo.setPeriod(param.get("period"));
            todoInfo.setRemark(param.get("content"));
            this.todoManipulationService.createUpdateTodo((TodoInfo)todoInfo);
        }
        TodoMsgToOtherEvent event = new TodoMsgToOtherEvent();
        event.setMessageId(msgid);
        event.setUserId(this.commonUtil.getCurrentUserId());
        String actionId = param.get("actionId");
        event.setActionId(actionId);
        event.setAppName("dataentry");
        param.put("contextEntityId", DsContextHolder.getDsContext().getContextEntityId());
        event.setExtendParams(param);
        event.setParticipants(this.fliterParticipant(participants, param.get("formSchemeId"), taskCode));
        String currentActionCode = param.get("currentActionId");
        event.setCurrentActionCode(currentActionCode);
        this.applicationContext.publishEvent(event);
    }

    public void sendDaiBan(String taskId, String taskCode, String content, List<String> participants, Map<String, String> param, String reportId) {
        List<String> participantList;
        String unit;
        String period;
        String taskKey;
        boolean send;
        if (this.useGD_GZW_MODE && !(send = this.sendTodoConfig.get(taskKey = param.get("taskId"), period = param.get("period"), unit = param.get("unitId")))) {
            return;
        }
        String msgid = null;
        if (this.todoVersion.equals("1.0")) {
            String actionName = param.get("action");
            participantList = this.fliterParticipant(participants, param.get("formSchemeId"), taskCode);
            MessageDTO messageDTO = MessageDTO.buildDefaultTodoMessage((String)(actionName + "\u901a\u77e5"), (String)"dataentry", param, (ParticipantTypeEnum)ParticipantTypeEnum.USER, participantList);
            messageDTO.setContent(content);
            messageDTO.setTodoType(actionName + "\u901a\u77e5");
            messageDTO.setActionName(actionName);
            messageDTO.setType(MessageTypeEnum.TODO.getCode());
            ArrayList<Integer> handleMode = new ArrayList<Integer>();
            handleMode.add(HandleModeEnum.SYSTEM.getCode());
            messageDTO.setHandleMode(handleMode);
            msgid = this.msgId(taskCode, reportId, param);
            messageDTO.setId(msgid);
            this.messagePipelineService.send(messageDTO);
        } else if (this.todoVersion.equals("2.0")) {
            msgid = this.msgId(taskCode, param);
            TodoInfoImpl todoInfo = new TodoInfoImpl();
            todoInfo.setTaskId(param.get("taskId"));
            todoInfo.setBusinessKey(msgid);
            todoInfo.setBusinessTitle(content);
            todoInfo.setWorkflowNodeTask(taskId);
            participantList = this.fliterParticipant(participants, param.get("formSchemeId"), taskCode);
            todoInfo.setParticipants(participantList);
            todoInfo.setWorkflowNode(param.get("todoType"));
            todoInfo.setWorkflowInstance(ProcessBuilderUtils.produceUUIDKey(msgid));
            todoInfo.setFormSchemeKey(param.get("formSchemeId"));
            todoInfo.setPeriod(param.get("period"));
            todoInfo.setRemark(param.get("content"));
            this.todoManipulationService.createTodo((TodoInfo)todoInfo);
        }
        TodoMsgToOtherEvent event = new TodoMsgToOtherEvent();
        event.setMessageId(msgid);
        event.setUserId(this.commonUtil.getCurrentUserId());
        String actionId = param.get("actionId");
        event.setActionId(actionId);
        event.setAppName("dataentry");
        param.put("contextEntityId", DsContextHolder.getDsContext().getContextEntityId());
        event.setExtendParams(param);
        event.setParticipants(this.fliterParticipant(participants, param.get("formSchemeId"), taskCode));
        String currentActionCode = param.get("currentActionId");
        event.setCurrentActionCode(currentActionCode);
        this.applicationContext.publishEvent(event);
    }

    public void sendEmail(String taskId, String content, List<String> participants, Map<String, String> param) {
        String actionName = param.get("action");
        VaMessageSendDTO dto = new VaMessageSendDTO();
        dto.setGrouptype("\u4ee3\u529e");
        dto.setTitle(actionName + "\u901a\u77e5");
        dto.setMsgtype(actionName + "\u901a\u77e5");
        dto.setReceiveUserIds(participants);
        dto.setContent(content);
        dto.setMsgChannel(VaMessageOption.MsgChannel.EMAIL);
        this.messageClient.addMsg(dto);
    }

    private String msgId(String taskCode, Map<String, String> param) {
        String formSchemeKey = param.get("formSchemeId");
        String period = param.get("period");
        String unitId = param.get("unitId");
        String formOrGroupKey = param.get("reportId");
        String adjust = param.get("adjust");
        String corporateValue = param.get("corporateValue");
        WorkFlowType startType = this.workflow.queryStartType(param.get("formSchemeId"));
        return this.workflow.getMessageId(formSchemeKey, period, unitId, adjust, formOrGroupKey, formOrGroupKey, startType, taskCode, corporateValue);
    }

    private String msgId(String taskCode, String reportId, Map<String, String> param) {
        String msgid = null;
        WorkFlowType startType = this.workflow.queryStartType(param.get("formSchemeId"));
        if (WorkFlowType.ENTITY.equals((Object)startType)) {
            msgid = param.get("formSchemeId") + ";" + param.get("period") + ";" + param.get("unitId") + ";" + taskCode;
        } else if (WorkFlowType.FORM.equals((Object)startType) || WorkFlowType.GROUP.equals((Object)startType)) {
            msgid = param.get("formSchemeId") + ";" + param.get("period") + ";" + param.get("unitId") + ";" + reportId + ";" + taskCode;
        }
        return msgid;
    }

    private List<String> fliterParticipant(List<String> participants, String formSchemeKey, String taskCode) {
        CountSignStartMode countSignStartMode;
        Set<String> actors;
        Object object;
        ArrayList usersByRoleKey = new ArrayList();
        NpContext context = NpContextHolder.getContext();
        ContextExtension signExtension = context.getExtension(CounterSignConst.NR_WORKFLOW_SIGN_TODO_PARTICI);
        if (signExtension != null && (object = signExtension.get(CounterSignConst.NR_WORKFLOW_SIGN_TODO_PARTICI_VALUE)) != null && (actors = (countSignStartMode = (CountSignStartMode)object).getActors()) != null) {
            for (String roleKey : actors) {
                List identityIdByRole = this.roleService.getIdentityIdByRole(roleKey);
                if (identityIdByRole == null || identityIdByRole.size() <= 0) continue;
                usersByRoleKey.addAll(identityIdByRole);
            }
        }
        if (usersByRoleKey != null && usersByRoleKey.size() > 0) {
            participants.retainAll(usersByRoleKey);
        }
        return participants;
    }
}

