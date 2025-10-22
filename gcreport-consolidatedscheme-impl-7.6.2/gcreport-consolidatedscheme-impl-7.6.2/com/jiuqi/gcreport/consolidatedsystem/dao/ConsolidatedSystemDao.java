/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.ConsolidatedSystemEO;
import java.util.List;

public interface ConsolidatedSystemDao
extends IDbSqlGenericDAO<ConsolidatedSystemEO, String> {
    public List<ConsolidatedSystemEO> findAllSystemsWithOrder();

    public ConsolidatedSystemEO getConsolidatedSystemByName(String var1);

    public List<ConsolidatedSystemEO> listSystemsByIds(List<String> var1);
}

