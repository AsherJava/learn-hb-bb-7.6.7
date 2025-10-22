/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam
 *  com.jiuqi.nr.context.infc.INRContext
 */
package com.jiuqi.nr.dataentry.bean;

import com.jiuqi.nr.bpm.de.dataflow.bean.ActionParam;
import com.jiuqi.nr.context.infc.INRContext;
import com.jiuqi.nr.dataentry.bean.TodoSelect;
import java.util.List;

public class WorkflowExecuteTodoParam
implements INRContext {
    private String taskKey;
    private String period;
    private List<String> multPeriod;
    private String adjust;
    private String nodeTaskId;
    private String nodeTaskCode;
    private String actionCode;
    private String actionTitle;
    private List<String> rangeUnits;
    private List<String> rangeForms;
    private List<String> rangeGroups;
    private List<TodoSelect> rangeSelect;
    private List<String> rejectFormKeys;
    private boolean forceCommit;
    private String returnType;
    private String comment;
    private boolean sendEmail;
    private ActionParam actionParam;
    private String workflowType;
    private String contextEntityId;
    private String contextFilterExpression;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public String getNodeTaskCode() {
        return this.nodeTaskCode;
    }

    public void setNodeTaskCode(String nodeTaskCode) {
        this.nodeTaskCode = nodeTaskCode;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public List<String> getRangeUnits() {
        return this.rangeUnits;
    }

    public void setRangeUnits(List<String> rangeUnits) {
        this.rangeUnits = rangeUnits;
    }

    public List<String> getRangeForms() {
        return this.rangeForms;
    }

    public void setRangeForms(List<String> rangeForms) {
        this.rangeForms = rangeForms;
    }

    public List<String> getRangeGroups() {
        return this.rangeGroups;
    }

    public void setRangeGroups(List<String> rangeGroups) {
        this.rangeGroups = rangeGroups;
    }

    public List<String> getRejectFormKeys() {
        return this.rejectFormKeys;
    }

    public void setRejectFormKeys(List<String> rejectFormKeys) {
        this.rejectFormKeys = rejectFormKeys;
    }

    public boolean isForceCommit() {
        return this.forceCommit;
    }

    public void setForceCommit(boolean forceCommit) {
        this.forceCommit = forceCommit;
    }

    public String getReturnType() {
        return this.returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean isSendEmail() {
        return this.sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getActionTitle() {
        return this.actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public ActionParam getActionParam() {
        return this.actionParam;
    }

    public void setActionParam(ActionParam actionParam) {
        this.actionParam = actionParam;
    }

    public String getWorkflowType() {
        return this.workflowType;
    }

    public void setWorkflowType(String workflowType) {
        this.workflowType = workflowType;
    }

    public List<String> getMultPeriod() {
        return this.multPeriod;
    }

    public void setMultPeriod(List<String> multPeriod) {
        this.multPeriod = multPeriod;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getNodeTaskId() {
        return this.nodeTaskId;
    }

    public void setNodeTaskId(String nodeTaskId) {
        this.nodeTaskId = nodeTaskId;
    }

    public List<TodoSelect> getRangeSelect() {
        return this.rangeSelect;
    }

    public void setRangeSelect(List<TodoSelect> rangeSelect) {
        this.rangeSelect = rangeSelect;
    }

    public String getContextEntityId() {
        return this.contextEntityId;
    }

    public String getContextFilterExpression() {
        return this.contextFilterExpression;
    }
}

