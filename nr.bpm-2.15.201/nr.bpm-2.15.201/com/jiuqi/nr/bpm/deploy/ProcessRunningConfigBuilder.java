/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.deploy;

import com.jiuqi.nr.bpm.common.ProcessRunningConfig;
import java.util.List;
import java.util.UUID;

public interface ProcessRunningConfigBuilder {
    public ProcessRunningConfigBuilder id(String var1);

    public ProcessRunningConfigBuilder name(String var1);

    public ProcessRunningConfigBuilder processDefinitionId(String var1);

    public ProcessRunningConfigBuilder taskId(UUID var1);

    public ProcessRunningConfigBuilder periods(List<String> var1);

    public ProcessRunningConfigBuilder entities(UUID var1, List<UUID> var2);

    public ProcessRunningConfigBuilder forms(List<UUID> var1);

    public ProcessRunningConfigBuilder startByIdentity(UUID var1);

    public ProcessRunningConfig get();

    public ProcessRunningConfig create();

    public ProcessRunningConfig update();

    public ProcessRunningConfig delete();
}

