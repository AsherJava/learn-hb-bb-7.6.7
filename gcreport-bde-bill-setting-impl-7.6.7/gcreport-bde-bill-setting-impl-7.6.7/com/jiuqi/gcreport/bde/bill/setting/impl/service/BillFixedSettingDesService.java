/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingDesEO;
import java.util.List;

public interface BillFixedSettingDesService {
    public BillFixedSettingDTO getBillFiexedSetting(BillSettingCondiDTO var1);

    public List<BillFixedSettingDTO> getBillFiexedSettingsInTable(BillSettingCondiDTO var1);

    public List<FetchSettingDesEO> getFiexedSettingEOsInTable(BillSettingCondiDTO var1);

    public void save(String var1, BillExtractSettingDTO var2);
}

