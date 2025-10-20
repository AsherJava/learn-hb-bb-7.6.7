/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.node.ObjectNode
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 *  com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO
 */
package com.jiuqi.va.workflow.mq.listener;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.domain.workflow.retract.WorkflowRetractLockDTO;
import com.jiuqi.va.workflow.mq.listener.AbstractWorkflowQueueListener;
import org.springframework.stereotype.Component;

@Component
public class WorkflowRetractLockQueueListener
extends AbstractWorkflowQueueListener {
    public String getJoinName() {
        return "VA_WORKFLOW_RETRACT_LOCK_QUEUE";
    }

    @Override
    protected R doMessage(ObjectNode param, String messageId) throws InterruptedException {
        if (!param.has("workflowLockRetract")) {
            throw new WorkflowException("\u7f3a\u5c11\u5fc5\u8981\u53c2\u6570");
        }
        try {
            WorkflowRetractLockDTO retractLockDTO = (WorkflowRetractLockDTO)JSONUtil.parseObject((String)JSONUtil.toJSONString((Object)param.get("workflowLockRetract")), WorkflowRetractLockDTO.class);
            this.retractLockService.add(retractLockDTO);
        }
        catch (Exception e) {
            throw new WorkflowException(e.getMessage(), (Throwable)e);
        }
        return R.ok();
    }
}

