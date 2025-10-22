/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.util.JsonUtil
 */
package com.jiuqi.nr.dataentry.monitor;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.paramInfo.BatchReturnInfo;
import com.jiuqi.nr.jtable.util.JsonUtil;

public class BatchCheckAnalysisFormProgressMonitor
implements AsyncTaskMonitor {
    private AsyncTaskMonitor asyncTaskMonitor;
    private double coefficient;
    private double progress;

    public BatchCheckAnalysisFormProgressMonitor(AsyncTaskMonitor asyncTaskMonitor, double coefficient, double progress) {
        this.asyncTaskMonitor = asyncTaskMonitor;
        this.coefficient = coefficient;
        this.progress = progress;
    }

    public String getTaskId() {
        return this.asyncTaskMonitor.getTaskId();
    }

    public String getTaskPoolTask() {
        return this.asyncTaskMonitor.getTaskPoolTask();
    }

    public void progressAndMessage(double progress, String message) {
        this.asyncTaskMonitor.progressAndMessage(this.progress + progress * this.coefficient, message);
    }

    public boolean isCancel() {
        return this.asyncTaskMonitor.isCancel();
    }

    public void finish(String result, Object detail) {
        this.asyncTaskMonitor.progressAndMessage(this.progress + this.coefficient, result);
    }

    public void canceling(String result, Object detail) {
    }

    public void canceled(String result, Object detail) {
    }

    public void error(String cause, Throwable t) {
        try {
            BatchReturnInfo batchReturnInfo = (BatchReturnInfo)JsonUtil.toObject((String)cause, BatchReturnInfo.class);
            batchReturnInfo.setStatus(2);
            String jsonObject = JsonUtil.objectToJson((Object)batchReturnInfo);
            this.asyncTaskMonitor.error(jsonObject, t);
        }
        catch (Exception e) {
            this.asyncTaskMonitor.error(cause, (Throwable)e);
        }
    }

    public boolean isFinish() {
        return false;
    }
}

