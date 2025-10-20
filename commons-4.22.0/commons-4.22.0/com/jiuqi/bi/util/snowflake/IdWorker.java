/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.snowflake;

public class IdWorker {
    private final long workerId;
    private final long datacenterId;
    private long sequence;
    private long twepoch = 1288834974657L;
    private static final long workerIdBits = 5L;
    private static final long datacenterIdBits = 5L;
    private static final long maxWorkerId = 31L;
    private static final long maxDatacenterId = 31L;
    private static final long sequenceBits = 12L;
    private static final long workerIdShift = 12L;
    private static final long datacenterIdShift = 17L;
    private static final long timestampLeftShift = 22L;
    private static final long sequenceMask = 4095L;
    public static final long WORKER_SIZE = 32L;
    public static final long DATACENTER_SIZE = 32L;
    private long lastTimestamp = -1L;

    public static IdWorker getInstance(long sequence) {
        return new IdWorker(System.currentTimeMillis(), sequence);
    }

    public static IdWorker getInstance(long serialId, long sequence) {
        if (serialId < 0L || serialId >= 1024L) {
            throw new IllegalArgumentException(String.format("serial Id can't be greater than %d or less than 0", 1024L));
        }
        return new IdWorker(serialId % 32L, serialId / 32L, sequence);
    }

    public static IdWorker getInstance(long workerId, long datacenterId, long sequence) {
        return new IdWorker(workerId, datacenterId, sequence);
    }

    public IdWorker(long seed, long sequence) {
        long position = Math.abs(seed) % 1024L;
        this.workerId = position % 32L;
        this.datacenterId = position / 32L;
        this.sequence = sequence;
    }

    public IdWorker(long workerId, long datacenterId, long sequence) {
        if (workerId > 31L || workerId < 0L) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", 31L));
        }
        if (datacenterId > 31L || datacenterId < 0L) {
            throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", 31L));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    public long getWorkerId() {
        return this.workerId;
    }

    public long getDatacenterId() {
        return this.datacenterId;
    }

    public long getTimestamp() {
        return System.currentTimeMillis();
    }

    public synchronized long nextId() {
        long timestamp = this.timeGen();
        if (timestamp < this.lastTimestamp) {
            if (timestamp + 100L < this.lastTimestamp) {
                throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", this.lastTimestamp - timestamp));
            }
            timestamp = this.lastTimestamp;
        }
        if (this.lastTimestamp == timestamp) {
            this.sequence = this.sequence + 1L & 0xFFFL;
            if (this.sequence == 0L) {
                timestamp = this.tilNextMillis(this.lastTimestamp);
            }
        } else {
            this.sequence = 0L;
        }
        this.lastTimestamp = timestamp;
        return timestamp - this.twepoch << 22 | this.datacenterId << 17 | this.workerId << 12 | this.sequence;
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = this.timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = this.timeGen();
        }
        return timestamp;
    }

    private long timeGen() {
        return System.currentTimeMillis();
    }
}

