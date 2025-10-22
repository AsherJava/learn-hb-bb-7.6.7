/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.investcarryover.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.investcarryover.vo.InvestBillCarryOverSettingVO;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.carryover.api.InvestBillCarryOverSettingClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InvestBillCarryOverSettingClient {
    public static final String API_PATH = "/api/gcreport/v1/investBillCarryOverSetting/";

    @GetMapping(value={"/api/gcreport/v1/investBillCarryOverSetting/listSettings/{carryOverSchemeId}"})
    public BusinessResponseEntity<List<InvestBillCarryOverSettingVO>> listSettings(@PathVariable(value="carryOverSchemeId") String var1);

    @PostMapping(value={"/api/gcreport/v1/investBillCarryOverSetting/saveSetting"})
    public BusinessResponseEntity<String> saveSetting(@RequestBody InvestBillCarryOverSettingVO var1);

    @PostMapping(value={"/api/gcreport/v1/investBillCarryOverSetting/updateSetting"})
    public BusinessResponseEntity<String> updateSetting(@RequestBody InvestBillCarryOverSettingVO var1);

    @PostMapping(value={"/api/gcreport/v1/investBillCarryOverSetting/deleteSetting/{id}"})
    public BusinessResponseEntity<String> deleteSetting(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/investBillCarryOverSetting/listCarryOverColums"})
    public BusinessResponseEntity<Map<String, Object>> listCarryOverColums();

    @PostMapping(value={"/api/gcreport/v1/investBillCarryOverSetting/exchangeSort/{currId}/{exchangeId}"})
    public BusinessResponseEntity<Object> exchangeSort(@PathVariable(value="currId") String var1, @PathVariable(value="exchangeId") String var2);
}

