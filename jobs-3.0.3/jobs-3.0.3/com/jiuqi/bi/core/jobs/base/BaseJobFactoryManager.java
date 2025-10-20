/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.core.jobs.base;

import com.jiuqi.bi.core.jobs.base.BaseJobFactory;
import com.jiuqi.bi.core.jobs.base.BaseJobManager;
import com.jiuqi.bi.core.jobs.monitor.JobMonitorManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class BaseJobFactoryManager {
    private Map<String, BaseJobFactory> factories = new HashMap<String, BaseJobFactory>();
    private static BaseJobFactoryManager instance = new BaseJobFactoryManager();

    private BaseJobFactoryManager() {
    }

    public static BaseJobFactoryManager getInstance() {
        return instance;
    }

    public void regFactory(BaseJobFactory factory) {
        if (factory == null) {
            return;
        }
        this.factories.put(factory.getJobCategoryId(), factory);
    }

    public void removeFactory(String categoryId) {
        this.factories.remove(categoryId);
    }

    public BaseJobFactory getJobFactory(String categoryId) {
        return this.factories.get(categoryId);
    }

    public BaseJobManager getBaseJobManager(String categoryId) {
        if (this.factories.containsKey(categoryId)) {
            BaseJobFactory baseJobFactory = this.factories.get(categoryId);
            return new BaseJobManager(baseJobFactory);
        }
        return null;
    }

    public JobMonitorManager getMonitorManager(String categoryId) {
        if (this.factories.containsKey(categoryId)) {
            BaseJobFactory baseJobFactory = this.factories.get(categoryId);
            return new JobMonitorManager(baseJobFactory);
        }
        return null;
    }

    public Iterator<BaseJobFactory> iterator() {
        return this.factories.values().iterator();
    }

    public List<String> getCategoryIds() {
        ArrayList<String> ids = new ArrayList<String>();
        for (Map.Entry<String, BaseJobFactory> entry : this.factories.entrySet()) {
            ids.add(entry.getKey());
        }
        return ids;
    }
}

