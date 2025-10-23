/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 */
package com.jiuqi.nr.bpm.repair.scheme;

import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.nr.bpm.repair.jobs.NrDaTangStateRepairJob;
import com.jiuqi.nr.bpm.repair.scheme.BpmRepairScheme;

public class DaTangStateRepairScheme
implements BpmRepairScheme {
    @Override
    public String getCode() {
        return "da_tang_state_repair_scheme";
    }

    @Override
    public String getTitle() {
        return "\u5927\u5510\u72b6\u6001\u9519\u4e71\u4fee\u590d\u65b9\u6848";
    }

    @Override
    public String getDesc() {
        return "\u9488\u5bf9\u5927\u5510\u9879\u76ee\u505a\u72b6\u6001\u4fee\u590d";
    }

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public NpRealTimeTaskExecutor getExecutor() {
        return new NrDaTangStateRepairJob();
    }
}

