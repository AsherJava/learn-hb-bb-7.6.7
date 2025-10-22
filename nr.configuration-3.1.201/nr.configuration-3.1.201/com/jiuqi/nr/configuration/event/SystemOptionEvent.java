/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.event;

import com.jiuqi.nr.configuration.event.bean.EventBO;
import org.springframework.context.ApplicationEvent;

@Deprecated
public class SystemOptionEvent
extends ApplicationEvent {
    private EventBO eventBO;

    public SystemOptionEvent(Object source, EventBO eventBO) {
        super(source);
        this.eventBO = eventBO;
    }

    public EventBO getEventBO() {
        return this.eventBO;
    }

    public void setEventBO(EventBO eventBO) {
        this.eventBO = eventBO;
    }
}

