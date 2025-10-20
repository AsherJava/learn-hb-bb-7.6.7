/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.BaseFactory;
import com.jiuqi.bi.core.jobs.IJobFactory;
import com.jiuqi.bi.core.jobs.JobExecutor;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.extension.DefaultJobClassifier;
import com.jiuqi.bi.core.jobs.extension.IJobClassifier;
import com.jiuqi.bi.core.jobs.extension.IJobListener;
import com.jiuqi.bi.core.jobs.extension.IUIConfiguration;
import com.jiuqi.bi.core.jobs.extension.JobListenerContext;

public abstract class JobFactory
extends BaseFactory
implements IJobFactory {
    public IUIConfiguration getUIConfiguration() {
        return null;
    }

    public abstract JobExecutor createJobExecutor(String var1) throws JobsException;

    @Override
    public IJobClassifier getJobClassifier() {
        return new DefaultJobClassifier(this.getJobCategoryId());
    }

    public IJobListener getJobListener() {
        return null;
    }

    @Override
    public void beforeJobDelete(JobListenerContext context) throws Exception {
        if (this.getJobListener() != null) {
            this.getJobListener().beforeJobDelete(context);
        }
    }

    @Override
    public void afterJobDelete(JobListenerContext context) throws Exception {
        if (this.getJobListener() != null) {
            this.getJobListener().afterJobDelete(context);
        }
    }
}

