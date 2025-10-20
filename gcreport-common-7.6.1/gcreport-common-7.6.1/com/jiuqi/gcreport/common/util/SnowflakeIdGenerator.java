/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.common.util;

public class SnowflakeIdGenerator {
    private static final long SEQUENCE_BITS = 12L;
    private static final long MAX_SEQUENCE = 4095L;
    private static final long TIMESTAMP_SHIFT = 12L;
    private static long sequence = 0L;
    private static long lastTimestamp = -1L;

    public static synchronized long nextId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastTimestamp) {
            throw new RuntimeException("\u65f6\u949f\u56de\u62e8\uff0c\u65e0\u6cd5\u751f\u6210 ID");
        }
        if (timestamp == lastTimestamp) {
            if ((sequence = sequence + 1L & 0xFFFL) == 0L) {
                timestamp = SnowflakeIdGenerator.waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;
        return timestamp << 12 | sequence;
    }

    private static long waitNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}

