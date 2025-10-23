/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.rwlock;

import com.jiuqi.nr.graph.IRWLockExecuter;
import com.jiuqi.nr.graph.IRWLockExecuterManager;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseLock;
import com.jiuqi.nr.graph.rwlock.executer.DatabaseRWLockExecuter;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

public class DatabaseRWLockManager
implements IRWLockExecuterManager {
    @Autowired
    private DatabaseLock databaseLock;
    private final Map<String, IRWLockExecuter> lockMap = new HashMap<String, IRWLockExecuter>();

    @Override
    public IRWLockExecuterManager.RWLockType getRWLockType() {
        return IRWLockExecuterManager.RWLockType.DATABASE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public IRWLockExecuter getRWLockExecuter(String lockName) {
        Assert.notNull((Object)lockName, "lockName ");
        IRWLockExecuter rwLockExecuter = this.lockMap.get(lockName);
        if (null == rwLockExecuter) {
            DatabaseRWLockManager databaseRWLockManager = this;
            synchronized (databaseRWLockManager) {
                return this.lockMap.computeIfAbsent(lockName, key -> new DatabaseRWLockExecuter(lockName, this.databaseLock));
            }
        }
        return rwLockExecuter;
    }
}

