/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.deploy;

import com.jiuqi.nr.bpm.common.ProcessRunningConfig;
import com.jiuqi.nr.bpm.common.Query;
import java.util.UUID;

public interface ProcessRunningConfigQuery
extends Query<ProcessRunningConfig> {
    public ProcessRunningConfigQuery id(String var1);

    public ProcessRunningConfigQuery name(String var1);

    public ProcessRunningConfigQuery processDefinitionId(String var1);

    public ProcessRunningConfigQuery taskId(UUID var1);

    public ProcessRunningConfigQuery containsEntitiy(UUID var1, UUID var2);

    public ProcessRunningConfigQuery containsForm(UUID var1);
}

