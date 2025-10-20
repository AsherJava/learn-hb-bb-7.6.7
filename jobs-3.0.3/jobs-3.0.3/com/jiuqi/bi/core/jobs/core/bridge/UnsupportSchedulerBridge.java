/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.core.bridge;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.bridge.AbstractJobBridge;
import com.jiuqi.bi.core.jobs.model.JobModel;
import java.util.Map;

public class UnsupportSchedulerBridge
extends AbstractJobBridge {
    public static final String TYPE = "UNSUPPORTSCHEDULER";
    private static final String MSG = "JDK\u7248\u672c\u5c0f\u4e8e1.8\uff0c\u6682\u4e0d\u652f\u6301\u8ba1\u5212\u4efb\u52a1\u3002\u8bf7\u5347\u7ea7JDK\u81f31.8\u53ca\u4ee5\u4e0a";

    @Override
    public String getBridgeType() {
        return TYPE;
    }

    @Override
    public void init() throws JobsException {
    }

    @Override
    public void shutdownAll() throws JobsException {
    }

    @Override
    public void restartAll() throws JobsException {
    }

    @Override
    public void scheduleJob(JobModel job) throws JobsException {
        throw new JobsException(MSG);
    }

    @Override
    public void unscheduleJob(String jobId, String category) throws JobsException {
        throw new JobsException(MSG);
    }

    @Override
    public void manualExecuteJob(JobModel job, String instanceId, Map<String, String> params, String userguid, String username) throws JobsException {
        throw new JobsException(MSG);
    }

    @Override
    public void enableJob(String jobId, String category) throws JobsException {
        throw new JobsException(MSG);
    }

    @Override
    public void disableJob(String jobId, String category) throws JobsException {
        throw new JobsException(MSG);
    }

    @Override
    public boolean isReady() {
        return false;
    }
}

