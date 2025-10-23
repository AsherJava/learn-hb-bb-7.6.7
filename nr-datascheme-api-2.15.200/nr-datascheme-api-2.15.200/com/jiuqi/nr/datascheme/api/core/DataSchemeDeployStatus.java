/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import com.jiuqi.nr.datascheme.api.core.DeployResult;
import com.jiuqi.nr.datascheme.api.core.DeployStatusEnum;
import java.time.Instant;

public interface DataSchemeDeployStatus {
    public String getDataSchemeKey();

    public DeployStatusEnum getDeployStatus();

    public Instant getUpdateTime();

    public Instant getLastUpdateTime();

    public DeployResult getDeployResult();
}

