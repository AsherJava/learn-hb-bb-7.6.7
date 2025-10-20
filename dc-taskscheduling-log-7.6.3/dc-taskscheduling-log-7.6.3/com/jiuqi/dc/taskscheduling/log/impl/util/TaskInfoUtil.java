/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.UUIDUtils
 */
package com.jiuqi.dc.taskscheduling.log.impl.util;

import com.jiuqi.common.base.util.UUIDUtils;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.enums.IDimType;
import java.util.Date;

public class TaskInfoUtil {
    public static DcTaskLogEO createTaskInfo(String taskType, String message) {
        DcTaskLogEO taskInfo = new DcTaskLogEO();
        taskInfo.setId(UUIDUtils.newHalfGUIDStr());
        taskInfo.setTaskType(taskType);
        taskInfo.setMessage(message);
        taskInfo.setCreateTime(new Date());
        return taskInfo;
    }

    public static DcTaskLogEO createTaskInfo(String taskType) {
        DcTaskLogEO taskInfo = new DcTaskLogEO();
        taskInfo.setId(UUIDUtils.newHalfGUIDStr());
        taskInfo.setTaskType(taskType);
        taskInfo.setCreateTime(new Date());
        return taskInfo;
    }

    public static DcTaskItemLogEO createTaskItemInfo(String taskType, String instanceId, String preNodeId, IDimType dimType, String dimCode) {
        DcTaskItemLogEO taskItemInfo = new DcTaskItemLogEO();
        taskItemInfo.setId(UUIDUtils.newHalfGUIDStr());
        taskItemInfo.setTaskType(taskType);
        taskItemInfo.setInstanceId(instanceId);
        taskItemInfo.setPreNodeId(preNodeId);
        taskItemInfo.setDimType(dimType.getName());
        taskItemInfo.setDimCode(dimCode);
        taskItemInfo.setExecuteState(DataHandleState.UNEXECUTE.getState());
        return taskItemInfo;
    }

    public static DcTaskItemLogEO createTaskItemInfo(String taskType, String instanceId, String preNodeId, IDimType dimType, String dimCode, String message, String runnerId) {
        DcTaskItemLogEO taskItemInfo = new DcTaskItemLogEO();
        taskItemInfo.setId(UUIDUtils.newHalfGUIDStr());
        taskItemInfo.setTaskType(taskType);
        taskItemInfo.setRunnerId(runnerId);
        taskItemInfo.setPreNodeId(preNodeId);
        taskItemInfo.setDimType(dimType.getName());
        taskItemInfo.setDimCode(dimCode);
        taskItemInfo.setExecuteState(DataHandleState.UNEXECUTE.getState());
        taskItemInfo.setMessage(message);
        taskItemInfo.setInstanceId(instanceId);
        return taskItemInfo;
    }
}

