/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs.core;

import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import com.jiuqi.bi.util.StringUtils;
import java.util.Map;
import java.util.Properties;

public class SchedulerConfig {
    public static final String PROP_SCHED_INSTANCE_NAME = "org.quartz.scheduler.instanceName";
    public static final String PROP_SCHED_THREADPOOL_THREAD_COUNT = "org.quartz.threadPool.threadCount";
    public static final String PROP_SCHED_MISFIRE_THRESHOLD = "org.quartz.jobStore.misfireThreshold";
    public static final long DEFAULT_MISFIRE_THRESHOLD = 12000000L;
    private String schedulerInstanceName;
    private int threadPoolThreadCount = 30;
    private long misfireThreshold = 12000000L;

    public SchedulerConfig(String schedulerInstanceName) {
        this.schedulerInstanceName = schedulerInstanceName;
    }

    public Properties toProp() {
        Properties properties = new Properties();
        Properties defaultProperties = SchedulerManager.getInstance().getDefaultProperties();
        properties.putAll((Map<?, ?>)defaultProperties);
        if (StringUtils.isNotEmpty((String)this.schedulerInstanceName)) {
            properties.setProperty(PROP_SCHED_INSTANCE_NAME, this.schedulerInstanceName);
        }
        if (this.threadPoolThreadCount <= 0) {
            this.threadPoolThreadCount = 30;
        }
        properties.setProperty(PROP_SCHED_THREADPOOL_THREAD_COUNT, String.valueOf(this.threadPoolThreadCount));
        if (this.misfireThreshold < 0L) {
            this.misfireThreshold = 12000000L;
        }
        properties.setProperty(PROP_SCHED_MISFIRE_THRESHOLD, String.valueOf(this.misfireThreshold));
        return properties;
    }

    public String getSchedulerInstanceName() {
        return this.schedulerInstanceName;
    }

    public void setSchedulerInstanceName(String schedulerInstanceName) {
        this.schedulerInstanceName = schedulerInstanceName;
    }

    public int getThreadPoolThreadCount() {
        return this.threadPoolThreadCount;
    }

    public void setThreadPoolThreadCount(int threadPoolThreadCount) {
        this.threadPoolThreadCount = threadPoolThreadCount;
    }

    public long getMisfireThreshold() {
        return this.misfireThreshold;
    }

    public void setMisfireThreshold(long misfireThreshold) {
        this.misfireThreshold = misfireThreshold;
    }
}

