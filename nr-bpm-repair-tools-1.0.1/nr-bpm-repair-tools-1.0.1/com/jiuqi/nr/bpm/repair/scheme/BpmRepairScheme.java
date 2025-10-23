/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 */
package com.jiuqi.nr.bpm.repair.scheme;

import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;

public interface BpmRepairScheme {
    public String getCode();

    public String getTitle();

    public String getDesc();

    public int getOrder();

    public NpRealTimeTaskExecutor getExecutor();
}

