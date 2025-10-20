/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.option;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.option.ConsolidatedOptionEO;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsolidatedOptionDao
extends IDbSqlGenericDAO<ConsolidatedOptionEO, String> {
    public ConsolidatedOptionEO getOptionDataBySystemId(String var1);

    public void deleteBySystemId(String var1);
}

