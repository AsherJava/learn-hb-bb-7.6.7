/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.UnitParamDTO
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.cloud.openfeign.FeignClient
 *  org.springframework.web.bind.annotation.GetMapping
 *  org.springframework.web.bind.annotation.PostMapping
 *  org.springframework.web.bind.annotation.RequestBody
 *  org.springframework.web.bind.annotation.RequestParam
 */
package com.jiuqi.bde.fetch.client;

import com.jiuqi.bde.bizmodel.client.vo.UnitParamDTO;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.Map;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(contextId="com.jiuqi.bde.bizmodel.client.BdeOrgMappingClient", name="${custom.service-name.bde:bde-service}", url="${custom.service-url.bde:}", primary=false)
public interface OrgMappingClient {
    public static final String API_PATH = "/api/bde/v1/orgmapping";

    @GetMapping(path={"/api/bde/v1/orgmapping/anon/get_orgmapping_by_unitcode"})
    public BusinessResponseEntity<OrgMappingDTO> getOrgMappingByUnitCode(@RequestParam(name="bblx", required=false) String var1, @RequestParam(name="unitCode") String var2);

    @PostMapping(path={"/api/bde/v1/orgmapping/get_orgmapping_by_rpunitcode"})
    public BusinessResponseEntity<OrgMappingDTO> getOrgMappingByRpUnitCode(@RequestBody UnitParamDTO var1);

    @PostMapping(path={"/api/bde/v1/orgmapping/get_datacenter_orgmapping_by_rpunitcode"})
    public BusinessResponseEntity<OrgMappingDTO> getDatacenterOrgMappingByRpUnitCode(@RequestBody UnitParamDTO var1);

    @PostMapping(path={"/api/bde/v1/orgmapping/get_orgmapping_by_acctorgcode"})
    public BusinessResponseEntity<OrgMappingDTO> getOrgMappingByAcctOrgCode(@RequestBody UnitParamDTO var1);

    @PostMapping(path={"/api/bde/v1/orgmapping/query_assist_mapping"})
    public BusinessResponseEntity<Map<String, String>> queryBeforeMappingAssistMap(@RequestBody UnitParamDTO var1);
}

