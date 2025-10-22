/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.impl.interceptor.CommandContext
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;

public class ReferCacheCmd
implements Command<Integer> {
    private String processDefinitionId;

    public ReferCacheCmd(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public Integer execute(CommandContext commandContext) {
        ProcessEngineConfigurationImpl cfg = commandContext.getProcessEngineConfiguration();
        cfg.getDeploymentManager().getProcessDefinitionCache().remove(this.processDefinitionId);
        return null;
    }
}

