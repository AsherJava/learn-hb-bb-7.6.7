/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg.event;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageData;
import java.io.Serializable;

public class SimpleMessageEvent
implements Serializable {
    private static final long serialVersionUID = 1L;
    private MessageData task;
    private BusinessKey businessKey;
    private String operator;

    public BusinessKey getBusinessKey() {
        return this.businessKey;
    }

    public void setBusinessKey(BusinessKey businessKey) {
        this.businessKey = businessKey;
    }

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
}

