/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.common.definition;

import com.jiuqi.nr.workflow2.engine.common.definition.model.ProcessDefinition;

public abstract class ProcessDefinitionProvider {
    public abstract String engineName();

    public abstract ProcessDefinition getProcessDefintion(String var1);

    public abstract void onProcessChanged(String var1);
}

