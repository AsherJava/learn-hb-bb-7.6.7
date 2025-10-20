/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.application;

import com.jiuqi.np.common.exception.NvwaUserNotFoundException;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public interface NpApplication {
    public String getApplicationName();

    public NpContext getTempContext(String var1) throws NvwaUserNotFoundException;

    public void runAsContext(NpContext var1, Runnable var2);

    public <R> R runAsContext(NpContext var1, Callable<R> var2) throws Exception;

    public void asyncRunAsContext(NpContext var1, Runnable var2);

    public <T> Future<T> asyncRunAsContext(NpContext var1, Callable<T> var2) throws Exception;

    default public void asyncRun(Runnable runnable) {
        this.asyncRunAsContext(NpContextHolder.getContext(), runnable);
    }

    default public <T> Future<T> asyncRun(Callable<T> callable) throws Exception {
        return this.asyncRunAsContext(NpContextHolder.getContext(), callable);
    }

    public void runAsTenant(String var1, Runnable var2);

    public <R> R runAsTenant(String var1, Callable<R> var2) throws Exception;

    public <T> Future<T> asyncRunAsTenant(String var1, Callable<T> var2) throws Exception;
}

