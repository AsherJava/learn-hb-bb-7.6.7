/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.gcreport.common.util.DimensionUtils
 *  com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils
 *  com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO
 */
package com.jiuqi.gcreport.multicriteria.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.common.util.DimensionUtils;
import com.jiuqi.gcreport.definition.impl.sqlutil.SqlUtils;
import com.jiuqi.gcreport.multicriteria.client.vo.GcMultiCriteriaConditionVO;
import com.jiuqi.gcreport.multicriteria.dao.GcMulCriAfterAmtDao;
import com.jiuqi.gcreport.multicriteria.entity.GcMulCriAfterAmtEO;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public class GcMulCriAfterAmtDaoImpl
extends GcDbSqlGenericDAO<GcMulCriAfterAmtEO, String>
implements GcMulCriAfterAmtDao {
    public GcMulCriAfterAmtDaoImpl() {
        super(GcMulCriAfterAmtEO.class);
    }

    @Override
    public void deleteByIds(Set<String> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return;
        }
        String inSql = SqlUtils.getConditionOfIdsUseOr(ids, (String)"mcAfterZbId");
        String sql = " delete from GC_MULCRIAFTERAMT   \n where " + inSql + " \n";
        this.execute(sql);
    }

    @Override
    public Map<String, Double> queryMulCriAfterAmt(List<String> mcAfterZbId, GcMultiCriteriaConditionVO condition) {
        String inSql = SqlUtils.getConditionOfIdsUseOr(mcAfterZbId, (String)"i.mcAfterZbId");
        String sql = "select i.mcAfterZbId AS ID,i.afterZbAmt AS AMT from GC_MULCRIAFTERAMT  i \nwhere " + inSql + " \n";
        sql = sql + this.getWhereSql(condition);
        List queryMapBySql = this.selectMap(sql, new Object[0]);
        HashMap<String, Double> afterAmtMap = new HashMap<String, Double>();
        for (Map v : queryMapBySql) {
            Double amt = v.get("AMT") == null ? 0.0 : new BigDecimal(v.get("AMT").toString()).doubleValue();
            afterAmtMap.put(String.valueOf(v.get("ID")), amt);
        }
        return afterAmtMap;
    }

    private String getWhereSql(GcMultiCriteriaConditionVO condition) {
        StringBuilder sql = new StringBuilder();
        if (!StringUtils.isEmpty((String)condition.getOrgId())) {
            sql.append(" and i.MDCODE='" + condition.getOrgId() + "' \n");
        }
        if (!StringUtils.isEmpty((String)condition.getOrgType())) {
            sql.append(" and i.MD_GCORGTYPE='" + condition.getOrgType() + "' \n");
        }
        if (!StringUtils.isEmpty((String)condition.getCurrency())) {
            sql.append(" and i.MD_CURRENCY='" + condition.getCurrency() + "' \n");
        }
        if (!StringUtils.isEmpty((String)condition.getPeriodStr())) {
            sql.append(" and i.DATATIME='" + condition.getPeriodStr() + "' \n");
        }
        if (DimensionUtils.isExistAdjust((String)condition.getTaskId())) {
            sql.append(" and i.ADJUST = ").append("'").append(condition.getSelectAdjustCode()).append("'");
        }
        return sql.toString();
    }

    @Override
    public List<GcMulCriAfterAmtEO> queryMulCriAfterAmtByDs(List<String> mcAfterZbId, GcMultiCriteriaConditionVO condition) {
        String allFieldsSQL = SqlUtils.getColumnsSqlByTableDefine((String)"GC_MULCRIAFTERAMT", (String)"i");
        String inSql = SqlUtils.getConditionOfIdsUseOr(mcAfterZbId, (String)"i.mcAfterZbId");
        String sql = "select " + allFieldsSQL + " from " + "GC_MULCRIAFTERAMT" + "  i \nwhere " + inSql + " \n";
        sql = sql + this.getWhereSql(condition);
        return this.selectEntity(sql, new Object[0]);
    }
}

