/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.event.source;

import java.util.Map;

public interface WorkflowEventSource {
    public String getEventId();

    public Map<String, Object> getSource(String var1);
}

