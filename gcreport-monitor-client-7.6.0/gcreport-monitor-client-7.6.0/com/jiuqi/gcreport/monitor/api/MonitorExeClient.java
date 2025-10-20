/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.np.common.exception.JQException
 *  org.springframework.cloud.context.config.annotation.RefreshScope
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 */
package com.jiuqi.gcreport.monitor.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.monitor.api.vo.execute.MonitorExeSchemeVO;
import com.jiuqi.gcreport.monitor.api.vo.execute.ValueAndLabelVO;
import com.jiuqi.np.common.exception.JQException;
import java.util.List;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(contextId="com.jiuqi.gcreport.monitor.client.feign.MonitorExeFeignClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
@RefreshScope
public interface MonitorExeClient {
    public static final String GC_API_BASE_PATH = "/api/gcreport/v1/monitor/execute";

    @GetMapping(value={"/api/gcreport/v1/monitor/execute/schemes"})
    public BusinessResponseEntity<List<MonitorExeSchemeVO>> getMonitorExeSchemes();

    @GetMapping(value={"/api/gcreport/v1/monitor/execute/nrschemes"})
    public BusinessResponseEntity<List<ValueAndLabelVO>> getNrSchemes() throws JQException;

    @GetMapping(value={"/api/gcreport/v1/monitor/execute/nrunittype"})
    public BusinessResponseEntity<List<ValueAndLabelVO>> getNrUnitType();

    @GetMapping(value={"/api/gcreport/v1/monitor/execute/monitornodes"})
    public BusinessResponseEntity<List<ValueAndLabelVO>> getMonitorNodes();

    @PostMapping(value={"/api/gcreport/v1/monitor/execute/addscheme"})
    public BusinessResponseEntity<String> addScheme(@RequestBody MonitorExeSchemeVO var1);

    @GetMapping(value={"/api/gcreport/v1/monitor/execute/scheme/{recid}"})
    public BusinessResponseEntity<MonitorExeSchemeVO> getExeScheme(@PathVariable(name="recid") String var1);

    @PostMapping(value={"/api/gcreport/v1/monitor/execute/delete/delscheme/{recid}"})
    public BusinessResponseEntity<String> delScheme(@PathVariable(name="recid") String var1);

    @GetMapping(value={"/api/gcreport/v1/monitor/execute/userconfig"})
    public BusinessResponseEntity<List<String>> getUserConfig();

    @PostMapping(value={"/api/gcreport/v1/monitor/execute/adduserconfig"})
    public BusinessResponseEntity<Object> addUserConfig(@RequestBody List<String> var1);
}

