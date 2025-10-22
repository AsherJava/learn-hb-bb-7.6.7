/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bpm.de.dataflow.bean;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bpm.common.TaskContext;
import java.util.List;

public class ExecuteParam {
    private String taskId;
    private String formSchemeKey;
    private DimensionValueSet dimSet;
    private String userId;
    private String actionId;
    private String comment;
    private String returnType;
    private boolean forceUpload;
    private boolean sendEmail;
    private String messageId;
    private String formKey;
    private String groupKey;
    private String period;
    private boolean agreed;
    private String nodeId;
    private TaskContext taskContext;
    private List<String> signBootModes;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public DimensionValueSet getDimSet() {
        return this.dimSet;
    }

    public void setDimSet(DimensionValueSet dimSet) {
        this.dimSet = dimSet;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getReturnType() {
        return this.returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isSendEmail() {
        return this.sendEmail;
    }

    public void setSendEmaill(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public boolean isForceUpload() {
        return this.forceUpload;
    }

    public void setForceUpload(boolean forceUpload) {
        this.forceUpload = forceUpload;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public boolean isAgreed() {
        return this.agreed;
    }

    public void setAgreed(boolean agreed) {
        this.agreed = agreed;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getNodeId() {
        return this.nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public TaskContext getTaskContext() {
        return this.taskContext;
    }

    public void setTaskContext(TaskContext taskContext) {
        this.taskContext = taskContext;
    }

    public List<String> getSignBootModes() {
        return this.signBootModes;
    }

    public void setSignBootModes(List<String> signBootModes) {
        this.signBootModes = signBootModes;
    }

    public String toString() {
        return "ExecuteParam{nodeId='" + this.nodeId + '\'' + ", agreed=" + this.agreed + ", period='" + this.period + '\'' + ", groupKey='" + this.groupKey + '\'' + ", formKey='" + this.formKey + '\'' + ", messageId='" + this.messageId + '\'' + ", sendEmail=" + this.sendEmail + ", forceUpload=" + this.forceUpload + ", comment='" + this.comment + '\'' + ", actionId='" + this.actionId + '\'' + ", userId='" + this.userId + '\'' + ", dimSet=" + this.dimSet + ", formSchemeKey='" + this.formSchemeKey + '\'' + ", taskId='" + this.taskId + '\'' + '}';
    }
}

