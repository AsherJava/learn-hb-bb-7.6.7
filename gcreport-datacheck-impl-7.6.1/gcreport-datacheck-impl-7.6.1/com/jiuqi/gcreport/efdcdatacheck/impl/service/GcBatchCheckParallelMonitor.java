/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;

public class GcBatchCheckParallelMonitor
extends AbstractMonitor {
    private AsyncTaskMonitor asyncTaskMonitor;

    public GcBatchCheckParallelMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public void onProgress(double progress) {
        super.onProgress(progress);
        if (this.asyncTaskMonitor != null) {
            this.asyncTaskMonitor.progressAndMessage(progress, "");
        }
    }

    public boolean isCancel() {
        if (this.asyncTaskMonitor != null) {
            return false;
        }
        return this.asyncTaskMonitor.isCancel();
    }

    public void start() {
        if (this.start == 0L) {
            this.start = System.currentTimeMillis();
        }
    }

    public void finish() {
        this.onProgress(1.0);
        if (this.asyncTaskMonitor != null) {
            this.asyncTaskMonitor.finish("\u6279\u91cf\u7a3d\u6838", (Object)"");
        }
    }
}

