/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.nr.data.engine.datacalc;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;

public class DataSchemeCalcMonitor
extends AbstractMonitor {
    private static final double PREPARE_WEIGHT = 0.01;
    private static final double END_WEIGHT = 0.0;
    public static final double CALC_WEIGHT = 0.99;
    private AsyncTaskMonitor monitor;

    public DataSchemeCalcMonitor(AsyncTaskMonitor monitor) {
        this.monitor = monitor;
    }

    public void prepare() {
        this.onProgress(0.01);
    }

    public void onProgress(double progress) {
        super.onProgress(progress);
        this.monitor.progressAndMessage(this.getCurrentProgress(), null);
    }

    public boolean isCancel() {
        return this.monitor.isCancel();
    }
}

