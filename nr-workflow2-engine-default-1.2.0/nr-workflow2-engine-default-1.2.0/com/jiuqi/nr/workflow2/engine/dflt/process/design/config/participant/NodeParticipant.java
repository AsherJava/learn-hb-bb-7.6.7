/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.TodoReceiver;

public interface NodeParticipant {
    public ParticipantItem getActionExecuter();

    public TodoReceiver getTodoReceiver();
}

