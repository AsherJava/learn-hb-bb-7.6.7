/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 *  com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum
 */
package com.jiuqi.gcreport.unionrule.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.IdTemporaryTableUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import com.jiuqi.gcreport.unionrule.dao.UnionRuleDao;
import com.jiuqi.gcreport.unionrule.entity.UnionRuleEO;
import com.jiuqi.gcreport.unionrule.enums.RuleTypeEnum;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class UnionRuleDaoImpl
extends GcDbSqlGenericDAO<UnionRuleEO, String>
implements UnionRuleDao {
    public UnionRuleDaoImpl() {
        super(UnionRuleEO.class);
    }

    private String getAllFieldsSQL() {
        return SqlUtils.getNewColumnsSqlByEntity(UnionRuleEO.class, (String)"scheme");
    }

    @Override
    public List<UnionRuleEO> findByParentId(String parentId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + " scheme \n where scheme.parentid = ? \n order by scheme.sortorder  \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{parentId});
        return ruleEOList;
    }

    @Override
    public List<UnionRuleEO> findActiveRuleByParentId(String parentId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + "  scheme \n  where scheme.parentid = ? \n  and scheme.startflag=?  \n  order by scheme.sortorder  \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{parentId, 1});
        return ruleEOList;
    }

    @Override
    public List<UnionRuleEO> findByParentIdAndReportSystem(String parentId, String reportSystem) {
        String sql = " select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + "  scheme \n  where scheme.parentid = ? \n  and scheme.reportsystem = ? \n  order by scheme.sortorder  \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{parentId, reportSystem});
        return ruleEOList;
    }

    @Override
    public List<UnionRuleEO> findByParentIdAndNotLeaf(String id) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + "  scheme \n  where scheme.id = ? \n  and scheme.leafflag=? \n  order by scheme.sortorder  \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{id, 0});
        return ruleEOList;
    }

    @Override
    public Integer findMaxSortOrderByParentId(String parentId) {
        String sql = "  select max(scheme.sortorder)  SORTORDER from GC_UNIONRULE  scheme \n  where scheme.parentid = ? \n";
        List re = this.selectFirstList(Integer.class, sql, new Object[]{parentId});
        if (!re.isEmpty() && re.get(0) != null) {
            return (int)((Integer)re.get(0));
        }
        return 0;
    }

    @Override
    public List<UnionRuleEO> findRuleListByReportSystem(String reportSystemId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + "  scheme \n  where scheme.reportsystem = ? \n  and scheme.leafflag=? and scheme.startflag=? \n  order by scheme.sortorder  \n";
        return this.selectEntity(sql, new Object[]{reportSystemId, 1, 1});
    }

    @Override
    public List<UnionRuleEO> findAllRuleListByReportSystem(String reportSystemId) {
        String sql = " select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + "  scheme \n  where scheme.reportSystem = ? \n";
        return this.selectEntity(sql, new Object[]{reportSystemId});
    }

    @Override
    public UnionRuleEO findPreNodeByParentIdAndOrder(String parentId, Integer sortOrder) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + " scheme \n where scheme.parentid = ? \n and scheme.sortorder = (select max(s.sortorder) from " + "GC_UNIONRULE" + " as s \n where s.parentid = ? \n and s.sortorder < ?) \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{parentId, parentId, sortOrder});
        if (!CollectionUtils.isEmpty((Collection)ruleEOList)) {
            return (UnionRuleEO)((Object)ruleEOList.get(0));
        }
        return null;
    }

    @Override
    public UnionRuleEO findNextNodeByParentIdAndOrder(String parentId, Integer sortOrder) {
        String sql = "  select " + this.getAllFieldsSQL() + "    from " + "GC_UNIONRULE" + "  scheme \n  where scheme.parentid = ? \n  and scheme.sortorder = (select min(s.sortorder) from " + "GC_UNIONRULE" + "  s \n where s.parentid = ?  \n and s.sortorder > ?) \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{parentId, parentId, sortOrder});
        if (!CollectionUtils.isEmpty((Collection)ruleEOList)) {
            return (UnionRuleEO)((Object)ruleEOList.get(0));
        }
        return null;
    }

    @Override
    public List<UnionRuleEO> findBetweenDragTargetAndDraggingByParentIdAndSortOrder(String parentId, Integer dragRuleNodeSortOrder, Integer dragRuleNodeSortOrderTwo) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + "  scheme \n  where scheme.parentid = ? \n  and scheme.sortorder >= ? \n  and scheme.sortorder <= ? \n  order by scheme.sortorder  \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{parentId, dragRuleNodeSortOrder, dragRuleNodeSortOrderTwo});
        return ruleEOList;
    }

    @Override
    public void updateSortOrder(List<UnionRuleEO> unionRuleEOs) {
        String sql = " update gc_unionrule t  set sortOrder=?\n   where t.id = ? \n";
        if (CollectionUtils.isEmpty(unionRuleEOs)) {
            return;
        }
        List param = unionRuleEOs.stream().map(m -> Arrays.asList(m.getSortOrder(), m.getId())).collect(Collectors.toList());
        this.executeBatch(sql, param);
    }

    @Override
    public Integer updateUnionRuleStartFlag(String id, boolean startFlag) {
        String sql = " update GC_UNIONRULE scheme \n set startflag = ? \n where scheme.id = ? \n";
        return this.execute(sql, new Object[]{startFlag ? 1 : 0, id});
    }

    @Override
    public String findParentIdById(String id) {
        String sql = " select scheme.parentid  PARENTID \n  from GC_UNIONRULE  scheme \n  where scheme.id = ? \n";
        List recordSet = this.selectFirstList(String.class, sql, new Object[]{id});
        if (!recordSet.isEmpty() && recordSet.get(0) != null) {
            return (String)recordSet.get(0);
        }
        return null;
    }

    @Override
    public int countOffsetEntryByRuleId(String ruleId) {
        String sql = "\tselect count(*)  NUM from GC_OFFSETVCHRITEM e \n where e.ruleid = ? \n";
        return this.count(sql, Arrays.asList(ruleId));
    }

    @Override
    public List<UnionRuleEO> findLeafByIdList(Collection<String> ids) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"scheme.id");
        String sql = "  select " + this.getAllFieldsSQL() + " \n  from " + "GC_UNIONRULE" + "  scheme \n  where " + inSql + " \n  and scheme.leafflag = ? and scheme.startflag=? \n  order by scheme.sortorder \n";
        return this.selectEntity(sql, new Object[]{1, 1});
    }

    @Override
    public UnionRuleEO findLeafById(String id) {
        String sql = " select " + this.getAllFieldsSQL() + " \n  from " + "GC_UNIONRULE" + "  scheme \n  where scheme.id = ? \n  and scheme.leafflag = ? \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{id, 1});
        if (!CollectionUtils.isEmpty((Collection)ruleEOList)) {
            return (UnionRuleEO)((Object)ruleEOList.get(0));
        }
        return null;
    }

    @Override
    public String findTitleById(String id) {
        String sql = " select t.title  TITLE \n  from GC_UNIONRULE  t \n  where t.id =? \n";
        List rs = this.selectFirstList(String.class, sql, new Object[]{id});
        if (!rs.isEmpty() && rs.get(0) != null) {
            return (String)rs.get(0);
        }
        return null;
    }

    @Override
    public List<UnionRuleEO> findRulesByReportSysIdAndTypes(String reportSysId, Collection<String> ruleList) {
        String str = "1=1";
        if (!CollectionUtils.isEmpty(ruleList)) {
            str = SqlUtils.getConditionOfMulStrUseOr(ruleList, (String)"scheme.RULETYPE");
        }
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + "  scheme \n  where scheme.reportsystem = ?\n  and scheme.leafflag=? and scheme.startflag=? \n  and " + str + " \n  order by scheme.sortorder \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{reportSysId, 1, 1});
        return ruleEOList;
    }

    @Override
    public int countRulesByReportSystemId(String systemId) {
        String sql = "  select count(scheme.ID) \n  from GC_UNIONRULE  scheme \n  where scheme.reportsystem = ? \n  and scheme.leafflag = ? \n";
        return this.count(sql, new Object[]{systemId, 1});
    }

    @Override
    public void batchDeleteByReportSysId(String systemId) {
        String sql = " delete from GC_UNIONRULE   where reportsystem = ?\n";
        this.execute(sql, new Object[]{systemId});
    }

    @Override
    public List<UnionRuleEO> findRuleListByReportSystemIdWithGroup(String reportSystemId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + " scheme \n  where scheme.reportsystem = ? \n  order by scheme.sortorder \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{reportSystemId});
        return ruleEOList;
    }

    @Override
    public List<UnionRuleEO> findByReportSystemIdAndRuleTypesOrGroup(String reportSystemId, List<String> ruleTypeList) {
        String str = "1=1";
        if (!CollectionUtils.isEmpty(ruleTypeList)) {
            str = SqlUtils.getConditionOfMulStrUseOr(ruleTypeList, (String)"scheme.RULETYPE");
        }
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + " scheme \n  where scheme.reportsystem = ? \n  and scheme.startflag=? \n  and " + str + " \n  order by scheme.sortorder \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{reportSystemId, 1});
        return ruleEOList;
    }

    @Override
    public List<UnionRuleEO> findInitRulesBySystem(String id, List<String> ruleTypeList) {
        String str = "1=1";
        if (!CollectionUtils.isEmpty(ruleTypeList)) {
            str = SqlUtils.getConditionOfMulStrUseOr(ruleTypeList, (String)"scheme.RULETYPE");
        }
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + " scheme \n  where scheme.reportsystem = ? \n  and scheme.startflag=? \n  and scheme.inittypeflag =? \n  and " + str + " \n  order by scheme.sortorder \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{id, 1, 1});
        return ruleEOList;
    }

    @Override
    public UnionRuleEO findUnionRuleEOByTitle(String systemId, String title) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_UNIONRULE" + " scheme \n where scheme.title = ?  and scheme.reportSystem = ? ";
        List ruleEOList = this.selectEntity(sql, new Object[]{title, systemId});
        return (UnionRuleEO)((Object)ruleEOList.get(0));
    }

    @Override
    public Set<String> findRuleIdsByRuleType(String reportSystemId, String ruleType) {
        String sql = "select scheme.ID from GC_UNIONRULE scheme \n  where scheme.reportSystem = ?\n  and scheme.ruleType = ?\n";
        List recordSet = this.selectFirstList(String.class, sql, new Object[]{reportSystemId, ruleType});
        Set<String> ruleIds = recordSet.stream().collect(Collectors.toSet());
        return ruleIds;
    }

    @Override
    public List<UnionRuleEO> findAllRuleTitles(String reportSystem) {
        String sql = " select scheme.ID,scheme.title from GC_UNIONRULE  scheme \n  where scheme.reportSystem = ?\n";
        return this.selectEntity(sql, new Object[]{reportSystem});
    }

    @Override
    public List<String> findAllRuleIdsBySystemIdAndRuleTypes(String reportSystemId, Collection<String> ruleTypeEnums) {
        String sql = "  select scheme.id ID from GC_UNIONRULE scheme \n where scheme.reportsystem = ? \n and " + SqlUtils.getConditionOfMulStrUseOr(ruleTypeEnums, (String)"scheme.ruleType");
        List ruleIds = this.selectFirstList(String.class, sql, new Object[]{reportSystemId});
        return ruleIds;
    }

    @Override
    public List<String> findAllLeaseRuleIdsBySystemIdAndBillDefineCode(String systemId, String billDefineCode) {
        String sql = "  select scheme.id ID from GC_UNIONRULE scheme \n where scheme.reportsystem = ? \n and scheme.startflag = ? \n and " + SqlUtils.getConditionOfMulStrUseOr(Arrays.asList(RuleTypeEnum.LEASE.getCode()), (String)"scheme.ruleType");
        if (!StringUtils.isEmpty((String)billDefineCode)) {
            String billDefineIdCondi = "\"billDefineId\":\"" + billDefineCode + "";
            sql = sql + " and scheme.jsonString like '%" + billDefineIdCondi + "%'";
        }
        List ruleIds = this.selectFirstList(String.class, sql, new Object[]{systemId, 1});
        return ruleIds;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public List<UnionRuleEO> listRulesByIds(List<String> ids) {
        TempTableCondition newConditionOfMulStr = SqlUtils.getNewConditionOfMulStr(ids, (String)"scheme.id");
        try {
            String sql = "  select " + this.getAllFieldsSQL() + " \n  from " + "GC_UNIONRULE" + "  scheme \n  where " + newConditionOfMulStr.getCondition() + " \n  order by scheme.sortorder \n";
            List list = this.selectEntity(sql, new Object[0]);
            return list;
        }
        finally {
            IdTemporaryTableUtils.deteteByGroupId((String)newConditionOfMulStr.getTempGroupId());
        }
    }
}

