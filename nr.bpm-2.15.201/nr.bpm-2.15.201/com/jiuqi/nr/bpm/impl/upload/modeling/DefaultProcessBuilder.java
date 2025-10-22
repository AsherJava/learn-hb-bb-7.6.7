/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nr.definition.facade.TaskFlowsDefine
 */
package com.jiuqi.nr.bpm.impl.upload.modeling;

import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.nr.bpm.impl.Actor.CanAuditActorStrategy;
import com.jiuqi.nr.bpm.impl.Actor.CanSubmitActorStrategy;
import com.jiuqi.nr.bpm.impl.Actor.CanUploadActorStrategy;
import com.jiuqi.nr.bpm.impl.common.NrParameterUtils;
import com.jiuqi.nr.bpm.impl.upload.modeling.DefaultProcessSettingsWrapper;
import com.jiuqi.nr.bpm.modeling.ProcessBuilder;
import com.jiuqi.nr.bpm.modeling.model.Action;
import com.jiuqi.nr.bpm.modeling.model.ActorStrategy;
import com.jiuqi.nr.bpm.modeling.model.ConditionExpression;
import com.jiuqi.nr.bpm.modeling.model.FlowableProcessElement;
import com.jiuqi.nr.bpm.modeling.model.FormEditable;
import com.jiuqi.nr.bpm.modeling.model.Retrivable;
import com.jiuqi.nr.bpm.modeling.model.StartEvent;
import com.jiuqi.nr.bpm.modeling.model.TaskListener;
import com.jiuqi.nr.bpm.modeling.model.UserTask;
import com.jiuqi.nr.bpm.upload.DefaultProcessSettings;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.facade.TaskFlowsDefine;
import java.util.ArrayList;
import java.util.List;

