/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.monitor;

import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.monitor.ProgressException;

public final class DummyProgressMonitor
implements IProgressMonitor {
    private boolean canceled;
    private int level;

    @Override
    public void cancel() {
        this.canceled = true;
    }

    @Override
    public void finishTask() {
        --this.level;
    }

    @Override
    public String getCurrentTask() {
        return null;
    }

    @Override
    public double getPosition() {
        return 0.0;
    }

    @Override
    public boolean isCanceled() {
        return this.canceled;
    }

    @Override
    public void prompt(String message) {
    }

    @Override
    public void startTask(String taskName, int stepCount) {
        ++this.level;
    }

    @Override
    public void startTask(String taskName, int[] steps) {
        ++this.level;
    }

    @Override
    public void stepIn() {
    }

    @Override
    public int getCurrentLevel() {
        return this.level;
    }

    @Override
    public void finishTask(String taskName) throws ProgressException {
    }
}

