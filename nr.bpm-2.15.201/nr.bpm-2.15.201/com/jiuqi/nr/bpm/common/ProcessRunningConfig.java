/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface ProcessRunningConfig {
    public String getId();

    public String getName();

    public String getProcessDefinitionId();

    public UUID getTaskId();

    public List<String> getPeriods();

    public Map<UUID, List<UUID>> getEntities();

    public List<UUID> getForms();

    public UUID getStartUserId();
}

