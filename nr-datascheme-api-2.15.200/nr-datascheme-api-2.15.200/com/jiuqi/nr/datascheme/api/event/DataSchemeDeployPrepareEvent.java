/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import com.jiuqi.nr.datascheme.api.event.DataSchemeDeployPrepareSource;
import org.springframework.context.ApplicationEvent;

public class DataSchemeDeployPrepareEvent
extends ApplicationEvent {
    private static final long serialVersionUID = -6196467161525035316L;

    public DataSchemeDeployPrepareEvent(DataSchemeDeployPrepareSource source) {
        super(source);
    }

    @Override
    public DataSchemeDeployPrepareSource getSource() {
        return (DataSchemeDeployPrepareSource)this.source;
    }
}

