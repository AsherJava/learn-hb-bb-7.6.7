/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.gcreport.common.SelectOptionVO
 *  javax.validation.Valid
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PathVariable
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.gcreport.consolidatedsystem.api;

import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.gcreport.common.SelectOptionVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.ConsolidatedSystemVO;
import com.jiuqi.gcreport.consolidatedsystem.vo.subject.ConsolidatedSubjectVO;
import java.util.List;
import javax.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.gcreport.consolidatedsystem.api.ConsolidatedSystemClient", name="${custom.service-name.gcreport:gcreport-service}", url="${custom.service-url.gcreport:}", primary=false)
public interface ConsolidatedSystemClient {
    public static final String CONSOLIDATED_API_BASE_PATH = "/api/gcreport/v1/consolidatedSystems";

    @GetMapping(value={"/api/gcreport/v1/consolidatedSystems/{id}"})
    public BusinessResponseEntity<ConsolidatedSystemVO> getConsolidatedSystem(@PathVariable(value="id") String var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSystems"})
    public BusinessResponseEntity<List<ConsolidatedSystemVO>> getConsolidatedSystems(@RequestParam(name="year", required=false) Integer var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSystems/byTaskId/{taskId}"})
    public BusinessResponseEntity<List<ConsolidatedSystemVO>> getConsolidatedSystemsByTaskId(@PathVariable(value="taskId") String var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSystemslistAllSubjectBySystemId/{systemId}"})
    public BusinessResponseEntity<List<ConsolidatedSubjectVO>> listAllSubjectBySystemId(@PathVariable(value="systemId") String var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSystems"})
    public BusinessResponseEntity<String> addConsolidatedSystem(@Valid @RequestBody ConsolidatedSystemVO var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSystems/edit/{id}"})
    public BusinessResponseEntity<String> editConsolidatedSystem(@PathVariable(value="id") String var1, @Valid @RequestBody ConsolidatedSystemVO var2);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSystems/delete/{id}"})
    public BusinessResponseEntity<String> deleteConsolidatedSystem(@PathVariable(value="id") String var1);

    @PostMapping(value={"/api/gcreport/v1/consolidatedSystems/{id}/actions/{action}"})
    public BusinessResponseEntity<String> handleConsolidatedSystem(@PathVariable(value="id") String var1, @PathVariable(value="action") String var2);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSystems/formualSchemes/{systemId}"})
    public BusinessResponseEntity<List<SelectOptionVO>> getFormualSchemes(@PathVariable(value="systemId") String var1);

    @GetMapping(value={"/api/gcreport/v1/consolidatedSystems/isCorporate/{taskId}/{periodStr}/{orgType}"})
    public BusinessResponseEntity<Boolean> isCorporate(@PathVariable(value="taskId") String var1, @PathVariable(value="periodStr") String var2, @PathVariable(value="orgType") String var3);
}

