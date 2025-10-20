/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.np.core.context.NpContext
 */
package com.jiuqi.gcreport.carryover.service;

import com.jiuqi.gcreport.carryover.entity.CarryOverTaskProcessEO;
import com.jiuqi.gcreport.carryover.task.GcCarryOverTaskExecutor;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.np.core.context.NpContext;

public interface GcCarryOverTaskService {
    public CarryOverTaskProcessEO getProcess(String var1);

    public void createAsyncTask(GcCarryOverTaskExecutor var1, QueryParamsVO var2);

    public void publishAsyncTask(GcCarryOverTaskExecutor var1, QueryParamsVO var2, NpContext var3);

    public boolean updateTaskState(String var1, int var2);
}

