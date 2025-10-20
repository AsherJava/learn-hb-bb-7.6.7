/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.bde.bill.setting.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillExtractSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFixedSettingDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFloatRegionConfigDTO;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFormDefine;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillSettingCondiDTO;
import java.util.Map;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.bde.bill.setting.client.BillSettingManageClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface BillSettingDesClient {
    public static final String API_PATH = "/api/gcreport/v1/fetch";

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/des/query"})
    public BusinessResponseEntity<BillFormDefine> queryBillDefine(@RequestParam(name="fetchSchemeId", required=true) String var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/des/queryByBillUniqueCode"})
    public BusinessResponseEntity<BillFormDefine> queryBillDefineByBillUniqueCode(@RequestParam(name="billUniqueCode", required=true) String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/des/floatsetting"})
    public BusinessResponseEntity<BillFloatRegionConfigDTO> getBillFloatSetting(@RequestBody BillSettingCondiDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/des/floatsettingByScheme"})
    public BusinessResponseEntity<Map<String, BillFloatRegionConfigDTO>> getBillFloatSettingByScheme(@RequestBody BillSettingCondiDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/des/cleanFloatSetting"})
    public BusinessResponseEntity<String> cleanFloatSetting(@RequestBody BillSettingCondiDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/des/fixedsetting"})
    public BusinessResponseEntity<BillFixedSettingDTO> getBillFiexedSetting(@RequestBody BillSettingCondiDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/des/save/{schemeId}"})
    public BusinessResponseEntity<String> save(@PathVariable(name="schemeId") String var1, @RequestBody BillExtractSettingDTO var2);
}

