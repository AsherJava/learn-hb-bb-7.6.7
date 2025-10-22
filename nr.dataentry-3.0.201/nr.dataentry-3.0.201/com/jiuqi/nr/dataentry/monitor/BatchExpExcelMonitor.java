/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.util.UUID;

public class BatchExpExcelMonitor
implements AsyncTaskMonitor {
    private final String taskId = UUID.randomUUID().toString();
    private double progress = 0.0;
    private String message;
    private String result;
    private Object detail;

    public String getTaskId() {
        return this.taskId;
    }

    public String getTaskPoolTask() {
        return null;
    }

    public void progressAndMessage(double progress, String message) {
        this.progress = progress;
        this.message = message;
    }

    public boolean isCancel() {
        return false;
    }

    public void finish(String result, Object detail) {
        this.progress = 1.0;
        this.result = result;
        this.detail = detail;
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

