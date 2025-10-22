/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.lock;

import java.util.concurrent.locks.Lock;

public interface ILockInfo
extends Lock {
    public long getLastAccessTime();

    public boolean isLocked();

    public boolean hasQueuedThreads();
}

