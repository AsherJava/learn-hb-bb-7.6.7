/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.lock;

import com.jiuqi.nr.itreebase.lock.ILockInfo;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockInfo
implements ILockInfo {
    private final ReentrantLock lock;
    private long lastAccessTime;

    public LockInfo(ReentrantLock lock) {
        this.lock = lock;
        this.refreshLastAccessTime();
    }

    @Override
    public void lock() {
        this.refreshLastAccessTime();
        this.lock.lock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        this.refreshLastAccessTime();
        this.lock.lockInterruptibly();
    }

    @Override
    public boolean tryLock() {
        this.refreshLastAccessTime();
        return this.lock.tryLock();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        this.refreshLastAccessTime();
        return this.lock.tryLock(time, unit);
    }

    @Override
    public void unlock() {
        this.refreshLastAccessTime();
        this.lock.unlock();
    }

    @Override
    public Condition newCondition() {
        this.refreshLastAccessTime();
        return this.lock.newCondition();
    }

    @Override
    public long getLastAccessTime() {
        return this.lastAccessTime;
    }

    @Override
    public boolean isLocked() {
        return this.lock.isLocked();
    }

    @Override
    public boolean hasQueuedThreads() {
        return this.lock.hasQueuedThreads();
    }

    public void refreshLastAccessTime() {
        this.lastAccessTime = System.currentTimeMillis();
    }
}

