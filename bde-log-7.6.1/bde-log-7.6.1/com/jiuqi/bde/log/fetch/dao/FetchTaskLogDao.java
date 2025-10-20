/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO
 */
package com.jiuqi.bde.log.fetch.dao;

import com.jiuqi.bde.common.dto.fetch.query.FetchTaskInfoQueryDTO;
import com.jiuqi.bde.log.fetch.dto.FetchItemLogDTO;
import com.jiuqi.bde.log.fetch.dto.FetchLogDTO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import java.util.List;

public interface FetchTaskLogDao {
    public DcTaskLogEO selectLog(String var1);

    public int getUnFinishTaskItemCount(String var1, String var2);

    public List<DcTaskItemLogEO> getFailedTaskItem(String var1, String var2);

    public String selectInstanceIdByCondi(String var1, FetchTaskInfoQueryDTO var2);

    public int countUnFinishBatchTask(String var1);

    public FetchItemLogDTO queryFailTask(String var1);

    public FetchLogDTO queryFailBatchTask(String var1);

    public boolean existExecutingTask(String var1);

    public void updateTaskFinished(String var1);
}

