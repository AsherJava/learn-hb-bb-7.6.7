/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.mq.processnode.listener.deprecated;

import com.jiuqi.va.workflow.mq.processnode.listener.AbstractProcessNodeQueueListener;
import java.text.ParseException;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@Deprecated
public class VaProcessNodeChangeQueueListenerDeprecated
extends AbstractProcessNodeQueueListener {
    public String getJoinName() {
        return "va.workflow.ProcessNode.change.queue";
    }

    @Override
    protected void doMessage(Map<String, Object> param) throws ParseException {
        this.vaWorkflowProcessNodeQueueMsgHandler.processNodeChange(param);
    }
}

