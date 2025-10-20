/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.EventFactory
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.queue;

import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEvent;
import com.lmax.disruptor.EventFactory;

public class MonitorEventFactory
implements EventFactory<MonitorEvent> {
    public MonitorEvent newInstance() {
        return new MonitorEvent();
    }
}

