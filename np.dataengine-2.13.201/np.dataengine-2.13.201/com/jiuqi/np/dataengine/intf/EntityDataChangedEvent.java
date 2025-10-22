/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import org.springframework.context.ApplicationEvent;

@Deprecated
public class EntityDataChangedEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 607512336388250147L;

    public EntityDataChangedEvent(String entityTableKey) {
        super(entityTableKey);
    }

    public String getEntityTableKey() {
        return (String)this.getSource();
    }
}

