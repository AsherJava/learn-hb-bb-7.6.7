/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.common;

import java.util.List;
import java.util.Map;

public class TodoMsgToOtherEvent {
    private String userId;
    private String messageId;
    private String actionId;
    private List<String> participants;
    private String appName;
    private Map<String, String> extendParams;
    private String currentActionCode;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Map<String, String> getExtendParams() {
        return this.extendParams;
    }

    public void setExtendParams(Map<String, String> extendParams) {
        this.extendParams = extendParams;
    }

    public List<String> getParticipants() {
        return this.participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getCurrentActionCode() {
        return this.currentActionCode;
    }

    public void setCurrentActionCode(String currentActionCode) {
        this.currentActionCode = currentActionCode;
    }
}

