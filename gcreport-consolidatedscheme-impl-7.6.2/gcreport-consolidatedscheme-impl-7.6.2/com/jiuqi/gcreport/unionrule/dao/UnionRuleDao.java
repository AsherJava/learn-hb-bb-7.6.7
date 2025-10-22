/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.unionrule.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface UnionRuleDao
extends IDbSqlGenericDAO<UnionRuleEO, String> {
    public List<UnionRuleEO> findByParentId(String var1);

    public List<UnionRuleEO> findActiveRuleByParentId(String var1);

    public List<UnionRuleEO> findByParentIdAndReportSystem(String var1, String var2);

    public List<UnionRuleEO> findByParentIdAndNotLeaf(String var1);

    public Integer findMaxSortOrderByParentId(String var1);

    public List<UnionRuleEO> findRuleListByReportSystem(String var1);

    public List<UnionRuleEO> findAllRuleListByReportSystem(String var1);

    public UnionRuleEO findPreNodeByParentIdAndOrder(String var1, Integer var2);

    public UnionRuleEO findNextNodeByParentIdAndOrder(String var1, Integer var2);

    public List<UnionRuleEO> findBetweenDragTargetAndDraggingByParentIdAndSortOrder(String var1, Integer var2, Integer var3);

    public void updateSortOrder(List<UnionRuleEO> var1);

    public Integer updateUnionRuleStartFlag(String var1, boolean var2);

    public String findParentIdById(String var1);

    public int countOffsetEntryByRuleId(String var1);

    public List<UnionRuleEO> findLeafByIdList(Collection<String> var1);

    public UnionRuleEO findLeafById(String var1);

    public String findTitleById(String var1);

    public List<UnionRuleEO> findRulesByReportSysIdAndTypes(String var1, Collection<String> var2);

    public int countRulesByReportSystemId(String var1);

    public void batchDeleteByReportSysId(String var1);

    public List<UnionRuleEO> findRuleListByReportSystemIdWithGroup(String var1);

    public List<UnionRuleEO> findByReportSystemIdAndRuleTypesOrGroup(String var1, List<String> var2);

    public List<UnionRuleEO> findInitRulesBySystem(String var1, List<String> var2);

    public UnionRuleEO findUnionRuleEOByTitle(String var1, String var2);

    public Set<String> findRuleIdsByRuleType(String var1, String var2);

    public List<UnionRuleEO> findAllRuleTitles(String var1);

    public List<String> findAllRuleIdsBySystemIdAndRuleTypes(String var1, Collection<String> var2);

    public List<String> findAllLeaseRuleIdsBySystemIdAndBillDefineCode(String var1, String var2);

    public List<UnionRuleEO> listRulesByIds(List<String> var1);
}

