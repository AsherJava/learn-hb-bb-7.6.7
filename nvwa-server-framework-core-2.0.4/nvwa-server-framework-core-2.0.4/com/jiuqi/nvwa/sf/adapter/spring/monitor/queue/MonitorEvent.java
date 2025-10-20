/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.queue;

import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorRecord;

public class MonitorEvent {
    private MonitorRecord monitorRecord;

    public MonitorRecord getMonitorRecord() {
        return this.monitorRecord;
    }

    public void setMonitorRecord(MonitorRecord monitorRecord) {
        this.monitorRecord = monitorRecord;
    }
}

