/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.carryover.vo.CarryOverTaskProcessVO
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.carryover.service;

import com.jiuqi.gcreport.carryover.vo.CarryOverTaskProcessVO;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.temp.dto.TaskLog;

public interface GcCarryOverProcessService {
    public void createTaskProcess(QueryParamsVO var1);

    public CarryOverTaskProcessVO queryTaskProcess(String var1);

    public void setTaskProcess(String var1, TaskLog var2);

    public TaskLog getTaskProcess(String var1);

    public void removeTaskLog(String var1);

    public TaskLog getTaskLog(String var1);
}

