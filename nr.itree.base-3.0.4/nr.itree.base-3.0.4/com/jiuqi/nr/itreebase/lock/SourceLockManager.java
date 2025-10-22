/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.lock;

import com.jiuqi.nr.itreebase.lock.ILockInfo;
import com.jiuqi.nr.itreebase.lock.ISourceLockManager;
import com.jiuqi.nr.itreebase.lock.LockInfo;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.stereotype.Component;

@Component
public class SourceLockManager
implements ISourceLockManager {
    private static final int EXPIRE_TIME_IN_MILLIS = 1800;
    private final ConcurrentHashMap<String, ILockInfo> sourceLocks = new ConcurrentHashMap();

    @Override
    public ILockInfo getSourceLock(String sourceId) {
        return this.sourceLocks.computeIfAbsent(sourceId, k -> new LockInfo(new ReentrantLock()));
    }

    @Override
    public void cleanupExpiredLocks() {
        long currentTime = System.currentTimeMillis();
        for (String sourceId : this.sourceLocks.keySet()) {
            ILockInfo lockInfo = this.sourceLocks.get(sourceId);
            if (currentTime - lockInfo.getLastAccessTime() <= TimeUnit.MINUTES.toMillis(30L) || lockInfo.hasQueuedThreads() || lockInfo.isLocked()) continue;
            this.sourceLocks.remove(sourceId);
        }
    }
}

