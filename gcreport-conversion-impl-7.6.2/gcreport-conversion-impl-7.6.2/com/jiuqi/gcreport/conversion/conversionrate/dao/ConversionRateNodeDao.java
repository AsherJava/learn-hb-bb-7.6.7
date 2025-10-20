/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.conversion.conversionrate.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionrate.entity.ConversionRateNodeEO;
import java.util.List;

public interface ConversionRateNodeDao
extends IDbSqlGenericDAO<ConversionRateNodeEO, String> {
    public List<ConversionRateNodeEO> queryByGroupid(String var1);

    public ConversionRateNodeEO get(String var1, String var2, String var3);

    public void deleteByGroupId(String var1);

    public List<ConversionRateNodeEO> queryByIds(List<String> var1);
}

