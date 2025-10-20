/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.base;

import com.jiuqi.bi.core.jobs.BaseFactory;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.base.AbstractBaseJob;
import com.jiuqi.bi.core.jobs.model.IJobParameterProvider;

public abstract class BaseJobFactory
extends BaseFactory {
    public abstract AbstractBaseJob createJob(String var1) throws JobsException;

    @Override
    public abstract IJobParameterProvider getParameterProvider();
}

