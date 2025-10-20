/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 */
package com.jiuqi.bde.log.fetch.service;

import com.jiuqi.bde.common.dto.fetch.init.FetchInitTaskDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoDTO;
import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.bde.log.fetch.dto.FetchLogDTO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import java.util.List;

public interface FetchTaskLogService {
    public FetchInitTaskDTO getLogVo(String var1);

    public int countUnFinishBatchTask(String var1);

    public int getUnFinishTaskItemCount(String var1, String var2);

    public List<DcTaskItemLogEO> getFailedTaskItem(String var1, String var2);

    public void insertTaskLog(DcTaskLogEO var1);

    public void insertTaskItemLog(DcTaskItemLogEO var1);

    public void updateTaskItemResultById(String var1, DataHandleState var2, String var3);

    public FetchTaskInfoDTO queryTaskInfo(FetchTaskInfoQueryDTO var1);

    public FetchLogDTO queryFailBatchTask(String var1);

    public FetchItemLogDTO queryFailTask(String var1);

    public boolean existExecutingTask(String var1);

    public void waitFetchTaskFinished(String var1);

    public void waitFetchTaskFinishedChkTimeout(String var1, Long var2);

    public int getUnFinishTaskItemCount(String var1);

    public void updateTaskFinished(String var1);
}

