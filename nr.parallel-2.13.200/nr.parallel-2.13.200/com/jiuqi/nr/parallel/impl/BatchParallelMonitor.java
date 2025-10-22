/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.parallel.BatchParallelExeTask;

public class BatchParallelMonitor
extends AbstractMonitor
implements IMonitor {
    protected BatchParallelExeTask task;
    protected AsyncTaskMonitor asyncTaskMonitor;

    public BatchParallelMonitor(BatchParallelExeTask task) {
        this.task = task;
    }

    public void onProgress(double progress) {
        super.onProgress(progress);
        if (this.asyncTaskMonitor != null) {
            this.asyncTaskMonitor.progressAndMessage(progress, "");
        }
    }

    public void finish() {
        if (!this.finished) {
            super.finish();
            if (this.task != null) {
                this.task.setFinished(true);
            }
            if (this.asyncTaskMonitor != null) {
                this.asyncTaskMonitor.finish(this.task.getKey(), (Object)this.task.getKey());
            }
        }
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }
}

