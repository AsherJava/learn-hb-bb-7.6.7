/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO
 */
package com.jiuqi.gcreport.onekeymerge.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.onekeymerge.entity.GcTaskResultEO;
import com.jiuqi.gcreport.onekeymerge.vo.GcActionParamsVO;
import com.jiuqi.gcreport.onekeymerge.vo.GcTaskResultVO;
import java.util.List;
import java.util.Map;

public interface TaskResultDao
extends IDbSqlGenericDAO<GcTaskResultEO, String> {
    public List<GcTaskResultEO> getResultEOByCondition(GcActionParamsVO var1, int var2);

    public String getResultEOByTaskCodeAndGroupId(String var1, String var2);

    public Map<String, GcTaskResultVO> getResultEOByGroupId(String var1);
}

