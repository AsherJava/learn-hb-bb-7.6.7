/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO
 */
package com.jiuqi.gcreport.financialcheckImpl.clbr.dao;

import com.jiuqi.gcreport.definition.impl.basic.dao.IBaseSqlGenericDAO;
import com.jiuqi.gcreport.financialcheckImpl.clbr.entity.ClbrVoucherItemTempEO;

public interface ClbrVoucherItemTempDao
extends IBaseSqlGenericDAO<ClbrVoucherItemTempEO> {
    public void deleteByBatchId(String var1);
}

