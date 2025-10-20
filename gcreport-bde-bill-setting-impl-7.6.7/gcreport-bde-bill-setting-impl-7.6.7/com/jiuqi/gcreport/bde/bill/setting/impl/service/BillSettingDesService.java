/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import java.util.Map;

public interface BillSettingDesService {
    public BillFloatRegionConfigDTO getBillFloatSetting(BillSettingCondiDTO var1);

    public Map<String, BillFloatRegionConfigDTO> getBillFloatSettingByScheme(BillSettingCondiDTO var1);

    public void cleanFloatSetting(BillSettingCondiDTO var1);

    public BillFixedSettingDTO getBillFiexedSetting(BillSettingCondiDTO var1);

    public String save(String var1, BillExtractSettingDTO var2);
}

