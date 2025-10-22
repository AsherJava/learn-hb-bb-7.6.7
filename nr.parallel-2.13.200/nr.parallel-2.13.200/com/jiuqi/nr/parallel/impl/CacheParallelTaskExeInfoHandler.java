/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.cache.NedisCache
 *  com.jiuqi.np.cache.NedisCacheManager
 *  com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider
 */
package com.jiuqi.nr.parallel.impl;

import com.jiuqi.np.cache.NedisCache;
import com.jiuqi.np.cache.NedisCacheManager;
import com.jiuqi.np.cache.internal.springcache.DefaultCacheProvider;
import com.jiuqi.nr.parallel.BatchParallelExeTask;
import com.jiuqi.nr.parallel.IParallelTaskExeInfoHandler;
import com.jiuqi.nr.parallel.ParallelExeInfo;
import java.util.ArrayList;
import java.util.List;
import org.springframework.cache.Cache;

@Deprecated
public class CacheParallelTaskExeInfoHandler
implements IParallelTaskExeInfoHandler {
    private NedisCacheManager cacheManager = DefaultCacheProvider.getCacheManager();

    public void recordProgress(String taskKey, double progress, String parentKey) {
        NedisCache cache = this.cacheManager.getCache(parentKey.toString());
        Cache.ValueWrapper valueWrapper = cache.get(taskKey);
        if (valueWrapper != null) {
            ParallelExeInfo info = (ParallelExeInfo)valueWrapper.get();
            if (info.getState() == ParallelExeInfo.TaskState.WAITING) {
                info.setState(ParallelExeInfo.TaskState.RUNNING);
            }
            info.setProgress(progress);
            cache.put(taskKey, (Object)info);
        }
    }

    @Override
    public List<ParallelExeInfo> initTasks(List<BatchParallelExeTask> tasks, String parentKey) {
        NedisCache cache = this.cacheManager.getCache(parentKey.toString());
        ArrayList<ParallelExeInfo> states = new ArrayList<ParallelExeInfo>();
        for (BatchParallelExeTask task : tasks) {
            ParallelExeInfo state = new ParallelExeInfo();
            state.setTaskKey(task.getKey());
            state.setParentKey(task.getParentTaskID());
            state.setWeight(task.getWeight());
            cache.put(task.getKey(), (Object)state);
            states.add(state);
        }
        return states;
    }

    @Override
    public ParallelExeInfo queryTaskInfo(String taskKey, String parentKey) {
        Cache.ValueWrapper valueWrapper = this.cacheManager.getCache(parentKey.toString()).get(taskKey);
        if (valueWrapper != null) {
            return (ParallelExeInfo)valueWrapper.get();
        }
        return null;
    }

    public void clearTaskInfos(String parentKey) {
        this.cacheManager.getCache(parentKey.toString()).clear();
    }

    public void finishTask(String taskKey, String parentKey) {
        NedisCache cache = this.cacheManager.getCache(parentKey.toString());
        Cache.ValueWrapper valueWrapper = cache.get(taskKey);
        if (valueWrapper != null) {
            ParallelExeInfo info = (ParallelExeInfo)valueWrapper.get();
            info.setState(ParallelExeInfo.TaskState.FINISH);
            cache.put(taskKey, (Object)info);
        }
    }

    @Override
    public void taskRunning(String taskKey, String parentKey) {
        NedisCache cache = this.cacheManager.getCache(parentKey.toString());
        Cache.ValueWrapper valueWrapper = cache.get(taskKey);
        if (valueWrapper != null) {
            ParallelExeInfo info = (ParallelExeInfo)valueWrapper.get();
            info.setUpdateTime(System.currentTimeMillis());
            cache.put(taskKey, (Object)info);
        }
    }

    @Override
    public void updateTask(ParallelExeInfo taskInfo) {
    }
}

