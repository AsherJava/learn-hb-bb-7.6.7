/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.utils;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class VaWorkflowThreadUtils {
    private static volatile ThreadPoolExecutor threadPool;
    public static final int CORE_POOL_SIZE;
    public static final int MAX_POOL_SIZE;
    public static final int KEEP_ALIVE_TIME = 1000;
    public static final int BLOCK_QUEUE_SIZE = 2000;

    private VaWorkflowThreadUtils() {
    }

    public static void executor(Runnable runnable) {
        VaWorkflowThreadUtils.getThreadPoolExecutor().execute(runnable);
    }

    public static <T> Future<T> submit(Callable<T> callable) {
        return VaWorkflowThreadUtils.getThreadPoolExecutor().submit(callable);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        if (threadPool != null) return threadPool;
        Class<VaWorkflowThreadUtils> clazz = VaWorkflowThreadUtils.class;
        synchronized (VaWorkflowThreadUtils.class) {
            if (threadPool != null) return threadPool;
            threadPool = new ThreadPoolExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, 1000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(2000), new ThreadPoolExecutor.CallerRunsPolicy());
            // ** MonitorExit[var0] (shouldn't be in output)
            return threadPool;
        }
    }

    static {
        CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors() + 1;
        MAX_POOL_SIZE = Runtime.getRuntime().availableProcessors() << 1;
    }
}

