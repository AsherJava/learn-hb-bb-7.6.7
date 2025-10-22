/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.de.dataflow.common;

import java.util.Map;

public class TodoSignExecuteEvent {
    private String messageId;
    private Map<String, String> variables;

    public Map<String, String> getVariables() {
        return this.variables;
    }

    public void setVariables(Map<String, String> variables) {
        this.variables = variables;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}