public class DefaultProcessBuilder
extends ProcessBuilder {
    private DefaultProcessSettings processSettings;
    private List<String> entityTableCodes;

    public DefaultProcessBuilder(DefaultProcessSettings processSettings, NrParameterUtils nrParameterUtils) {
        super(DefaultProcessBuilder.generateDefaultProcessId(processSettings.getFormScheme().getFormSchemeCode()));
        this.processSettings = processSettings;
    }

    public DefaultProcessBuilder(FormSchemeDefine formScheme, TaskFlowsDefine taskFlowsDefine, NrParameterUtils nrParameterUtils) {
        this(new DefaultProcessSettingsWrapper(formScheme, taskFlowsDefine), nrParameterUtils);
    }

    private void initiailze(NrParameterUtils nrParameterUtils) {
        this.entityTableCodes = new ArrayList<String>();
        for (String tableKey : this.processSettings.getUploadEntityKeys()) {
            TableDefine table = nrParameterUtils.getTableDefine(tableKey);
            if (table.getKind() == TableKind.TABLE_KIND_ENTITY_PERIOD) continue;
            this.entityTableCodes.add(table.getCode());
        }
    }

    @Override
    public ProcessBuilder build() {
        this.buildTaskAndFlow();
        return this;
    }

    private void buildTaskAndFlow() {
        boolean enableSubmitCheck = this.processSettings.startupSubmitCheck();
        boolean enableConfirm = this.processSettings.startupConfirm();
        StartEvent startEvent = new StartEvent("start");
        startEvent.setName("Start");
        this.addStart(startEvent);
        UserTask submitCheckTask = null;
        if (enableSubmitCheck) {
            submitCheckTask = this.buildSubmitCheckTask();
            this.addUserTask(submitCheckTask);
        }
        UserTask uploadTask = this.buildUploadTask();
        this.addUserTask(uploadTask);
        UserTask auditTask = this.buildAuditTask();
        this.addUserTask(auditTask);
        UserTask auditAfterConfirmTask = null;
        if (enableConfirm) {
            auditAfterConfirmTask = this.buildAuditAfterConfirmTask();
            this.addUserTask(auditAfterConfirmTask);
        }
        if (enableSubmitCheck) {
            if (enableConfirm) {
                this.addSequenceFlow(startEvent, submitCheckTask);
                this.addSequenceFlow(submitCheckTask, uploadTask);
                ConditionExpression.OnActionCondition onReturnCheck = new ConditionExpression.OnActionCondition((FlowableProcessElement)uploadTask, "act_return");
                this.addSequenceFlowConditional(uploadTask, submitCheckTask, onReturnCheck);
                this.addSequenceFlowDefault(uploadTask, auditTask);
                ConditionExpression.OnActionCondition onReject = new ConditionExpression.OnActionCondition((FlowableProcessElement)auditTask, "act_reject");
                this.addSequenceFlowConditional(auditTask, submitCheckTask, onReject);
                this.addSequenceFlowDefault(auditTask, auditAfterConfirmTask);
                ConditionExpression.OnActionCondition onReturn = new ConditionExpression.OnActionCondition((FlowableProcessElement)auditAfterConfirmTask, "act_reject");
                this.addSequenceFlowConditional(auditAfterConfirmTask, submitCheckTask, onReturn);
                ConditionExpression.OnActionCondition onCancel = new ConditionExpression.OnActionCondition((FlowableProcessElement)auditAfterConfirmTask, "act_cancel_confirm");
                this.addSequenceFlowConditional(auditAfterConfirmTask, auditTask, onCancel);
            } else {
                this.addSequenceFlow(startEvent, submitCheckTask);
                this.addSequenceFlow(submitCheckTask, uploadTask);
                ConditionExpression.OnActionCondition onReturnCheck = new ConditionExpression.OnActionCondition((FlowableProcessElement)uploadTask, "act_return");
                this.addSequenceFlowConditional(uploadTask, submitCheckTask, onReturnCheck);
                this.addSequenceFlowDefault(uploadTask, auditTask);
                this.addSequenceFlow(auditTask, submitCheckTask);
            }
        } else if (enableConfirm) {
            this.addSequenceFlow(startEvent, uploadTask);
            this.addSequenceFlow(uploadTask, auditTask);
            ConditionExpression.OnActionCondition onReject = new ConditionExpression.OnActionCondition((FlowableProcessElement)auditTask, "act_reject");
            this.addSequenceFlowConditional(auditTask, uploadTask, onReject);
            this.addSequenceFlowDefault(auditTask, auditAfterConfirmTask);
            ConditionExpression.OnActionCondition onReturn = new ConditionExpression.OnActionCondition((FlowableProcessElement)auditAfterConfirmTask, "act_reject");
            this.addSequenceFlowConditional(auditAfterConfirmTask, uploadTask, onReturn);
            ConditionExpression.OnActionCondition onCancel = new ConditionExpression.OnActionCondition((FlowableProcessElement)auditAfterConfirmTask, "act_cancel_confirm");
            this.addSequenceFlowConditional(auditAfterConfirmTask, auditTask, onCancel);
        } else {
            this.addSequenceFlow(startEvent, uploadTask);
            this.addSequenceFlow(uploadTask, auditTask);
            this.addSequenceFlow(auditTask, uploadTask);
        }
    }

    private UserTask buildSubmitCheckTask() {
        UserTask userTask = new UserTask("tsk_submit");
        userTask.setName("\u9001\u5ba1");
        TaskListener.DefaultTaskCreateListener taskListenerExtension = new TaskListener.DefaultTaskCreateListener();
        userTask.addExtension(taskListenerExtension);
        ActorStrategy actorStrategyExtension = new ActorStrategy();
        actorStrategyExtension.setType(CanSubmitActorStrategy.class);
        actorStrategyExtension.setParameter(this.processSettings.getSubmitCheckTaskActorStrategyParameter());
        userTask.addExtension(actorStrategyExtension);
        Action submitAction = new Action("act_submit");
        submitAction.setName("\u9001\u5ba1");
        submitAction.setNeedComment(false);
        userTask.addExtension(submitAction);
        userTask.addExtension(new Retrivable(this.processSettings.isRetrivable()));
        userTask.addExtension(new FormEditable(true));
        return userTask;
    }

    private UserTask buildUploadTask() {
        UserTask userTask = new UserTask("tsk_upload");
        userTask.setName("\u4e0a\u62a5");
        TaskListener.DefaultTaskCreateListener taskListenerExtension = new TaskListener.DefaultTaskCreateListener();
        userTask.addExtension(taskListenerExtension);
        ActorStrategy actorStrategyExtension = new ActorStrategy();
        actorStrategyExtension.setType(CanUploadActorStrategy.class);
        userTask.addExtension(actorStrategyExtension);
        Action uploadAction = new Action("act_upload");
        uploadAction.setName("\u4e0a\u62a5");
        uploadAction.setNeedComment(this.processSettings.needUploadComment());
        userTask.addExtension(uploadAction);
        if (this.processSettings.startupSubmitCheck()) {
            Action returnCheckAction = new Action("act_return");
            returnCheckAction.setName("\u9000\u5ba1");
            returnCheckAction.setNeedComment(false);
            userTask.addExtension(returnCheckAction);
        }
        userTask.addExtension(new Retrivable(this.processSettings.isRetrivable()));
        userTask.addExtension(new FormEditable(true));
        return userTask;
    }

    private UserTask buildAuditTask() {
        UserTask userTask = new UserTask("tsk_audit");
        userTask.setName("\u5ba1\u6279");
        TaskListener.DefaultTaskCreateListener taskListenerExtension = new TaskListener.DefaultTaskCreateListener();
        userTask.addExtension(taskListenerExtension);
        ActorStrategy actorStrategyExtension = new ActorStrategy();
        actorStrategyExtension.setType(CanAuditActorStrategy.class);
        userTask.addExtension(actorStrategyExtension);
        Action rejectAction = new Action("act_reject");
        rejectAction.setName("\u9000\u56de");
        rejectAction.setNeedComment(true);
        userTask.addExtension(rejectAction);
        if (this.processSettings.startupConfirm()) {
            Action confirmAction = new Action("act_confirm");
            confirmAction.setName("\u786e\u8ba4");
            confirmAction.setNeedComment(false);
            userTask.addExtension(confirmAction);
        }
        userTask.addExtension(new Retrivable(this.processSettings.isRetrivable()));
        userTask.addExtension(new FormEditable(this.processSettings.isFormEditableAfterUpload()));
        return userTask;
    }

    private UserTask buildAuditAfterConfirmTask() {
        UserTask userTask = new UserTask("tsk_audit_after_confirm");
        userTask.setName("\u5ba1\u6279");
        TaskListener.DefaultTaskCreateListener taskListenerExtension = new TaskListener.DefaultTaskCreateListener();
        userTask.addExtension(taskListenerExtension);
        ActorStrategy actorStrategyExtension = new ActorStrategy();
        actorStrategyExtension.setType(CanAuditActorStrategy.class);
        userTask.addExtension(actorStrategyExtension);
        Action rejectAgainAction = new Action("act_reject");
        rejectAgainAction.setName("\u9000\u56de");
        rejectAgainAction.setNeedComment(true);
        userTask.addExtension(rejectAgainAction);
        Action cancelAction = new Action("act_cancel_confirm");
        cancelAction.setName("\u53d6\u6d88\u786e\u8ba4");
        cancelAction.setNeedComment(true);
        userTask.addExtension(cancelAction);
        userTask.addExtension(new Retrivable(this.processSettings.isRetrivable()));
        userTask.addExtension(new FormEditable(this.processSettings.isFormEditableAfterUpload()));
        return userTask;
    }

    private String buildSubmitCheckTaskTodo() {
        if (this.entityTableCodes.isEmpty()) {
            return "\u8bf7\u9001\u5ba1";
        }
        StringBuilder builder = new StringBuilder();
        for (String entityTableCode : this.entityTableCodes) {
            builder.append(this.wrappingVariable("entity_" + entityTableCode)).append("-");
        }
        builder.setLength(builder.length() - 1);
        builder.append("\u7684\u6570\u636e\u5f85\u9001\u5ba1\uff0c\u8bf7\u5904\u7406");
        return builder.toString();
    }

    private String buildUploadTaskTodo() {
        if (this.entityTableCodes.isEmpty()) {
            return "\u8bf7\u4e0a\u62a5";
        }
        StringBuilder builder = new StringBuilder();
        for (String entityTableCode : this.entityTableCodes) {
            builder.append(this.wrappingVariable("entity_" + entityTableCode)).append("-");
        }
        builder.setLength(builder.length() - 1);
        builder.append("\u7684\u6570\u636e\u5f85\u4e0a\u62a5\uff0c\u8bf7\u5904\u7406");
        return builder.toString();
    }

    private String buildAuditTaskTodo() {
        if (this.entityTableCodes.isEmpty()) {
            return "\u8bf7\u5ba1\u6838";
        }
        StringBuilder builder = new StringBuilder();
        for (String entityTableCode : this.entityTableCodes) {
            builder.append(this.wrappingVariable("entity_" + entityTableCode)).append("-");
        }
        builder.setLength(builder.length() - 1);
        builder.append("\u7684\u6570\u636e\u5f85\u5ba1\u6838\uff0c\u8bf7\u5904\u7406");
        return builder.toString();
    }

    public static String generateDefaultProcessId(String formSchemeCode) {
        return String.format("%s%s", "prc_dft_", formSchemeCode);
    }
}

