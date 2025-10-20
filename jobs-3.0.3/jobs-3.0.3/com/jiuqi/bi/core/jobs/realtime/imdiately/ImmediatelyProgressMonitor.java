/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.monitor.AbstractProgressMonitor
 *  com.jiuqi.bi.monitor.ProgressException
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.monitor.State;
import com.jiuqi.bi.core.jobs.realtime.imdiately.IImmediatelyJobStorageMiddleware;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobInfo;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobManager;
import com.jiuqi.bi.monitor.AbstractProgressMonitor;
import com.jiuqi.bi.monitor.ProgressException;

public class ImmediatelyProgressMonitor
extends AbstractProgressMonitor {
    private final ImmediatelyJobInfo jobInfo;
    private final IImmediatelyJobStorageMiddleware storageMiddleware;
    private static final long COMMIT_PROGRESS_INTERVAL = 2000L;
    private double currentProgress;
    private String currentPrompt;
    private long lastCommitTime;

    public ImmediatelyProgressMonitor(ImmediatelyJobInfo jobInfo) {
        this.jobInfo = jobInfo;
        this.storageMiddleware = ImmediatelyJobManager.getInstance().getStorageMiddleware();
    }

    protected void notify(double progress) {
        this.currentProgress = progress;
    }

    public void prompt(String msg) {
        this.currentPrompt = msg;
        this.commitProgress(System.currentTimeMillis());
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

    private void commitProgress(long now) {
        this.lastCommitTime = now;
        int progress = (int)(this.currentProgress * 100.0);
        this.jobInfo.setProgress(progress);
        this.jobInfo.setPrompt(this.currentPrompt);
        this.storageMiddleware.patch(this.jobInfo);
    }

    public void cancel() {
        super.cancel();
        this.jobInfo.setState(State.CANCELING.getValue());
        this.storageMiddleware.patch(this.jobInfo);
    }
}

