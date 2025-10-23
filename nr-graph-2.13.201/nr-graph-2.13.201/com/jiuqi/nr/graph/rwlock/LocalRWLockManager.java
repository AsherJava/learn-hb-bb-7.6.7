/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.rwlock;

import com.jiuqi.nr.graph.IRWLockExecuter;
import com.jiuqi.nr.graph.IRWLockExecuterManager;
import com.jiuqi.nr.graph.rwlock.executer.DefaultRWLockExecuter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.springframework.util.Assert;

public class LocalRWLockManager
implements IRWLockExecuterManager {
    private final Map<String, IRWLockExecuter> lockMap = new HashMap<String, IRWLockExecuter>();

    @Override
    public IRWLockExecuterManager.RWLockType getRWLockType() {
        return IRWLockExecuterManager.RWLockType.LOCAL;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IRWLockExecuter getRWLockExecuter(String lockName) {
        Assert.notNull((Object)lockName, "lockName ");
        IRWLockExecuter rwLockExecuter = this.lockMap.get(lockName);
        if (null == rwLockExecuter) {
            LocalRWLockManager localRWLockManager = this;
            synchronized (localRWLockManager) {
                return this.lockMap.computeIfAbsent(lockName, key -> new DefaultRWLockExecuter(new ReentrantReadWriteLock()));
            }
        }
        return rwLockExecuter;
    }
}

