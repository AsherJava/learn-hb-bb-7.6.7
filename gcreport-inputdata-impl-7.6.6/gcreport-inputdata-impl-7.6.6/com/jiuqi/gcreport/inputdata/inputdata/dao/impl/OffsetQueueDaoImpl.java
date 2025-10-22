/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.inputdata.inputdata.dao.OffsetQueueDao;
import com.jiuqi.gcreport.inputdata.inputdata.entity.OffsetQueueEO;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class OffsetQueueDaoImpl
extends GcDbSqlGenericDAO<OffsetQueueEO, String>
implements OffsetQueueDao {
    private static String QUERYBYUNIQUEINDEXANDLESSTHENCREATETIME_SQL = "  select 1 \n    from GC_OFFSETQUEUE   where %s \n     and NRPERIOD = ? \n     and taskId = ?\n     and currencyCode = ? \n     and unionRuleId = ? \n     and createTime < ? \n";
    private static String DELETEBYID_SQL = "  delete from GC_OFFSETQUEUE   where id = ? \n";
    private static String QUERYUNITBYCONDITION_SQL = "  select unitComb  UNITCOMB \n    from GC_OFFSETQUEUE   where nrperiod = ? \n     and taskId = ? \n     and currencyCode = ? \n     and unionRuleId = ? \n     and createTime < ? \n";
    private static String DELETEOVERTIEMOFFSETQUEUE_SQL = "  delete from GC_OFFSETQUEUE   where %s \n     and nrperiod = ? \n     and taskId = ? \n     and currencyCode = ? \n     and unionRuleId = ? \n     and createTime < ? \n";

    public OffsetQueueDaoImpl() {
        super(OffsetQueueEO.class);
    }

    @Override
    public boolean existsBeforeCreateTime(OffsetQueueEO offsetQueueEO, String mergingUnit) {
        if (StringUtils.isEmpty(offsetQueueEO.getUnitComb()) || StringUtils.isEmpty(offsetQueueEO.getNrPeriod()) || StringUtils.isEmpty(offsetQueueEO.getCurrencyCode()) || offsetQueueEO.getSchemeId() == null) {
            return false;
        }
        String condition = mergingUnit == null ? "unitComb = '" + offsetQueueEO.getUnitComb() + "'" : "unitComb in ('" + offsetQueueEO.getUnitComb() + "','" + mergingUnit.toString() + "')";
        String sql = String.format(QUERYBYUNIQUEINDEXANDLESSTHENCREATETIME_SQL, condition);
        List rs = this.selectMap(sql, new Object[]{offsetQueueEO.getNrPeriod(), offsetQueueEO.getSchemeId(), offsetQueueEO.getCurrencyCode(), offsetQueueEO.getUnionRuleId(), offsetQueueEO.getCreateTime()});
        return !rs.isEmpty();
    }

    @Override
    public void deleteById(String id) {
        String sql = String.format(DELETEBYID_SQL, new Object[0]);
        this.execute(sql, new Object[]{id});
    }

    @Override
    public Set<String> queryUnitByCondition(String nrPeriod, String taskId, String currencyCode, long createTime, String unionRuleId) {
        List rs = this.selectFirstList(String.class, QUERYUNITBYCONDITION_SQL, new Object[]{nrPeriod, taskId, currencyCode, unionRuleId, createTime});
        Set<String> units = rs.stream().collect(Collectors.toSet());
        return units;
    }

    @Override
    public void deleteOverTime(OffsetQueueEO offsetQueueEO, String mergingUnit) {
        if (StringUtils.isEmpty(offsetQueueEO.getUnitComb()) || StringUtils.isEmpty(offsetQueueEO.getNrPeriod()) || StringUtils.isEmpty(offsetQueueEO.getCurrencyCode()) || offsetQueueEO.getSchemeId() == null) {
            return;
        }
        String condition = mergingUnit == null ? "unitComb = '" + offsetQueueEO.getUnitComb() + "'" : "unitComb in ('" + offsetQueueEO.getUnitComb() + "','" + mergingUnit.toString() + "')";
        String sql = String.format(DELETEOVERTIEMOFFSETQUEUE_SQL, condition);
        this.execute(sql, new Object[]{offsetQueueEO.getNrPeriod(), offsetQueueEO.getSchemeId(), offsetQueueEO.getCurrencyCode(), offsetQueueEO.getUnionRuleId(), offsetQueueEO.getCreateTime()});
    }
}

