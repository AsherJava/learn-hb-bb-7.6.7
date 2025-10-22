/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ILogger
 */
package com.jiuqi.np.asynctask;

import com.jiuqi.bi.logging.ILogger;

public interface AsyncTaskMonitor {
    public String getTaskId();

    public String getTaskPoolTask();

    public void progressAndMessage(double var1, String var3);

    public boolean isCancel();

    public void finish(String var1, Object var2);

    default public void finished(String result, Object detail) {
    }

    public void canceling(String var1, Object var2);

    public void canceled(String var1, Object var2);

    public void error(String var1, Throwable var2);

    default public void error(String result, Throwable t, String detail) {
        this.error(result, t);
    }

    public boolean isFinish();

    default public double getLastProgress() {
        return 0.0;
    }

    default public ILogger getBILogger() {
        return null;
    }
}

