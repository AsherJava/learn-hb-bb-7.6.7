/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipant;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.TodoReceiver;

public class NodeParticipantImpl
implements NodeParticipant {
    private ParticipantItem actionExecuter;
    private TodoReceiver todoReceiver;

    @Override
    public ParticipantItem getActionExecuter() {
        return this.actionExecuter;
    }

    public void setActionExecuter(ParticipantItem actionExecuter) {
        this.actionExecuter = actionExecuter;
    }

    @Override
    public TodoReceiver getTodoReceiver() {
        return this.todoReceiver;
    }

    public void setTodoReceiver(TodoReceiver todoReceiverImpl) {
        this.todoReceiver = todoReceiverImpl;
    }
}

