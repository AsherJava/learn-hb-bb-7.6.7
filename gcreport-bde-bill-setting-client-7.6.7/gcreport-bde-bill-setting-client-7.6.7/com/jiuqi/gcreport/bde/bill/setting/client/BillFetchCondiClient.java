/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.bde.bill.setting.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.bde.bill.setting.client.dto.BillFetchCondiDTO;
import com.jiuqi.va.bizmeta.domain.metadata.MetaDataDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface BillFetchCondiClient {
    public static final String API_PATH = "/api/gcreport/v1/fetch";

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/fetch/condi/default"})
    public BusinessResponseEntity<Boolean> createDefaultFetchCondi(String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/fetch/condi/save"})
    public BusinessResponseEntity<Boolean> saveFetchCondi(@RequestBody BillFetchCondiDTO var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/fetch/condi/query"})
    public BusinessResponseEntity<BillFetchCondiDTO> queryFetchCondiByFetchSchemeId(@RequestParam(name="fetchSchemeId", required=true) String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/bill/check/formula"})
    public BusinessResponseEntity<Boolean> checkFetchCondi(@RequestBody BillFetchCondiDTO var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/bill/billDefine/get"})
    public BusinessResponseEntity<MetaDataDTO> getBillDefineByBillId(@RequestParam(name="billUniqueCode") String var1);
}

