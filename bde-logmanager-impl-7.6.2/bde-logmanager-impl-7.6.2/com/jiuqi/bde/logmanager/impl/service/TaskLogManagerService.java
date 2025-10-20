/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.logmanager.client.LogDetailRefreshCondition
 *  com.jiuqi.bde.logmanager.client.LogManagerCondition
 *  com.jiuqi.bde.logmanager.client.LogManagerInfoItemVO
 *  com.jiuqi.bde.logmanager.client.LogManagerInfoVO
 *  com.jiuqi.bde.logmanager.client.LogResultMsgVO
 *  com.jiuqi.va.domain.common.PageVO
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.bde.logmanager.impl.service;

import com.jiuqi.bde.logmanager.client.LogDetailRefreshCondition;
import com.jiuqi.bde.logmanager.client.LogManagerCondition;
import com.jiuqi.bde.logmanager.client.LogManagerInfoItemVO;
import com.jiuqi.bde.logmanager.client.LogManagerInfoVO;
import com.jiuqi.bde.logmanager.client.LogResultMsgVO;
import com.jiuqi.bde.logmanager.impl.intf.FetchSqlLogDTO;
import com.jiuqi.va.domain.common.PageVO;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

public interface TaskLogManagerService {
    public PageVO<LogManagerInfoVO> listLogData(LogManagerCondition var1);

    public LogResultMsgVO getLogDetailListById(String var1);

    public Map<String, LogManagerInfoItemVO> refreshLogDetailData(LogDetailRefreshCondition var1);

    public List<LogManagerInfoItemVO> listLogItemData(String var1, String var2);

    public String getLogResultLogById(String var1);

    public void exportErrorLog(String var1, String var2, HttpServletResponse var3);

    public List<FetchSqlLogDTO> getExecuteSqlById(String var1, String var2);
}

