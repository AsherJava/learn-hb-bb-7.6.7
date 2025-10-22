/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.nr.context.infc.INRContext
 *  com.jiuqi.nr.definition.internal.impl.WorkFlowType
 *  com.jiuqi.nr.jtable.annotation.JtableLog
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.dataentry.bean.DUserActionParam;
import com.jiuqi.nr.definition.internal.impl.WorkFlowType;
import com.jiuqi.nr.jtable.annotation.JtableLog;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class BatchExecuteTaskParam
extends JtableLog
implements INRContext {
    private static final long serialVersionUID = 1L;
    private JtableContext context;
    private String actionId;
    private String taskCode;
    private String taskId;
    private String comment;
    private String formulaSchemeKeys;
    private boolean forceCommit;
    private List<String> formKeys;
    private List<String> formGroupKeys;
    private WorkFlowType workFlowType;
    private boolean sendEmail;
    private List<String> messageIds;
    private String templateActionParams;
    private String returnType;
    private List<String> signBootModes;
    private boolean changeMonitorState = true;
    public String contextTaskKey;
    public String contextEntityId;
    public String contextFormSchemeKey;
    public String contextFilterExpression;
    private DUserActionParam userActionParam;
    private String actionTitle;

    public String getActionTitle() {
        return this.actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public DUserActionParam getUserActionParam() {
        return this.userActionParam;
    }

    public void setUserActionParam(DUserActionParam userActionParam) {
        this.userActionParam = userActionParam;
    }

    public JtableContext getContext() {
        return this.context;
    }

    public void setContext(JtableContext context) {
        this.context = context;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFormulaSchemeKeys() {
        return this.formulaSchemeKeys;
    }

    public void setFormulaSchemeKeys(String formulaSchemeKeys) {
        this.formulaSchemeKeys = formulaSchemeKeys;
    }

    public String getTaskCode() {
        return this.taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public boolean isForceCommit() {
        return this.forceCommit;
    }

    public void setForceCommit(boolean forceCommit) {
        this.forceCommit = forceCommit;
    }

    public List<String> getFormKeys() {
        return this.formKeys;
    }

    public void setFormKeys(List<String> formKeys) {
        this.formKeys = formKeys;
    }

    public WorkFlowType getWorkFlowType() {
        return this.workFlowType;
    }

    public void setWorkFlowType(WorkFlowType workFlowType) {
        this.workFlowType = workFlowType;
    }

    public List<String> getFormGroupKeys() {
        return this.formGroupKeys;
    }

    public void setFormGroupKeys(List<String> formGroupKeys) {
        this.formGroupKeys = formGroupKeys;
    }

    public List<String> getMessageIds() {
        return this.messageIds;
    }

    public void setMessageIds(List<String> messageIds) {
        this.messageIds = messageIds;
    }

    public boolean isSendEmail() {
        return this.sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getTemplateActionParams() {
        return this.templateActionParams;
    }

    public void setTemplateActionParams(String templateActionParams) {
        this.templateActionParams = templateActionParams;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public boolean isChangeMonitorState() {
        return this.changeMonitorState;
    }

    public void setChangeMonitorState(boolean changeMonitorState) {
        this.changeMonitorState = changeMonitorState;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getSignBootModes() {
        return this.signBootModes;
    }

    public void setSignBootModes(List<String> signBootModes) {
        this.signBootModes = signBootModes;
    }
}

