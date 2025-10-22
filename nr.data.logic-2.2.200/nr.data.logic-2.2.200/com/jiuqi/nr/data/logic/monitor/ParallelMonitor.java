/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.intf.impl.AbstractMonitor
 *  com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor
 */
package com.jiuqi.nr.data.logic.monitor;

import com.jiuqi.np.dataengine.intf.impl.AbstractMonitor;
import com.jiuqi.np.dataengine.intf.impl.FmlEngineBaseMonitor;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParallelMonitor
extends FmlEngineBaseMonitor {
    private final IFmlMonitor fmlMonitor;
    private double startProgress = 0.01;
    private double endProgress = 0.99;
    private double lastProgress = 0.0;
    private static final Logger logger = LoggerFactory.getLogger(ParallelMonitor.class);

    public ParallelMonitor(IFmlMonitor fmlMonitor) {
        this.fmlMonitor = fmlMonitor;
    }

    public void setStartProgress(double startProgress, double endProgress) {
        if (startProgress > this.startProgress) {
            this.startProgress = startProgress;
        }
        if (this.endProgress > endProgress) {
            this.endProgress = endProgress;
        }
        this.lastProgress = this.startProgress;
        this.start();
    }

    public void onProgress(double progress) {
        super.onProgress(progress);
        if (this.fmlMonitor != null) {
            double currProgress = this.startProgress + progress * (this.endProgress - this.startProgress);
            logger.debug("taskId-{}\nstart-{}\nend-{}\ncur-{}", this.fmlMonitor.getTaskId(), this.startProgress, this.endProgress, currProgress);
            if (currProgress >= this.lastProgress) {
                this.lastProgress = currProgress;
                this.fmlMonitor.progressAndMessage(currProgress, "");
            }
        }
    }

    public boolean isCancel() {
        if (this.fmlMonitor == null) {
            return false;
        }
        return this.fmlMonitor.isCancel();
    }

    public void start() {
        if (this.start == 0L) {
            this.start = System.currentTimeMillis();
        }
    }

    public void error(String msg, Object sender) {
        super.error(msg, sender);
        if (this.fmlMonitor != null && sender instanceof Throwable) {
            this.fmlMonitor.error(msg, (Throwable)sender);
        }
    }

    public void exception(Exception e) {
        super.exception(e);
        if (this.fmlMonitor != null) {
            this.fmlMonitor.error(e.getMessage(), e);
        }
    }

    public AbstractMonitor getFmlEngineExtMonitor() {
        if (this.fmlMonitor == null) {
            return null;
        }
        return this.fmlMonitor.getFmlEngineMonitor();
    }
}

