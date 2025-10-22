/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 */
package com.jiuqi.gcreport.carryover.task;

import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import java.util.List;

public interface GcCarryOverTaskExecutor {
    public String getName();

    public String publishTask(QueryParamsVO var1);

    public void checkParam(QueryParamsVO var1);

    public List<CarryOverLogEO> createTasks(QueryParamsVO var1);
}

