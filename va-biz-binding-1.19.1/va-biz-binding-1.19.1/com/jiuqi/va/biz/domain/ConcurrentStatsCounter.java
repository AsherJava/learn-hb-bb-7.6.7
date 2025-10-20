/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.domain;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public final class ConcurrentStatsCounter {
    private final LongAdder count;
    private final AtomicLong maxTime;
    private final AtomicLong minTime;
    private final LongAdder totalTime;

    public ConcurrentStatsCounter() {
        this.count = new LongAdder();
        this.maxTime = new AtomicLong(0L);
        this.minTime = new AtomicLong(Long.MAX_VALUE);
        this.totalTime = new LongAdder();
    }

    public ConcurrentStatsCounter(long time) {
        this.count = new LongAdder();
        this.count.increment();
        this.totalTime = new LongAdder();
        this.totalTime.add(time);
        this.maxTime = new AtomicLong(time);
        this.minTime = new AtomicLong(time);
    }

    private ConcurrentStatsCounter(LongAdder count, LongAdder totalTime, AtomicLong maxTime, AtomicLong minTime) {
        this.count = count;
        this.totalTime = totalTime;
        this.maxTime = maxTime;
        this.minTime = minTime;
    }

    public void record(long time) {
        this.count.increment();
        this.totalTime.add(time);
        this.setMinTime(time);
        this.setMaxTime(time);
    }

    private void setMinTime(long time) {
        long curTime;
        do {
            if ((curTime = this.minTime.get()) > time) continue;
            return;
        } while (!this.minTime.compareAndSet(curTime, time));
    }

    private void setMaxTime(long time) {
        long curTime;
        do {
            if ((curTime = this.maxTime.get()) < time) continue;
            return;
        } while (!this.maxTime.compareAndSet(curTime, time));
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

    public ConcurrentStatsCounter snapshot() {
        return new ConcurrentStatsCounter(this.count, this.totalTime, this.maxTime, this.minTime);
    }
}

