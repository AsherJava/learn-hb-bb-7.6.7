/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.core.monitor;

import org.springframework.context.ApplicationEvent;

public class RabbitMQStateCheckEvent
extends ApplicationEvent {
    private String moduleName;

    public RabbitMQStateCheckEvent(Object source, String moduleName) {
        super(source);
        this.moduleName = moduleName;
    }

    public String getModuleName() {
        return this.moduleName;
    }
}

