/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.deploy;

import com.jiuqi.nr.bpm.common.ProcessDefinition;
import java.io.InputStream;

public interface DeploymentBuilder {
    public DeploymentBuilder name(String var1);

    public DeploymentBuilder addStreamResource(String var1, InputStream var2);

    public DeploymentBuilder coverMode();

    public ProcessDefinition deploy();
}

