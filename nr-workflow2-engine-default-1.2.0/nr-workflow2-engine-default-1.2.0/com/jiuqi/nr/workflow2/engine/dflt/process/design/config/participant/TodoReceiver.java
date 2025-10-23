/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.enumeration.TodoReceiverStrategy;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.ParticipantItem;
import java.util.List;

public interface TodoReceiver {
    public TodoReceiverStrategy getType();

    public List<ParticipantItem> getCustomParticipant();
}

