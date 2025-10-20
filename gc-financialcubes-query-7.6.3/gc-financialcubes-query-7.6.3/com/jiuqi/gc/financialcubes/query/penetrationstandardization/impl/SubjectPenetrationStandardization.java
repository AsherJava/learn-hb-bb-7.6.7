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
import org.springframework.stereotype.Service;

@Service
@Order(value=4)
public class SubjectPenetrationStandardization
implements PenetrationStandardization {
    @Autowired
    private PenetrationParamCache penetrationParamCache;

    @Override
    public boolean match(String jsonKey, String jsonValue, PenetrationContextInfo penetrationContextInfo) {
        jsonKey = PenetrationValueUtil.removeObjectCodeSuffix(jsonKey);
        Map<String, Map<String, String>> cacheMap = this.penetrationParamCache.getDefaultValue(penetrationContextInfo.getDataSchemeKey(), penetrationContextInfo.getDataSchemeTableCode());
        return cacheMap != null && cacheMap.containsKey(penetrationContextInfo.getDataSchemeTableCode()) && "SUBJECTCODE".equals(cacheMap.get(penetrationContextInfo.getDataSchemeTableCode()).get(jsonKey));
    }

    @Override
    public void process(PenetrationParamDTO penetrationParamDTO, String jsonKey, String jsonValue, PenetrationContextInfo context) {
        String cleanedJsonValue = jsonValue.replace("%", "");
        penetrationParamDTO.setSubjectCode(cleanedJsonValue);
    }
}

