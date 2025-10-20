/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.http.PageInfo
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.asset.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.http.PageInfo;
import java.util.List;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.assetbill.api.CombinedAssetBillClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface CombinedAssetBillClient {
    public static final String API_PATH = "/api/gcreport/v1/combinedAssetBill";

    @GetMapping(value={"/api/gcreport/v1/combinedAssetBill/listAllBillList"})
    public String getAllBillListJson(String var1);

    @PostMapping(value={"/api/gcreport/v1/combinedAssetBill/list"})
    public BusinessResponseEntity<PageInfo<Map<String, Object>>> listAssetBills(@RequestBody Map<String, Object> var1);

    @PostMapping(value={"/api/gcreport/v1/combinedAssetBill/batchDelete"})
    public BusinessResponseEntity<String> batchDelete(@RequestBody List<String> var1);

    @PostMapping(value={"/api/gcreport/v1/combinedAssetBill/batchUnDisposal"})
    public BusinessResponseEntity<String> batchUnDisposal(@RequestBody List<String> var1);

    @GetMapping(value={"/api/gcreport/v1/combinedAssetBill/queryIdsByBillCode/{tableName}/{billCode}"})
    public BusinessResponseEntity<String> queryIdsByBillCode(@PathVariable(value="tableName") String var1, @PathVariable(value="billCode") String var2);
}

