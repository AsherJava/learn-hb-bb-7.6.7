/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.np.period.PeriodWrapper
 */
package com.jiuqi.gc.financialcubes.query.penetrationstandardization.impl;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationContextInfo;
import com.jiuqi.gc.financialcubes.query.dto.PenetrationParamDTO;
import com.jiuqi.gc.financialcubes.query.penetrationstandardization.PenetrationStandardization;
import com.jiuqi.gc.financialcubes.query.utils.PenetrationValueUtil;
import com.jiuqi.np.period.PeriodWrapper;
import java.util.Arrays;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value=1)
public class DataTimePenetrationStandardization
implements PenetrationStandardization {
    @Override
    public boolean match(String jsonKey, String jsonValue, PenetrationContextInfo penetrationContextInfo) {
        PenetrationValueUtil.checkMutilValue(jsonKey, jsonValue);
        return "P_TIMEKEY".equals(jsonKey);
    }

    @Override
    public void process(PenetrationParamDTO penetrationParamDTO, String jsonKey, String jsonValue, PenetrationContextInfo context) {
        if (StringUtils.isEmpty((String)jsonValue)) {
            return;
        }
        if (((String)jsonValue).contains(",")) {
            Object[] splitResult = ((String)jsonValue).split("\\,");
            if (splitResult.length == 2 && splitResult[0].equals(splitResult[1])) {
                jsonValue = splitResult[0];
            }
            if (splitResult.length == 3) {
                Arrays.sort(splitResult);
                jsonValue = splitResult[1];
            }
        }
        String tableName = context.getDataSchemeTableCode();
        String type = StringUtils.toViewString((Object)Character.valueOf(tableName.charAt(tableName.length() - 1)));
        int timePeriod = Integer.parseInt(((String)jsonValue).substring(4, 6));
        int year = Integer.parseInt(((String)jsonValue).substring(0, 4));
        int typeNum = PenetrationValueUtil.convertStr2Type(type);
        int convertTimePeriod = PenetrationValueUtil.convertMonthToType(typeNum, timePeriod);
        penetrationParamDTO.setDataTime(new PeriodWrapper(year, typeNum, convertTimePeriod).toString());
    }
}

