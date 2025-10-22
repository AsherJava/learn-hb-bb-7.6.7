/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.common;

import com.jiuqi.nr.bpm.common.ProcessDefinition;
import java.io.InputStream;

public interface DeploymentBuilder {
    public DeploymentBuilder name(String var1);

    public DeploymentBuilder addStreamResource(String var1, InputStream var2, String var3);

    public DeploymentBuilder coverMode(String var1);

    public ProcessDefinition deploy();
}

