/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO
 *  com.jiuqi.gcreport.offsetitem.vo.Pagination
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 *  com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond
 */
package com.jiuqi.gcreport.samecontrol.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.offsetitem.vo.GcActionParamsVO;
import com.jiuqi.gcreport.offsetitem.vo.Pagination;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlOffSetItemEO;
import com.jiuqi.gcreport.samecontrol.vo.SameCtrlOffsetCond;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SameCtrlOffSetItemDao
extends IDbSqlGenericDAO<SameCtrlOffSetItemEO, String> {
    public List<SameCtrlOffSetItemEO> rewriteDisposeParentOffset(GcActionParamsVO var1, String var2);

    public List<SameCtrlOffSetItemEO> rewriteDisposeSameParentUnitOffset(GcActionParamsVO var1, String var2);

    public List<SameCtrlOffSetItemEO> listBeginInputUnitParentsOffset(String var1, String var2, GcOrgCacheVO var3);

    public List<SameCtrlOffSetItemEO> listBeginInputUnitParentsOffsetLimitYear(String var1, String var2, GcOrgCacheVO var3);

    public List<SameCtrlOffSetItemEO> listBeginSameParentUnitOffset(String var1, String var2, String var3);

    public List<SameCtrlOffSetItemEO> listBeginSameParentUnitOffsetLimitYear(String var1, String var2, String var3);

    public Pagination<SameCtrlOffSetItemEO> listOffsets(SameCtrlOffsetCond var1, List<String> var2, List<String> var3);

    public void deleteByCondition(SameCtrlOffsetCond var1, List<String> var2);

    public void checkItemDTO(SameCtrlOffSetItemEO var1);

    public Map<String, Integer> listOffsetCountBySameCtrlChgId(List<String> var1);

    public List<SameCtrlOffSetItemEO> listSameCtrlOffsets(Collection<String> var1);

    public List<SameCtrlOffSetItemEO> listSameCtrlOffsetsOrderByMrecidAndId(Collection<String> var1);

    public int queryMrecidsByInputUnit(SameCtrlOffsetCond var1, Set<String> var2, List<String> var3, List<String> var4);

    public List<SameCtrlOffSetItemEO> listOffsetsByParams(SameCtrlOffsetCond var1, List<String> var2);

    public void deleteOffsetEntrysByMrecid(List<String> var1);

    public void deleteOffsetEntrysBySrcOffsetGroupId(Set<String> var1, List<String> var2);

    public List<SameCtrlOffSetItemEO> queryInputAdjustment(String var1);

    public List<SameCtrlOffSetItemEO> listOffsetEntrysBySrcOffsetGroupIds(List<String> var1);

    public List<SameCtrlOffSetItemEO> listOffsetsBySameCtrlChgId(SameCtrlOffsetCond var1, List<String> var2, List<String> var3, List<String> var4);

    public void deleteByRuleAndSrcType(SameCtrlOffsetCond var1, List<String> var2, List<String> var3);

    public void deleteByUnitAndSrcType(SameCtrlOffsetCond var1, List<String> var2);

    public List<SameCtrlOffSetItemEO> listOffsetsByRuleAndSrcType(SameCtrlOffsetCond var1, List<String> var2, List<String> var3);

    public List<Map<String, Object>> sumOffsetsBySameSubjectCode(SameCtrlOffsetCond var1, List<String> var2, List<String> var3, List<String> var4);

    public List<SameCtrlOffSetItemEO> queryOffsetRecordsByWhere(String[] var1, Object[] var2);

    public int listOffsetsByParams(SameCtrlOffsetCond var1, Set<String> var2);

    public int deleteSameCtrlByCondition(SameCtrlOffsetCond var1, List<String> var2);

    public int[] deleteByMRecid(List<String> var1);

    public int countBySameCtrlChgId(String var1);
}

