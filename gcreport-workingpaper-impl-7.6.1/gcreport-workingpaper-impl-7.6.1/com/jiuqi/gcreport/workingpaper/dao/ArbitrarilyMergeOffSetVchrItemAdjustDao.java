/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO
 *  com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi
 */
package com.jiuqi.gcreport.workingpaper.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.common.task.entity.GcTaskBaseArguments;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.offsetitem.vo.QueryParamsVO;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOffSetVchrItemAdjustEO;
import com.jiuqi.gcreport.workingpaper.vo.ArbitrarilyMergeInputAdjustQueryCondi;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ArbitrarilyMergeOffSetVchrItemAdjustDao
extends IDbSqlGenericDAO<ArbitrarilyMergeOffSetVchrItemAdjustEO, String> {
    public void deleteRyBySrcOffsetGroupIds(String var1, Collection<String> var2, int var3, int var4, int var5, String var6, String var7, int var8, String var9);

    public void deleteByOffsetGroupIdsAndSrcType(Collection<String> var1, Integer var2, GcTaskBaseArguments var3);

    public void deleteRyByMrecids(List<String> var1, String var2, Integer var3, Integer var4, String var5, String var6, String var7);

    public void deleteRyByMrecids(List<String> var1);

    public List<ArbitrarilyMergeOffSetVchrItemAdjustEO> queryRyOffsetRecordsByWhere(String[] var1, Object[] var2, ArbitrarilyMergeInputAdjustQueryCondi var3);

    public Pagination<Map<String, Object>> queryRyOffsetingEntry(QueryParamsVO var1);

    public int queryRyMrecids(QueryParamsVO var1, Set<String> var2, Set<String> var3);

    public List<Map<String, Object>> queryRyOffsetingEntryByMrecids(QueryParamsVO var1, Set<String> var2);

    public List<Map<String, Object>> sumRyOffsetValueGroupBySubjectcode(QueryParamsVO var1);
}

