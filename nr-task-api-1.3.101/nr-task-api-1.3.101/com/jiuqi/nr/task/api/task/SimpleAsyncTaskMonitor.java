/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.task.api.task;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;

public class SimpleAsyncTaskMonitor
implements AsyncTaskMonitor {
    public String getTaskId() {
        return "";
    }

    public String getTaskPoolTask() {
        return "";
    }

    public void progressAndMessage(double progress, String message) {
    }

    public boolean isCancel() {
        return false;
    }

    public void finish(String result, Object detail) {
    }

    public void canceling(String result, Object detail) {
    }

    public void canceled(String result, Object detail) {
    }

    public void error(String result, Throwable t) {
    }

    public boolean isFinish() {
        return false;
    }
}

