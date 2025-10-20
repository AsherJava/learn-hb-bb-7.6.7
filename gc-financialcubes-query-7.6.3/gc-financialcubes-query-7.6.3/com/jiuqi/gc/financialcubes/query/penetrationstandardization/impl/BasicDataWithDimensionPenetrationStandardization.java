/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.penetrationstandardization.impl;

import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.penetrationstandardization.PenetrationStandardization;
import com.jiuqi.gc.financialcubes.query.redisservice.PenetrationParamCache;
import com.jiuqi.gc.financialcubes.query.utils.DimensionSearchService;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationValueUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value=6)
public class BasicDataWithDimensionPenetrationStandardization
implements PenetrationStandardization {
    @Autowired
    private PenetrationParamCache penetrationParamCache;
    @Autowired
    private DimensionSearchService dimensionSearchService;

    @Override
    public boolean match(String jsonKey, String jsonValue, PenetrationContextInfo penetrationContextInfo) {
        PenetrationValueUtil.checkMutilValue(jsonKey, jsonValue);
        if (!jsonKey.startsWith("MD_") || jsonKey.endsWith("OBJECTCODE")) {
            return false;
        }
        String[] jsonKeySplit = jsonKey.split("\\.");
        if (jsonKeySplit.length != 2) {
            return false;
        }
        String basicData = jsonKeySplit[0];
        String attribute = jsonKeySplit[1];
        Map<String, Map<String, String>> cacheMap = this.penetrationParamCache.getDefaultValue(penetrationContextInfo.getDataSchemeKey(), penetrationContextInfo.getDataSchemeTableCode());
        if (cacheMap == null || cacheMap.get(penetrationContextInfo.getDataSchemeTableCode()) == null) {
            return false;
        }
        Map<String, String> schemeMap = cacheMap.get(penetrationContextInfo.getDataSchemeTableCode());
        Map<String, String> codeRefFieldMap = this.dimensionSearchService.getCodeRefFieldMap();
        String refResult = "";
        refResult = "MD_ACCTSUBJECT".equals(basicData) ? "MD_ACCTSUBJECT" : schemeMap.get(basicData);
        return schemeMap.containsKey(basicData) && codeRefFieldMap.containsKey(refResult + "_" + attribute);
    }

    @Override
    public void process(PenetrationParamDTO penetrationParamDTO, String jsonKey, String jsonValue, PenetrationContextInfo context) {
        String[] jsonKeySplit = jsonKey.split("\\.");
        String basicData = jsonKeySplit[0];
        String attribute = jsonKeySplit[1];
        String refResult = "";
        Map<String, String> schemeMap = this.penetrationParamCache.getDefaultValue(context.getDataSchemeKey(), context.getDataSchemeTableCode()).get(context.getDataSchemeTableCode());
        refResult = "MD_ACCTSUBJECT".equals(basicData) ? "MD_ACCTSUBJECT" : schemeMap.get(basicData);
        penetrationParamDTO.addData(refResult + "_" + attribute, jsonValue);
    }
}

