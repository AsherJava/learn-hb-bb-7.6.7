/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.task;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.task.ConsolidatedTaskDataEO;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsolidatedTaskDataDao
extends IDbSqlGenericDAO<ConsolidatedTaskDataEO, String> {
    public void deleteByConsTaskId(String var1);
}

