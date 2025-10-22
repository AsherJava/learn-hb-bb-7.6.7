/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition
 */
package com.jiuqi.gcreport.lease.leasebill.dao;

import com.jiuqi.gcreport.definition.impl.sqlutil.TempTableCondition;
import java.util.List;
import java.util.Map;

public interface TenantryBillDao {
    public List<Map<String, Object>> listTenantryBillsByPaging(TempTableCondition var1, Map<String, Object> var2);

    public int countTenantryBills(TempTableCondition var1, Map<String, Object> var2);

    public void batchDeleteByIdList(List<String> var1);
}

