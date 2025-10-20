/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.core.bridge;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.model.JobModel;
import java.util.Map;

public abstract class AbstractJobBridge {
    public abstract String getBridgeType();

    public abstract void restartAll() throws JobsException;

    public abstract void init() throws JobsException;

    public abstract void shutdownAll() throws JobsException;

    public abstract boolean isReady();

    public abstract void scheduleJob(JobModel var1) throws JobsException;

    public abstract void unscheduleJob(String var1, String var2) throws JobsException;

    public abstract void manualExecuteJob(JobModel var1, String var2, Map<String, String> var3, String var4, String var5) throws JobsException;

    public abstract void enableJob(String var1, String var2) throws JobsException;

    public abstract void disableJob(String var1, String var2) throws JobsException;
}

