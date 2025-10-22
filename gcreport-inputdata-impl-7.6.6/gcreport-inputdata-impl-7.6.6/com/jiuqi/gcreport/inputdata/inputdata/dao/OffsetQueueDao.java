/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.inputdata.inputdata.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.inputdata.inputdata.entity.OffsetQueueEO;
import java.util.Set;

public interface OffsetQueueDao
extends IDbSqlGenericDAO<OffsetQueueEO, String> {
    public boolean existsBeforeCreateTime(OffsetQueueEO var1, String var2);

    public void deleteById(String var1);

    public Set<String> queryUnitByCondition(String var1, String var2, String var3, long var4, String var6);

    public void deleteOverTime(OffsetQueueEO var1, String var2);
}

