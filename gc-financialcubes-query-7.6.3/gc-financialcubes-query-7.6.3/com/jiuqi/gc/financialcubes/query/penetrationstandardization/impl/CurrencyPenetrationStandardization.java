/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gc.financialcubes.query.penetrationstandardization.impl;

import com.jiuqi.gc.financialcubes.query.constant.StandardizationLayerPenetrationConstants;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.penetrationstandardization.PenetrationStandardization;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationValueUtil;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value=2)
public class CurrencyPenetrationStandardization
implements PenetrationStandardization {
    @Override
    public boolean match(String jsonKey, String jsonValue, PenetrationContextInfo penetrationContextInfo) {
        PenetrationValueUtil.checkMutilValue(jsonKey, jsonValue);
        for (String key : StandardizationLayerPenetrationConstants.CURRENCY_START_KEYS) {
            if (!jsonKey.startsWith(key)) continue;
            return true;
        }
        return jsonKey.endsWith("ORGNCURRENCY");
    }

    @Override
    public void process(PenetrationParamDTO penetrationParamDTO, String jsonKey, String jsonValue, PenetrationContextInfo context) {
        penetrationParamDTO.setMdCurrency("CNY");
    }
}

