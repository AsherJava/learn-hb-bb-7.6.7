/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.IMonitor
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.nr.parallel.impl.BatchParallelMonitor;

public class BatchSerialMonitor
extends AbstractMonitor
implements IMonitor {
    protected double progressWeight = 1.0;
    protected AbstractMonitor mainMonitor;
    protected double startProgress = 0.0;
    protected BatchParallelMonitor errorMonitor;

    public void onProgress(double progress) {
        double currentProgress = this.startProgress + progress * this.progressWeight;
        if (this.mainMonitor != null) {
            this.mainMonitor.onProgress(currentProgress);
        }
    }

    public void finish() {
        if (!this.finished) {
            super.finish();
            if (this.errorMonitor != null) {
                this.errorMonitor.finish();
            }
        }
    }

    public void error(FormulaCheckEventImpl event) {
        if (this.errorMonitor != null) {
            this.errorMonitor.error(event);
        }
    }

    public void setMainMonitor(AbstractMonitor mainMonitor) {
        this.mainMonitor = mainMonitor;
    }

    public double getStartProgress() {
        return this.startProgress;
    }

    public void setStartProgress(double startProgress) {
        this.startProgress = startProgress;
    }

    public void setProgressWeight(double progressWeight) {
        this.progressWeight = progressWeight;
    }

    public BatchParallelMonitor getErrorMonitor() {
        return this.errorMonitor;
    }

    public void setErrorMonitor(BatchParallelMonitor errorMonitor) {
        this.errorMonitor = errorMonitor;
    }
}

