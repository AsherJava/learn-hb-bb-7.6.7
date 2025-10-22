/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.bpm.deploy;

import com.jiuqi.nr.bpm.common.ProcessDefinition;
import com.jiuqi.nr.bpm.common.Query;

public interface ProcessDefinitionQuery
extends Query<ProcessDefinition> {
    public ProcessDefinitionQuery active();

    public ProcessDefinitionQuery suspended();

    public ProcessDefinitionQuery processDefinitionId(String var1);

    public ProcessDefinitionQuery processDefinitionKey(String var1);

    public ProcessDefinitionQuery latestVersion();

    public ProcessDefinitionQuery orderByProcessDefinitionId(boolean var1);
}

