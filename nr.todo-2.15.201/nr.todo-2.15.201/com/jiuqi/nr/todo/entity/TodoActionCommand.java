/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.todo.entity;

import java.util.List;
import java.util.Map;

public class TodoActionCommand {
    private List<String> messageId;
    private String actionId;
    private String appName;
    private Map<String, Object> extendsParams;

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

    public Map<String, Object> getExtendsParams() {
        return this.extendsParams;
    }

    public void setExtendsParams(Map<String, Object> extendsParams) {
        this.extendsParams = extendsParams;
    }
}

