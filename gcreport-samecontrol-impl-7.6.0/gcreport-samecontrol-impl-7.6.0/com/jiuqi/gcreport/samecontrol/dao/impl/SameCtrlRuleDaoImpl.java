/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 */
package com.jiuqi.gcreport.samecontrol.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.samecontrol.dao.SameCtrlRuleDao;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlRuleEO;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;

@Repository
public class SameCtrlRuleDaoImpl
extends GcDbSqlGenericDAO<SameCtrlRuleEO, String>
implements SameCtrlRuleDao {
    public SameCtrlRuleDaoImpl() {
        super(SameCtrlRuleEO.class);
    }

    private String getAllFieldsSQL() {
        return SqlUtils.getColumnsSqlByEntity(SameCtrlRuleEO.class, (String)"scheme");
    }

    @Override
    public List<SameCtrlRuleEO> findByParentIdAndIsolatedFiled(String parentId, String reportSystem, String taskId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_SAMECTRLRULE" + "  scheme \n  where scheme.parentId = ? \n  and scheme.reportSystem = ? \n  and scheme.taskId = ? \n  order by scheme.sortOrder  \n";
        return this.selectEntity(sql, new Object[]{parentId, reportSystem, taskId});
    }

    @Override
    public List<SameCtrlRuleEO> findByParentId(String parentId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_SAMECTRLRULE" + "  scheme \n  where scheme.parentId = ? \n  order by scheme.sortOrder  \n";
        return this.selectEntity(sql, new Object[]{parentId});
    }

    @Override
    public Integer findMaxSortOrderByParentId(String parentId) {
        String sql = "  select max(scheme.sortOrder) as SORTORDER from GC_SAMECTRLRULE  scheme \n  where scheme.parentId = ? \n";
        List re = this.selectFirstList(Integer.class, sql, new Object[]{parentId});
        if (!re.isEmpty() && re.get(0) != null) {
            return (int)((Integer)re.get(0));
        }
        return 0;
    }

    @Override
    public String findParentIdById(String id) {
        String sql = "  select scheme.parentId \n  from GC_SAMECTRLRULE  scheme \n  where scheme.id = ? \n";
        List recordSet = this.selectFirstList(String.class, sql, new Object[]{id});
        if (!recordSet.isEmpty() && recordSet.get(0) != null) {
            return (String)recordSet.get(0);
        }
        return null;
    }

    @Override
    public Integer updateSameCtrlRuleStartFlagById(String id, boolean startFlag) {
        String sql = "  update GC_SAMECTRLRULE  scheme \n  set startFlag = ? \n  where scheme.id = ? \n";
        return this.execute(sql, new Object[]{startFlag ? 1 : 0, id});
    }

    @Override
    public List<SameCtrlRuleEO> listSameCtrlRuleAndGroup(String reportSystemId, String taskId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_SAMECTRLRULE" + "  scheme \n  where scheme.reportsystem = ? and scheme.taskId = ? \n  order by scheme.sortorder \n";
        return this.selectEntity(sql, new Object[]{reportSystemId, taskId});
    }

    @Override
    public void batchDeleteByReportSystemId(String reportsystemid, String taskId) {
        String sql = "\tdelete from GC_SAMECTRLRULE  \n   where reportSystem =? and taskId = ? \n";
        this.execute(sql, new Object[]{reportsystemid, taskId});
    }

    @Override
    public List<SameCtrlRuleEO> findRuleList(String reportSystemId, String taskId) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_SAMECTRLRULE" + "  scheme \n  where scheme.reportSystem = ? and scheme.taskId = ? \n  and scheme.leafFlag = 1 and scheme.startFlag = 1 \n  order by scheme.sortOrder  \n";
        return this.selectEntity(sql, new Object[]{reportSystemId, taskId});
    }

    @Override
    public List<SameCtrlRuleEO> findBetweenDragTargetAndDraggingByParentIdAndSortOrder(String parentId, Integer dragRuleNodeSortOrder, Integer dragRuleNodeSortOrderTwo) {
        String sql = "  select " + this.getAllFieldsSQL() + " from " + "GC_SAMECTRLRULE" + "  scheme \n  where scheme.parentId = ? \n  and scheme.sortOrder >= ? \n  and scheme.sortOrder <= ? \n  order by scheme.sortOrder  \n";
        List ruleEOList = this.selectEntity(sql, new Object[]{parentId, dragRuleNodeSortOrder, dragRuleNodeSortOrderTwo});
        return ruleEOList;
    }

    @Override
    public void updateSortOrder(List<SameCtrlRuleEO> sameCtrlRules) {
        String sql = " update GC_SAMECTRLRULE t  set sortOrder=?\n where t.id = ? \n";
        if (CollectionUtils.isEmpty(sameCtrlRules)) {
            return;
        }
        List param = sameCtrlRules.stream().map(m -> Arrays.asList(m.getSortOrder(), m.getId())).collect(Collectors.toList());
        this.executeBatch(sql, param);
    }
}

