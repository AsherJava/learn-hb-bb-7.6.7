/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.event;

import com.jiuqi.nr.datascheme.api.event.DataTableDeploySource;
import org.springframework.context.ApplicationEvent;

public class DataTableDeployEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 7075892870634973728L;

    public DataTableDeployEvent(DataTableDeploySource source) {
        super(source);
    }

    @Override
    public DataTableDeploySource getSource() {
        return (DataTableDeploySource)super.getSource();
    }
}

