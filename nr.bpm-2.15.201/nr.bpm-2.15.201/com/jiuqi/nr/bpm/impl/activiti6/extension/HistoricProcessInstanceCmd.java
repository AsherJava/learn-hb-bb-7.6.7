/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.impl.interceptor.CommandContext
 *  org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import java.util.List;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricIdentityLinkEntityImpl;

public class HistoricProcessInstanceCmd
implements Command<List<HistoricIdentityLinkEntityImpl>> {
    private String processInstanceId;

    public HistoricProcessInstanceCmd(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public HistoricProcessInstanceCmd() {
    }

    public List<HistoricIdentityLinkEntityImpl> execute(CommandContext commandContext) {
        commandContext.getHistoricIdentityLinkEntityManager().findHistoricIdentityLinksByProcessInstanceId(this.processInstanceId);
        return null;
    }
}

