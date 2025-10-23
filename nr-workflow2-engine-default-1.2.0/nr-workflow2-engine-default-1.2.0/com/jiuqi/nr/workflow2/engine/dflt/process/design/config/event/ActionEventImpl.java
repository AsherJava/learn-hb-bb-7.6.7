/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event;

import com.jiuqi.nr.workflow2.engine.dflt.process.design.config.event.ActionEvent;

public class ActionEventImpl
implements ActionEvent {
    private String eventId;
    private String eventParam;

    @Override
    public String getEventId() {
        return this.eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    @Override
    public String getEventParam() {
        return this.eventParam;
    }

    public void setEventParam(String eventParam) {
        this.eventParam = eventParam;
    }
}

