/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.dc.base.common.vo.SelectOptionVO
 *  com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO
 *  com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO
 *  com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO
 *  com.jiuqi.va.domain.common.PageVO
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.service;

import com.jiuqi.dc.base.common.vo.SelectOptionVO;
import com.jiuqi.dc.taskscheduling.log.impl.vo.LogManagerDetailVO;
import com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO;
import com.jiuqi.dc.taskscheduling.logquery.client.vo.LogManagerVO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface LogManagerService {
    public PageVO<LogManagerVO> getOverViewList(LogManagerDTO var1);

    public PageVO<LogManagerDetailVO> getDetailList(LogManagerDTO var1);

    public LogManagerVO getOverViewProgress(String var1);

    public LogManagerDetailVO getDetailProgress(String var1);

    public LogManagerDetailVO getResultLog(String var1);

    public List<SelectOptionVO> getOverTaskType();

    public List<SelectOptionVO> getDetailTaskType(LogManagerDTO var1);

    public List<SelectOptionVO> getDetailDimType();

    public void exportExecuteRecordByCondition(HttpServletResponse var1, LogManagerDTO var2);

    public void exportSqlRecordByTaskItemId(HttpServletResponse var1, String var2);
}

