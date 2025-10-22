/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.temp.dto.TaskLog
 */
package com.jiuqi.gcreport.carryover.service;

import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.temp.dto.TaskLog;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GcCarryOverLogService {
    public void saveLog(TaskLog var1, QueryParamsVO var2, Date var3);

    public void saveLogExtend(String var1, Map<String, String> var2);

    public Pagination<Map<String, Object>> listLogInfo(QueryParamsVO var1);

    public Pagination<Map<String, Object>> listLogInfoBySchemeId(String var1, int var2, int var3);

    public Map<String, Map<String, Object>> listLogExtendInfoByIds(List<String> var1);

    public CarryOverLogEO getCarryOverLogById(String var1);
}

