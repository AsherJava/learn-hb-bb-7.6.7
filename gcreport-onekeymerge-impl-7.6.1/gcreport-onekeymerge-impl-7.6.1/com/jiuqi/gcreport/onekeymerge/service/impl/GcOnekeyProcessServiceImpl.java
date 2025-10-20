/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.enums.TaskStateEnum
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.onekeymerge.service.impl;

import com.jiuqi.gcreport.common.enums.TaskStateEnum;
import com.jiuqi.gcreport.onekeymerge.service.GcOnekeyProcessService;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class GcOnekeyProcessServiceImpl
implements GcOnekeyProcessService {
    private static ConcurrentHashMap<String, SoftReference<TaskLog>> taskLogMap = new ConcurrentHashMap();

    @Override
    public void setTaskProcess(String index, TaskLog taskLog) {
        SoftReference<TaskLog> taskLogSoftReference = new SoftReference<TaskLog>(taskLog);
        taskLogMap.put(index, taskLogSoftReference);
    }

    @Override
    public void setTaskProcessWithCode(String index, TaskLog taskLog, String taskCode) {
        taskLog.setState(TaskStateEnum.EXECUTING);
        SoftReference<TaskLog> taskLogSoftReference = new SoftReference<TaskLog>(taskLog);
        taskLogMap.put(taskCode.toUpperCase() + "_" + index, taskLogSoftReference);
    }

    @Override
    public TaskLog getTaskProcess(String index) {
        SoftReference<TaskLog> taskLogSoftReference = taskLogMap.get(index);
        if (null == taskLogSoftReference) {
            return null;
        }
        return taskLogSoftReference.get();
    }

    @Override
    public TaskLog getTaskProcessWithCode(String index, String taskCode) {
        SoftReference<TaskLog> taskLogSoftReference = taskLogMap.get(taskCode.toUpperCase() + "_" + index);
        if (null == taskLogSoftReference) {
            return null;
        }
        return taskLogSoftReference.get();
    }

    @Override
    public void removeTaskLog(String index) {
        taskLogMap.remove(index);
    }
}

