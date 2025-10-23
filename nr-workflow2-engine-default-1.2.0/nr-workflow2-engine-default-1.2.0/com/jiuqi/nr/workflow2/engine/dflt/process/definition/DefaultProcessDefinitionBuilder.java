/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.Role
 *  com.jiuqi.np.authz2.service.RoleService
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyDefinition
 *  com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition$ExecutionTiming
 *  com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory
 *  com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode
 *  com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException
 *  org.json.JSONObject
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.definition;

import com.jiuqi.np.authz2.Role;
import com.jiuqi.np.authz2.service.RoleService;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ActorStrategy;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;
import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessStatus;
import com.jiuqi.nr.workflow2.engine.common.definition.model.RetriveActionPath;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserAction;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionBuilder;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserActionEvent;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTask;
import com.jiuqi.nr.workflow2.engine.common.definition.model.UserTaskBuilder;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyDefinition;
import com.jiuqi.nr.workflow2.engine.core.actor.IActorStrategyFactory;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventDefinition;
import com.jiuqi.nr.workflow2.engine.core.event.IActionEventFactory;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;
import com.jiuqi.nr.workflow2.engine.core.exception.ProcessDefinitionException;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.DefaultProcessConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.FillInDescStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.RetrieveStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.UploadLayerByLayerStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.WorkflowDefineTemplate;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEvent;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.AuditNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.ReportNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipant;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.TodoReceiver;
import com.jiuqi.nr.workflow2.engine.dflt.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class DefaultProcessDefinitionBuilder {
    private static final String PROCESS_DEFINITION_TITLE = "\u9ed8\u8ba4\u6d41\u7a0b";
    private IActorStrategyFactory actorStrategyFactory;
    private IActionEventFactory eventFactory;
    private RoleService roleService;
    private String processId;
    private SubmitNodeConfig submitNodeConfig;
    private ReportNodeConfig reportNodeConfig;
    private AuditNodeConfig auditNodeConfig;
    private boolean needSubmitNode;
    private boolean needFinalNode;
    private UserTaskBuilder startTaskBuilder = new UserTaskBuilder("tsk_start");
    private UserTaskBuilder submitTaskBuilder = new UserTaskBuilder("tsk_submit");
    private UserTaskBuilder reportTaskBuilder = new UserTaskBuilder("tsk_upload");
    private UserTaskBuilder auditTaskBuilder = new UserTaskBuilder("tsk_audit");
    private UserTaskBuilder finalTaskBuilder = new UserTaskBuilder("tsk_audit_after_confirm");
    private UserActionBuilder startActionBuilder = new UserActionBuilder("start");
    private UserActionBuilder submitActionBuilder = new UserActionBuilder("act_submit");
    private UserActionBuilder backActionBuilder = new UserActionBuilder("act_return");
    private UserActionBuilder reportActionBuilder = new UserActionBuilder("act_upload");
    private UserActionBuilder rejectActionBuilder = new UserActionBuilder("act_reject");
    private UserActionBuilder confirmActionBuilder = new UserActionBuilder("act_confirm");
    private UserActionBuilder cancelConfirmActionBuilder = new UserActionBuilder("act_cancel_confirm");
    private UserAction retriveToSubmit;
    private UserAction retriveToReport;
    private UserAction applyReturn;
    private String unsubmitStatusAlias;
    private String unreportStatusAlias;

    public DefaultProcessDefinitionBuilder(String definitionId, DefaultProcessConfig processConfig, IActorStrategyFactory actorStrategyFactory, IActionEventFactory eventFactory, RoleService roleService) {
        this.processId = definitionId;
        this.actorStrategyFactory = actorStrategyFactory;
        this.eventFactory = eventFactory;
        this.roleService = roleService;
        WorkflowDefineTemplate template = processConfig.getWorkflowDefineTemplate();
        switch (template) {
            case STANDARD_WORKFLOW: {
                this.reportNodeConfig = processConfig.getReportNodeConfig();
                this.auditNodeConfig = processConfig.getAuditNodeConfig();
                break;
            }
            case SUBMIT_WORKFLOW: {
                this.submitNodeConfig = processConfig.getSubmitNodeConfig();
                this.reportNodeConfig = processConfig.getReportNodeConfig();
                this.auditNodeConfig = processConfig.getAuditNodeConfig();
                break;
            }
            default: {
                throw new ProcessDefinitionException("jiuqi.nr.default", definitionId, ErrorCode.UNKNOW, "unknow workflow type.");
            }
        }
        this.needSubmitNode = this.submitNodeConfig != null;
        this.needFinalNode = this.auditNodeConfig.getProperty().isConfirmEnable();
    }

    public ProcessDefinition build() {
        this.buildSubmitUserTask();
        this.buildReportUserTask();
        this.buildAuditUserTask();
        this.buildFinalUserTask();
        this.buildSubmitAction();
        this.buildBackAction();
        this.buildRetriveToSubmitAction();
        this.buildReportAction();
        this.buildRejectAction();
        this.buildRetriveToReportAction();
        this.buildConfirmAction();
        this.buildCancelConfirmAction();
        this.bindActionToUsertask();
        return this.buildPorcess();
    }

    private void buildSubmitUserTask() {
        if (this.needSubmitNode) {
            this.buildUserTask(this.submitTaskBuilder, this.submitNodeConfig.getProperty().getRename(), this.submitNodeConfig.getParticipant(), true);
            if (this.submitNodeConfig.getProperty().getRetrieveOrReturn() != null) {
                this.submitTaskBuilder.setProperty("ENABLE_RETRIVE_TO", true);
                this.reportTaskBuilder.setProperty("ENABLE_RETRIVE_FROM", true);
            }
        }
    }

    private void buildReportUserTask() {
        Role forceReportRole;
        this.buildUserTask(this.reportTaskBuilder, this.reportNodeConfig.getProperty().getRename(), this.reportNodeConfig.getParticipant(), true);
        if (!StringUtils.isEmpty(this.reportNodeConfig.getProperty().getForceUpload()) && (forceReportRole = this.getUniqueRoleByCode(this.reportNodeConfig.getProperty().getForceUpload())) != null) {
            this.reportTaskBuilder.setProperty("ENABLE_FORCE_REPORT", true);
            this.reportTaskBuilder.setProperty("FORCE_REPORT_ROLE", forceReportRole.getId());
            if (this.needSubmitNode) {
                this.submitTaskBuilder.setProperty("ENABLE_FORCE_REPORT", true);
                this.submitTaskBuilder.setProperty("FORCE_REPORT_ROLE", forceReportRole.getId());
            }
        }
        if (this.reportNodeConfig.getProperty().getRetrieveOrReturn() != null) {
            switch (this.reportNodeConfig.getProperty().getRetrieveOrReturn()) {
                case RETRIEVE_SELF: {
                    this.reportTaskBuilder.setProperty("ENABLE_RETRIVE_TO", true);
                    this.auditTaskBuilder.setProperty("ENABLE_RETRIVE_FROM", true);
                    break;
                }
                case APPLY_RETURN: {
                    this.auditTaskBuilder.setProperty("ENABLE_APPLY_RETURN", true);
                    break;
                }
            }
        }
    }

    private Role getUniqueRoleByCode(String roleCode) {
        return this.roleService.get(roleCode).orElse(null);
    }

    private void buildAuditUserTask() {
        this.buildUserTask(this.auditTaskBuilder, this.auditNodeConfig.getProperty().getRename(), this.auditNodeConfig.getParticipant(), true);
    }

    private void buildFinalUserTask() {
        if (this.needFinalNode) {
            this.buildUserTask(this.finalTaskBuilder, this.auditNodeConfig.getProperty().getRename(), this.auditNodeConfig.getParticipant(), false);
        }
    }

    private void buildUserTask(UserTaskBuilder builder, String alias, NodeParticipant participant, boolean enableSendTodo) {
        builder.setAlias(alias);
        ParticipantItem actionExecutor = participant.getActionExecuter();
        this.checkActorStrategy(actionExecutor);
        builder.addActionExecutors(new ActorStrategy(actionExecutor.getStrategy(), actionExecutor.getParam()));
        builder.setEnableSendTodo(enableSendTodo);
        if (enableSendTodo) {
            TodoReceiver todoConfig = participant.getTodoReceiver();
            if (todoConfig.getType().equals((Object)TodoReceiverStrategy.IDENTICAL_TO_EXECUTOR)) {
                builder.todoReceiversSameToActionExecutors(true);
            } else {
                for (ParticipantItem item : todoConfig.getCustomParticipant()) {
                    this.checkActorStrategy(item);
                    builder.AddTodoReceivers(new ActorStrategy(item.getStrategy(), item.getParam()));
                }
            }
        }
    }

    private void checkActorStrategy(ParticipantItem participant) {
        IActorStrategyDefinition actorStrategyDef = this.actorStrategyFactory.queryActorStrategyDefinition(participant.getStrategy());
        if (actorStrategyDef == null) {
            throw new ProcessDefinitionException("jiuqi.nr.default", this.processId, "unknow actor strategy \u2018" + participant.getStrategy() + "'.");
        }
    }

    private void buildSubmitAction() {
        if (this.needSubmitNode) {
            ActionInfo actionInfo = this.submitNodeConfig.getActions().get("act_submit");
            this.buildAction(this.submitActionBuilder, actionInfo.getButtonName(), this.submitNodeConfig.getEvents().get("act_submit"));
            if (!StringUtils.isEmpty(actionInfo.getButtonName())) {
                this.unsubmitStatusAlias = "\u672a" + actionInfo.getButtonName();
            }
        }
    }

    private void buildRetriveToSubmitAction() {
        if (this.needSubmitNode && this.submitNodeConfig.getProperty().getRetrieveOrReturn() != null) {
            UserActionBuilder builder = new UserActionBuilder("act_retrieve");
            ActionInfo actionInfo = this.submitNodeConfig.getActions().get("act_retrieve");
            this.buildAction(builder, actionInfo == null ? null : actionInfo.getButtonName(), this.submitNodeConfig.getEvents().get("act_retrieve"));
            this.retriveToSubmit = builder.build();
        }
    }

    private void buildBackAction() {
        if (this.needSubmitNode) {
            ActionInfo actionInfo = this.reportNodeConfig.getActions().get("act_return");
            this.buildAction(this.backActionBuilder, actionInfo.getButtonName(), this.reportNodeConfig.getEvents().get("act_return"));
            FillInDescStrategy commentSetting = this.reportNodeConfig.getProperty().getBackDesc();
            if (commentSetting != null) {
                this.backActionBuilder.setProperty("SUPPORT_COMMENT", true);
                this.backActionBuilder.setProperty("COMMENT_NOTNULL", commentSetting.equals((Object)FillInDescStrategy.REQUIRED));
            } else {
                this.backActionBuilder.setProperty("SUPPORT_COMMENT", false);
                this.backActionBuilder.setProperty("COMMENT_NOTNULL", false);
            }
        }
    }

    private void buildReportAction() {
        ActionInfo actionInfo = this.reportNodeConfig.getActions().get("act_upload");
        this.buildAction(this.reportActionBuilder, actionInfo.getButtonName(), this.reportNodeConfig.getEvents().get("act_upload"));
        FillInDescStrategy commentSetting = this.reportNodeConfig.getProperty().getUploadDesc();
        if (commentSetting != null) {
            this.reportActionBuilder.setProperty("SUPPORT_COMMENT", true);
            this.reportActionBuilder.setProperty("COMMENT_NOTNULL", commentSetting.equals((Object)FillInDescStrategy.REQUIRED));
        } else {
            this.reportActionBuilder.setProperty("SUPPORT_COMMENT", false);
            this.reportActionBuilder.setProperty("COMMENT_NOTNULL", false);
        }
        UploadLayerByLayerStrategy uploadLayerByLayerStrategy = this.reportNodeConfig.getProperty().getUploadLayerByLayer();
        if (uploadLayerByLayerStrategy != null) {
            String STEP_BY_STEP_REPORT_EVENT_ID = "step-by-step-upload-event";
            String STEP_BY_STEP_REPORT_STRATEGY = "StepbystepUploadStrategy";
            JSONObject settings = new JSONObject();
            settings.put("StepbystepUploadStrategy", (Object)uploadLayerByLayerStrategy);
            UserActionEvent event = new UserActionEvent(null, "step-by-step-upload-event", settings.toString());
            this.reportActionBuilder.addEvent(event, IActionEventDefinition.ExecutionTiming.PRE_ACTION);
        }
        if (!StringUtils.isEmpty(actionInfo.getButtonName())) {
            this.unreportStatusAlias = "\u672a" + actionInfo.getButtonName();
        }
    }

    private void buildRetriveToReportAction() {
        String STEP_BY_STEP_REJECT_EVENT_ID = "step-by-step-applyreject-event";
        String STEP_BY_STEP_RETRIVE_REJECT_EVENT_ID = "step-by-step-retrievereject-event";
        String STEP_BY_STEP_REJECT = "StepbystepReturn";
        String REJECT_All_SUPERIOR = "ReturnAllSuperior";
        if (RetrieveStrategy.RETRIEVE_SELF.equals((Object)this.reportNodeConfig.getProperty().getRetrieveOrReturn())) {
            UserActionBuilder builder = new UserActionBuilder("act_retrieve");
            ActionInfo actionInfo = this.reportNodeConfig.getActions().get("act_retrieve");
            this.buildAction(builder, actionInfo == null ? null : actionInfo.getButtonName(), this.reportNodeConfig.getEvents().get("act_retrieve"));
            if (this.reportNodeConfig.getProperty().getUploadLayerByLayer() != null) {
                String settings = null;
                UserActionEvent event = new UserActionEvent(null, "step-by-step-retrievereject-event", settings);
                builder.addEvent(event, IActionEventDefinition.ExecutionTiming.PRE_ACTION);
            }
            this.retriveToReport = builder.build();
        } else if (RetrieveStrategy.APPLY_RETURN.equals((Object)this.reportNodeConfig.getProperty().getRetrieveOrReturn())) {
            UserActionBuilder builder = new UserActionBuilder("act_apply_reject");
            ActionInfo actionInfo = this.reportNodeConfig.getActions().get("act_apply_reject");
            this.buildAction(builder, actionInfo == null ? null : actionInfo.getButtonName(), this.reportNodeConfig.getEvents().get("act_apply_reject"));
            builder.setProperty("SUPPORT_COMMENT", true);
            builder.setProperty("COMMENT_NOTNULL", false);
            if (this.auditNodeConfig.getProperty().isReturnLayerByLayer()) {
                JSONObject settings = new JSONObject();
                settings.put("StepbystepReturn", true);
                settings.put("ReturnAllSuperior", this.auditNodeConfig.getProperty().isReturnAllSuperior());
                UserActionEvent event = new UserActionEvent(null, "step-by-step-applyreject-event", settings.toString());
                builder.addEvent(event, IActionEventDefinition.ExecutionTiming.PRE_ACTION);
            }
            this.applyReturn = builder.build();
        }
    }

    private void buildRejectAction() {
        ActionInfo actionInfo = this.auditNodeConfig.getActions().get("act_reject");
        this.buildAction(this.rejectActionBuilder, actionInfo.getButtonName(), this.auditNodeConfig.getEvents().get("act_reject"));
        FillInDescStrategy commentSetting = this.auditNodeConfig.getProperty().getReturnDesc();
        if (commentSetting != null) {
            this.rejectActionBuilder.setProperty("SUPPORT_COMMENT", true);
            this.rejectActionBuilder.setProperty("COMMENT_NOTNULL", commentSetting.equals((Object)FillInDescStrategy.REQUIRED));
        } else {
            this.rejectActionBuilder.setProperty("SUPPORT_COMMENT", false);
            this.rejectActionBuilder.setProperty("COMMENT_NOTNULL", false);
        }
        String returnTypeEntityId = this.auditNodeConfig.getProperty().getReturnType();
        if (!StringUtils.isEmpty(returnTypeEntityId)) {
            this.rejectActionBuilder.setProperty("SUPPORT_RETURN_TYPE", true);
            this.rejectActionBuilder.setProperty("RETURN_TYPE_ENTITY", returnTypeEntityId);
        } else {
            this.rejectActionBuilder.setProperty("SUPPORT_RETURN_TYPE", false);
            this.rejectActionBuilder.setProperty("RETURN_TYPE_ENTITY", null);
        }
        if (this.auditNodeConfig.getProperty().isReturnLayerByLayer() || this.auditNodeConfig.getProperty().isReturnAllSuperior()) {
            String STEP_BY_STEP_REJECT_EVENT_ID = "step-by-step-reject-event";
            String STEP_BY_STEP_REJECT = "StepbystepReturn";
            String REJECT_All_SUPERIOR = "ReturnAllSuperior";
            JSONObject settings = new JSONObject();
            settings.put("StepbystepReturn", this.auditNodeConfig.getProperty().isReturnLayerByLayer());
            settings.put("ReturnAllSuperior", this.auditNodeConfig.getProperty().isReturnAllSuperior());
            UserActionEvent event = new UserActionEvent(null, "step-by-step-reject-event", settings.toString());
            this.rejectActionBuilder.addEvent(event, IActionEventDefinition.ExecutionTiming.PRE_ACTION);
        }
    }

    private void buildConfirmAction() {
        if (this.needFinalNode) {
            ActionInfo actionInfo = this.auditNodeConfig.getActions().get("act_confirm");
            this.buildAction(this.confirmActionBuilder, actionInfo.getButtonName(), this.auditNodeConfig.getEvents().get("act_confirm"));
        }
    }

    private void buildCancelConfirmAction() {
        if (this.needFinalNode) {
            ActionInfo actionInfo = this.auditNodeConfig.getActions().get("act_cancel_confirm");
            if (this.reportNodeConfig.getProperty().getUploadLayerByLayer() == UploadLayerByLayerStrategy.UPLOAD_AFTER_SUBORDINATE_CONFIRMED) {
                String STEP_BY_STEP_CANCEL_CONFIRM_EVENT = "step-by-step-cancel-confirm-event";
                String settings = null;
                UserActionEvent event = new UserActionEvent(null, "step-by-step-cancel-confirm-event", settings);
                this.cancelConfirmActionBuilder.addEvent(event, IActionEventDefinition.ExecutionTiming.PRE_ACTION);
            }
            this.buildAction(this.cancelConfirmActionBuilder, actionInfo.getButtonName(), this.auditNodeConfig.getEvents().get("act_cancel_confirm"));
        }
    }

    private void buildAction(UserActionBuilder builder, String alias, List<ActionEvent> events) {
        builder.alias(alias);
        if (events.isEmpty()) {
            return;
        }
        ArrayList<UserActionEvent> preEvents = null;
        ArrayList<UserActionEvent> postEvents = null;
        for (ActionEvent event : events) {
            IActionEventDefinition eventDef = this.checkEvent(event);
            UserActionEvent userActionEvent = new UserActionEvent(eventDef.getTitle(), event.getEventId(), event.getEventParam());
            if (eventDef.getExecutionTiming().equals((Object)IActionEventDefinition.ExecutionTiming.PRE_ACTION)) {
                if (preEvents == null) {
                    preEvents = new ArrayList<UserActionEvent>(2);
                }
                preEvents.add(userActionEvent);
                continue;
            }
            if (!eventDef.getExecutionTiming().equals((Object)IActionEventDefinition.ExecutionTiming.POST_ACTION)) continue;
            if (postEvents == null) {
                postEvents = new ArrayList<UserActionEvent>(2);
            }
            postEvents.add(userActionEvent);
        }
        if (preEvents != null) {
            preEvents.sort((e1, e2) -> Short.compare(this.eventFactory.getActionEventOrder(e1.getDefinitionId()), this.eventFactory.getActionEventOrder(e2.getDefinitionId())));
            for (UserActionEvent userActionEvent : preEvents) {
                builder.addEvent(userActionEvent, IActionEventDefinition.ExecutionTiming.PRE_ACTION);
            }
        }
        if (postEvents != null) {
            postEvents.sort((e1, e2) -> Short.compare(this.eventFactory.getActionEventOrder(e1.getDefinitionId()), this.eventFactory.getActionEventOrder(e2.getDefinitionId())));
            for (UserActionEvent userActionEvent : postEvents) {
                builder.addEvent(userActionEvent, IActionEventDefinition.ExecutionTiming.POST_ACTION);
            }
        }
    }

    private IActionEventDefinition checkEvent(ActionEvent event) {
        IActionEventDefinition eventDefinition = this.eventFactory.queryActionEventDefinition(event.getEventId());
        if (eventDefinition == null) {
            throw new ProcessDefinitionException("jiuqi.nr.default", this.processId, "unknow event \u2018" + event.getEventId() + "'.");
        }
        return eventDefinition;
    }

    private void bindActionToUsertask() {
        RetriveActionPath retrivePath;
        ActionInfo actionInfo;
        if (this.needSubmitNode) {
            this.startTaskBuilder.addUserActionPaths(this.startActionBuilder, this.submitTaskBuilder, new ProcessStatus("unsubmited", this.unsubmitStatusAlias));
            actionInfo = this.submitNodeConfig.getActions().get("act_submit");
            this.submitTaskBuilder.addUserActionPaths(this.submitActionBuilder, this.reportTaskBuilder, new ProcessStatus("submited", actionInfo.getStateName()));
            actionInfo = this.reportNodeConfig.getActions().get("act_return");
            this.reportTaskBuilder.addUserActionPaths(this.backActionBuilder, this.submitTaskBuilder, new ProcessStatus("backed", actionInfo.getStateName()));
            if (this.retriveToSubmit != null) {
                retrivePath = new RetriveActionPath(this.retriveToSubmit, this.submitTaskBuilder.userTask);
                this.reportTaskBuilder.setRetriveAction(retrivePath);
            }
        } else {
            this.startTaskBuilder.addUserActionPaths(this.startActionBuilder, this.reportTaskBuilder, new ProcessStatus("unreported", this.unreportStatusAlias));
        }
        actionInfo = this.reportNodeConfig.getActions().get("act_upload");
        this.reportTaskBuilder.addUserActionPaths(this.reportActionBuilder, this.auditTaskBuilder, new ProcessStatus("reported", actionInfo.getStateName()));
        if (this.needSubmitNode) {
            actionInfo = this.auditNodeConfig.getActions().get("act_reject");
            this.auditTaskBuilder.addUserActionPaths(this.rejectActionBuilder, this.submitTaskBuilder, new ProcessStatus("rejected", actionInfo.getStateName()));
        } else {
            actionInfo = this.auditNodeConfig.getActions().get("act_reject");
            this.auditTaskBuilder.addUserActionPaths(this.rejectActionBuilder, this.reportTaskBuilder, new ProcessStatus("rejected", actionInfo.getStateName()));
        }
        if (this.retriveToReport != null) {
            retrivePath = new RetriveActionPath(this.retriveToReport, this.reportTaskBuilder.userTask);
            this.auditTaskBuilder.setRetriveAction(retrivePath);
        } else if (this.applyReturn != null) {
            retrivePath = new RetriveActionPath(this.applyReturn, this.reportTaskBuilder.userTask);
            this.auditTaskBuilder.setApplyReturnAction(retrivePath);
        }
        if (this.needFinalNode) {
            actionInfo = this.auditNodeConfig.getActions().get("act_confirm");
            this.auditTaskBuilder.addUserActionPaths(this.confirmActionBuilder, this.finalTaskBuilder, new ProcessStatus("confirmed", actionInfo.getStateName()));
            actionInfo = this.reportNodeConfig.getActions().get("act_upload");
            this.finalTaskBuilder.addUserActionPaths(this.cancelConfirmActionBuilder, this.auditTaskBuilder, new ProcessStatus("reported", actionInfo.getStateName()));
        }
    }

    private ProcessDefinition buildPorcess() {
        ArrayList<UserTask> userTasks = new ArrayList<UserTask>();
        userTasks.add(this.startTaskBuilder.build());
        if (this.needSubmitNode) {
            userTasks.add(this.submitTaskBuilder.build());
        }
        userTasks.add(this.reportTaskBuilder.build());
        userTasks.add(this.auditTaskBuilder.build());
        if (this.needFinalNode) {
            userTasks.add(this.finalTaskBuilder.build());
        }
        ProcessDefinition processDefinition = new ProcessDefinition(this.processId, PROCESS_DEFINITION_TITLE, userTasks);
        return processDefinition;
    }
}

