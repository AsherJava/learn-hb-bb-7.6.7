/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.lockmgr.dao;

import com.jiuqi.dc.taskscheduling.lockmgr.domain.TaskManageDO;
import java.util.List;

public interface TaskManageDao {
    public int updateBeginHandle(TaskManageDO var1);

    public int updateEndHandle(TaskManageDO var1);

    public List<String> getUnitCodesByTask(TaskManageDO var1);

    public void insert(List<TaskManageDO> var1);

    public TaskManageDO getTaskManageByName(String var1, String var2);

    public List<TaskManageDO> getTaskMangeListByName(String var1);
}

