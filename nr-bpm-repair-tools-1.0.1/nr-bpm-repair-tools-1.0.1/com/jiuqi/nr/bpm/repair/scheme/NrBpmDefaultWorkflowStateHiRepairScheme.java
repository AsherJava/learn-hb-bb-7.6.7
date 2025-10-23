/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.NpRealTimeTaskExecutor
 */
package com.jiuqi.nr.bpm.repair.scheme;

import com.jiuqi.np.asynctask.NpRealTimeTaskExecutor;
import com.jiuqi.nr.bpm.repair.jobs.NrBpmDefaultWorkflowStateHiRepairJob;
import com.jiuqi.nr.bpm.repair.scheme.BpmRepairScheme;
import org.springframework.stereotype.Component;

@Component
public class NrBpmDefaultWorkflowStateHiRepairScheme
implements BpmRepairScheme {
    @Override
    public String getCode() {
        return "bpm-repair-job-with_nr_default_workflow_state_hi";
    }

    @Override
    public String getTitle() {
        return "\u3010\u9ed8\u8ba4\u6d41\u7a0b-\u72b6\u6001\u8868-\u5386\u53f2\u8868\u3011\u4fee\u590d\u4efb\u52a1";
    }

    @Override
    public String getDesc() {
        return "\u6ce8\u610f\uff1a\u53ea\u4fee\u590d\u9ed8\u8ba4\u6d41\u7a0b\u7684\u5386\u53f2\u72b6\u6001\u8868";
    }

    @Override
    public int getOrder() {
        return 99;
    }

    @Override
    public NpRealTimeTaskExecutor getExecutor() {
        return new NrBpmDefaultWorkflowStateHiRepairJob();
    }
}

