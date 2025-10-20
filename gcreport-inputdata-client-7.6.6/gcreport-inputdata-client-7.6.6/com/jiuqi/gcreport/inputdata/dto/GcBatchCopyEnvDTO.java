/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.gcreport.inputdata.dto;

import com.jiuqi.gcreport.inputdata.dto.GcBatchCopyActionParamDTO;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

public class GcBatchCopyEnvDTO {
    private GcBatchCopyActionParamDTO param;
    private Set<String> messages = new CopyOnWriteArraySet<String>();
    private AsyncTaskMonitor asyncTaskMonitor;
    private double stepProgress;
    private double currentProgress;

    public GcBatchCopyEnvDTO(GcBatchCopyActionParamDTO param) {
        this.param = param;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }

    public GcBatchCopyActionParamDTO getParam() {
        return this.param;
    }

    public void setParam(GcBatchCopyActionParamDTO param) {
        this.param = param;
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public Set<String> getMessages() {
        return this.messages;
    }

    public void setMessages(Set<String> messages) {
        this.messages = messages;
    }

    public double getStepProgress() {
        return this.stepProgress;
    }

    public void setStepProgress(double stepProgress) {
        this.stepProgress = stepProgress;
    }

    public double getCurrentProgress() {
        return this.currentProgress;
    }

    public void setCurrentProgress(double currentProgress) {
        this.currentProgress = currentProgress;
    }
}

