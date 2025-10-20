/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.task;

import com.jiuqi.gcreport.common.basesql.base.IDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.entity.task.ConsolidatedTaskEO;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsolidatedTaskDao
extends IDbSqlGenericDAO<ConsolidatedTaskEO, String> {
    public List<ConsolidatedTaskEO> getTaskByTaskKey(String var1);

    public List<ConsolidatedTaskEO> getTaskByTaskKeyAndPeriodStr(String var1, String var2);

    public void deleteTasksBySystem(String var1);

    public List<ConsolidatedTaskEO> getAllBoundTasks();

    public ConsolidatedTaskEO getPreNodeBySystemIdAndOrder(String var1, String var2);

    public ConsolidatedTaskEO getNextNodeBySystemIdAndOrder(String var1, String var2);
}

