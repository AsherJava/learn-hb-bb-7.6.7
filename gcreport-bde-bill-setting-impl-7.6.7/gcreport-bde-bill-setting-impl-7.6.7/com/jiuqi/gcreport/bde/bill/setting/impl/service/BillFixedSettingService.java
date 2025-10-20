/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.service;

import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.fetchsetting.impl.entity.FetchSettingEO;
import java.util.List;
import java.util.Set;

public interface BillFixedSettingService {
    public List<BillFixedSettingDTO> getFixedSetting(BillSettingCondiDTO var1);

    public List<FetchSettingEO> getFixedSettingEOS(BillSettingCondiDTO var1);

    public Set<String> listTableName(BillSettingCondiDTO var1);
}

