/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.event.runtime;

import com.jiuqi.nr.workflow2.engine.core.event.runtime.AsyncJobResult;

public interface IProcessAsyncMonitor {
    public String getAsyncTaskId();

    public int getWeight();

    public double getProgress();

    public String getProcessPercent();

    public void setJobProgress(double var1);

    public void setJobProgress(double var1, String var3);

    public void setJobResult(AsyncJobResult var1, String var2);

    public void setJobResult(AsyncJobResult var1, String var2, Object var3);

    public boolean isCancel();

    public void error(String var1, double var2, Throwable var4);

    public void error(String var1);

    public void error(String var1, double var2);

    public void error(String var1, Throwable var2);

    public void info(String var1, double var2, Throwable var4);

    public void info(String var1);

    public void info(String var1, double var2);

    public void info(String var1, Throwable var2);
}

