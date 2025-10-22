/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import java.util.Set;

public interface BillFloatSettingService {
    public BillFloatRegionConfigDTO getFloatSetting(BillSettingCondiDTO var1);

    public Set<String> listTableName(BillSettingCondiDTO var1);
}

