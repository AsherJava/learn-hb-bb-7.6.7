/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.impl.event;

import com.jiuqi.nr.bpm.event.ReferCacheEvent;

public class ReferCacheEventImpl
implements ReferCacheEvent {
    private String processDefinitionKey;

    @Override
    public String getProcessDefinitionKey() {
        return this.processDefinitionKey;
    }

    public void setProcessDefinitionKey(String processDefinitionKey) {
        this.processDefinitionKey = processDefinitionKey;
    }
}

