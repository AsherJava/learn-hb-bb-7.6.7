/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.quartz.JobDetail
 *  org.quartz.JobKey
 *  org.quartz.JobPersistenceException
 *  org.quartz.Trigger$CompletedExecutionInstruction
 *  org.quartz.TriggerKey
 *  org.quartz.impl.jdbcjobstore.FiredTriggerRecord
 *  org.quartz.impl.jdbcjobstore.JobStoreSupport$RecoverMisfiredJobsResult
 *  org.quartz.impl.jdbcjobstore.JobStoreSupport$TransactionCallback
 *  org.quartz.impl.jdbcjobstore.JobStoreSupport$TransactionValidator
 *  org.quartz.impl.jdbcjobstore.JobStoreTX
 *  org.quartz.impl.jdbcjobstore.LockException
 *  org.quartz.impl.jdbcjobstore.Semaphore
 *  org.quartz.spi.OperableTrigger
 */
package com.jiuqi.bi.core.jobs.core.quartz;

import com.jiuqi.bi.core.jobs.core.impl.jdbcjobstore.JobDelegate;
import com.jiuqi.bi.core.jobs.core.quartz.RedisLockSemaphore;
import com.jiuqi.bi.core.jobs.core.quartz.SemaphoreLogProxy;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.JobPersistenceException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.quartz.impl.jdbcjobstore.FiredTriggerRecord;
import org.quartz.impl.jdbcjobstore.JobStoreSupport;
import org.quartz.impl.jdbcjobstore.JobStoreTX;
import org.quartz.impl.jdbcjobstore.LockException;
import org.quartz.impl.jdbcjobstore.Semaphore;
import org.quartz.spi.OperableTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobStoreQTX
extends JobStoreTX {
    private static Logger logger = LoggerFactory.getLogger(JobStoreQTX.class);
    private String redisKeyInfo;
    private Semaphore lockHandler = null;
    private final Map<String, Semaphore> threadSemaphoreMap = new ConcurrentHashMap<String, Semaphore>();

    public void triggeredJobComplete(OperableTrigger trigger, JobDetail jobDetail, Trigger.CompletedExecutionInstruction triggerInstCode) {
        this.retryExecuteInNonManagedTXLock(null, conn -> {
            this.triggeredJobComplete(conn, trigger, jobDetail, triggerInstCode);
            return null;
        });
    }

    protected Semaphore getLockHandler() {
        if (this.isRealTime()) {
            String key = this.redisKeyInfo + Thread.currentThread().getId();
            if (this.threadSemaphoreMap.containsKey(key)) {
                return this.threadSemaphoreMap.get(key);
            }
            RedisLockSemaphore semaphore = new RedisLockSemaphore(key);
            this.threadSemaphoreMap.put(key, semaphore);
            return semaphore;
        }
        return this.lockHandler;
    }

    public void setLockHandler(Semaphore lockHandler) {
        this.redisKeyInfo = "REALTIME_";
        this.lockHandler = new SemaphoreLogProxy(lockHandler, this.instanceName);
    }

    public boolean isLockOnInsert() {
        return !this.isRealTime();
    }

    public boolean isAcquireTriggersWithinLock() {
        return !this.isRealTime();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected JobStoreSupport.RecoverMisfiredJobsResult doRecoverMisfires() throws JobPersistenceException {
        JobStoreSupport.RecoverMisfiredJobsResult recoverMisfiredJobsResult;
        boolean transOwner = false;
        Connection conn = this.getNonManagedTXConnection();
        Semaphore semaphore = null;
        try {
            int misfireCount;
            JobStoreSupport.RecoverMisfiredJobsResult result = JobStoreSupport.RecoverMisfiredJobsResult.NO_OP;
            int n = misfireCount = this.getDoubleCheckLockMisfireHandler() ? this.getDelegate().countMisfiredTriggersInState(conn, "WAITING", this.getMisfireTime()) : Integer.MAX_VALUE;
            if (misfireCount == 0) {
                this.getLog().debug("Found 0 triggers that missed their scheduled fire-time.");
            } else {
                semaphore = this.getLockHandler();
                transOwner = semaphore.obtainLock(conn, "TRIGGER_ACCESS");
                result = this.recoverMisfiredJobs(conn, false);
            }
            this.commitConnection(conn);
            recoverMisfiredJobsResult = result;
        }
        catch (JobPersistenceException e) {
            try {
                this.rollbackConnection(conn);
                throw e;
                catch (SQLException e2) {
                    this.rollbackConnection(conn);
                    throw new JobPersistenceException("Database error recovering from misfires.", (Throwable)e2);
                }
                catch (RuntimeException e3) {
                    this.rollbackConnection(conn);
                    throw new JobPersistenceException("Unexpected runtime exception: " + e3.getMessage(), (Throwable)e3);
                }
            }
            catch (Throwable throwable) {
                try {
                    this.releaseLock("TRIGGER_ACCESS", transOwner, semaphore);
                    throw throwable;
                }
                finally {
                    this.cleanupConnection(conn);
                }
            }
        }
        try {
            this.releaseLock("TRIGGER_ACCESS", transOwner, semaphore);
            return recoverMisfiredJobsResult;
        }
        finally {
            this.cleanupConnection(conn);
        }
    }

    public List<OperableTrigger> acquireNextTriggers(long noLaterThan, int maxCount, long timeWindow) throws JobPersistenceException {
        String lockName = this.isRealTime() ? null : (this.isAcquireTriggersWithinLock() || maxCount > 1 ? "TRIGGER_ACCESS" : null);
        return (List)this.executeInNonManagedTXLock(lockName, conn -> this.acquireNextTrigger(conn, noLaterThan, maxCount, timeWindow), (conn, result) -> {
            try {
                List acquired = this.getDelegate().selectInstancesFiredTriggerRecords(conn, this.getInstanceId());
                HashSet<String> fireInstanceIds = new HashSet<String>();
                for (FiredTriggerRecord ft : acquired) {
                    fireInstanceIds.add(ft.getFireInstanceId());
                }
                for (OperableTrigger tr : result) {
                    if (!fireInstanceIds.contains(tr.getFireInstanceId())) continue;
                    return true;
                }
                return false;
            }
            catch (SQLException e) {
                throw new JobPersistenceException("error validating trigger acquisition", (Throwable)e);
            }
        });
    }

    protected List<OperableTrigger> acquireNextTrigger(Connection conn, long noLaterThan, int maxCount, long timeWindow) throws JobPersistenceException {
        if (timeWindow < 0L) {
            throw new IllegalArgumentException();
        }
        if (!this.isRealTime()) {
            return super.acquireNextTrigger(conn, noLaterThan, maxCount, timeWindow);
        }
        ArrayList<OperableTrigger> acquiredTriggers = new ArrayList<OperableTrigger>();
        HashSet<JobKey> acquiredJobKeysForNoConcurrentExec = new HashSet<JobKey>();
        int MAX_DO_LOOP_RETRY = 3;
        int currentLoopCount = 0;
        while (true) {
            ++currentLoopCount;
            try {
                List keys = this.getDelegate().selectTriggerToAcquire(conn, noLaterThan + timeWindow, this.getMisfireTime(), maxCount);
                if (keys == null || keys.size() == 0) {
                    return acquiredTriggers;
                }
                long batchEnd = noLaterThan;
                for (TriggerKey triggerKey : keys) {
                    int rowsUpdated;
                    JobDelegate delegate;
                    int count;
                    JobDetail job;
                    OperableTrigger nextTrigger;
                    String lockName = "TRIGGER_ACCESS" + triggerKey.getName();
                    Semaphore semaphore = this.getLockHandler();
                    if (!semaphore.obtainLock(null, lockName) || (nextTrigger = this.retrieveTrigger(conn, triggerKey)) == null || nextTrigger.getNextFireTime() == null) continue;
                    JobKey jobKey = nextTrigger.getJobKey();
                    try {
                        job = this.retrieveJob(conn, jobKey);
                    }
                    catch (JobPersistenceException jpe) {
                        try {
                            this.getLog().error("Error retrieving job, setting trigger state to ERROR.", jpe);
                            this.getDelegate().updateTriggerState(conn, triggerKey, "ERROR");
                        }
                        catch (SQLException sqle) {
                            this.getLog().error("Unable to set trigger state to ERROR.", sqle);
                        }
                        continue;
                    }
                    if (job == null) continue;
                    if (job.isConcurrentExectionDisallowed()) {
                        if (acquiredJobKeysForNoConcurrentExec.contains(jobKey)) continue;
                        acquiredJobKeysForNoConcurrentExec.add(jobKey);
                    }
                    if (nextTrigger.getNextFireTime().getTime() > batchEnd) break;
                    if (this.isRealTime() && this.getDelegate() instanceof JobDelegate && (count = (delegate = (JobDelegate)this.getDelegate()).selectTriggerStateFromOtherState(conn, triggerKey, "WAITING")) <= 0 || (rowsUpdated = this.getDelegate().updateTriggerStateFromOtherState(conn, triggerKey, "ACQUIRED", "WAITING")) <= 0) continue;
                    nextTrigger.setFireInstanceId(this.getFiredTriggerRecordId());
                    this.getDelegate().insertFiredTrigger(conn, nextTrigger, "ACQUIRED", null);
                    if (acquiredTriggers.isEmpty()) {
                        batchEnd = Math.max(nextTrigger.getNextFireTime().getTime(), System.currentTimeMillis()) + timeWindow;
                    }
                    acquiredTriggers.add(nextTrigger);
                }
                if (acquiredTriggers.size() != 0 || currentLoopCount >= 3) break;
            }
            catch (Exception e) {
                this.getLog().error("Couldn't acquire next trigger: " + e.getMessage(), e);
                throw new JobPersistenceException("Couldn't acquire next trigger: " + e.getMessage(), (Throwable)e);
            }
        }
        return acquiredTriggers;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected <T> T executeInNonManagedTXLock(String lockName, JobStoreSupport.TransactionCallback<T> txCallback, final JobStoreSupport.TransactionValidator<T> txValidator) throws JobPersistenceException {
        boolean transOwner = false;
        Connection conn = null;
        Semaphore lockHandler = this.getLockHandler();
        try {
            Object result;
            block18: {
                if (lockName != null) {
                    if (lockHandler.requiresConnection()) {
                        conn = this.getNonManagedTXConnection();
                    }
                    transOwner = lockHandler.obtainLock(conn, lockName);
                }
                if (conn == null) {
                    conn = this.getNonManagedTXConnection();
                }
                result = txCallback.execute(conn);
                try {
                    this.commitConnection(conn);
                }
                catch (JobPersistenceException e) {
                    this.rollbackConnection(conn);
                    if (txValidator != null && ((Boolean)this.retryExecuteInNonManagedTXLock(lockName, (JobStoreSupport.TransactionCallback)new JobStoreSupport.TransactionCallback<Boolean>(){

                        public Boolean execute(Connection conn) throws JobPersistenceException {
                            return txValidator.validate(conn, result);
                        }
                    })).booleanValue()) break block18;
                    throw e;
                }
            }
            Long sigTime = this.clearAndGetSignalSchedulingChangeOnTxCompletion();
            if (sigTime != null && sigTime >= 0L) {
                this.signalSchedulingChangeImmediately(sigTime);
            }
            Object object = result;
            return (T)object;
        }
        catch (JobPersistenceException e) {
            this.rollbackConnection(conn);
            throw e;
        }
        catch (RuntimeException e) {
            this.rollbackConnection(conn);
            throw new JobPersistenceException("Unexpected runtime exception: " + e.getMessage(), (Throwable)e);
        }
        finally {
            try {
                Semaphore semaphore;
                this.releaseLock(lockName, transOwner, lockHandler);
                if (lockName == null && (semaphore = this.getLockHandler()) instanceof RedisLockSemaphore) {
                    ((RedisLockSemaphore)semaphore).releaseAll();
                }
            }
            finally {
                this.cleanupConnection(conn);
            }
        }
    }

    protected void releaseLock(String lockName, boolean doIt, Semaphore semaphore) {
        if (doIt) {
            try {
                if (semaphore != null) {
                    semaphore.releaseLock(lockName);
                } else {
                    this.getLockHandler().releaseLock(lockName);
                }
            }
            catch (LockException le) {
                this.getLog().error("Error returning lock: " + le.getMessage(), le);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected Object executeInLock(String lockName, JobStoreSupport.TransactionCallback txCallback) throws JobPersistenceException {
        boolean transOwner = false;
        Connection conn = null;
        Semaphore lockHandler = this.getLockHandler();
        try {
            if (lockName != null) {
                if (lockHandler.requiresConnection()) {
                    conn = this.getConnection();
                }
                transOwner = lockHandler.obtainLock(conn, lockName);
            }
            if (conn == null) {
                conn = this.getConnection();
            }
            Object object = txCallback.execute(conn);
            return object;
        }
        finally {
            try {
                this.releaseLock(lockName, transOwner, lockHandler);
            }
            finally {
                this.cleanupConnection(conn);
            }
        }
    }

    private boolean isRealTime() {
        return this.instanceName.equals("BI_REALTIME") || this.instanceName.equals("BI_SUB_REALTIME");
    }
}

