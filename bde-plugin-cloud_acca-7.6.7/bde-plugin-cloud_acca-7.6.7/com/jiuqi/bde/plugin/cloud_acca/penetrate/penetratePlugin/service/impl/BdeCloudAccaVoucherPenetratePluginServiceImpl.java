/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.dc.base.common.enums.DataType
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO
 *  com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService
 *  tk.mybatis.mapper.util.StringUtil
 */
package com.jiuqi.bde.plugin.cloud_acca.penetrate.penetratePlugin.service.impl;

import com.jiuqi.bde.bizmodel.impl.orgmapping.service.IOrgMappingServiceProvider;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.plugin.cloud_acca.penetrate.penetratePlugin.service.BdeCloudAccaVoucherPenetratePluginService;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.dc.base.common.enums.DataType;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeOptionDTO;
import com.jiuqi.dc.mappingscheme.client.option.DataSchemeOptionValue;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeOptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class BdeCloudAccaVoucherPenetratePluginServiceImpl
implements BdeCloudAccaVoucherPenetratePluginService {
    private static final String EGAS_DEFAULT_SSO_APP_ID = "EGAS";
    @Autowired
    private IOrgMappingServiceProvider orgMappingProvider;
    @Autowired
    private DataSchemeOptionService schemeOptionService;

    @Override
    public String getEgasPenetrateSsoAppId(String unitCode) {
        OrgMappingDTO orgMapping = this.orgMappingProvider.getByCode(null).getOrgMapping(unitCode);
        String dataSchemeCode = orgMapping.getDataSchemeCode();
        String appId = this.getEgasServerAppId(dataSchemeCode);
        return StringUtil.isNotEmpty((String)appId) ? appId : EGAS_DEFAULT_SSO_APP_ID;
    }

    private String getEgasServerAppId(String dataSchemeCode) {
        Assert.isNotEmpty((String)dataSchemeCode);
        DataSchemeOptionDTO optionDTO = this.schemeOptionService.getValueByDataSchemeCode(dataSchemeCode, "egasSsOAppidOption");
        if (optionDTO != null) {
            return optionDTO.getOptionValue().getStringValue();
        }
        DataSchemeOptionValue defaultValue = new DataSchemeOptionValue(DataType.STRING, (Object)EGAS_DEFAULT_SSO_APP_ID);
        return defaultValue.getStringValue();
    }
}

