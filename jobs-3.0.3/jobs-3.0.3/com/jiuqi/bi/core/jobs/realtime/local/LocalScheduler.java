/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.realtime.local;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.manager.JobOperationManager;
import com.jiuqi.bi.core.jobs.realtime.AbstractRealTimeJob;
import com.jiuqi.bi.core.jobs.realtime.IRealTimePostProcessor;
import com.jiuqi.bi.core.jobs.realtime.RealTimePostProcessManager;
import com.jiuqi.bi.core.jobs.realtime.core.Executor;
import com.jiuqi.bi.core.jobs.realtime.local.RealTimeLocalJobContextImpl;
import com.jiuqi.bi.util.StringUtils;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocalScheduler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final int DEFAULT_THREAD_NUMBER = 30;
    private int threadNumber = 30;
    private ThreadPoolExecutor executor = null;
    private static final Map<String, RealTimeLocalJobContextImpl> jobContextMap = new ConcurrentHashMap<String, RealTimeLocalJobContextImpl>();

    public LocalScheduler(int level, int threadNumber) {
        this.threadNumber = threadNumber;
        String levelStr = String.valueOf(level);
        this.executor = new ThreadPoolExecutor(threadNumber, threadNumber, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), new RealTimeLocalJobThreadFactory(levelStr), new ThreadPoolExecutor.AbortPolicy());
        this.executor.allowCoreThreadTimeOut(true);
    }

    public void setThreadNumber(int threadNumber) {
        if (threadNumber == this.threadNumber) {
            return;
        }
        this.threadNumber = threadNumber;
        if (this.executor != null) {
            this.executor.setCorePoolSize(threadNumber);
            this.executor.setMaximumPoolSize(threadNumber);
        }
    }

    public LocalScheduler(int level) {
        this(level, 30);
    }

    public String commit(AbstractRealTimeJob job, RealTimeLocalJobContextImpl context) {
        String jobId = context.getParameterValue("_SYS_INSTANCE_ID");
        String parentInstanceId = context.getParameterValue("_SYS_PARENT_INSTANCE_ID");
        this.executor.submit(() -> {
            JobOperationManager jobOperationManager;
            try {
                Iterator<IRealTimePostProcessor> iterator;
                JobOperationManager jobOperationManager2 = new JobOperationManager();
                for (IRealTimePostProcessor processor : RealTimePostProcessManager.getInstance().getProcessors()) {
                    processor.afterPost(job);
                }
                try {
                    if (jobOperationManager2.instanceStateIsCanceled(jobId, job.getTitle())) {
                        this.logger.info("\u4efb\u52a1[" + job.getTitle() + "]\u5df2\u88ab\u8bbe\u7f6e\u4e3a\u5df2\u53d6\u6d88\uff0c\u53d6\u6d88\u6267\u884c");
                        iterator = null;
                        return iterator;
                    }
                }
                catch (JobsException e) {
                    this.logger.error(e.getMessage(), e);
                    jobOperationManager = null;
                    return jobOperationManager;
                }
                if (context.getMonitor().isCanceled()) {
                    this.logger.debug(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u5df2\u53d6\u6d88", job.getTitle()));
                    Executor.jobCanceled(jobId);
                    iterator = null;
                    return iterator;
                }
                Executor.doExec(job, context);
            }
            finally {
                if (StringUtils.isNotEmpty((String)parentInstanceId)) {
                    RealTimeLocalJobContextImpl parentContext = jobContextMap.get(parentInstanceId);
                    parentContext.removeSubJobInstanceId(jobId);
                }
                jobContextMap.remove(jobId);
                jobOperationManager = new JobOperationManager();
                jobOperationManager.deleteRunning(jobId);
            }
            return null;
        });
        jobContextMap.put(jobId, context);
        return jobId;
    }

    public void cancel(String instanceId) {
        RealTimeLocalJobContextImpl context = jobContextMap.get(instanceId);
        if (context == null) {
            this.logger.warn("\u4efb\u52a1[" + instanceId + "]\u5e76\u6ca1\u6709\u5728\u8fd0\u884c\u72b6\u6001\uff0c\u5ffd\u7565\u53d6\u6d88\u8bf7\u6c42");
            return;
        }
        context.getMonitor().cancel();
        this.logger.debug(String.format("\u5373\u65f6\u4efb\u52a1[%s]\u6b63\u5728\u53d6\u6d88", instanceId));
    }

    public void shutdown() {
        this.executor.shutdown();
    }

    static class RealTimeLocalJobThreadFactory
    implements ThreadFactory {
        private final String namePrefix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        RealTimeLocalJobThreadFactory(String name) {
            this.namePrefix = "BI-Local-Scheduler-" + name;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, this.namePrefix + "_" + this.threadNumber.getAndIncrement());
            if (t.isDaemon()) {
                t.setDaemon(false);
            }
            return t;
        }
    }
}

