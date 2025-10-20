/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.SchedulerConfigException
 *  org.quartz.spi.ThreadPool
 */
package com.jiuqi.bi.core.jobs.core;

import com.jiuqi.bi.core.jobs.core.QuartzJobThreadFactory;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.quartz.SchedulerConfigException;
import org.quartz.spi.ThreadPool;

public class QuartzDynamicThreadPool
implements ThreadPool {
    private ThreadPoolExecutor executor = null;
    private int threadCount;
    private String instanceId;
    private String instanceName;
    private int threadPriority;

    public boolean runInThread(Runnable runnable) {
        if (runnable == null) {
            return false;
        }
        this.executor.submit(runnable);
        return true;
    }

    public int blockForAvailableThreads() {
        return this.threadCount - this.executor.getActiveCount();
    }

    public void initialize() throws SchedulerConfigException {
        if (this.threadCount < 0) {
            throw new SchedulerConfigException("Thread count must be > 0");
        }
        this.executor = new ThreadPoolExecutor(this.threadCount, this.threadCount, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new QuartzJobThreadFactory(this.instanceName), new ThreadPoolExecutor.AbortPolicy());
        this.executor.allowCoreThreadTimeOut(true);
    }

    public int getThreadCount() {
        return this.threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        if (this.executor != null) {
            this.executor.setCorePoolSize(threadCount);
            this.executor.setMaximumPoolSize(threadCount);
        }
    }

    public void shutdown(boolean b) {
        if (b) {
            this.executor.shutdown();
        } else {
            this.executor.shutdownNow();
        }
    }

    public int getPoolSize() {
        return this.getThreadCount();
    }

    public void setInstanceId(String s) {
        this.instanceId = s;
    }

    public void setInstanceName(String s) {
        this.instanceName = s;
    }

    public void setThreadPriority(int prio) {
        this.threadPriority = prio;
    }
}

