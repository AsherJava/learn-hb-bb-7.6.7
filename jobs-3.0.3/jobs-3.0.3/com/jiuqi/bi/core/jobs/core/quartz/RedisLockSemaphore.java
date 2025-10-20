/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.impl.jdbcjobstore.JobStoreSupport
 *  org.quartz.impl.jdbcjobstore.Semaphore
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.JobRedisLockManager;
import com.jiuqi.bi.core.jobs.monitor.JobLockMonitorManager;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;
import org.quartz.impl.jdbcjobstore.JobStoreSupport;
import org.quartz.impl.jdbcjobstore.Semaphore;

public class RedisLockSemaphore
implements Semaphore {
    private final String contextId;
    private Set<String> lockNames;

    public RedisLockSemaphore(String contextId) {
        this.contextId = contextId;
        this.lockNames = new HashSet<String>();
    }

    public boolean obtainLock(Connection conn, String lockName) {
        if (lockName == null) {
            return true;
        }
        JobRedisLockManager.IJobRedisLockProvider jobRedisLockProvider = JobRedisLockManager.getInstance().getProvider();
        String id = jobRedisLockProvider.getLock(lockName, this.contextId);
        if (id != null) {
            this.lockNames.add(lockName);
            JobLockMonitorManager.getInstance().obtainLock(RedisLockSemaphore.getEntryMethodName(), "REALTIME", lockName);
        } else {
            JobLockMonitorManager.getInstance().obtainLockFailed(RedisLockSemaphore.getEntryMethodName(), "REALTIME", lockName);
        }
        return id != null;
    }

    public void releaseLock(String lockName) {
        if (lockName == null) {
            return;
        }
        this.lockNames.remove(lockName);
        JobRedisLockManager.IJobRedisLockProvider jobRedisLockProvider = JobRedisLockManager.getInstance().getProvider();
        JobLockMonitorManager.getInstance().releaseLock(RedisLockSemaphore.getEntryMethodName(), "REALTIME", lockName);
        jobRedisLockProvider.releaseLock(lockName, this.contextId);
    }

    public void releaseAll() {
        for (String lockName : this.lockNames) {
            JobRedisLockManager.IJobRedisLockProvider jobRedisLockProvider = JobRedisLockManager.getInstance().getProvider();
            JobLockMonitorManager.getInstance().releaseLock(RedisLockSemaphore.getEntryMethodName(), "REALTIME", lockName);
            jobRedisLockProvider.releaseLock(lockName, this.contextId);
        }
        this.lockNames = new HashSet<String>();
    }

    public static String getEntryMethodName() {
        Class<JobStoreSupport> targetClass = JobStoreSupport.class;
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 2; i < stackTrace.length; ++i) {
            if (!stackTrace[i].getClassName().equals(targetClass.getName())) continue;
            return stackTrace[i].getMethodName();
        }
        return "\u672a\u627e\u5230\u5165\u53e3\u65b9\u6cd5";
    }

    public boolean requiresConnection() {
        return false;
    }
}

