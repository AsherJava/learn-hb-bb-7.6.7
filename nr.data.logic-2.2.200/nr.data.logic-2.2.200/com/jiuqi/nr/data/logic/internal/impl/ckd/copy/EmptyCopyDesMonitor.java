/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.internal.impl.ckd.copy;

import com.jiuqi.nr.data.logic.spi.ICopyDesMonitor;

public class EmptyCopyDesMonitor
implements ICopyDesMonitor {
    @Override
    public void info(String msg) {
    }

    @Override
    public void error(String msg) {
    }

    public static EmptyCopyDesMonitor getInstance() {
        return EmptyCopyDesMonitorInstance.INSTANCE;
    }

    public static class EmptyCopyDesMonitorInstance {
        private static final EmptyCopyDesMonitor INSTANCE = new EmptyCopyDesMonitor();
    }
}

