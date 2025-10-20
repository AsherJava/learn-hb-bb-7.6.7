/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.monitor.api.vo.show.ConditionVO
 *  com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableColumnVO
 *  com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableVO
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.monitor.impl.service;

import com.jiuqi.gcreport.monitor.api.vo.show.ConditionVO;
import com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableColumnVO;
import com.jiuqi.gcreport.monitor.api.vo.show.MonitorTableVO;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

public interface MonitorShowService {
    public List<MonitorTableVO> monitorShowResult_V2(ConditionVO var1);

    public List<MonitorTableColumnVO> getMonitorColumns(ConditionVO var1);

    public void exportExcel(HttpServletResponse var1, ConditionVO var2);
}

