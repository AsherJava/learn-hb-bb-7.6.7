/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.repository.ProcessDefinition
 */
package com.jiuqi.nr.bpm.impl.activiti6.common;

import com.jiuqi.nr.bpm.common.ProcessDefinition;
import org.springframework.util.Assert;

class ProcessDefinitionWrapper
implements ProcessDefinition {
    private final org.activiti.engine.repository.ProcessDefinition innerProcessDefinition;

    public ProcessDefinitionWrapper(org.activiti.engine.repository.ProcessDefinition innerProcessDefinition) {
        Assert.notNull((Object)innerProcessDefinition, "'innerProcessDefinition' must not be null.");
        this.innerProcessDefinition = innerProcessDefinition;
    }

    @Override
    public String getId() {
        return this.innerProcessDefinition.getId();
    }

    @Override
    public String getKey() {
        return this.innerProcessDefinition.getKey();
    }

    @Override
    public String getName() {
        return this.innerProcessDefinition.getName();
    }

    @Override
    public String getResourceName() {
        return this.innerProcessDefinition.getResourceName();
    }

    @Override
    public boolean isSuspended() {
        return this.innerProcessDefinition.isSuspended();
    }
}

