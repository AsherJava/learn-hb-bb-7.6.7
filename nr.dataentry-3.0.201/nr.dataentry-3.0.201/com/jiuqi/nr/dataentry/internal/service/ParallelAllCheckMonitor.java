/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl
 *  com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo
 *  com.jiuqi.nr.jtable.service.impl.CheckMonitor
 *  com.jiuqi.nr.parallel.BatchParallelExeTask
 *  com.jiuqi.nr.parallel.impl.BatchParallelMonitor
 */
package com.jiuqi.nr.dataentry.internal.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.np.dataengine.intf.impl.FormulaCheckEventImpl;
import com.jiuqi.nr.jtable.params.output.FormulaCheckReturnInfo;
import com.jiuqi.nr.jtable.service.impl.CheckMonitor;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.impl.BatchParallelMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ParallelAllCheckMonitor
extends BatchParallelMonitor {
    private static final Logger logger = LoggerFactory.getLogger(ParallelAllCheckMonitor.class);
    private double startProgress = 0.01;
    private double endProgress = 0.99;
    private AsyncTaskMonitor asyncTaskMonitor;
    private CheckMonitor checkMonitor;

    public ParallelAllCheckMonitor(BatchParallelExeTask task) {
        super(task);
    }

    public ParallelAllCheckMonitor(BatchParallelExeTask task, AsyncTaskMonitor asyncTaskMonitor) {
        super(task);
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public void error(FormulaCheckEventImpl event) {
        this.checkMonitor.error(event);
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
        if (this.task != null && this.task.isFinished()) {
            return;
        }
        super.onProgress(progress);
        if (this.asyncTaskMonitor != null) {
            double currProgress = this.startProgress + progress * (this.endProgress - this.startProgress);
            String batchCheck = "batch_check_ing";
            this.asyncTaskMonitor.progressAndMessage(currProgress, batchCheck);
        }
    }

    public void finish() {
        if (!this.finished) {
            try {
                super.finish();
            }
            catch (Exception e) {
                logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
            }
        }
        this.checkMonitor.finish();
    }

    public FormulaCheckReturnInfo getCheckResult() {
        return this.checkMonitor.getCheckResult();
    }

    public CheckMonitor getCheckMonitor() {
        return this.checkMonitor;
    }

    public void setCheckMonitor(CheckMonitor checkMonitor) {
        this.checkMonitor = checkMonitor;
    }
}

