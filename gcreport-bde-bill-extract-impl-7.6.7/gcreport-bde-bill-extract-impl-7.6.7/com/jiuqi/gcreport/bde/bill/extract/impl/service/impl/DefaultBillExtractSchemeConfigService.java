/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractSchemeService
 *  com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO
 *  com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO
 *  com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeConfigService
 *  com.jiuqi.va.basedata.service.BaseDataService
 *  com.jiuqi.va.domain.basedata.BaseDataDO
 *  com.jiuqi.va.domain.basedata.BaseDataDTO
 *  com.jiuqi.va.domain.basedata.BaseDataOption$AuthType
 */
package com.jiuqi.gcreport.bde.bill.extract.impl.service.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gcreport.bde.bill.setting.impl.service.BillExtractSchemeService;
import com.jiuqi.gcreport.billextract.client.dto.BillFetchSchemeDTO;
import com.jiuqi.gcreport.billextract.client.dto.BillSchemeConfigDTO;
import com.jiuqi.gcreport.billextract.client.intf.IBillExtractSchemeConfigService;
import com.jiuqi.va.basedata.service.BaseDataService;
import com.jiuqi.va.domain.basedata.BaseDataDO;
import com.jiuqi.va.domain.basedata.BaseDataDTO;
import com.jiuqi.va.domain.basedata.BaseDataOption;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;

public class DefaultBillExtractSchemeConfigService
implements IBillExtractSchemeConfigService {
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private BillExtractSchemeService extractSchemeService;
    public static final String TB_BILLSCHEMECONFIG = "MD_BILLFETCHSCHEME";

    public BillSchemeConfigDTO getSchemeByOrgId(String defineName, String unitCode) {
        BaseDataDTO condi = new BaseDataDTO();
        condi.setTableName(TB_BILLSCHEMECONFIG);
        condi.setAuthType(BaseDataOption.AuthType.NONE);
        List basedataList = this.baseDataService.list(condi).getRows();
        Map<String, String> schemeMap = basedataList.stream().collect(Collectors.toMap(BaseDataDO::getCode, item -> (String)item.getValueOf("SCHEMENAME"), (k1, k2) -> k2));
        if (schemeMap.isEmpty() || StringUtils.isEmpty((String)schemeMap.get(defineName))) {
            return null;
        }
        List schemeList = this.extractSchemeService.listScheme(defineName);
        for (BillFetchSchemeDTO fetchSchemeDTO : schemeList) {
            if (!fetchSchemeDTO.getName().equals(schemeMap.get(defineName))) continue;
            BillSchemeConfigDTO billSchemeConfigDTO = new BillSchemeConfigDTO();
            billSchemeConfigDTO.setBillId(defineName);
            billSchemeConfigDTO.setFetchSchemeId(fetchSchemeDTO.getId());
            return billSchemeConfigDTO;
        }
        return null;
    }
}

