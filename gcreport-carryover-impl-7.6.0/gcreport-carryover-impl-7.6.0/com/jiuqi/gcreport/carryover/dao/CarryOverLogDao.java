/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.carryover.vo.QueryParamsVO
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 */
package com.jiuqi.gcreport.carryover.dao;

import com.jiuqi.gcreport.carryover.entity.CarryOverLogEO;
import com.jiuqi.gcreport.carryover.vo.QueryParamsVO;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CarryOverLogDao
extends IDbSqlGenericDAO<CarryOverLogEO, String> {
    public CarryOverLogEO queryGcCarryOverInfoEO(String var1, String var2, Integer var3, String var4);

    public List<Map<String, Object>> listLogInfoBySchemeId(String var1);

    public int countByGroupId(String var1);

    public List<CarryOverLogEO> listByIds(Set<String> var1);

    public Pagination<Map<String, Object>> listLogInfo(QueryParamsVO var1, Map<String, Object> var2);
}

