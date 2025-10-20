/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.IBaseFactory;
import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.bi.core.jobs.defaultlog.AsyncLogger;
import com.jiuqi.bi.core.jobs.defaultlog.DefaultLogGenerator;
import com.jiuqi.bi.core.jobs.defaultlog.Logger;
import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.ILogGenerator;
import com.jiuqi.bi.core.jobs.model.IJobParameterProvider;
import java.util.List;

public abstract class BaseFactory
implements IBaseFactory {
    public abstract IJobClassifier getJobClassifier();

    @Override
    public List<ILogGenerator.LogItem> getLastLogsAfter(String instanceId, long afterTimeStamp, boolean includeSubLog) throws Exception {
        return this.getLogGenerator().getLastLogsAfter(instanceId, afterTimeStamp, includeSubLog);
    }

    public ILogGenerator getLogGenerator() {
        return new DefaultLogGenerator();
    }

    public Logger createLogger(JobContext context) {
        return new AsyncLogger(context);
    }

    public IJobParameterProvider getParameterProvider() {
        return null;
    }
}

