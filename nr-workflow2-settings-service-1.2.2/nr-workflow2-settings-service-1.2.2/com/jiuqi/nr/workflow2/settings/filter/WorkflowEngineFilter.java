/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.filter;

import java.util.List;
import org.springframework.beans.factory.annotation.Value;

public class WorkflowEngineFilter {
    @Value(value="${jiuqi.nr.workflow2.engine.whitList:#{null}}")
    private List<String> workflowEngineWhiteList;

    public boolean isEnabled(String engineName) {
        if (this.workflowEngineWhiteList == null || this.workflowEngineWhiteList.isEmpty()) {
            return true;
        }
        return this.workflowEngineWhiteList.contains(engineName);
    }

    public List<String> getWorkflowEngineWhiteList() {
        return this.workflowEngineWhiteList;
    }
}

