/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.basedata.impl.event;

import org.springframework.context.ApplicationEvent;

public class BaseDataChangedEvent
extends ApplicationEvent {
    public BaseDataChangedEvent(BaseDataChangedInfo source) {
        super(source);
    }

    public BaseDataChangedInfo getBaseDataChangedInfo() {
        if (this.getSource() != null) {
            return (BaseDataChangedInfo)this.getSource();
        }
        return null;
    }

    public static class BaseDataChangedInfo {
    }
}

