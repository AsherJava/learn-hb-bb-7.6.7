/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.lock;

import com.jiuqi.nr.itreebase.lock.ILockInfo;

public interface ISourceLockManager {
    public ILockInfo getSourceLock(String var1);

    public void cleanupExpiredLocks();
}

