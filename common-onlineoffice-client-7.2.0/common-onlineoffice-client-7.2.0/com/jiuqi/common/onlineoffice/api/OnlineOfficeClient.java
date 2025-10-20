/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.RequestMapping
 */
package com.jiuqi.common.onlineoffice.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(contextId="com.jiuqi.common.onlineoffice.api.OnlineOfficeClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface OnlineOfficeClient {
    public static final String API_PATH = "/api/gcreport/v1/common/onlineoffice";
    public static final String API_PATH_DOWNLOAD = "/api/gcreport/v1/common/onlineoffice/download/{docFileKey}";

    @RequestMapping(value={"/api/gcreport/v1/common/onlineoffice/download/{docFileKey}"})
    public void download(HttpServletResponse var1, HttpServletRequest var2, @PathVariable(value="docFileKey") String var3);
}

