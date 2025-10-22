/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.invest.monthcalcscheme.entity.MonthCalcSchemeEO;

public interface MonthCalcSchemeDao
extends IDbSqlGenericDAO<MonthCalcSchemeEO, String> {
    public MonthCalcSchemeEO getMonthCalcSchemeId(String var1, int var2);
}

