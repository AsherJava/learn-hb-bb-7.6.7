/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.samecontrol.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.samecontrol.entity.SameCtrlRuleEO;
import java.util.List;

public interface SameCtrlRuleDao
extends IDbSqlGenericDAO<SameCtrlRuleEO, String> {
    public List<SameCtrlRuleEO> findByParentIdAndIsolatedFiled(String var1, String var2, String var3);

    public List<SameCtrlRuleEO> findByParentId(String var1);

    public Integer findMaxSortOrderByParentId(String var1);

    public String findParentIdById(String var1);

    public Integer updateSameCtrlRuleStartFlagById(String var1, boolean var2);

    public List<SameCtrlRuleEO> listSameCtrlRuleAndGroup(String var1, String var2);

    public void batchDeleteByReportSystemId(String var1, String var2);

    public List<SameCtrlRuleEO> findRuleList(String var1, String var2);

    public List<SameCtrlRuleEO> findBetweenDragTargetAndDraggingByParentIdAndSortOrder(String var1, Integer var2, Integer var3);

    public void updateSortOrder(List<SameCtrlRuleEO> var1);
}

