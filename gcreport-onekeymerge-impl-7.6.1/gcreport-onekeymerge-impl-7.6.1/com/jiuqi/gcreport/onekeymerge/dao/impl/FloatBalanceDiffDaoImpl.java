/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.calculate.entity.FloatBalanceDiffEO
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.util.NrTool
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition
 *  com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO
 */
package com.jiuqi.gcreport.onekeymerge.dao.impl;

import com.jiuqi.gcreport.calculate.entity.FloatBalanceDiffEO;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.util.NrTool;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.onekeymerge.dao.FloatBalanceDiffDao;
import com.jiuqi.gcreport.onekeymerge.util.OrgUtils;
import com.jiuqi.gcreport.onekeymerge.vo.GcDiffProcessCondition;
import com.jiuqi.gcreport.org.api.vo.GcOrgCacheVO;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class FloatBalanceDiffDaoImpl
extends GcDbSqlGenericDAO<FloatBalanceDiffEO, String>
implements FloatBalanceDiffDao {
    private final String BATCH_DELETE_EMPTY_ROW_SQL = "\tdelete from GC_FLOATBALANCE_DIFF  \n  where MDCODE=? and DATATIME=?\n %1s\n";

    public FloatBalanceDiffDaoImpl() {
        super(FloatBalanceDiffEO.class);
    }

    private StringBuilder buildEntityTableWhere(String unitCode, GcDiffProcessCondition condition) {
        Set entityTableNames = NrTool.getEntityTableNames((String)condition.getSchemeId());
        StringBuilder whereSql = new StringBuilder(128);
        if (entityTableNames.contains("MD_CURRENCY")) {
            whereSql.append(" and i.").append("MD_CURRENCY").append("='").append(condition.getCurrency()).append("'\n");
        }
        if (entityTableNames.contains("MD_GCORGTYPE")) {
            GcOrgCacheVO currentUnit = OrgUtils.getCurrentUnit(condition.getOrgType(), condition.getPeriodStr(), unitCode);
            whereSql.append(" and i.").append("MD_GCORGTYPE").append("='").append(currentUnit.getOrgTypeId()).append("'\n");
        }
        return whereSql;
    }

    @Override
    public void batchDeleteAllBalance(String unitCode, List<String> subjectCodes, String tableName, GcDiffProcessCondition condition) {
        StringBuilder whereSql = this.buildEntityTableWhere(unitCode, condition);
        whereSql.append(" and ").append(SqlUtils.getConditionOfIdsUseOr(subjectCodes, (String)"subjectCode"));
        String sql = "\tdelete from " + tableName + "   \n  where " + "MDCODE" + "=? and " + "DATATIME" + "=?\n %1s\n";
        this.execute(String.format(sql, whereSql), new Object[]{unitCode, condition.getPeriodStr()});
    }

    @Override
    public void batchDeleteAllBalanceByOppunitTitle(String diffUnitId, String oppUnitCode, String oppunitTitle, String tableName, GcDiffProcessCondition condition) {
        StringBuilder whereSql = this.buildEntityTableWhere(diffUnitId, condition);
        whereSql.append(" and i.").append(oppUnitCode).append(" = ?");
        String sql = "\tdelete from " + tableName + " i  \n  where i." + "MDCODE" + "=? and i." + "DATATIME" + "=?\n %1s\n";
        this.execute(String.format(sql, whereSql), new Object[]{diffUnitId, condition.getPeriodStr(), oppunitTitle});
    }
}

