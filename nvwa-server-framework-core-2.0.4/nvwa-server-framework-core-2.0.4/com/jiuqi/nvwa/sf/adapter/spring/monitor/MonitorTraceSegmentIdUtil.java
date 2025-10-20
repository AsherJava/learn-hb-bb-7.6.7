/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class MonitorTraceSegmentIdUtil {
    public static UUID getUUID() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        long mostSigBits = random.nextLong();
        long leastSigBits = random.nextLong();
        mostSigBits &= 0xFFFFFFFFFFFF0FFFL;
        leastSigBits &= 0x3FFFFFFFFFFFFFFFL;
        return new UUID(mostSigBits |= 0x4000L, leastSigBits |= Long.MIN_VALUE);
    }
}

