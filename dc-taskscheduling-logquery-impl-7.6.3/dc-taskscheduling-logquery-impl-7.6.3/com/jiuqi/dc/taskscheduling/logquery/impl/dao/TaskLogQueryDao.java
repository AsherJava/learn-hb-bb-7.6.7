/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.dao;

import com.jiuqi.dc.taskscheduling.log.impl.enums.DataHandleState;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.ExecuteStateVO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO;
import java.util.List;
import java.util.Map;

public interface TaskLogQueryDao {
    public Integer getStateCountByRunnerId(String var1, DataHandleState var2);

    public List<String> listTaskItemDimCode(String var1, DataHandleState var2);

    public List<LogManagerVO> listOverView(LogManagerDTO var1);

    public Integer listOverTotal(LogManagerDTO var1);

    public List<LogManagerDetailVO> listDetail(LogManagerDTO var1);

    public Integer listDetailTotal(LogManagerDTO var1);

    public LogManagerVO listOverRefresh(String var1);

    public ExecuteStateVO listOverExecute(String var1);

    public LogManagerDetailVO getHandleStateByItemId(String var1);

    public LogManagerDetailVO getResultLog(String var1);

    public List<String> getOverTaskType();

    public List<String> getDetailTaskType(LogManagerDTO var1);

    public List<Map<String, Object>> getExecuteRecordByCondition(LogManagerDTO var1);

    public List<Map<String, Object>> getExecuteErrorRecord(String var1);

    public List<Map<String, Object>> getSqlRecord(String var1);
}

