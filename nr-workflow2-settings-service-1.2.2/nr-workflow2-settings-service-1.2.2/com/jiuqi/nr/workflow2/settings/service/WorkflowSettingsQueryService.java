/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.service;

import com.jiuqi.nr.workflow2.settings.dto.WorkflowSettingsQueryContext;
import java.util.List;
import java.util.Map;

public interface WorkflowSettingsQueryService {
    public Map<String, Object> getWorkflowSettings(String var1);

    public Map<String, Object> getOtherSettings(String var1);

    public Map<String, Object> getConfigWithSource(WorkflowSettingsQueryContext var1);

    public Map<String, Object> getCustomConfigSource(String var1);

    public Map<String, Object> getNodePropertySource(WorkflowSettingsQueryContext var1);

    public List<Map<String, Object>> getNodeActionSource(WorkflowSettingsQueryContext var1);

    public List<Map<String, Object>> getNodeEventSource(WorkflowSettingsQueryContext var1);

    public Map<String, Object> getEventSource(String var1, String var2);

    public List<Map<String, Object>> getNodeParticipantSource(WorkflowSettingsQueryContext var1);

    public boolean isExistWorkflowInstance(String var1);

    public Map<String, Object> getSaveTips(String var1, String var2, String var3);
}

