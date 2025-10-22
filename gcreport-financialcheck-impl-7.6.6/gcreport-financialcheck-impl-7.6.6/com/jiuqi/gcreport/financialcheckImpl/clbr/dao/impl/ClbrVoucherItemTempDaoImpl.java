/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO
 *  com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate
 *  com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate
 */
package com.jiuqi.gcreport.financialcheckImpl.clbr.dao.impl;

import com.jiuqi.gcreport.definition.impl.basic.dao.AbstractEntDbSqlGenericDAO;
import com.jiuqi.gcreport.definition.impl.basic.intf.FEntSqlTemplate;
import com.jiuqi.gcreport.financialcheckImpl.clbr.dao.ClbrVoucherItemTempDao;
import com.jiuqi.gcreport.financialcheckImpl.clbr.entity.ClbrVoucherItemTempEO;
import com.jiuqi.gcreport.financialcheckcore.sqlutils.FcNativeSqlTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ClbrVoucherItemTempDaoImpl
extends AbstractEntDbSqlGenericDAO<ClbrVoucherItemTempEO>
implements ClbrVoucherItemTempDao {
    public ClbrVoucherItemTempDaoImpl() {
        super(ClbrVoucherItemTempEO.class, (FEntSqlTemplate)new FcNativeSqlTemplate());
        this.setTableName("GC_CLBR_VOUCHERITEM_TEMP");
    }

    @Override
    public void deleteByBatchId(String batchId) {
        String sqlTemplate = "delete from GC_CLBR_VOUCHERITEM_TEMP where BATCHID = ?";
        this.execute(sqlTemplate, new Object[]{batchId});
    }
}

