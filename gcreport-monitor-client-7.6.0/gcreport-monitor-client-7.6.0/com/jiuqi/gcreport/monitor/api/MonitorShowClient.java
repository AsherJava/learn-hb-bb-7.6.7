/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.monitor.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.monitor.api.vo.show.ConditionVO;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.monitor.client.feign.MonitorShowFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface MonitorShowClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/monitor/show";

    @PostMapping(value={"/api/gcreport/v1/monitor/show/v2"})
    public BusinessResponseEntity<Map<String, Object>> monitorShowNew(@RequestBody ConditionVO var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/show/export2Excel"})
    public void exportExcel(HttpServletResponse var1, @RequestBody ConditionVO var2);
}

