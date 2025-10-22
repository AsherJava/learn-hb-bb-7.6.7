/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.bde.bill.setting.client;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.bde.bill.setting.client.BillExtractSchemeClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface BillExtractSchemeClient {
    public static final String API_PATH = "/api/gcreport/v1/fetch";

    @GetMapping(value={"/api/gcreport/v1/fetch/scheme/list"})
    public BusinessResponseEntity<List<BillFetchSchemeDTO>> listScheme(@RequestParam(name="billType", required=true) String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/scheme/save"})
    public BusinessResponseEntity<Boolean> save(@RequestBody BillFetchSchemeDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/scheme/update"})
    public BusinessResponseEntity<Boolean> update(@RequestBody BillFetchSchemeDTO var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/scheme/delete"})
    public BusinessResponseEntity<Boolean> delete(@RequestParam(name="schemeId", required=true) String var1);

    @GetMapping(value={"/api/gcreport/v1/fetch/scheme/publish"})
    public BusinessResponseEntity<Boolean> publish(@RequestParam(name="schemeId", required=true) String var1);

    @PostMapping(value={"/api/gcreport/v1/fetch/scheme/copy"})
    public BusinessResponseEntity<Boolean> copy(@RequestParam(name="schemeId", required=true) String var1, @RequestParam(name="newSchemeName", required=true) String var2);

    @PostMapping(value={"/api/gcreport/v1/fetch/scheme/exchangeOrdinal"})
    public BusinessResponseEntity<Boolean> exchangeOrdinal(@RequestParam(name="srcSchemeId", required=true) String var1, @RequestParam(name="targetSchemeId", required=true) String var2);
}

