/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import java.util.Map;

public interface BillFloatSettingDesService {
    public BillFloatRegionConfigDTO getBillFloatSetting(BillSettingCondiDTO var1);

    public Map<String, BillFloatRegionConfigDTO> getBillFloatSettingByBillCodeAndScheme(BillSettingCondiDTO var1);

    public void save(String var1, BillExtractSettingDTO var2);
}

