/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.transfer.dispatch;

import com.jiuqi.nr.attachment.transfer.context.TaskContext;
import com.jiuqi.nr.attachment.transfer.dispatch.TaskScheduler;

public class TaskDispatch
extends Thread {
    protected final TaskScheduler scheduler;
    protected final TaskContext taskContext;

    public TaskDispatch(TaskScheduler scheduler, TaskContext taskContext) {
        this.scheduler = scheduler;
        this.taskContext = taskContext;
    }

    public void lock() throws InterruptedException {
        this.scheduler.lock();
    }

    public void unLock() {
        this.scheduler.unlock();
    }

    protected void stopThread(boolean cancel, boolean force) {
        this.scheduler.shutdown(cancel, force);
    }

    protected boolean pauseThread() {
        this.scheduler.pause();
        return true;
    }

    protected boolean resumeThread() {
        this.scheduler.resume();
        return true;
    }

    public boolean isRunning() {
        return this.scheduler.isAlive();
    }
}

