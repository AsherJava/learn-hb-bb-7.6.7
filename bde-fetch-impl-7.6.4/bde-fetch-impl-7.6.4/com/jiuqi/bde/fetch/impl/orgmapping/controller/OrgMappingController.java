/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.client.vo.UnitParamDTO
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.GCQueryBeforeMappingAssistMapService
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.OrgMappingQueryService
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.bde.common.exception.BdeRuntimeException
 *  com.jiuqi.bde.fetch.client.OrgMappingClient
 *  com.jiuqi.common.base.http.BusinessResponseEntity
 *  com.jiuqi.common.base.util.StringUtils
 *  org.springframework.web.bind.annotation.RestController
 */
package com.jiuqi.bde.fetch.impl.orgmapping.controller;

import com.jiuqi.bde.bizmodel.client.vo.UnitParamDTO;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.GCQueryBeforeMappingAssistMapService;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.OrgMappingQueryService;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.common.exception.BdeRuntimeException;
import com.jiuqi.bde.fetch.client.OrgMappingClient;
import com.jiuqi.common.base.http.BusinessResponseEntity;
import com.jiuqi.common.base.util.StringUtils;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrgMappingController
implements OrgMappingClient {
    @Autowired
    private IOrgMappingServiceProvider orgMappingProvider;
    @Autowired
    private OrgMappingQueryService orgMappingQueryService;
    @Autowired
    private GCQueryBeforeMappingAssistMapService queryBeforeMappingAssistMapService;

    public BusinessResponseEntity<OrgMappingDTO> getOrgMappingByUnitCode(String bblx, String unitCode) {
        return BusinessResponseEntity.ok((Object)this.orgMappingProvider.getByCode(bblx).getOrgMapping(unitCode));
    }

    public BusinessResponseEntity<OrgMappingDTO> getOrgMappingByRpUnitCode(UnitParamDTO unitParam) {
        return BusinessResponseEntity.ok((Object)this.orgMappingProvider.getByCode(unitParam.getBblx()).getOrgMapping(unitParam.getUnitCode()));
    }

    public BusinessResponseEntity<OrgMappingDTO> getDatacenterOrgMappingByRpUnitCode(UnitParamDTO unitParam) {
        return BusinessResponseEntity.ok((Object)this.orgMappingQueryService.getDatacenterOrgMappingByRpUnitCode(unitParam.getUnitCode()));
    }

    public BusinessResponseEntity<OrgMappingDTO> getOrgMappingByAcctOrgCode(UnitParamDTO unitParam) {
        return BusinessResponseEntity.ok((Object)this.orgMappingQueryService.getOrgMappingByAcctOrgCode(unitParam.getAcctOrgCode()));
    }

    public BusinessResponseEntity<Map<String, String>> queryBeforeMappingAssistMap(UnitParamDTO unitParam) {
        if (StringUtils.isEmpty((String)unitParam.getUnitCode())) {
            throw new BdeRuntimeException("\u7f3a\u5c11\u67e5\u8be2\u8f85\u52a9\u7ef4\u5ea6\u6620\u5c04\u5fc5\u987b\u7684\u53c2\u6570UnitCode");
        }
        OrgMappingDTO orgMapping = this.orgMappingProvider.getByCode(unitParam.getBblx()).getOrgMapping(unitParam.getUnitCode());
        return BusinessResponseEntity.ok((Object)this.queryBeforeMappingAssistMapService.queryBdeAssistMappingMap(orgMapping));
    }
}

