/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import com.jiuqi.nr.datascheme.api.event.RefreshCache;
import org.springframework.context.ApplicationEvent;

public class RefreshSchemeCacheEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 2486760621461868571L;

    public RefreshSchemeCacheEvent(RefreshCache source) {
        super(source);
    }

    @Override
    public RefreshCache getSource() {
        return (RefreshCache)this.source;
    }
}

