/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO
 */
package com.jiuqi.gcreport.billextract.impl.dao;

import com.jiuqi.gcreport.billextract.client.dto.BillExtractLisDTO;
import java.util.List;
import java.util.Map;

public interface BillExtractSettingDao {
    public List<Map<String, Object>> selectBills(String var1, BillExtractLisDTO var2);

    public Map<String, Object> selectBill(String var1, String var2);
}

