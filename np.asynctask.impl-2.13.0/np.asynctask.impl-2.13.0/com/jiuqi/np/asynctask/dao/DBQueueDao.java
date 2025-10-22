/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.dao;

public interface DBQueueDao {
    public String receive(String var1, String var2);

    public void publishSimpleQueue(String var1, String var2, Integer var3);

    public void publishSplitQueue(String var1, String var2, Integer var3);
}

