/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.impl.jdbcjobstore.JobStoreSupport
 *  org.quartz.impl.jdbcjobstore.LockException
 *  org.quartz.impl.jdbcjobstore.Semaphore
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.monitor.JobLockMonitorManager;
import java.sql.Connection;
import org.quartz.impl.jdbcjobstore.JobStoreSupport;
import org.quartz.impl.jdbcjobstore.LockException;
import org.quartz.impl.jdbcjobstore.Semaphore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SemaphoreLogProxy
implements Semaphore {
    private static Logger logger = LoggerFactory.getLogger(SemaphoreLogProxy.class);
    private final Semaphore proxy;
    private final String instance;

    public SemaphoreLogProxy(Semaphore proxy, String instance) {
        this.proxy = proxy;
        this.instance = instance;
    }

    public boolean obtainLock(Connection conn, String lockName) throws LockException {
        boolean res = this.proxy.obtainLock(conn, lockName);
        if (res) {
            JobLockMonitorManager.getInstance().obtainLock(SemaphoreLogProxy.getEntryMethodName(), this.instance, lockName);
        } else {
            JobLockMonitorManager.getInstance().obtainLockFailed(SemaphoreLogProxy.getEntryMethodName(), this.instance, lockName);
        }
        return res;
    }

    public void releaseLock(String lockName) throws LockException {
        JobLockMonitorManager.getInstance().releaseLock(SemaphoreLogProxy.getEntryMethodName(), this.instance, lockName);
        this.proxy.releaseLock(lockName);
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
        return this.proxy.requiresConnection();
    }
}

