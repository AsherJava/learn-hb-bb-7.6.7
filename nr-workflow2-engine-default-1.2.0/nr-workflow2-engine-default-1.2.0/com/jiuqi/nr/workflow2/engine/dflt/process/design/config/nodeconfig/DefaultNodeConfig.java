/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEvent;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipant;
import java.util.List;
import java.util.Map;

public interface DefaultNodeConfig {
    public Map<String, ActionInfo> getActions();

    public Map<String, List<ActionEvent>> getEvents();

    public NodeParticipant getParticipant();
}

