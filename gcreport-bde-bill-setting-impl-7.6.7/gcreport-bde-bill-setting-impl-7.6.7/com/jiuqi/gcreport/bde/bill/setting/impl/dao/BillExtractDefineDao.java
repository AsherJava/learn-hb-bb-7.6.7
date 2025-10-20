/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.dao;

import com.jiuqi.gcreport.bde.bill.setting.impl.entity.BillExtractDefineEO;
import java.util.List;

public interface BillExtractDefineDao {
    public List<BillExtractDefineEO> listByBillSettingType(String var1);

    public void batchInsert(String var1, List<BillExtractDefineEO> var2);

    public void batchDelete(String var1, List<String> var2);
}

