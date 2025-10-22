/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.asynctask;

import com.jiuqi.nr.common.asynctask.entity.AsyncTaskInfo;

public interface AsyncTaskFuture<R> {
    public String getResult(String var1);

    public R getDetail(String var1, Class<? extends R> var2);

    public AsyncTaskInfo getTaskInfo(String var1);
}

