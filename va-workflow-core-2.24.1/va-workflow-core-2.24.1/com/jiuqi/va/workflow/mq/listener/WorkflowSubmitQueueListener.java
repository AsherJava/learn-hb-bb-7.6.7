/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 */
package com.jiuqi.va.workflow.mq.listener;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.workflow.model.WorkflowModel;
import com.jiuqi.va.workflow.mq.listener.AbstractWorkflowQueueListener;
import org.springframework.stereotype.Component;

@Component
public class WorkflowSubmitQueueListener
extends AbstractWorkflowQueueListener {
    public String getJoinName() {
        return "VA_WORKFLOW_SUBMIT_QUEUE";
    }

    @Override
    protected R doMessage(ObjectNode param, String messageId) {
        String content = param.get("content").textValue();
        WorkflowDTO workflowDTO = (WorkflowDTO)JSONUtil.parseObject((String)content, WorkflowDTO.class);
        workflowDTO.setTransMessageId(messageId);
        WorkflowModel workflowModel = this.getModel(workflowDTO.getUniqueCode(), workflowDTO.getProcessDefineVersion());
        return workflowModel.startProcess(workflowDTO);
    }
}

