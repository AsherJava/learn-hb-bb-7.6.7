/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 */
package com.jiuqi.gcreport.samecontrol.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrFormTabSelectClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrFormTabSelectClient {
    public static final String API_PATH = "/api/gcreport/v1/samecontrol/formTabSelect/";

    @GetMapping(value={"/api/gcreport/v1/samecontrol/formTabSelect/queryFormData/{formKey}"})
    public BusinessResponseEntity<String> queryFormData(@PathVariable(value="formKey") String var1);
}

