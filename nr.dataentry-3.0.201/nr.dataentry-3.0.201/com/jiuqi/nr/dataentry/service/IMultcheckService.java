/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.BatchExecuteTaskParam;
import com.jiuqi.nr.dataentry.bean.ExecuteTaskParam;

public interface IMultcheckService {
    public String comprehensiveAudit(ExecuteTaskParam var1, AsyncTaskMonitor var2);

    public String bathComprehensiveAudit(BatchExecuteTaskParam var1, AsyncTaskMonitor var2);
}

