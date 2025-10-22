/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.deploy;

import com.jiuqi.nr.bpm.common.Query;
import java.io.InputStream;

public interface DeploymentResourceQuery
extends Query<InputStream> {
    public DeploymentResourceQuery processDefinitionKey(String var1, String var2);

    public DeploymentResourceQuery orderByDeployTime(boolean var1);
}

