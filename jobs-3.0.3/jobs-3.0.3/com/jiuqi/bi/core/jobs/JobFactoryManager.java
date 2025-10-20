/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.IJobFactory;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobManager;
import com.jiuqi.bi.core.jobs.combination.CombinationJobFactory;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobFactoryManager {
    private static Logger logger = LoggerFactory.getLogger(JobMonitorManager.class);
    private static final JobFactoryManager instance = new JobFactoryManager();
    private Map<String, JobFactory> factories = new HashMap<String, JobFactory>();
    private List<JobFactory> cacheList = new ArrayList<JobFactory>();
    private List<IJobFactory> remoteJobFactories = new ArrayList<IJobFactory>();

    private JobFactoryManager() {
        this.regJobFactory(new CombinationJobFactory());
    }

    public void setRemoteJobFactories(List<IJobFactory> jobFactories) {
        this.remoteJobFactories = jobFactories;
    }

    public static JobFactoryManager getInstance() {
        return instance;
    }

    public void regJobFactory(JobFactory factory) {
        if (factory == null) {
            return;
        }
        if (!this.factories.containsKey(factory.getJobCategoryId())) {
            this.factories.put(factory.getJobCategoryId(), factory);
            this.cacheList.add(factory);
        } else {
            logger.error("\u4efb\u52a1\u91cd\u590d\u6ce8\u518c, \u4efb\u52a1\u7c7b\u578b:{}", (Object)factory.getJobCategoryId());
        }
    }

    public void removeJobFactory(String categoryId) {
        JobFactory remove = this.factories.remove(categoryId);
        this.cacheList.remove(remove);
    }

    public JobFactory getJobFactory(String categoryId) {
        categoryId = categoryId.replace("<sub>", "");
        return this.factories.get(categoryId);
    }

    public IJobFactory getJobFactory(String categoryId, boolean includingRemote) {
        Optional<IJobFactory> any;
        String categoryIdTemp = categoryId.replace("<sub>", "");
        if (this.factories.containsKey(categoryIdTemp)) {
            return this.factories.get(categoryIdTemp);
        }
        if (includingRemote && (any = this.remoteJobFactories.stream().filter(e -> e.getJobCategoryId().equals(categoryIdTemp)).findAny()).isPresent()) {
            return any.get();
        }
        return null;
    }

    public JobManager getJobManager(String categoryId) {
        if (this.factories.containsKey(categoryId)) {
            JobFactory jobFactory = this.factories.get(categoryId);
            return new JobManager(jobFactory);
        }
        return null;
    }

    public JobManager getJobManager(String categoryId, boolean includingRemote) {
        Optional<IJobFactory> any;
        JobManager jobManager = this.getJobManager(categoryId);
        if (null == jobManager && includingRemote && (any = this.remoteJobFactories.stream().filter(e -> e.getJobCategoryId().equals(categoryId)).findAny()).isPresent()) {
            IJobFactory iJobFactory = any.get();
            jobManager = new JobManager(iJobFactory);
        }
        return jobManager;
    }

    public JobMonitorManager getMonitorManager(String categoryId) {
        if (this.factories.containsKey(categoryId)) {
            JobFactory jobFactory = this.factories.get(categoryId);
            return new JobMonitorManager(jobFactory);
        }
        return null;
    }

    public Iterator<JobFactory> iterator() {
        return this.cacheList.iterator();
    }

    public Iterator<IJobFactory> iterator(boolean includingRemote) {
        ArrayList<IJobFactory> tempList = new ArrayList<IJobFactory>();
        tempList.addAll(this.cacheList);
        if (includingRemote && null != this.remoteJobFactories) {
            tempList.addAll(this.remoteJobFactories);
        }
        return tempList.iterator();
    }

    public List<String> getCategoryIds() {
        ArrayList<String> ids = new ArrayList<String>();
        for (Map.Entry<String, JobFactory> entry : this.factories.entrySet()) {
            ids.add(entry.getKey());
        }
        return ids;
    }
}

