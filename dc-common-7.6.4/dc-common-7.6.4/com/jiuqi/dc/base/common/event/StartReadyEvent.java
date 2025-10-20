/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.event;

import org.springframework.context.ApplicationEvent;

public class StartReadyEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 8995235093117478364L;

    public StartReadyEvent(Object source) {
        super(source);
    }
}

