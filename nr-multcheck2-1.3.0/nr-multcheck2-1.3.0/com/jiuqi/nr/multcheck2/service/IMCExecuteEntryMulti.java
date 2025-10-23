/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.core.jobs.JobContext
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.multcheck2.service;

import com.jiuqi.bi.core.jobs.JobContext;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.multcheck2.web.result.MCExecuteResult;
import com.jiuqi.nr.multcheck2.web.vo.MCRunVO;

public interface IMCExecuteEntryMulti {
    public MCExecuteResult multiExecute(JobContext var1, AsyncTaskMonitor var2, MCRunVO var3);
}

