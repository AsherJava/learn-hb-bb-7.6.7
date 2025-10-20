/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 */
package com.jiuqi.gcreport.reportdatasync.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId="com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncPenetrateClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ReportDataSyncPenetrateClient {
    @GetMapping(value={"/multiLevel/multiLevelPentrate/redirect"})
    public void multiLevelPentrateRedirect(HttpServletRequest var1, HttpServletResponse var2, String var3) throws Exception;

    @GetMapping(value={"/multiLevel/multiLevelPentrate"})
    public void multiLevelPentrate(HttpServletRequest var1, HttpServletResponse var2, String var3) throws Exception;
}

