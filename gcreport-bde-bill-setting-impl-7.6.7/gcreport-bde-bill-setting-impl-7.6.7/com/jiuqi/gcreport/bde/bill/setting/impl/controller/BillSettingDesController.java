/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.bde.bill.setting.client.BillSettingDesClient
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFormDefine
 *  com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.gcreport.bde.bill.setting.impl.controller;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.setting.client.BillSettingDesClient;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFormDefine;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillFormDefineService;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillSettingDesService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillSettingDesController
implements BillSettingDesClient {
    @Autowired
    private BillSettingDesService service;
    @Autowired
    private BillFormDefineService formDefineService;

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/des/query"})
    public BusinessResponseEntity<BillFormDefine> queryBillDefine(String fetchSchemeId) {
        return BusinessResponseEntity.ok((Object)this.formDefineService.queryBillDefine(fetchSchemeId));
    }

    public BusinessResponseEntity<BillFormDefine> queryBillDefineByBillUniqueCode(String billUniqueCode) {
        return BusinessResponseEntity.ok((Object)this.formDefineService.queryBillDefineByBillUniqueCode(billUniqueCode));
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/des/floatsetting"})
    public BusinessResponseEntity<BillFloatRegionConfigDTO> getBillFloatSetting(@RequestBody BillSettingCondiDTO billSettingCondi) {
        return BusinessResponseEntity.ok((Object)this.service.getBillFloatSetting(billSettingCondi));
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/des/floatsettingByScheme"})
    public BusinessResponseEntity<Map<String, BillFloatRegionConfigDTO>> getBillFloatSettingByScheme(@RequestBody BillSettingCondiDTO billSettingCondi) {
        return BusinessResponseEntity.ok(this.service.getBillFloatSettingByScheme(billSettingCondi));
    }

    public BusinessResponseEntity<String> cleanFloatSetting(BillSettingCondiDTO fetchSettingCond) {
        this.service.cleanFloatSetting(fetchSettingCond);
        return BusinessResponseEntity.ok((Object)"\u6e05\u9664\u6210\u529f");
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/des/fixedsetting"})
    public BusinessResponseEntity<BillFixedSettingDTO> getBillFiexedSetting(@RequestBody BillSettingCondiDTO billSettingCondi) {
        return BusinessResponseEntity.ok((Object)this.service.getBillFiexedSetting(billSettingCondi));
    }

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/des/save/{schemeId}"})
    public BusinessResponseEntity<String> save(@PathVariable(name="schemeId") String schemeId, @RequestBody BillExtractSettingDTO setting) {
        return BusinessResponseEntity.ok((Object)this.service.save(schemeId, setting));
    }
}

