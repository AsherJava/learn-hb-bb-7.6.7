/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.logmanager.client.LogManagerCondition
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO
 *  com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO
 */
package com.jiuqi.bde.logmanager.impl.dao;

import com.jiuqi.bde.logmanager.client.LogManagerCondition;
import com.jiuqi.bde.logmanager.impl.intf.FetchExecuteStatus;
import com.jiuqi.bde.logmanager.impl.intf.FetchSqlLogDTO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskItemLogEO;
import com.jiuqi.dc.taskscheduling.log.impl.entity.DcTaskLogEO;
import java.util.List;
import java.util.Map;

public interface TaskLogManagerDao {
    public Integer countLog(LogManagerCondition var1);

    public Map<String, FetchExecuteStatus> countExecuteStatus(List<String> var1);

    public Map<String, FetchExecuteStatus> countTaskExecuteStatus(String var1);

    public Map<String, FetchExecuteStatus> countItemExecuteStatus(String var1);

    public List<DcTaskLogEO> selectLog(LogManagerCondition var1);

    public List<DcTaskItemLogEO> selectItemLog(DcTaskItemLogEO var1);

    public String getResultLogById(String var1);

    public List<FetchSqlLogDTO> getExecuteSqlById(String var1, String var2);
}

