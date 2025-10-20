/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.progress.executor;

import com.jiuqi.common.expimp.progress.common.ProgressData;

public interface ProgressExecutor<T extends ProgressData<E>, E> {
    public void createProgressData(T var1);

    public void refreshProgressData(T var1);

    public T queryProgressData(String var1, boolean var2);

    public void removeProgressData(String var1);

    public String getExecutorName();
}

