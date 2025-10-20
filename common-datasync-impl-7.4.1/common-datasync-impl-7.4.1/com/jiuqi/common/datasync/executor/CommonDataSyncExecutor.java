/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.datasync.executor;

import com.jiuqi.common.datasync.executor.CommonDataSyncExecutorContext;

public interface CommonDataSyncExecutor {
    public void execute(CommonDataSyncExecutorContext var1) throws Exception;

    public String title();

    public String type();

    default public int order() {
        return 0;
    }

    default public String description() {
        return "\u65e0";
    }
}

