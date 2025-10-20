/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.lockmgr.service;

import com.jiuqi.dc.taskscheduling.lockmgr.common.TaskTypeEnum;
import com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO;
import java.util.Date;
import java.util.List;

public interface TaskManageService {
    public void initTaskManageByUnitCodes(List<String> var1);

    public TaskManageDO getTaskManageByName(TaskTypeEnum var1, String var2);

    public TaskManageDO getTaskManageByName(String var1, String var2);

    public void updateBeginHandle(TaskTypeEnum var1, String var2);

    public void updateBeginHandle(String var1, String var2, Date var3);

    public void updateEndHandle(String var1, String var2, int var3);

    public void updateEndHandle(TaskTypeEnum var1, String var2, int var3);

    public List<TaskManageDO> getTaskMangeListByName(TaskTypeEnum var1);

    public void updateVer(String var1, String var2);

    public void initTaskManageByUnitCodes(String var1, List<String> var2, Date var3);

    public void initRunnerLock();

    public void updateRunnerLock(String var1);
}

