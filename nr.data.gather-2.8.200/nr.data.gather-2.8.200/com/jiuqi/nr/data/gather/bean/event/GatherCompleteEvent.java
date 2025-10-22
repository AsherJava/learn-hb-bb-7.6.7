/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.gather.bean.event;

import com.jiuqi.nr.data.gather.bean.event.GatherCompleteSource;
import org.springframework.context.ApplicationEvent;

public class GatherCompleteEvent
extends ApplicationEvent {
    private static final long serialVersionUID = -6596357864287401869L;

    public GatherCompleteEvent(GatherCompleteSource source) {
        super(source);
    }

    @Override
    public GatherCompleteSource getSource() {
        return (GatherCompleteSource)this.source;
    }
}

