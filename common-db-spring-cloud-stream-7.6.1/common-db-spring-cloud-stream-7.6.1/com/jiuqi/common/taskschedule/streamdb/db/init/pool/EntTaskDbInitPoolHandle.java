/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.jiuqi.common.taskschedule.streamdb.db.init.pool;

import com.jiuqi.common.taskschedule.streamdb.db.init.pool.EntDbTaskScheduleNamedThreadFactory;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.jetbrains.annotations.NotNull;

public class EntTaskDbInitPoolHandle {
    private static Map<String, ThreadPoolExecutor> queueNameToPoolMap = new ConcurrentHashMap<String, ThreadPoolExecutor>();

    public static void addQueueToPool(String queueName, int minSize, int maxSize) {
        ThreadPoolExecutor threadPoolExecutor = EntTaskDbInitPoolHandle.getThreadPoolExecutor(minSize, maxSize, queueName);
        queueNameToPoolMap.put(queueName, threadPoolExecutor);
    }

    @NotNull
    private static ThreadPoolExecutor getThreadPoolExecutor(int minSize, int maxSize, String queuesName) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(minSize, maxSize, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(655350), new EntDbTaskScheduleNamedThreadFactory(queuesName));
        threadPoolExecutor.allowCoreThreadTimeOut(true);
        return threadPoolExecutor;
    }

    public static Map<String, ThreadPoolExecutor> getQueueNameToPoolMap() {
        return queueNameToPoolMap;
    }
}

