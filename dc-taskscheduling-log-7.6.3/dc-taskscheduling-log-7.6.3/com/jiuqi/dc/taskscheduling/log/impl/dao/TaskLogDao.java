/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.formula.functions.T
 */
package com.jiuqi.dc.taskscheduling.log.impl.dao;

import com.jiuqi.dc.taskscheduling.log.impl.data.ArchiveParam;
import com.jiuqi.dc.taskscheduling.log.impl.data.TaskCountQueryDTO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.formula.functions.T;

public interface TaskLogDao {
    public void insertTaskLogs(DcTaskLogEO var1);

    public void insertTaskItems(List<DcTaskItemLogEO> var1);

    public void updateTaskResult(String var1, String var2);

    public void updateTaskLog(String var1, Map<String, Object> var2);

    public void updateTaskItemStartById(String var1, String var2, String var3, String var4);

    public void updateTaskItemProgress(String var1, double var2, String var4);

    public void updateTaskItemResultById(String var1, DataHandleState var2, String var3);

    public Integer getStateCountByRunnerId(String var1, DataHandleState var2);

    public Integer getStateCountByRunnerId(String var1, List<DataHandleState> var2);

    public List<String> listTaskItemDimCode(String var1, DataHandleState var2);

    public void cancelTask(String var1);

    public boolean isCancel(String var1);

    public Double getTaskProgressByRunnerId(String var1);

    public Integer getStateCountByRunnerIdAndDimCode(String var1, String var2, String var3, DataHandleState var4);

    public List<DcTaskLogEO> getTaskInfoArchiveLog(String var1);

    public void prepareTaskInfoTemp(String var1);

    public void deleteTaskInfoLog(String var1);

    public void clearTemp();

    public void prepareTaskItemInfoTemp();

    public void prepareSqlExcuteInfoTemp();

    public void deleteArchiveLog(String var1, String var2, String var3);

    public List<T> getArchiveLog(String var1, Class var2, String var3, String var4);

    public List<ArchiveParam> getArchiveDimList(Date var1);

    public Integer countTask(TaskCountQueryDTO var1);

    public List<LogManagerDetailVO> queryTaskLog(TaskCountQueryDTO var1);
}

