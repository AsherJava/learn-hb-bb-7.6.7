/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.conversion.conversionrate.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateEO;
import java.util.List;

public interface ConversionRateDao
extends IDbSqlGenericDAO<ConversionRateEO, String> {
    public List<ConversionRateEO> getRateInfoList(String var1, String var2, String var3, String var4, String var5, String var6, String var7);

    public List<ConversionRateEO> getRateEO(String var1, String var2, String var3, String var4, String var5, String var6);

    public Boolean updateRate(ConversionRateEO var1);

    public List<ConversionRateEO> queryByNodeId(String var1);

    public void deleteByNodeId(String var1);

    public List<ConversionRateEO> queryByRowId(String var1);

    public ConversionRateEO queryByRowId(String var1, String var2);

    public void deleteByRowId(String var1);

    public List<ConversionRateEO> queryByPeriod(String var1, String var2);

    public List<ConversionRateEO> queryByGroup(String var1);

    public List<ConversionRateEO> queryByNode(String var1);

    public List<ConversionRateEO> queryAll(String var1);

    public List<ConversionRateEO> getSumAvgRateInfoList(String var1, String var2, String var3, String var4, String var5, String var6);
}

