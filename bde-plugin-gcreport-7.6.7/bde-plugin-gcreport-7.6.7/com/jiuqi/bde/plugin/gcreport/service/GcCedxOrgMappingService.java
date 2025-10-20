/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.dao.BdeOrgMappingDao
 *  com.jiuqi.bde.bizmodel.impl.orgmapping.service.OrgMappingService
 *  com.jiuqi.bde.common.dto.OrgMappingDTO
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO
 *  com.jiuqi.dc.mappingscheme.client.dto.DataSchemeListDTO
 *  com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService
 */
package com.jiuqi.bde.plugin.gcreport.service;

import com.jiuqi.bde.bizmodel.impl.orgmapping.dao.BdeOrgMappingDao;
import com.jiuqi.bde.bizmodel.impl.orgmapping.service.OrgMappingService;
import com.jiuqi.bde.common.dto.OrgMappingDTO;
import com.jiuqi.bde.plugin.gcreport.BdeGcreportPluginType;
import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeDTO;
import com.jiuqi.dc.mappingscheme.client.dto.DataSchemeListDTO;
import com.jiuqi.dc.mappingscheme.impl.service.DataSchemeService;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class GcCedxOrgMappingService
implements OrgMappingService {
    @Autowired
    private BdeOrgMappingDao orgMappingDao;
    @Autowired
    private DataSchemeService schemeServcie;
    @Autowired
    private BdeGcreportPluginType pluginType;
    @Value(value="${jiuqi.bde.plugin.gcreport.bblx:}")
    private String externalBblx;
    public static final String FN_CEDX_BBLX = "1";
    public static final String FN_DEFAULT_BBLX = "0";

    public String getCode() {
        String[] externalBblxArr;
        if (StringUtils.isEmpty(this.externalBblx)) {
            return FN_CEDX_BBLX;
        }
        HashSet<String> codeSet = new HashSet<String>();
        codeSet.add(FN_CEDX_BBLX);
        for (String bblx : externalBblxArr = this.externalBblx.split(",")) {
            if (StringUtils.isEmpty(bblx) || FN_DEFAULT_BBLX.equals(bblx)) continue;
            codeSet.add(bblx);
        }
        return String.join((CharSequence)",", codeSet);
    }

    public OrgMappingDTO getOrgMapping(String rpUnitCode) {
        Assert.isNotEmpty((String)rpUnitCode);
        OrgMappingDTO orgMappingDTO = this.orgMappingDao.get(rpUnitCode);
        if (orgMappingDTO != null) {
            DataSchemeDTO scheme = this.schemeServcie.getByCode(orgMappingDTO.getDataSchemeCode());
            orgMappingDTO.setDataSourceCode(scheme.getDataSourceCode());
            orgMappingDTO.setPluginType(this.pluginType.getSymbol());
            return orgMappingDTO;
        }
        DataSchemeListDTO dataSchemeListDTO = new DataSchemeListDTO();
        dataSchemeListDTO.setPluginType(this.pluginType.getSymbol());
        List list = this.schemeServcie.list(dataSchemeListDTO);
        if (CollectionUtils.isEmpty((Collection)list)) {
            throw new BusinessRuntimeException("\u5728BDE\u6570\u636e\u6620\u5c04\u65b9\u6848\u529f\u80fd\u4e2d\u672a\u627e\u5230\u6838\u7b97\u8f6f\u4ef6\u7248\u672c\u4e3a\u3010\u5408\u5e76\u62a5\u8868\u3011\u7684\u6620\u5c04\u65b9\u6848\u3002");
        }
        OrgMappingDTO orgMapping = new OrgMappingDTO();
        orgMapping.setAcctOrgCode(rpUnitCode);
        orgMapping.setAcctOrgName(rpUnitCode);
        orgMapping.setReportOrgCode(rpUnitCode);
        orgMapping.setReportOrgName(rpUnitCode);
        orgMapping.setDataSourceCode(((DataSchemeDTO)list.get(0)).getDataSourceCode());
        orgMapping.setDataSchemeCode(((DataSchemeDTO)list.get(0)).getCode());
        orgMapping.setPluginType(this.pluginType.getSymbol());
        return orgMapping;
    }
}

