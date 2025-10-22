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

@FeignClient(contextId="com.jiuqi.gcreport.samecontrol.api.SameCtrlOrgViewClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface SameCtrlOrgViewClient {
    public static final String ORGVIEW_PATH = "/api/gcreport/v1/samecontrol/orgView";

    @GetMapping(value={"/api/gcreport/v1/samecontrol/orgView/setOrgViewStopFlag"})
    public BusinessResponseEntity<Object> setOrgViewStopFlag();

    @GetMapping(value={"/api/gcreport/v1/samecontrol/orgView/getSameCtrlViewId/{schemeId}"})
    public BusinessResponseEntity<Object> getSameCtrlViewId(@PathVariable(value="schemeId") String var1);
}

