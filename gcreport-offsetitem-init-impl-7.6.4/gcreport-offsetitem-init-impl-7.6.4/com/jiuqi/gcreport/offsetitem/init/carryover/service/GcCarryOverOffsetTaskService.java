/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.gcreport.offsetitem.init.carryover.service;

import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;

public interface GcCarryOverOffsetTaskService {
    public TaskLog doTask(QueryParamsVO var1, AsyncTaskMonitor var2);
}

