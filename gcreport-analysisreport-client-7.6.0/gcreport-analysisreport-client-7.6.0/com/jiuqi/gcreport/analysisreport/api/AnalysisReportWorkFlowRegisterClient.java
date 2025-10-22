/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.workflow.WorkflowDTO
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.analysisreport.api;

import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.workflow.WorkflowDTO;
import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(primary=false, contextId="com.jiuqi.gcreport.analysisreport.api.gcAnalysisReportWorkFlowClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}")
public interface AnalysisReportWorkFlowRegisterClient {
    public static final String API = "/api/gcreport/v1/";

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/biz/title/get"})
    public R getWorkFlowForTitle(@RequestBody TenantDO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/bussiness/message/template/param/get"})
    public R getWorkFlowMessageForKey(@RequestBody TenantDO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/bussiness/param/variables/get"})
    public Map<String, Object> getWorkFlowParamForKey(@RequestBody TenantDO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/bussiness/state/update"})
    public R updateWorkFlowParamForKey(@RequestBody TenantDO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/biz/action/agree"})
    public R agree(@RequestBody TenantDO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/biz/action/reject"})
    public R reject(@RequestBody TenantDO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/biz/action/retract"})
    public R retract(@RequestBody TenantDO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/biz/todo/param/get"})
    public Map<String, Object> get(@RequestBody TenantDO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/action/agree"})
    public R agreeTest(@RequestBody WorkflowDTO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/gcanalysis/bussiness/process/view"})
    public R processView(@RequestBody WorkflowDTO var1);

    @PostMapping(value={"/api/gcreport/v1/gcreport-analysisreport/action/reject"})
    public R todoReject(@RequestBody WorkflowDTO var1);
}

