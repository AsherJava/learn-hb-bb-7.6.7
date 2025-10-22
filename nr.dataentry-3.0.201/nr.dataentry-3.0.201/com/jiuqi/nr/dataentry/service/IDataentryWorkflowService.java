/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.StepByStepCheckResult;

public interface IDataentryWorkflowService {
    public void dataentryExecuteTask(ExecuteTaskParam var1, AsyncTaskMonitor var2);

    public StepByStepCheckResult stepByStepUploadResult(String var1);
}

