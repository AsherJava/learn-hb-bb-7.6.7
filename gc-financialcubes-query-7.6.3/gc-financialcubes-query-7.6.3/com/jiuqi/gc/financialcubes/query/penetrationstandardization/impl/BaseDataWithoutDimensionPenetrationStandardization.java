/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.penetrationstandardization.impl;

import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.penetrationstandardization.PenetrationStandardization;
import com.jiuqi.gc.financialcubes.query.redisservice.PenetrationParamCache;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationValueUtil;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=5)
public class BaseDataWithoutDimensionPenetrationStandardization
implements PenetrationStandardization {
    @Autowired
    private PenetrationParamCache penetrationParamCache;

    @Override
    public boolean match(String jsonKey, String jsonValue, PenetrationContextInfo penetrationContextInfo) {
        PenetrationValueUtil.checkMutilValue(jsonKey, jsonValue);
        jsonKey = PenetrationValueUtil.removeObjectCodeSuffix(jsonKey);
        Map<String, Map<String, String>> cacheMap = this.penetrationParamCache.getDefaultValue(penetrationContextInfo.getDataSchemeKey(), penetrationContextInfo.getDataSchemeTableCode());
        return cacheMap != null && cacheMap.get(penetrationContextInfo.getDataSchemeTableCode()).containsKey(jsonKey);
    }

    @Override
    public void process(PenetrationParamDTO penetrationParamDTO, String jsonKey, String jsonValue, PenetrationContextInfo penetrationContextInfo) {
        Map<String, Map<String, String>> cacheMap = this.penetrationParamCache.getDefaultValue(penetrationContextInfo.getDataSchemeKey(), penetrationContextInfo.getDataSchemeTableCode());
        jsonKey = PenetrationValueUtil.removeObjectCodeSuffix(jsonKey);
        penetrationParamDTO.addData(cacheMap.get(penetrationContextInfo.getDataSchemeTableCode()).get(jsonKey), jsonValue);
    }
}

