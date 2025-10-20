/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.lmax.disruptor.InsufficientCapacityException
 *  com.lmax.disruptor.RingBuffer
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.queue;

import com.jiuqi.nvwa.sf.adapter.spring.monitor.MonitorRecord;
import com.jiuqi.nvwa.sf.adapter.spring.monitor.queue.MonitorEvent;
import com.lmax.disruptor.InsufficientCapacityException;
import com.lmax.disruptor.RingBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MonitorEventProducer {
    public static final Logger LOGGER = LoggerFactory.getLogger("com.jiuqi.nvwa.ticket.queue");
    private RingBuffer<MonitorEvent> ringBuffer;

    public MonitorEventProducer(RingBuffer<MonitorEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void publish(MonitorRecord monitorRecord) {
        long sequence;
        try {
            sequence = this.ringBuffer.tryNext();
        }
        catch (InsufficientCapacityException e) {
            LOGGER.warn("\u76d1\u63a7\u5e73\u53f0\uff1a\u751f\u4ea7\u8005\u961f\u5217\u5df2\u6ee1\uff0c\u4e22\u5f03\u8bb0\u5f55\uff1a" + monitorRecord.toString());
            return;
        }
        try {
            MonitorEvent monitorEvent = (MonitorEvent)this.ringBuffer.get(sequence);
            monitorEvent.setMonitorRecord(monitorRecord);
        }
        finally {
            this.ringBuffer.publish(sequence);
        }
    }
}

