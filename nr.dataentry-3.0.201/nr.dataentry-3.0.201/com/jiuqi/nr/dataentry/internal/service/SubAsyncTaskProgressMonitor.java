/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.nr.jtable.params.output.CopyReturnInfo
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.nr.jtable.params.output.CopyReturnInfo;

public class SubAsyncTaskProgressMonitor
extends AbstractMonitor {
    private AsyncTaskMonitor asyncTaskMonitor;
    private double startProgress = 0.01;
    private double endProgress = 0.99;
    private CopyReturnInfo copyReturnInfo;

    public SubAsyncTaskProgressMonitor(AsyncTaskMonitor asyncTaskMonitor, CopyReturnInfo copyReturnInfo) {
        this.asyncTaskMonitor = asyncTaskMonitor;
        this.copyReturnInfo = copyReturnInfo;
    }

    public void setStartProgress(double startProgress, double endProgress) {
        if (startProgress > this.startProgress) {
            this.startProgress = startProgress;
        }
        if (this.endProgress > endProgress) {
            this.endProgress = endProgress;
        }
    }

    public void onProgress(double progress) {
        if (this.asyncTaskMonitor != null) {
            double currProgress = this.startProgress + progress * (this.endProgress - this.startProgress);
            String batchcopying = "batch_copy_success_ing";
            this.asyncTaskMonitor.progressAndMessage(currProgress, batchcopying);
        }
    }

    public void exception(Exception e) {
        this.copyReturnInfo.getMessage().add(e.getMessage());
    }

    public boolean isCancel() {
        if (this.asyncTaskMonitor != null) {
            return false;
        }
        return this.asyncTaskMonitor.isCancel();
    }
}

