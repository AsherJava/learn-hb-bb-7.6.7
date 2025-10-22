/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor
 */
package com.jiuqi.nr.batch.summary.service.engine;

import com.jiuqi.nr.batch.summary.common.StringLogger;
import com.jiuqi.nr.data.logic.facade.monitor.IFmlMonitor;

public class BatchCalcProgressMonitor
implements IFmlMonitor {
    private final StringLogger logger = new StringLogger();

    public String getTaskId() {
        return "batch-summary-calc-progress-monitor";
    }

    public void progressAndMessage(double currProgress, String message) {
        this.logger.addProcess(currProgress);
        this.logger.logInfo(message);
    }

    public void error(String message, Throwable sender) {
        this.logger.logError(message, sender);
    }

    public void error(String message, Throwable sender, Object detail) {
        this.error(message, sender);
    }

    public void finish(String result, Object detail) {
        this.logger.logInfo(result);
    }

    public void cancel(String message, Object detail) {
        this.logger.logWarn(message);
    }

    public boolean isCancel() {
        return false;
    }

    public String getLogMessage() {
        return this.logger.getLogMessage();
    }
}

