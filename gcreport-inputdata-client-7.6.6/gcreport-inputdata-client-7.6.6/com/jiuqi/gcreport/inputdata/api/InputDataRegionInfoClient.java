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
package com.jiuqi.gcreport.inputdata.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.List;
import java.util.Set;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.adjustingentry.api.InputDataRegionInfoClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface InputDataRegionInfoClient {
    public static final String API_PATH = "/api/gcreport/v1/inputDataRegion/";

    @PostMapping(value={"/api/gcreport/v1/inputDataRegion/listInputDataRegionKeyByFromKey"})
    public BusinessResponseEntity<Set<String>> listInputDataRegionKeyByFromKey(@RequestBody List<String> var1);

    @GetMapping(value={"/api/gcreport/v1/unionrules/offsetGroupingFields/{systemId}"})
    public BusinessResponseEntity<List> getOffsetGroupingField(@PathVariable(value="systemId") String var1);
}

