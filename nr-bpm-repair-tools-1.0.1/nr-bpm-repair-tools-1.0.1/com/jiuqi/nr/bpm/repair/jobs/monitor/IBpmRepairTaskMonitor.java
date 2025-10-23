/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobExecutionException
 *  com.jiuqi.bi.core.jobs.extension.ILogGenerator
 */
package com.jiuqi.nr.bpm.repair.jobs.monitor;

import com.jiuqi.bi.core.jobs.JobExecutionException;
import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import com.jiuqi.nr.bpm.repair.jobs.monitor.AsyncJobResult;

public interface IBpmRepairTaskMonitor {
    public String getAsyncTaskId();

    public String getProcessPercent();

    public ILogGenerator getLogGenerator();

    public void setJobProgress(double var1);

    public void setJobResult(AsyncJobResult var1, String var2);

    public void setJobDetail(Object var1);

    public void setJobResultAndDetail(AsyncJobResult var1, String var2, Object var3);

    public void debug(String var1, double var2, Throwable var4);

    public void debug(String var1);

    public void debug(String var1, double var2) throws JobExecutionException;

    public void debug(String var1, Throwable var2);

    public void error(String var1, double var2, Throwable var4);

    public void error(String var1);

    public void error(String var1, double var2);

    public void error(String var1, Throwable var2);

    public void info(String var1, double var2, Throwable var4);

    public void info(String var1);

    public void info(String var1, double var2);

    public void info(String var1, Throwable var2);

    public void trace(String var1, double var2, Throwable var4);

    public void trace(String var1);

    public void trace(String var1, double var2);

    public void trace(String var1, Throwable var2);

    public void warn(String var1, double var2, Throwable var4);

    public void warn(String var1);

    public void warn(String var1, double var2);

    public void warn(String var1, Throwable var2);
}

