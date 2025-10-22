/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.nr.data.gather.listener;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;

public class DataGatherParallelMonitor
extends AbstractMonitor {
    private AsyncTaskMonitor asyncTaskMonitor;
    private double startProgress = 0.01;
    private double endProgress = 0.99;

    public DataGatherParallelMonitor() {
    }

    public DataGatherParallelMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public void setStartProgress(double startProgress, double endProgress) {
        if (startProgress > this.startProgress) {
            this.startProgress = startProgress;
        }
        if (this.endProgress > endProgress) {
            this.endProgress = endProgress;
        }
        this.start();
    }

    public void onProgress(double progress) {
        super.onProgress(progress);
        if (this.asyncTaskMonitor != null) {
            double currProgress = this.startProgress + progress * (this.endProgress - this.startProgress);
            this.asyncTaskMonitor.progressAndMessage(currProgress, "");
        }
    }

    public boolean isCancel() {
        if (this.asyncTaskMonitor == null) {
            return false;
        }
        return this.asyncTaskMonitor.isCancel();
    }

    public void canceled(String result, Object detail) {
        if (this.asyncTaskMonitor != null) {
            this.asyncTaskMonitor.canceled(result, detail);
        }
    }

    public void start() {
        if (this.start == 0L) {
            this.start = System.currentTimeMillis();
        }
    }

    public void error(String msg, Object sender) {
        if (this.asyncTaskMonitor != null && sender instanceof Throwable) {
            this.asyncTaskMonitor.error(msg, (Throwable)sender);
        } else {
            super.error(msg, sender);
        }
    }
}

