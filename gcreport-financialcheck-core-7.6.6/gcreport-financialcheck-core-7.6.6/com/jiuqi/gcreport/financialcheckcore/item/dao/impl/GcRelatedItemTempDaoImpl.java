/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 */
package com.jiuqi.gcreport.financialcheckcore.item.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.financialcheckcore.item.dao.GcRelatedItemTempDao;
import com.jiuqi.gcreport.financialcheckcore.item.entity.GcRelatedItemTempEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class GcRelatedItemTempDaoImpl
extends AbstractEntDbSqlGenericDAO<GcRelatedItemTempEO>
implements GcRelatedItemTempDao {
    public GcRelatedItemTempDaoImpl() {
        super(GcRelatedItemTempEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_RELATEDITEM_TEMPORARY");
    }

    @Override
    public void deleteItemTempsByBatchId(String batchId) {
        String sqlTemplate = "delete from GC_RELATEDITEM_TEMPORARY where BATCHID = ?";
        this.execute(sqlTemplate, new Object[]{batchId});
    }
}

