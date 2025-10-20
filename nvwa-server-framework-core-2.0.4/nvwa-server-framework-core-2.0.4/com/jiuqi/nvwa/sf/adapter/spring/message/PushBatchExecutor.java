/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.concurrent.BasicThreadFactory$Builder
 */
package com.jiuqi.nvwa.sf.adapter.spring.message;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class PushBatchExecutor {
    private static final ScheduledExecutorService scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, (ThreadFactory)new BasicThreadFactory.Builder().namingPattern("nvwa-message-delayqueue-%d").daemon(true).build());

    private PushBatchExecutor() {
    }

    public static ScheduledExecutorService getScheduledThreadPoolExecutor() {
        return scheduledThreadPoolExecutor;
    }
}

