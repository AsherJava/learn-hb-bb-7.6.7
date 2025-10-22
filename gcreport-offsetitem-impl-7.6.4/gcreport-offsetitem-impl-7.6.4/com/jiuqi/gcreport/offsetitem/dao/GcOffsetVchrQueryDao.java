/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.org.api.tool.GcOrgCenterService
 *  com.jiuqi.np.sql.da.RecordSet
 */
package com.jiuqi.gcreport.offsetitem.dao;

import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.org.api.tool.GcOrgCenterService;
import com.jiuqi.np.sql.da.RecordSet;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GcOffsetVchrQueryDao {
    public String getQueryOrgPeriod(String var1);

    public void initUnitCondition(QueryParamsVO var1, StringBuffer var2, GcOrgCenterService var3);

    public void initValidtimeCondition(StringBuffer var1, StringBuffer var2, List<Object> var3, Date var4);

    public Map<String, Object> getObject(RecordSet var1);

    public void initPeriodCondition(QueryParamsVO var1, List<Object> var2, StringBuffer var3, StringBuffer var4);

    public void initOtherCondition(StringBuffer var1, QueryParamsVO var2);
}

