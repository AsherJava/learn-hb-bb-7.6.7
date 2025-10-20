/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

public class JobRedisLockManager {
    private static final JobRedisLockManager instance = new JobRedisLockManager();
    private IJobRedisLockProvider provider;
    public static final long EXPIRE_TIME = 600000L;

    private JobRedisLockManager() {
    }

    public static JobRedisLockManager getInstance() {
        return instance;
    }

    public void setProvider(IJobRedisLockProvider jobRedisLockProvider) {
        this.provider = jobRedisLockProvider;
    }

    public IJobRedisLockProvider getProvider() {
        return this.provider;
    }

    public static interface IJobRedisLockProvider {
        public String getLock(String var1, String var2);

        public void releaseLock(String var1, String var2);
    }
}

