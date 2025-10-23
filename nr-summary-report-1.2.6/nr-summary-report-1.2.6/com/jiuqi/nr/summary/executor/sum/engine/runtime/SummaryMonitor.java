/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.nr.summary.executor.sum.engine.runtime;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;

public class SummaryMonitor
extends AbstractMonitor {
    private static final double PREPARE_WEIGHT = 0.1;
    private static final double END_WEIGHT = 0.1;
    public static final double SUMMARY_WEIGHT = 0.8;
    private AsyncTaskMonitor monitor;

    public SummaryMonitor(AsyncTaskMonitor monitor) {
        this.monitor = monitor;
    }

    public void prepare() {
        this.onProgress(0.1);
    }

    public void onProgress(double progress) {
        super.onProgress(progress);
        this.monitor.progressAndMessage(this.getCurrentProgress(), null);
    }

    public boolean isCancel() {
        return this.monitor.isCancel();
    }
}

