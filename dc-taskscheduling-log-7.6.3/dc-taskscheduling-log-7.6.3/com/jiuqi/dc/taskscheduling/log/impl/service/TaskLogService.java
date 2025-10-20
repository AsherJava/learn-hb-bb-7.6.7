/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.taskscheduling.log.impl.service;

import com.jiuqi.dc.taskscheduling.log.impl.data.ArchiveParam;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface TaskLogService {
    public void insertTaskLogs(DcTaskLogEO var1, List<DcTaskItemLogEO> var2);

    public void insertSuccessTaskAndItemLogs(String var1, String var2, String var3, Date var4);

    public void insertFailureTaskAndItemLogs(String var1, String var2, String var3, Date var4);

    public void insertTaskItemLogs(List<DcTaskItemLogEO> var1);

    public void updateTaskLogSourceTypeAndExtFields(String var1, int var2, Map<String, Object> var3);

    public void updateTaskLogExtFields(String var1, Map<String, Object> var2);

    public void updateTaskResult(String var1, String var2);

    public void updateTaskItemStartTimeById(String var1, String var2);

    public void updateTaskItemProgress(String var1, double var2, String var4);

    public void updateTaskItemResultById(String var1, DataHandleState var2, String var3);

    public Integer getStateCountByRunnerId(String var1, DataHandleState var2);

    public List<String> listTaskItemDimCode(String var1, DataHandleState var2);

    public Integer getUnFinishTaskItemCount(String var1);

    public void cancelTask(String var1);

    public boolean isCancel(String var1);

    public Double getTaskProgressByRunnerId(String var1);

    public Integer getStateCountByRunnerIdAndDimCode(String var1, String var2, String var3, DataHandleState var4);

    public String logArchive(ArchiveParam var1) throws Exception;

    public Integer countTask(TaskCountQueryDTO var1);

    public List<LogManagerDetailVO> queryTaskLog(TaskCountQueryDTO var1);
}

