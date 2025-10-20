/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.realtime.imdiately.IImmediatelyJobStorageMiddleware;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobPostProcessor;
import com.jiuqi.bi.core.jobs.realtime.imdiately.ImmediatelyJobThreadPool;

public class ImmediatelyJobManager {
    private static final ImmediatelyJobManager instance = new ImmediatelyJobManager();
    private ImmediatelyJobPostProcessor jobPostProcessor;
    private IImmediatelyJobStorageMiddleware storageMiddleware;

    private ImmediatelyJobManager() {
    }

    public static ImmediatelyJobManager getInstance() {
        return instance;
    }

    public void registerStorageMiddleware(IImmediatelyJobStorageMiddleware storageMiddleware) {
        this.storageMiddleware = storageMiddleware;
    }

    public void registerJobPostProcessor(ImmediatelyJobPostProcessor jobPostProcessor) {
        this.jobPostProcessor = jobPostProcessor;
    }

    public void launchImmediatelyJobThreadPool(int coreSize, int maxSize, int queueSize) {
        ImmediatelyJobThreadPool.getInstance().initialize(coreSize, maxSize);
    }

    public void launchImmediatelyJobThreadPool() {
        ImmediatelyJobThreadPool.getInstance().initialize();
    }

    public IImmediatelyJobStorageMiddleware getStorageMiddleware() {
        return this.storageMiddleware;
    }

    public ImmediatelyJobPostProcessor getJobPostProcessor() {
        return this.jobPostProcessor;
    }
}

