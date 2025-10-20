/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.core.bridge;

import com.jiuqi.bi.core.jobs.core.bridge.AbstractJobBridge;

public class JobBridgeFactory {
    private static final JobBridgeFactory INSTANCE = new JobBridgeFactory();
    private AbstractJobBridge bridge;

    public static JobBridgeFactory getInstance() {
        return INSTANCE;
    }

    private JobBridgeFactory() {
    }

    public void setBridge(AbstractJobBridge jobBridge) {
        this.bridge = jobBridge;
    }

    public AbstractJobBridge getDefaultBridge() {
        return this.bridge;
    }
}

