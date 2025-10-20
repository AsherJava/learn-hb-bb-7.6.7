/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.definition.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import javax.servlet.http.HttpServletResponse;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.definition.client.fegin.GcReportDefinitionClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface GcReportDefinitionClient {
    public static final String API_PATH = "/api/gcreport/v1/tabledefine";

    @PostMapping(value={"/api/gcreport/v1/tabledefine/init"})
    public BusinessResponseEntity<String> initTableDefine(@RequestBody String var1);

    @GetMapping(value={"/api/gcreport/v1/tabledefine/basedata", "/api/gcreport/v1/tabledefine/basedata/{tenant}"})
    public BusinessResponseEntity<String> initVaTable(@PathVariable(name="tenant", required=false) String var1);

    @GetMapping(value={"/api/gcreport/v1/tabledefine/checkfield", "/api/gcreport/v1/tabledefine/checkfield/{tableName}", "/api/gcreport/v1/tabledefine/checkfield/{tableName}/{tenant}"})
    public BusinessResponseEntity<String> checkTableFields(HttpServletResponse var1, @PathVariable(name="tableName", required=false) String var2, @PathVariable(name="tenant", required=false) String var3);

    @GetMapping(value={"/api/gcreport/v1/tabledefine/business", "/api/gcreport/v1/tabledefine/business/{tableName}"})
    public BusinessResponseEntity<String> initGcTable(@PathVariable(name="tableName", required=false) String var1);
}

