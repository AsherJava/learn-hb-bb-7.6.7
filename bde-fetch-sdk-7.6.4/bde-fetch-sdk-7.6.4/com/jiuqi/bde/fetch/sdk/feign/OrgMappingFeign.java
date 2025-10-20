/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.UnitParamDTO
 *  com.jiuqi.bde.common.certify.service.RequestCertifyService
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.fetch.client.OrgMappingClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.fetch.sdk.feign;

import com.jiuqi.bde.bizmodel.client.vo.UnitParamDTO;
import com.jiuqi.bde.common.certify.service.RequestCertifyService;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.fetch.client.OrgMappingClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrgMappingFeign
implements OrgMappingClient {
    @Autowired
    private RequestCertifyService requestCertifyService;

    public BusinessResponseEntity<OrgMappingDTO> getOrgMappingByUnitCode(String bblx, String unitCode) {
        OrgMappingClient dynamicClient = (OrgMappingClient)this.requestCertifyService.getFeignClient(OrgMappingClient.class);
        return dynamicClient.getOrgMappingByUnitCode(bblx, unitCode);
    }

    public BusinessResponseEntity<OrgMappingDTO> getOrgMappingByRpUnitCode(UnitParamDTO unitParam) {
        OrgMappingClient dynamicClient = (OrgMappingClient)this.requestCertifyService.getFeignClient(OrgMappingClient.class);
        return dynamicClient.getOrgMappingByRpUnitCode(unitParam);
    }

    public BusinessResponseEntity<OrgMappingDTO> getDatacenterOrgMappingByRpUnitCode(UnitParamDTO unitParam) {
        OrgMappingClient dynamicClient = (OrgMappingClient)this.requestCertifyService.getFeignClient(OrgMappingClient.class);
        return dynamicClient.getDatacenterOrgMappingByRpUnitCode(unitParam);
    }

    public BusinessResponseEntity<OrgMappingDTO> getOrgMappingByAcctOrgCode(UnitParamDTO unitParam) {
        OrgMappingClient dynamicClient = (OrgMappingClient)this.requestCertifyService.getFeignClient(OrgMappingClient.class);
        return dynamicClient.getOrgMappingByAcctOrgCode(unitParam);
    }

    public BusinessResponseEntity<Map<String, String>> queryBeforeMappingAssistMap(UnitParamDTO unitParam) {
        OrgMappingClient dynamicClient = (OrgMappingClient)this.requestCertifyService.getFeignClient(OrgMappingClient.class);
        return dynamicClient.queryBeforeMappingAssistMap(unitParam);
    }
}

