/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.taskschedule.streamdb.db.init.pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class EntDbTaskScheduleNamedThreadFactory
implements ThreadFactory {
    private final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup threadGroup;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    public final String namePrefix;

    public EntDbTaskScheduleNamedThreadFactory(String name) {
        SecurityManager s = System.getSecurityManager();
        ThreadGroup threadGroup = this.threadGroup = s != null ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
        if (null == name || "".equals(name.trim())) {
            name = "pool";
        }
        this.namePrefix = name + "-" + this.poolNumber.getAndIncrement() + "-thread-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(this.threadGroup, r, this.namePrefix + this.threadNumber.getAndIncrement(), 0L);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != 5) {
            t.setPriority(5);
        }
        return t;
    }
}

