/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.model;

import com.jiuqi.bi.core.jobs.model.JobParameter;
import java.util.List;

public interface IJobParameterProvider {
    public List<JobParameter> getParameterList(String var1);
}

