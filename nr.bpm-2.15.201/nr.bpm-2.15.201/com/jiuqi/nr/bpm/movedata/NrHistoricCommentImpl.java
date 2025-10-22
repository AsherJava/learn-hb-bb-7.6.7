/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.movedata;

import java.util.Date;

public class NrHistoricCommentImpl {
    private String id;
    private String type;
    private Date time;
    private String userId;
    private String taskId;
    private String processInstanceId;
    private String action;
    private String message;
    private byte[] fullMessage;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return this.time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessInstanceId() {
        return this.processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getAction() {
        return this.action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getFullMessage() {
        return this.fullMessage;
    }

    public void setFullMessage(byte[] fullMessage) {
        this.fullMessage = fullMessage;
    }
}

