/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobDetail
 *  org.quartz.Scheduler
 *  org.quartz.SimpleTrigger
 *  org.quartz.Trigger
 */
package com.jiuqi.bi.core.jobs.core;

import com.jiuqi.bi.core.jobs.core.SchedulerManager;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SchedulerCommitPool {
    private static final SchedulerCommitPool instance = new SchedulerCommitPool();
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final ConcurrentHashMap<JobDetail, SimpleTrigger> mainJobTriggerMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<JobDetail, SimpleTrigger> realTimeJobTriggerMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<JobDetail, SimpleTrigger> subJobTriggerMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<JobDetail, SimpleTrigger> realTimeSubJobTriggerMap = new ConcurrentHashMap();
    private final ThreadPoolExecutor executorService = new ThreadPoolExecutor(2, 2, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), r -> {
        Thread t = new Thread(r);
        t.setName("SchedulerCommitPool-" + t.getId());
        t.setPriority(7);
        return t;
    });

    public static SchedulerCommitPool getInstance() {
        return instance;
    }

    public int getMainTriggerCount() {
        return this.mainJobTriggerMap.size();
    }

    public int getSubJobTriggerCount() {
        return this.subJobTriggerMap.size();
    }

    public int getRealTimeJobTriggerCount() {
        return this.realTimeJobTriggerMap.size();
    }

    public int getQueueSize() {
        return this.executorService.getQueue().size();
    }

    public void addMainTrigger(JobDetail jobDetail, SimpleTrigger trigger) {
        this.mainJobTriggerMap.put(jobDetail, trigger);
        this.doTrigger();
    }

    public void addSubTrigger(JobDetail jobDetail, SimpleTrigger trigger) {
        this.subJobTriggerMap.put(jobDetail, trigger);
        this.doTrigger();
    }

    public void addRealTimeTrigger(JobDetail jobDetail, SimpleTrigger trigger) {
        this.realTimeJobTriggerMap.put(jobDetail, trigger);
        this.doTrigger();
    }

    public void addSubRealTimeTrigger(JobDetail jobDetail, SimpleTrigger trigger) {
        this.realTimeSubJobTriggerMap.put(jobDetail, trigger);
        this.doTrigger();
    }

    private void doTrigger() {
        if (this.executorService.getQueue().size() < 5) {
            this.executorService.submit(new QuartzJobSubmitter());
        }
    }

    private Map<JobDetail, Set<? extends Trigger>> transferJobs(ConcurrentHashMap<JobDetail, SimpleTrigger> source) {
        HashMap<JobDetail, SimpleTrigger> snapshot = new HashMap<JobDetail, SimpleTrigger>(source);
        ((ConcurrentHashMap.KeySetView)source.keySet()).removeAll((Collection)snapshot.keySet());
        return snapshot.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> Collections.singleton((SimpleTrigger)e.getValue())));
    }

    private class QuartzJobSubmitter
    implements Runnable {
        private QuartzJobSubmitter() {
        }

        @Override
        public void run() {
            try {
                if (!SchedulerCommitPool.this.mainJobTriggerMap.isEmpty()) {
                    Scheduler scheduler = SchedulerManager.getInstance().getScheduler();
                    Map mainJobs = SchedulerCommitPool.this.transferJobs(SchedulerCommitPool.this.mainJobTriggerMap);
                    scheduler.scheduleJobs(mainJobs, false);
                }
                if (!SchedulerCommitPool.this.subJobTriggerMap.isEmpty()) {
                    Scheduler subScheduler = SchedulerManager.getInstance().getSubScheduler();
                    Map subJobs = SchedulerCommitPool.this.transferJobs(SchedulerCommitPool.this.subJobTriggerMap);
                    subScheduler.scheduleJobs(subJobs, false);
                }
                if (!SchedulerCommitPool.this.realTimeJobTriggerMap.isEmpty()) {
                    Scheduler realTimeScheduler = SchedulerManager.getInstance().getRealTimeScheduler();
                    Map realTimeJobs = SchedulerCommitPool.this.transferJobs(SchedulerCommitPool.this.realTimeJobTriggerMap);
                    realTimeScheduler.scheduleJobs(realTimeJobs, false);
                }
                if (!SchedulerCommitPool.this.realTimeSubJobTriggerMap.isEmpty()) {
                    Scheduler subRealTimeScheduler = SchedulerManager.getInstance().getSubRealTimeScheduler();
                    Map subRealTimeJobs = SchedulerCommitPool.this.transferJobs(SchedulerCommitPool.this.realTimeSubJobTriggerMap);
                    subRealTimeScheduler.scheduleJobs(subRealTimeJobs, false);
                }
                if (SchedulerCommitPool.this.mainJobTriggerMap.isEmpty() && SchedulerCommitPool.this.subJobTriggerMap.isEmpty()) {
                    Thread.sleep(100L);
                }
            }
            catch (Throwable e) {
                SchedulerCommitPool.this.logger.error("QuartzJobSubmitter \u63d0\u4ea4\u6709\u672a\u77e5\u5f02\u5e38", e);
            }
        }
    }
}

