/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.multcheck2.provider;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.multcheck2.bean.MultcheckItem;
import com.jiuqi.nr.multcheck2.provider.MultcheckContext;
import java.io.Serializable;

public class CheckItemParam
implements Serializable {
    private String runId;
    private boolean beforeReport;
    private MultcheckContext context;
    private MultcheckItem checkItem;
    private AsyncTaskMonitor asyncTaskMonitor;

    public String getRunId() {
        return this.runId;
    }

    public void setRunId(String runId) {
        this.runId = runId;
    }

    public boolean isBeforeReport() {
        return this.beforeReport;
    }

    public void setBeforeReport(boolean beforeReport) {
        this.beforeReport = beforeReport;
    }

    public MultcheckContext getContext() {
        return this.context;
    }

    public void setContext(MultcheckContext context) {
        this.context = context;
    }

    public MultcheckItem getCheckItem() {
        return this.checkItem;
    }

    public void setCheckItem(MultcheckItem checkItem) {
        this.checkItem = checkItem;
    }

    public AsyncTaskMonitor getAsyncTaskMonitor() {
        return this.asyncTaskMonitor;
    }

    public void setAsyncTaskMonitor(AsyncTaskMonitor asyncTaskMonitor) {
        this.asyncTaskMonitor = asyncTaskMonitor;
    }
}

