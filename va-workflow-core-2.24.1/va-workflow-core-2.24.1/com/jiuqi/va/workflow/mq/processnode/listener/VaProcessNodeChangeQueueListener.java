/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.mq.processnode.listener;

import com.jiuqi.va.workflow.mq.processnode.listener.AbstractProcessNodeQueueListener;
import java.text.ParseException;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class VaProcessNodeChangeQueueListener
extends AbstractProcessNodeQueueListener {
    public String getJoinName() {
        return "VA_WORKFLOW_NODE_CHANGE_QUEUE";
    }

    @Override
    protected void doMessage(Map<String, Object> param) throws ParseException {
        this.vaWorkflowProcessNodeQueueMsgHandler.processNodeChange(param);
    }
}

