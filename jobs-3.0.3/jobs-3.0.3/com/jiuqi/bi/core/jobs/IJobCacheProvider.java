/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.IJobCache;

public interface IJobCacheProvider {
    public IJobCache getJobCache(String var1);
}

