/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg.event;

import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageData;
import java.io.Serializable;
import java.util.Set;

public class MessageParamEvent
implements Serializable {
    private static final long serialVersionUID = 1L;
    private MessageData task;
    private String dimensionValue;
    private String operator;
    private String actionCode;
    private String content;
    private boolean sendMail;
    private Set<String> fromOrGroupKeys;
    private int canUploadUnitSize;
    private String formKey;
    private Set<String> signRoles;

    public String getOperator() {
        return this.operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public MessageData getTask() {
        MessageData messageData = new MessageData();
        messageData.setId(this.task.getId());
        messageData.setName(this.task.getName());
        messageData.setProcessDefinitionId(this.task.getProcessDefinitionId());
        messageData.setProcessInstanceId(this.task.getProcessInstanceId());
        messageData.setUserTaskId(this.task.getUserTaskId());
        return messageData;
    }

    public void setTask(MessageData task) {
        MessageData messageData = new MessageData();
        messageData.setId(task.getId());
        messageData.setName(task.getName());
        messageData.setProcessDefinitionId(task.getProcessDefinitionId());
        messageData.setProcessInstanceId(task.getProcessInstanceId());
        messageData.setUserTaskId(task.getUserTaskId());
        this.task = messageData;
    }

    public String getDimensionValue() {
        return this.dimensionValue;
    }

    public void setDimensionValue(String dimensionValue) {
        this.dimensionValue = dimensionValue;
    }

    public String getActionCode() {
        return this.actionCode;
    }

    public void setActionCode(String actionCode) {
        this.actionCode = actionCode;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSendMail() {
        return this.sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }

    public Set<String> getFromOrGroupKeys() {
        return this.fromOrGroupKeys;
    }

    public void setFromOrGroupKeys(Set<String> fromOrGroupKeys) {
        this.fromOrGroupKeys = fromOrGroupKeys;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getCanUploadUnitSize() {
        return this.canUploadUnitSize;
    }

    public void setCanUploadUnitSize(int canUploadUnitSize) {
        this.canUploadUnitSize = canUploadUnitSize;
    }

    public Set<String> getSignRoles() {
        return this.signRoles;
    }

    public void setSignRoles(Set<String> signRoles) {
        this.signRoles = signRoles;
    }
}

