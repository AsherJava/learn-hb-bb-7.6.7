/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.bill.impl.BillModelImpl
 */
package com.jiuqi.gcreport.billcore.fetchdata.builder;

import com.jiuqi.va.bill.impl.BillModelImpl;
import java.util.List;
import java.util.Map;

public interface GcBillFetchDataBuilder {
    public String getBillModelType();

    public void buildSubDatas(BillModelImpl var1, List<Map<String, Object>> var2, String var3);

    public void buildMasteData(BillModelImpl var1, Map<String, Object> var2);
}

