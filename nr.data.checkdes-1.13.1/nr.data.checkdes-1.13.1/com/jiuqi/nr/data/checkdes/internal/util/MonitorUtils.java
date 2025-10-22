/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.checkdes.internal.util;

import com.jiuqi.nr.data.checkdes.api.IMonitor;

public class MonitorUtils {
    private MonitorUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static void process(IMonitor monitor, double currProgress, String message) {
        if (monitor == null) {
            return;
        }
        monitor.progressAndMessage(currProgress, message);
    }
}

