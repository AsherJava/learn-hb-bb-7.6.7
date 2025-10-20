/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO
 */
package com.jiuqi.gcreport.workingpaper.dao.impl;

import com.jiuqi.gcreport.common.basesql.base.GcDbSqlGenericDAO;
import com.jiuqi.gcreport.workingpaper.dao.ArbitrarilyMergeOrgTemporaryBatchDao;
import com.jiuqi.gcreport.workingpaper.entity.ArbitrarilyMergeOrgTemporaryEO;
import org.springframework.stereotype.Repository;

@Repository
public class ArbitrarilyMergeOrgTemporaryBatchDaoImpl
extends GcDbSqlGenericDAO<ArbitrarilyMergeOrgTemporaryEO, String>
implements ArbitrarilyMergeOrgTemporaryBatchDao {
    public ArbitrarilyMergeOrgTemporaryBatchDaoImpl() {
        super(ArbitrarilyMergeOrgTemporaryEO.class);
    }

    @Override
    public void deleteAllOrgTemporaryData(String orgBatchId) {
        String sql = "delete from GC_ORGTEMPORARY_AM where batchId = ?";
        this.execute("delete from GC_ORGTEMPORARY_AM where batchId = ?", new Object[]{orgBatchId});
    }
}

