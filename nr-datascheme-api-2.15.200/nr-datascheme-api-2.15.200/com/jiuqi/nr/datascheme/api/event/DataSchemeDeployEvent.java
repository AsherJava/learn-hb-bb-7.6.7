/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import com.jiuqi.nr.datascheme.api.event.DataSchemeDeploySource;
import org.springframework.context.ApplicationEvent;

public class DataSchemeDeployEvent
extends ApplicationEvent {
    private static final long serialVersionUID = -6196467161525035316L;

    public DataSchemeDeployEvent(DataSchemeDeploySource source) {
        super(source);
    }

    @Override
    public DataSchemeDeploySource getSource() {
        return (DataSchemeDeploySource)this.source;
    }
}

