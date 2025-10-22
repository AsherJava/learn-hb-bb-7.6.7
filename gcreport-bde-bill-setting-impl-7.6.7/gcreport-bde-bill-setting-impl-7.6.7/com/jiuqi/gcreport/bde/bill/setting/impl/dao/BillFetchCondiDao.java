/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.dao;

import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillFetchCondiEO;
import java.util.List;

public interface BillFetchCondiDao {
    public List<BillFetchCondiEO> queryBillFetchCondiEOByFetchSchemeId(String var1);

    public void saveBillFetchCondi(BillFetchCondiEO var1);

    public void saveBillFetchCondiEOs(List<BillFetchCondiEO> var1);

    public int deleteBillFetchCondiEOByFetchSchemeId(String var1);
}

