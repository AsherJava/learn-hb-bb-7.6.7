/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.action.ActionInfo;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEvent;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.nodeconfig.SubmitNodeConfig;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.participant.NodeParticipant;
import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.property.SubmitProperty;
import java.util.List;
import java.util.Map;

public class SubmitNodeConfigImpl
implements SubmitNodeConfig {
    private SubmitProperty property;
    private Map<String, ActionInfo> actions;
    private Map<String, List<ActionEvent>> events;
    private NodeParticipant participant;

    @Override
    public SubmitProperty getProperty() {
        return this.property;
    }

    public void setProperty(SubmitProperty property) {
        this.property = property;
    }

    @Override
    public Map<String, ActionInfo> getActions() {
        return this.actions;
    }

    public void setActions(Map<String, ActionInfo> actions) {
        this.actions = actions;
    }

    @Override
    public Map<String, List<ActionEvent>> getEvents() {
        return this.events;
    }

    public void setEvents(Map<String, List<ActionEvent>> events) {
        this.events = events;
    }

    @Override
    public NodeParticipant getParticipant() {
        return this.participant;
    }

    public void setParticipant(NodeParticipant participant) {
        this.participant = participant;
    }
}

