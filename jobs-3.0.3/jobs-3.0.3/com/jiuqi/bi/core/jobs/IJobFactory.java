/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.IBaseFactory;
import com.jiuqi.bi.core.jobs.IJobModelName;
import com.jiuqi.bi.core.jobs.JobFactoryCustomPrivilege;
import com.jiuqi.bi.core.jobs.extension.JobListenerContext;

public interface IJobFactory
extends IBaseFactory,
IJobModelName {
    default public boolean configable() {
        return true;
    }

    default public boolean userRequire() {
        return false;
    }

    default public String getJobSubject() {
        return "\u9ed8\u8ba4[\u8ba1\u5212\u4efb\u52a1]";
    }

    @Override
    default public String getModelName() {
        return null;
    }

    public void beforeJobDelete(JobListenerContext var1) throws Exception;

    public void afterJobDelete(JobListenerContext var1) throws Exception;

    default public JobFactoryCustomPrivilege getJobFactoryCustomPrivilege() {
        return null;
    }
}

