/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 *  com.jiuqi.np.period.PeriodConsts
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.dao.impl;

import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.invest.monthcalcscheme.dao.MonthCalcSchemeDao;
import com.jiuqi.gcreport.invest.monthcalcscheme.entity.MonthCalcSchemeEO;
import com.jiuqi.np.period.PeriodConsts;
import java.util.Collection;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class MonthCalcSchemeDaoImpl
extends GcDbSqlGenericDAO<MonthCalcSchemeEO, String>
implements MonthCalcSchemeDao {
    public MonthCalcSchemeDaoImpl() {
        super(MonthCalcSchemeEO.class);
    }

    @Override
    public MonthCalcSchemeEO getMonthCalcSchemeId(String taskId, int periodType) {
        List monthCalcSchemeEOS = null;
        char type = (char)PeriodConsts.typeToCode((int)periodType);
        if ('N' == type) {
            String sql = "select * from GC_MONTHCALCSCHEME where taskId_N like '%" + taskId + "%'";
            monthCalcSchemeEOS = this.selectEntity(sql, new Object[0]);
        } else {
            String sql = String.format("select * from %s where taskId_%s=?", "GC_MONTHCALCSCHEME", Character.valueOf(type));
            monthCalcSchemeEOS = this.selectEntity(sql, new Object[]{taskId});
        }
        if (!CollectionUtils.isEmpty((Collection)monthCalcSchemeEOS)) {
            return (MonthCalcSchemeEO)((Object)monthCalcSchemeEOS.get(0));
        }
        return null;
    }
}

