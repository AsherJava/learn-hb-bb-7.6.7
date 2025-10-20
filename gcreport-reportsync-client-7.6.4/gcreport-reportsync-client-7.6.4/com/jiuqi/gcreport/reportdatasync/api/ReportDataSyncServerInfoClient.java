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
package com.jiuqi.gcreport.reportdatasync.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.reportdatasync.api.ReportDataSyncServerInfoClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ReportDataSyncServerInfoClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/paramsync/serverinfo/";

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverinfo/save"})
    public BusinessResponseEntity<ReportDataSyncServerInfoVO> saveServerInfo(@RequestBody ReportDataSyncServerInfoVO var1);

    @GetMapping(value={"/api/gcreport/v1/paramsync/serverinfo/query"})
    public BusinessResponseEntity<ReportDataSyncServerInfoVO> queryServerInfo();

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverinfo/regist"})
    public BusinessResponseEntity<ReportDataSyncServerInfoVO> register(@RequestBody ReportDataSyncServerInfoVO var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverinfo/connection"})
    public BusinessResponseEntity<Boolean> connection(@RequestBody ReportDataSyncServerInfoVO var1);

    @PostMapping(value={"/api/gcreport/v1/paramsync/serverinfo/syncModifyFlag/{startFlag}"})
    public BusinessResponseEntity<ReportDataSyncServerInfoVO> syncModifyFlagUpdate(@PathVariable(value="startFlag") Boolean var1);
}

