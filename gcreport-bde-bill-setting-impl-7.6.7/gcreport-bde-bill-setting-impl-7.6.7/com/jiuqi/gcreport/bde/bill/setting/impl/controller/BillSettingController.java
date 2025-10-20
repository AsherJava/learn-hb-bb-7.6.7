/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.bill.setting.client.BillSettingClient
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.setting.client.BillSettingClient;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFixedSettingService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFloatSettingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillSettingController
implements BillSettingClient {
    @Autowired
    private BillFloatSettingService floatSettingService;
    @Autowired
    private BillFixedSettingService fixedSettingService;

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/fixedsetting"})
    public BusinessResponseEntity<List<BillFixedSettingDTO>> getFixedSetting(@RequestBody BillSettingCondiDTO billSettingCondi) {
        return BusinessResponseEntity.ok(this.fixedSettingService.getFixedSetting(billSettingCondi));
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/floatsetting"})
    public BusinessResponseEntity<BillFloatRegionConfigDTO> getFloatSetting(@RequestBody BillSettingCondiDTO billSettingCondi) {
        return BusinessResponseEntity.ok((Object)this.floatSettingService.getFloatSetting(billSettingCondi));
    }
}

