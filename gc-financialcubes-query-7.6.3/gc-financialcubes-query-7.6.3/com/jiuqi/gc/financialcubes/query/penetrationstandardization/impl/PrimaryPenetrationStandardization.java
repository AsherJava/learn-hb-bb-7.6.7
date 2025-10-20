/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.penetrationstandardization.impl;

import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.penetrationstandardization.PenetrationStandardization;
import com.jiuqi.gc.financialcubes.query.utils.DimensionSearchService;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationValueUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value=3)
public class PrimaryPenetrationStandardization
implements PenetrationStandardization {
    @Autowired
    DimensionSearchService dimensionSearchService;

    @Override
    public boolean match(String jsonKey, String jsonValue, PenetrationContextInfo penetrationContextInfo) {
        PenetrationValueUtil.checkMutilValue(jsonKey, jsonValue);
        if ("MD_ORG.CODE".equals(jsonKey)) {
            return true;
        }
        String dimCode = "";
        if (jsonKey.contains(".")) {
            dimCode = "MD_ORG_" + jsonKey.split("\\.")[1];
        }
        Map<String, String> codeRefFieldMap = this.dimensionSearchService.getCodeRefFieldMap();
        return codeRefFieldMap.containsKey(dimCode);
    }

    @Override
    public void process(PenetrationParamDTO penetrationParamDTO, String jsonKey, String jsonValue, PenetrationContextInfo context) {
        if ("MD_ORG.CODE".equals(jsonKey)) {
            penetrationParamDTO.setMdCode(jsonValue);
            return;
        }
        String dimCode = "MD_ORG_" + jsonKey.split("\\.")[1];
        penetrationParamDTO.addData(dimCode, jsonValue);
    }
}

