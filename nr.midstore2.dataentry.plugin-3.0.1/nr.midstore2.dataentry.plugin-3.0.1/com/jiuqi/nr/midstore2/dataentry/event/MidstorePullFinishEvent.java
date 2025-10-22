/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.midstore2.dataentry.event;

import com.jiuqi.nr.midstore2.dataentry.bean.MidstoreParam;
import org.springframework.context.ApplicationEvent;

public class MidstorePullFinishEvent
extends ApplicationEvent {
    private static final long serialVersionUID = 1L;

    public MidstorePullFinishEvent(MidstoreParam source) {
        super(source);
    }

    @Override
    public MidstoreParam getSource() {
        return (MidstoreParam)this.source;
    }
}

