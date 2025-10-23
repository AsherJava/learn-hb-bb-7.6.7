/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.TodoReceiver;
import java.util.List;

public class TodoReceiverImpl
implements TodoReceiver {
    public static final String SUBMIT_NODE_ACTION_EXECUTER = "submitNodeActionExecuterStrategy";
    public static final String REPORT_NODE_ACTION_EXECUTER = "reportNodeActionExecuterStrategy";
    public static final String AUDIT_NODE_ACTION_EXECUTER = "auditNodeActionExecuterStrategy";
    public static final String SUBMIT_NODE_TODO_RECEIVER = "submitNodeTodoReceiverStrategy";
    public static final String REPORT_NODE_TODO_RECEIVER = "reportNodeTodoReceiverStrategy";
    public static final String AUDIT_NODE_TODO_RECEIVER = "auditNodeTodoReceiverStrategy";
    private TodoReceiverStrategy type;
    private List<ParticipantItem> customParticipant;

    @Override
    public TodoReceiverStrategy getType() {
        return this.type;
    }

    public void setType(TodoReceiverStrategy type) {
        this.type = type;
    }

    @Override
    public List<ParticipantItem> getCustomParticipant() {
        return this.customParticipant;
    }

    public void setCustomParticipant(List<ParticipantItem> customParticipant) {
        this.customParticipant = customParticipant;
    }
}

