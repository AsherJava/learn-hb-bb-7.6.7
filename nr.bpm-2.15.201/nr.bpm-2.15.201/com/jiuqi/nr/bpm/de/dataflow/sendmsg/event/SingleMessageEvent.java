/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.sendmsg.event;

import com.jiuqi.nr.bpm.businesskey.BusinessKey;
import com.jiuqi.nr.bpm.de.dataflow.sendmsg.event.MessageData;
import java.io.Serializable;
import java.util.Set;

public class SingleMessageEvent
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String actionCode;
    private String content;
    private boolean sendMail;
    private MessageData task;
    private BusinessKey businessKey;
    private String operator;
    private Set<String> signRoles;

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

    public MessageData getTask() {
        return this.task;
    }

    public void setTask(MessageData task) {
        this.task = task;
    }

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

    public Set<String> getSignRoles() {
        return this.signRoles;
    }

    public void setSignRoles(Set<String> signRoles) {
        this.signRoles = signRoles;
    }
}

