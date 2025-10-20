/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.nodekeeper.DistributionManager
 *  com.jiuqi.bi.util.StringUtils
 *  org.quartz.JobListener
 *  org.quartz.Matcher
 *  org.quartz.Scheduler
 *  org.quartz.SchedulerException
 *  org.quartz.SchedulerListener
 *  org.quartz.TriggerListener
 *  org.quartz.impl.SchedulerRepository
 *  org.quartz.impl.StdSchedulerFactory
 *  org.quartz.impl.matchers.EverythingMatcher
 */
package com.jiuqi.bi.core.jobs.core;

import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.core.jobs.core.SchedulerConfig;
import com.jiuqi.bi.core.jobs.core.impl.jdbcjobstore.JobDelegate;
import com.jiuqi.bi.core.jobs.core.listener.GlobalJobListener;
import com.jiuqi.bi.core.jobs.core.listener.GlobalSchedulerListener;
import com.jiuqi.bi.core.jobs.core.listener.GlobalTriggerListener;
import com.jiuqi.bi.core.jobs.manager.ConfigManager;
import com.jiuqi.bi.core.nodekeeper.DistributionManager;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import org.quartz.JobListener;
import org.quartz.Matcher;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerListener;
import org.quartz.TriggerListener;
import org.quartz.impl.SchedulerRepository;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.EverythingMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerManager {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerManager.class);
    private StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
    private Properties defaultProperties = new Properties();
    public static final String SYS_SUB_SCHEDULER_INSTANCE_NAME_PREFIX = "BI_SUBJOB";
    public static final String SYS_SCHEDULER_DEFAULT_INSTANCE_NAME = "BI_JOB";
    public static final String SYS_REALTIME_SCHEDULER_DEFAULT_INSTANCE_NAME = "BI_REALTIME";
    public static final String SYS_SUB_REALTIME_SCHEDULER_DEFAULT_INSTANCE_NAME = "BI_SUB_REALTIME";
    private static String tablePrefix = "BI_Q_";
    private static final int MAX_SUB_SCHEDULER = 1;
    private static final String PROP_SCHED_DRIVER_DELEGATE_CLASS = "org.quartz.jobStore.driverDelegateClass";
    public static final String PROP_SCHED_IDLE_WAIT_TIME_PLAN_TASK = "org.quartz.scheduler.idleWaitTimePlanTask";

    public static SchedulerManager getInstance() {
        return SchedulerManagerImpl.INSTANCE;
    }

    public static String getTablePrefix() {
        return tablePrefix;
    }

    private SchedulerManager() {
        InputStream inputStream = SchedulerManager.class.getResourceAsStream("quartz.properties");
        try {
            try {
                if (inputStream == null) {
                    logger.warn("\u83b7\u53d6\u7cfb\u7edf\u9ed8\u8ba4\u914d\u7f6e\u6587\u4ef6\u5931\u8d25");
                } else {
                    this.defaultProperties.load(inputStream);
                    String interfaceId = this.defaultProperties.getOrDefault((Object)"org.quartz.scheduler.instanceId", "AUTO").toString();
                    if ("AUTO".equals(interfaceId)) {
                        this.defaultProperties.setProperty("org.quartz.scheduler.instanceId", DistributionManager.getInstance().getMachineName());
                    }
                    tablePrefix = this.defaultProperties.getOrDefault((Object)"org.quartz.jobStore.tablePrefix", "BI_Q_").toString();
                }
            }
            finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        catch (IOException e) {
            logger.error("\u6839\u636e\u7cfb\u7edf\u9ed8\u8ba4\u914d\u7f6e\u6587\u4ef6\u6784\u9020Properties\u5931\u8d25", e);
        }
    }

    public Scheduler getScheduler() throws JobsException {
        return this.getScheduler(SYS_SCHEDULER_DEFAULT_INSTANCE_NAME);
    }

    public Scheduler getSubScheduler() throws JobsException {
        return this.getScheduler(1);
    }

    public Scheduler getRealTimeScheduler() throws JobsException {
        return this.getScheduler(SYS_REALTIME_SCHEDULER_DEFAULT_INSTANCE_NAME);
    }

    public Scheduler getSubRealTimeScheduler() throws JobsException {
        return this.getScheduler(SYS_SUB_REALTIME_SCHEDULER_DEFAULT_INSTANCE_NAME);
    }

    public Scheduler getScheduler(int level) throws JobsException {
        return this.getScheduler(this.getSchedulerNameByLevel(level));
    }

    private void restart(int level) throws JobsException {
        this.shutdown(level, true);
        Scheduler scheduler = this.getScheduler(level);
        try {
            scheduler.startDelayed(2);
        }
        catch (SchedulerException e) {
            throw new JobsException("\u542f\u52a8scheduler\u5931\u8d25", e);
        }
    }

    public void restartAll() throws JobsException {
        try {
            this.restart(0);
            this.restart(-1);
            this.restart(-2);
            this.restart(1);
        }
        catch (Exception e) {
            throw new JobsException(e);
        }
    }

    private String getSchedulerNameByLevel(int level) throws JobsException {
        if (level > 1) {
            throw new JobsException("\u76ee\u524d\u53ea\u652f\u63011\u7ea7\u5b50\u4efb\u52a1");
        }
        if (level == 0) {
            return SYS_SCHEDULER_DEFAULT_INSTANCE_NAME;
        }
        if (level == -1) {
            return SYS_REALTIME_SCHEDULER_DEFAULT_INSTANCE_NAME;
        }
        if (level <= -2) {
            return SYS_SUB_REALTIME_SCHEDULER_DEFAULT_INSTANCE_NAME;
        }
        return SYS_SUB_SCHEDULER_INSTANCE_NAME_PREFIX + level;
    }

    private void shutdown(int level, boolean waitForJobsToComplete) throws JobsException {
        this.shutdown(this.getSchedulerNameByLevel(level), waitForJobsToComplete);
    }

    private void shutdown(String schedulerName, boolean waitForJobsToComplete) throws JobsException {
        try {
            Scheduler scheduler = this.getSchedulerFactory(this.isRealTime(schedulerName)).getScheduler(schedulerName);
            if (scheduler != null) {
                scheduler.shutdown(waitForJobsToComplete);
                while (!scheduler.isShutdown()) {
                    logger.info("Scheduler[\u3010{}\u3011\u6b63\u5728\u7ec8\u6b62...", (Object)schedulerName);
                    try {
                        Thread.sleep(2000L);
                    }
                    catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                logger.info("Scheduler[\u3010{}\u3011\u5df2\u7ec8\u6b62...", (Object)schedulerName);
            } else {
                logger.warn("\u65e0\u6cd5\u627e\u5230Scheduler\u3010{}\u3011", (Object)schedulerName);
            }
        }
        catch (SchedulerException e) {
            throw new JobsException(e);
        }
    }

    public void shutdownAll() throws JobsException {
        try {
            Collection schedulerCollection = this.schedulerFactory.getAllSchedulers();
            Iterator iterator = schedulerCollection.iterator();
            ArrayList<Scheduler> schedulers = new ArrayList<Scheduler>();
            while (iterator.hasNext()) {
                schedulers.add((Scheduler)iterator.next());
            }
            for (int i = 0; i < schedulers.size(); ++i) {
                Scheduler scheduler = (Scheduler)schedulers.get(i);
                String schedulerName = scheduler.getSchedulerName();
                this.shutdown(schedulerName, false);
            }
        }
        catch (Exception e) {
            throw new JobsException(e);
        }
    }

    private Scheduler getScheduler(String schedulerName) throws JobsException {
        try {
            Scheduler scheduler;
            if (StringUtils.isEmpty((String)schedulerName)) {
                schedulerName = SYS_SCHEDULER_DEFAULT_INSTANCE_NAME;
            }
            if ((scheduler = this.getSchedulerFactory(this.isRealTime(schedulerName)).getScheduler(schedulerName)) == null) {
                SchedulerConfig config = new SchedulerConfig(schedulerName);
                int threadPoolThreadCount = ConfigManager.getInstance().getMaxJobExecuteCount();
                config.setThreadPoolThreadCount(threadPoolThreadCount);
                scheduler = this.getScheduler(config);
            }
            return scheduler;
        }
        catch (SchedulerException e) {
            throw new JobsException(e);
        }
    }

    private synchronized Scheduler getScheduler(SchedulerConfig config) throws JobsException {
        if (config == null) {
            throw new JobsException("Scheduler\u914d\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (StringUtils.isEmpty((String)config.getSchedulerInstanceName())) {
            throw new JobsException("Scheduler\u5b9e\u4f8b\u540d\u79f0\u4e0d\u80fd\u4e3a\u7a7a");
        }
        try {
            String schedulerName = config.getSchedulerInstanceName();
            Scheduler scheduler = this.getSchedulerFactory(this.isRealTime(schedulerName)).getScheduler(schedulerName);
            if (scheduler != null) {
                SchedulerRepository.getInstance().remove(schedulerName);
            }
            SchedulerRepository.getInstance().remove(schedulerName);
            scheduler = this.getSchedulerFactory(this.isRealTime(schedulerName), config.toProp()).getScheduler();
            scheduler.getListenerManager().addJobListener((JobListener)new GlobalJobListener(schedulerName), (Matcher)EverythingMatcher.allJobs());
            scheduler.getListenerManager().addTriggerListener((TriggerListener)new GlobalTriggerListener(schedulerName), (Matcher)EverythingMatcher.allTriggers());
            scheduler.getListenerManager().addSchedulerListener((SchedulerListener)new GlobalSchedulerListener(schedulerName));
            return scheduler;
        }
        catch (SchedulerException e) {
            throw new JobsException(e);
        }
    }

    private StdSchedulerFactory getSchedulerFactory(boolean isRealTime) throws JobsException {
        return this.getSchedulerFactory(isRealTime, null);
    }

    private StdSchedulerFactory getSchedulerFactory(boolean isRealTime, Properties props) throws JobsException {
        String value;
        Properties properties = new Properties();
        properties.putAll((Map<?, ?>)this.defaultProperties);
        if (props != null && !props.isEmpty()) {
            properties.putAll((Map<?, ?>)props);
        }
        properties.setProperty(PROP_SCHED_DRIVER_DELEGATE_CLASS, this.getDatabaseDelegateClass());
        if (!isRealTime && StringUtils.isNotEmpty((String)(value = properties.getProperty(PROP_SCHED_IDLE_WAIT_TIME_PLAN_TASK)))) {
            properties.setProperty("org.quartz.scheduler.idleWaitTime", value);
        }
        try {
            this.schedulerFactory.initialize(properties);
        }
        catch (SchedulerException e) {
            throw new JobsException("StdSchedulerFactory\u521d\u59cb\u5316\u914d\u7f6e\u5931\u8d25", e);
        }
        return this.schedulerFactory;
    }

    private String getDatabaseDelegateClass() {
        return JobDelegate.class.getName();
    }

    public void overwriteProperties(String key, String value) {
        this.defaultProperties.setProperty(key, value);
    }

    public Properties getDefaultProperties() {
        return this.defaultProperties;
    }

    private boolean isRealTime(String name) {
        return name.equals(SYS_REALTIME_SCHEDULER_DEFAULT_INSTANCE_NAME) || name.equals(SYS_SUB_REALTIME_SCHEDULER_DEFAULT_INSTANCE_NAME);
    }

    private static class SchedulerManagerImpl {
        static final SchedulerManager INSTANCE = new SchedulerManager();

        private SchedulerManagerImpl() {
        }
    }
}

