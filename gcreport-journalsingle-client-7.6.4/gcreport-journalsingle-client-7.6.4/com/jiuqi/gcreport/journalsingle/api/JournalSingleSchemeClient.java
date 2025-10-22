/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.journalsingle.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.journalsingle.vo.JournalRelateSchemeVO;
import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.journalsingle.api.JournalSingleSchemeClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface JournalSingleSchemeClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/journalSingleScheme";

    @PostMapping(value={"/api/gcreport/v1/journalSingleScheme/insertRelateScheme"})
    public BusinessResponseEntity<String> insertRelateScheme(@RequestBody JournalRelateSchemeVO var1);

    @PostMapping(value={"/api/gcreport/v1/journalSingleScheme/deleteRelateScheme"})
    public BusinessResponseEntity<String> deleteRelateScheme(@RequestBody JournalRelateSchemeVO var1);

    @GetMapping(value={"/api/gcreport/v1/journalSingleScheme/listRelateSchemes"})
    public BusinessResponseEntity<List<JournalRelateSchemeVO>> listRelateSchemes();
}

