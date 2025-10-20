/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.consolidatedsystem.dao.impl.task;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.consolidatedsystem.dao.task.ConsolidatedTaskDataDao;
import com.jiuqi.gcreport.consolidatedsystem.entity.task.ConsolidatedTaskDataEO;
import org.springframework.stereotype.Repository;

@Repository
public class ConsolidatedTaskDataDaoImpl
extends GcDbSqlGenericDAO<ConsolidatedTaskDataEO, String>
implements ConsolidatedTaskDataDao {
    public ConsolidatedTaskDataDaoImpl() {
        super(ConsolidatedTaskDataEO.class);
    }

    @Override
    public void deleteByConsTaskId(String consTaskId) {
        String sql = "  delete     from GC_CONSTASKDATA   \n     where consTaskId = ?";
        this.execute(sql, new Object[]{consTaskId});
    }
}

