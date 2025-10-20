/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.monitor;

import com.jiuqi.va.query.monitor.QueryLogEvent;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import org.springframework.beans.BeanUtils;

public class QueryStateInfo {
    private QueryLogEvent logEvent;
    private final LongAdder count;
    private final AtomicLong maxTime;
    private final AtomicLong minTime;
    private final LongAdder totalTime;

    public QueryStateInfo() {
        this.count = new LongAdder();
        this.totalTime = new LongAdder();
        this.maxTime = new AtomicLong(0L);
        this.minTime = new AtomicLong(Long.MAX_VALUE);
    }

    private QueryStateInfo(LongAdder count, LongAdder totalTime, AtomicLong maxTime, AtomicLong minTime, QueryLogEvent logEvent) {
        this.count = count;
        this.totalTime = totalTime;
        this.maxTime = maxTime;
        this.minTime = minTime;
        this.logEvent = logEvent;
    }

    public void record(long time, QueryLogEvent myEvent) {
        this.count.increment();
        this.totalTime.add(time);
        this.setMinTime(time);
        this.setMaxTime(time, myEvent);
    }

    private void setMinTime(long time) {
        long curTime;
        while ((curTime = this.minTime.get()) > time && !this.minTime.compareAndSet(curTime, time)) {
        }
    }

    private void setMaxTime(long time, QueryLogEvent myEvent) {
        long curTime = this.maxTime.get();
        if (curTime >= time) {
            return;
        }
        boolean b = this.maxTime.compareAndSet(curTime, time);
        if (b) {
            this.logEvent = myEvent;
        }
    }

    public QueryStateInfo snapshot() {
        QueryLogEvent target = new QueryLogEvent();
        BeanUtils.copyProperties(this.logEvent, target);
        return new QueryStateInfo(this.count, this.totalTime, this.maxTime, this.minTime, target);
    }

    public LongAdder getCount() {
        return this.count;
    }

    public AtomicLong getMaxTime() {
        return this.maxTime;
    }

    public AtomicLong getMinTime() {
        return this.minTime;
    }

    public LongAdder getTotalTime() {
        return this.totalTime;
    }

    public QueryLogEvent getLogEvent() {
        return this.logEvent;
    }

    public void setLogEvent(QueryLogEvent logEvent) {
        this.logEvent = logEvent;
    }
}

