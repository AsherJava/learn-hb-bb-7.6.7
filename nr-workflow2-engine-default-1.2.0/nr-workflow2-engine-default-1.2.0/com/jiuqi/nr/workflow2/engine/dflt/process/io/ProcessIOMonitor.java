/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.AbstractProgressMonitor
 *  com.jiuqi.bi.monitor.ProgressException
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.workflow2.engine.dflt.process.io;

import com.jiuqi.bi.monitor.AbstractProgressMonitor;
import com.jiuqi.bi.monitor.ProgressException;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;

public class ProcessIOMonitor
extends AbstractProgressMonitor {
    private final AsyncTaskMonitor monitor;
    private static final long COMMIT_PROGRESS_INTERVAL = 2000L;
    private double currentProgress;
    private String currentPrompt;
    private long lastCommitTime;

    public ProcessIOMonitor(AsyncTaskMonitor monitor) {
        this.monitor = monitor;
    }

    public void prompt(String msg) {
        this.currentPrompt = msg;
        this.commitProgress(System.currentTimeMillis());
    }

    protected void notify(double progress) {
        this.currentProgress = progress;
    }

    public void stepIn() {
        super.stepIn();
        long now = System.currentTimeMillis();
        if (now - this.lastCommitTime > 2000L) {
            this.commitProgress(now);
        }
    }

    public void finishTask(String taskName) throws ProgressException {
        super.finishTask(taskName);
        this.commitProgress(System.currentTimeMillis());
    }

    public void finishTask() {
        super.finishTask();
        this.commitProgress(System.currentTimeMillis());
    }

    public void cancel() {
        super.cancel();
        this.monitor.canceled("\u53d6\u6d88", (Object)"");
    }

    private void commitProgress(long now) {
        this.lastCommitTime = now;
        int progress = (int)(this.currentProgress * 100.0);
        this.monitor.progressAndMessage((double)progress, "");
    }
}

