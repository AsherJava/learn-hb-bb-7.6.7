/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.engine.history.HistoricVariableInstance
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.impl.interceptor.CommandContext
 *  org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import java.util.ArrayList;
import java.util.List;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.HistoricVariableInstanceEntity;

public class HistoricVariableInstanceEntityCmd
implements Command<List<HistoricVariableInstanceEntity>> {
    private List<HistoricVariableInstance> variableInstanceIds;

    public HistoricVariableInstanceEntityCmd(List<HistoricVariableInstance> variableInstanceIds) {
        this.variableInstanceIds = variableInstanceIds;
    }

    public List<HistoricVariableInstanceEntity> execute(CommandContext commandContext) {
        ArrayList<HistoricVariableInstanceEntity> historicIdentityLinkEntities = new ArrayList<HistoricVariableInstanceEntity>();
        for (HistoricVariableInstance historicVariableInstance : this.variableInstanceIds) {
            HistoricVariableInstanceEntity historicVariableInstanceEntity = commandContext.getHistoricVariableInstanceEntityManager().findHistoricVariableInstanceByVariableInstanceId(historicVariableInstance.getId());
            historicIdentityLinkEntities.add(historicVariableInstanceEntity);
        }
        return historicIdentityLinkEntities;
    }
}

