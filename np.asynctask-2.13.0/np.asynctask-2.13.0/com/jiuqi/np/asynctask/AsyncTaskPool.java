/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask;

@Deprecated
public interface AsyncTaskPool {
    public String getType();

    default public String getTitle() {
        return "";
    }

    default public Integer getQueueSize() {
        return -1;
    }

    default public Integer getParallelSize() {
        return 5;
    }

    default public Boolean isConfig() {
        return false;
    }
}

