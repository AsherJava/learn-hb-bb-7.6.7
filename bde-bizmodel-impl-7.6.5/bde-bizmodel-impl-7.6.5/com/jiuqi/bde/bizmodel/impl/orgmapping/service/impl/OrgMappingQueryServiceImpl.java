/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 */
package com.jiuqi.bde.bizmodel.impl.orgmapping.service.impl;

import com.jiuqi.bde.bizmodel.impl.orgmapping.dao.BdeOrgMappingDao;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.OrgMappingQueryService;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrgMappingQueryServiceImpl
implements OrgMappingQueryService {
    @Autowired
    private BdeOrgMappingDao orgMappingDao;
    @Autowired
    private DataSchemeService schemeServcie;

    @Override
    public OrgMappingDTO getOrgMappingByAcctOrgCode(String acctOrgCode) {
        Assert.isNotEmpty((String)acctOrgCode);
        OrgMappingDTO orgMappingDTO = this.orgMappingDao.getByAcctOrgCode(acctOrgCode);
        if (orgMappingDTO == null) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d\u6620\u5c04\u4e2d\u672a\u627e\u5230\u4ee3\u7801\u4e3a'%1$s'\u7684\u6838\u7b97\u5355\u4f4d", acctOrgCode));
        }
        DataSchemeDTO dataScheme = this.schemeServcie.getByCode(orgMappingDTO.getDataSchemeCode());
        orgMappingDTO.setDataSourceCode(this.schemeServcie.getByCode(orgMappingDTO.getDataSchemeCode()).getDataSourceCode());
        orgMappingDTO.setPluginType(dataScheme.getPluginType());
        return orgMappingDTO;
    }

    @Override
    public OrgMappingDTO getDatacenterOrgMappingByRpUnitCode(String rpUnitCode) {
        Assert.isNotEmpty((String)rpUnitCode);
        OrgMappingDTO orgMappingDTO = this.orgMappingDao.get(rpUnitCode);
        if (orgMappingDTO == null) {
            throw new BusinessRuntimeException(String.format("\u5355\u4f4d\u6620\u5c04\u4e2d\u672a\u627e\u5230\u4ee3\u7801\u4e3a'%1$s'\u7684\u62a5\u8868\u5355\u4f4d", rpUnitCode));
        }
        DataSchemeDTO dataScheme = this.schemeServcie.getByCode(orgMappingDTO.getDataSchemeCode());
        orgMappingDTO.setDataSourceCode(this.schemeServcie.getByCode(orgMappingDTO.getDataSchemeCode()).getDataSourceCode());
        orgMappingDTO.setPluginType(dataScheme.getPluginType());
        return orgMappingDTO;
    }
}

