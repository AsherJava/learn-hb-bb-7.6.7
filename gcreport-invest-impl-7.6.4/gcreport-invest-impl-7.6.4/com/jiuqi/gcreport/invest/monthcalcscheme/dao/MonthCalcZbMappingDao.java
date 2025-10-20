/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.PageInfo
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 *  com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond
 */
package com.jiuqi.gcreport.invest.monthcalcscheme.dao;

import com.jiuqi.common.base.http.PageInfo;
import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.invest.monthcalcscheme.entity.MonthCalcZbMappingEO;
import com.jiuqi.gcreport.monthcalcscheme.vo.MonthCalcZbMappingCond;
import java.util.List;

public interface MonthCalcZbMappingDao
extends IDbSqlGenericDAO<MonthCalcZbMappingEO, String> {
    public PageInfo<MonthCalcZbMappingEO> listZbMappings(MonthCalcZbMappingCond var1);

    public void delBySchemeId(String var1);

    public List<MonthCalcZbMappingEO> getZbMappingsBySchemeID(String var1);

    public void deleteBatchByIds(List<String> var1);

    public MonthCalcZbMappingEO getPreNodeBySchemeIdAndOrder(String var1, String var2);

    public MonthCalcZbMappingEO getNextNodeBySchemeIdAndOrder(String var1, String var2);
}

