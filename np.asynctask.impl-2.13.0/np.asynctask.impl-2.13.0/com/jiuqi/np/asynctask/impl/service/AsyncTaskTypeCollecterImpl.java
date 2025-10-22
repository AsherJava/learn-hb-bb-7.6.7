/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskPool
 *  com.jiuqi.np.asynctask.AsyncTaskTypeCollecter
 *  com.jiuqi.np.asynctask.NpAsyncTaskExecutor
 *  com.jiuqi.np.asynctask.exception.MultipleTaskPoolException
 *  com.jiuqi.np.sql.type.Convert
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.np.asynctask.impl.service;

import com.jiuqi.np.asynctask.AsyncTaskPool;
import com.jiuqi.np.asynctask.AsyncTaskTypeCollecter;
import com.jiuqi.np.asynctask.NpAsyncTaskExecutor;
import com.jiuqi.np.asynctask.exception.MultipleTaskPoolException;
import com.jiuqi.np.sql.type.Convert;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AsyncTaskTypeCollecterImpl
implements AsyncTaskTypeCollecter,
InitializingBean {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskTypeCollecterImpl.class);
    private final ConcurrentHashMap<String, NpAsyncTaskExecutor> executorMap = new ConcurrentHashMap();
    private final List<String> taskToolTypeList = new ArrayList<String>();
    private final ConcurrentHashMap<String, AsyncTaskPool> taskPoolMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, Integer> taskPoolParallelSizeMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, Integer> taskPoolQueueSizeMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, String> taskPoolTitleMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<String, Boolean> taskPoolConfigMap = new ConcurrentHashMap();
    private final ConcurrentHashMap<Integer, List<String>> clearDataExecutorMap = new ConcurrentHashMap();
    private final List<String> taskTypeList = new ArrayList<String>();
    public static final Integer defaultQueueSize = -1;
    public static final Integer defaultParallelSize = 5;
    @Autowired(required=false)
    private List<NpAsyncTaskExecutor> executorList;
    @Autowired(required=false)
    private List<AsyncTaskPool> taskPoolList;
    @Autowired
    private INvwaSystemOptionService iNvwaSystemOptionService;
    public static final String QUEUE_PREFIX = "QUEUE_";

    @Override
    public void afterPropertiesSet() throws Exception {
        this.registerTaskExecutor();
        this.registerTaskPool();
        this.registerTaskDetail();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void registerTaskExecutor() {
        ConcurrentHashMap<String, NpAsyncTaskExecutor> concurrentHashMap = this.executorMap;
        synchronized (concurrentHashMap) {
            if (null != this.executorList) {
                this.clearDataExecutorMap.put(NpAsyncTaskExecutor.CLEAN_TYPE_BYTYPE, new ArrayList());
                this.clearDataExecutorMap.put(NpAsyncTaskExecutor.CLEAN_TYPE_BYID, new ArrayList());
                for (NpAsyncTaskExecutor npAsyncTaskExecutor : this.executorList) {
                    String taskPoolType = npAsyncTaskExecutor.getTaskPoolType();
                    if (this.executorMap.containsKey(taskPoolType)) {
                        throw new MultipleTaskPoolException("Need one TaskPool '" + taskPoolType + "',But Find two.");
                    }
                    this.executorMap.put(taskPoolType, npAsyncTaskExecutor);
                    if (Objects.equals(npAsyncTaskExecutor.getCleanDataType(), NpAsyncTaskExecutor.CLEAN_TYPE_NONE)) continue;
                    List<String> list = this.clearDataExecutorMap.get(npAsyncTaskExecutor.getCleanDataType());
                    list.add(npAsyncTaskExecutor.getTaskPoolType());
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void registerTaskPool() {
        ConcurrentHashMap<String, AsyncTaskPool> concurrentHashMap = this.taskPoolMap;
        synchronized (concurrentHashMap) {
            if (null != this.taskPoolList) {
                for (AsyncTaskPool taskPool : this.taskPoolList) {
                    String taskPoolType = taskPool.getType();
                    if (this.taskPoolMap.containsKey(taskPoolType)) {
                        throw new MultipleTaskPoolException("Need one TaskPool '" + taskPoolType + "',But Find two.");
                    }
                    this.taskPoolMap.put(taskPoolType, taskPool);
                }
            }
        }
    }

    private void registerTaskDetail() {
        for (String taskPoolType : this.executorMap.keySet()) {
            int parallelSize = defaultParallelSize;
            int queueSize = defaultQueueSize;
            String title = "";
            boolean isConfig = false;
            AsyncTaskPool taskPool = this.taskPoolMap.get(taskPoolType);
            if (null != taskPool) {
                parallelSize = taskPool.getParallelSize();
                queueSize = taskPool.getQueueSize();
                title = taskPool.getTitle();
                isConfig = taskPool.isConfig();
            }
            this.taskPoolQueueSizeMap.put(taskPoolType, queueSize);
            this.taskPoolParallelSizeMap.put(taskPoolType, parallelSize);
            this.taskPoolTitleMap.put(taskPoolType, Objects.isNull(title) || title.isEmpty() ? taskPoolType : title);
            this.taskPoolConfigMap.put(taskPoolType, isConfig);
            this.taskTypeList.add(taskPoolType);
        }
    }

    public List<String> getTaskPoolTypes() {
        return this.taskTypeList;
    }

    public Integer getQueueSize(String taskPoolType) {
        AsyncTaskPool taskPool = this.taskPoolMap.get(taskPoolType);
        if (Objects.nonNull(taskPool) && taskPool.isConfig().booleanValue()) {
            String config = this.iNvwaSystemOptionService.get("async-task-queue-declare", QUEUE_PREFIX.concat(taskPool.getType()));
            if (StringUtils.hasLength(config)) {
                try {
                    return Convert.toInt((String)config);
                }
                catch (Exception e) {
                    return taskPool.getQueueSize();
                }
            }
            return taskPool.getQueueSize();
        }
        return this.getGlobalQueueSize(taskPoolType);
    }

    public Integer getParallelSize(String taskPoolType) {
        AsyncTaskPool taskPool = this.taskPoolMap.get(taskPoolType);
        if (Objects.nonNull(taskPool) && taskPool.isConfig().booleanValue()) {
            String config = this.iNvwaSystemOptionService.get("async-task-parallel-declare", taskPool.getType());
            if (StringUtils.hasLength(config)) {
                try {
                    return Convert.toInt((String)config);
                }
                catch (Exception e) {
                    return taskPool.getParallelSize();
                }
            }
            return taskPool.getParallelSize();
        }
        return this.getGlobalParalleSize(taskPoolType);
    }

    public String getTitle(String taskPoolType) {
        return this.taskPoolTitleMap.get(taskPoolType);
    }

    public boolean isConfig(String taskPoolType) {
        return this.taskPoolConfigMap.get(taskPoolType);
    }

    private int getGlobalParalleSize(String taskPoolType) {
        String value = null;
        try {
            value = this.iNvwaSystemOptionService.get("async-task-parallel-declare", "asynctask-max-parallel-size");
        }
        catch (Exception e) {
            logger.error("\u7cfb\u7edf\u914d\u7f6easync-task-parallel-declare\u5c1a\u672a\u5b8c\u6210\u6ce8\u518c\uff0c\u83b7\u53d6\u7cfb\u7edf\u914d\u7f6e\u5931\u8d25\uff01");
        }
        if (StringUtils.hasLength(value)) {
            try {
                return Convert.toInt((String)value);
            }
            catch (Exception e) {
                return this.taskPoolParallelSizeMap.get(taskPoolType);
            }
        }
        return this.taskPoolParallelSizeMap.get(taskPoolType);
    }

    private int getGlobalQueueSize(String taskPoolType) {
        String value = null;
        try {
            value = this.iNvwaSystemOptionService.get("async-task-queue-declare", "asynctask-max-queue-size");
        }
        catch (Exception e) {
            logger.error("\u7cfb\u7edf\u914d\u7f6easync-task-queue-declare\u5c1a\u672a\u5b8c\u6210\u6ce8\u518c\uff0c\u83b7\u53d6\u7cfb\u7edf\u914d\u7f6e\u5931\u8d25\uff01");
        }
        if (StringUtils.hasLength(value)) {
            try {
                int intValue = Convert.toInt((String)value);
                return intValue < 0 ? -1 : intValue;
            }
            catch (Exception e) {
                return this.taskPoolQueueSizeMap.get(taskPoolType);
            }
        }
        return this.taskPoolQueueSizeMap.get(taskPoolType);
    }

    public NpAsyncTaskExecutor getExecutorByType(String taskPoolType) {
        return this.executorMap.get(taskPoolType);
    }

    public List<String> getTaskToolTypeList() {
        return this.taskToolTypeList;
    }

    public List<String> getClearDataByTypeTasks() {
        return this.clearDataExecutorMap.get(NpAsyncTaskExecutor.CLEAN_TYPE_BYTYPE);
    }

    public List<String> getClearDataByTaskIdTasks() {
        return this.clearDataExecutorMap.get(NpAsyncTaskExecutor.CLEAN_TYPE_BYID);
    }
}

