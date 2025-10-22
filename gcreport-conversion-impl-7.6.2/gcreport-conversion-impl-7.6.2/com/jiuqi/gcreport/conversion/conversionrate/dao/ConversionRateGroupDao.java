/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.conversion.conversionrate.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateGroupEO;
import java.util.List;
import java.util.Map;

public interface ConversionRateGroupDao
extends IDbSqlGenericDAO<ConversionRateGroupEO, String> {
    public Map<String, String> queryGroups(String var1, String var2);

    public List<String> queryPeriodList(String var1);

    public ConversionRateGroupEO get(String var1, String var2, String var3);

    public List<ConversionRateGroupEO> queryByPeriod(String var1, String var2);

    public List<ConversionRateGroupEO> queryBySystem(String var1);

    public void deleteByPeriod(String var1, String var2);

    public void deleteBySystem(String var1);

    public void updateGroupName(String var1, String var2);

    public List<ConversionRateGroupEO> queryByIds(List<String> var1);
}

