/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.realtime.imdiately;

import com.jiuqi.bi.core.jobs.core.QuartzJobThreadFactory;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class ImmediatelyJobThreadPool {
    private ThreadPoolExecutor executor = null;
    private static final ImmediatelyJobThreadPool instance = new ImmediatelyJobThreadPool();

    private ImmediatelyJobThreadPool() {
    }

    public void initialize() {
        this.initialize(0, Integer.MAX_VALUE);
    }

    public void initialize(int coreSize, int maxSize) {
        if (coreSize < 0) {
            coreSize = 0;
        }
        if (maxSize <= 0) {
            maxSize = Integer.MAX_VALUE;
        }
        if (ImmediatelyJobThreadPool.instance.executor == null) {
            ImmediatelyJobThreadPool.instance.executor = new ThreadPoolExecutor(coreSize, maxSize, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), new QuartzJobThreadFactory("ImmediatelyJob"), new ThreadPoolExecutor.AbortPolicy());
        }
    }

    public static ImmediatelyJobThreadPool getInstance() {
        return instance;
    }

    public void submit(Runnable runnable) {
        if (this.executor == null) {
            throw new RuntimeException("\u7acb\u5373\u6267\u884c\u4efb\u52a1\u7ebf\u7a0b\u6c60\u672a\u521d\u59cb\u5316");
        }
        this.executor.submit(runnable);
    }
}

