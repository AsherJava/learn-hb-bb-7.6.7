/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.conversion.conversionsystem.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.conversion.conversionsystem.entity.ConversionSystemTaskEO;
import java.util.List;

public interface ConversionSystemTaskDao
extends IDbSqlGenericDAO<ConversionSystemTaskEO, String> {
    public ConversionSystemTaskEO queryByTaskAndScheme(String var1, String var2);

    public List<ConversionSystemTaskEO> queryBySystemId(String var1);

    public void deleteBySystemId(String var1);
}

