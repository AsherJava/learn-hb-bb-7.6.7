/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity
 */
package com.jiuqi.gcreport.invest.investbill.service;

import com.jiuqi.gcreport.definition.impl.basic.entity.DefaultTableEntity;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FairValueBillService {
    public Map<String, Object> queryFvchBillCode(String var1, String var2);

    public Map<String, List<DefaultTableEntity>> getFvchItemBills(int var1, Set<String> var2, Map<String, String> var3);

    public List<DefaultTableEntity> getFvchItemsByMasterSrcId(String var1, Integer var2);
}

