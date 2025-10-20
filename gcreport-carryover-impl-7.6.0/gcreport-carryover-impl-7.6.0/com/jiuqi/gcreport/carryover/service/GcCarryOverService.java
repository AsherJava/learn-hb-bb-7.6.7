/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.task.vo.Scheme
 *  com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.carryover.service;

import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.task.vo.Scheme;
import com.jiuqi.gcreport.offsetitem.vo.DesignFieldDefineVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import java.util.Map;

public interface GcCarryOverService {
    public void doCarryOver(QueryParamsVO var1);

    public Map<String, String> getOrgVerAndType(String var1, String var2);

    public Scheme getSchemeByTaskKeyAndAcctYear(String var1, Integer var2);

    public List<DesignFieldDefineVO> listCarryOverSumColumns();

    public Pagination<Map<String, Object>> listCarryOverLogInfo(QueryParamsVO var1);

    public Map<String, Object> getTaskProcess(String var1);
}

