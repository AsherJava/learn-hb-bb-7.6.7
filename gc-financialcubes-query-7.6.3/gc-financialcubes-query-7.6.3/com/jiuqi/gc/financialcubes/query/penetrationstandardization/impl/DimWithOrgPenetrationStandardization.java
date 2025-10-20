/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.penetrationstandardization.impl;

import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.penetrationstandardization.PenetrationStandardization;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationValueUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value=7)
public class DimWithOrgPenetrationStandardization
implements PenetrationStandardization {
    @Override
    public boolean match(String jsonKey, String jsonValue, PenetrationContextInfo penetrationContextInfo) {
        PenetrationValueUtil.checkMutilValue(jsonKey, jsonValue);
        return jsonKey.endsWith("_MD_ORG.CODE");
    }

    @Override
    public void process(PenetrationParamDTO penetrationParamDTO, String jsonKey, String jsonValue, PenetrationContextInfo context) {
        String key = jsonKey.substring(0, jsonKey.indexOf("_MD_ORG.CODE"));
        penetrationParamDTO.addData(key, jsonValue);
    }
}

