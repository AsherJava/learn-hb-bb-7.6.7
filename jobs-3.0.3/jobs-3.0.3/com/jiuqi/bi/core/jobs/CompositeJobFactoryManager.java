/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.bi.core.jobs;

import com.jiuqi.bi.core.jobs.BaseFactory;
import com.jiuqi.bi.core.jobs.JobFactory;
import com.jiuqi.bi.core.jobs.JobFactoryManager;
import com.jiuqi.bi.core.jobs.base.BaseJobFactory;
import com.jiuqi.bi.core.jobs.base.BaseJobFactoryManager;
import com.jiuqi.bi.util.StringUtils;
import java.util.Iterator;

public class CompositeJobFactoryManager {
    private CompositeJobFactoryManager() {
    }

    public static BaseFactory getJobFactory(String categoryId) {
        if (StringUtils.isEmpty((String)categoryId)) {
            return null;
        }
        BaseFactory jobFactory = JobFactoryManager.getInstance().getJobFactory(categoryId);
        if (jobFactory == null) {
            jobFactory = BaseJobFactoryManager.getInstance().getJobFactory(categoryId);
        }
        return jobFactory;
    }

    public static Iterator<BaseFactory> iterator() {
        return new Iterator<BaseFactory>(){
            private Iterator<JobFactory> jobFactoryIterator = JobFactoryManager.getInstance().iterator();
            private Iterator<BaseJobFactory> remoteJobFactoryIterator = BaseJobFactoryManager.getInstance().iterator();

            @Override
            public boolean hasNext() {
                return this.jobFactoryIterator.hasNext() || this.remoteJobFactoryIterator.hasNext();
            }

            @Override
            public BaseFactory next() {
                if (this.jobFactoryIterator.hasNext()) {
                    return this.jobFactoryIterator.next();
                }
                if (this.remoteJobFactoryIterator.hasNext()) {
                    return this.remoteJobFactoryIterator.next();
                }
                return null;
            }
        };
    }
}

