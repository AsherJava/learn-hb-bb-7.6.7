/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.samecontrol.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.samecontrol.vo.samectrlextract.SameCtrlExtAfterSchemeVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrlExtAfterSchemeClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrlExtAfterSchemeClient {
    public static final String EXT_AFTER_SCHEME = "/api/gcreport/v1/samecontrol/extAfterScheme";

    @PostMapping(value={"/api/gcreport/v1/samecontrol/extAfterScheme/getSameCtrlExtAfterScheme"})
    public BusinessResponseEntity<Object> getSameCtrlExtAfterScheme(@RequestBody SameCtrlExtAfterSchemeVO var1);
}

