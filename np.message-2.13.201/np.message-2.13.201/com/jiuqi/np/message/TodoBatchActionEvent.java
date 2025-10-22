/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message;

import java.util.List;
import java.util.Map;

public class TodoBatchActionEvent {
    private String userId;
    private List<String> messageId;
    private String actionId;
    private String appName;
    private Map<String, Object> extendParams;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppName() {
        return this.appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public List<String> getMessageId() {
        return this.messageId;
    }

    public void setMessageId(List<String> messageId) {
        this.messageId = messageId;
    }

    public String getActionId() {
        return this.actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public Map<String, Object> getExtendParams() {
        return this.extendParams;
    }

    public void setExtendParams(Map<String, Object> extendParams) {
        this.extendParams = extendParams;
    }
}

