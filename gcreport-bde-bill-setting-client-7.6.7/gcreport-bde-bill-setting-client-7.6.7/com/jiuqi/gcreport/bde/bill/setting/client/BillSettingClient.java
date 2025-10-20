/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.bde.bill.setting.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BillSettingClient {
    public static final String API_PATH = "/api/gcreport/v1/fetch";

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/fixedsetting"})
    public BusinessResponseEntity<List<BillFixedSettingDTO>> getFixedSetting(@RequestBody BillSettingCondiDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/floatsetting"})
    public BusinessResponseEntity<BillFloatRegionConfigDTO> getFloatSetting(@RequestBody BillSettingCondiDTO var1);
}

