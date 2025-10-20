/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.IJobCache;
import com.jiuqi.bi.core.jobs.IJobCacheProvider;

public class JobCacheProviderManager {
    public static final String CACHE_NAME_JOB_LOCATION = "JOB_LOCATION";
    public static final String CACHE_NAME_JOB_BEAN = "JOB_BEAN";
    public static final String CACHE_NAME_LOCK_LOG = "LOCK_LOG";
    private static final JobCacheProviderManager instance = new JobCacheProviderManager();
    private IJobCacheProvider provider;

    private JobCacheProviderManager() {
    }

    public static JobCacheProviderManager getInstance() {
        return instance;
    }

    public void setProvider(IJobCacheProvider jobCacheProvider) {
        this.provider = jobCacheProvider;
    }

    public IJobCache getJobLocationCache() {
        return this.provider.getJobCache(CACHE_NAME_JOB_LOCATION);
    }

    public IJobCache getJobInstanceBeanCache() {
        return this.provider.getJobCache(CACHE_NAME_JOB_BEAN);
    }

    public IJobCache getLockLogCache() {
        return this.provider.getJobCache(CACHE_NAME_LOCK_LOG);
    }
}

