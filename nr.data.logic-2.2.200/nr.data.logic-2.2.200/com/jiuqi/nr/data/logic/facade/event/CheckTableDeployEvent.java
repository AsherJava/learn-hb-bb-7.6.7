/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.event;

import com.jiuqi.nr.data.logic.facade.event.CheckTableDeploySource;
import org.springframework.context.ApplicationEvent;

public class CheckTableDeployEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 8534551808465441577L;

    public CheckTableDeployEvent(CheckTableDeploySource source) {
        super(source);
    }

    @Override
    public CheckTableDeploySource getSource() {
        return (CheckTableDeploySource)this.source;
    }
}

