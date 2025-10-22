/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.activiti.bpmn.model.FlowElement
 *  org.activiti.bpmn.model.FlowNode
 *  org.activiti.engine.ActivitiException
 *  org.activiti.engine.impl.interceptor.Command
 *  org.activiti.engine.impl.interceptor.CommandContext
 *  org.activiti.engine.impl.persistence.entity.ExecutionEntity
 */
package com.jiuqi.nr.bpm.impl.activiti6.extension;

import java.util.List;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.ActivitiException;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;

public class SetFLowNodeAndGoCmd
implements Command<Void> {
    private FlowNode flowElement;
    private String executionId;

    public SetFLowNodeAndGoCmd(FlowNode flowElement, String executionId) {
        this.flowElement = flowElement;
        this.executionId = executionId;
    }

    public Void execute(CommandContext commandContext) {
        List flows = this.flowElement.getIncomingFlows();
        if (flows == null || flows.size() < 1) {
            throw new ActivitiException("can not jump to node. target node must have incoming flows");
        }
        ExecutionEntity executionEntity = (ExecutionEntity)commandContext.getExecutionEntityManager().findById(this.executionId);
        executionEntity.setCurrentFlowElement((FlowElement)flows.get(0));
        executionEntity.removeVariables();
        commandContext.getAgenda().planTakeOutgoingSequenceFlowsOperation(executionEntity, true);
        return null;
    }
}

