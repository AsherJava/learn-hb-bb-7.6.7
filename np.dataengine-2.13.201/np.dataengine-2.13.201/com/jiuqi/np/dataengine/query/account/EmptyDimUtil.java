/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.StringUtils
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 */
package com.jiuqi.np.dataengine.query.account;

import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.definition.common.StringUtils;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.common.ColumnModelType;

public class EmptyDimUtil {
    public static Object getStringEmptyKeyValue(Object keyValue, ColumnModelType keyType) {
        if (keyValue == null || StringUtils.isEmpty((String)keyValue.toString())) {
            keyValue = ColumnModelType.DATETIME == keyType ? "9999R0001" : "-";
        }
        return keyValue;
    }

    public static Object validateKeyValue(Object keyValue, ColumnModelType keyType) {
        keyValue = EmptyDimUtil.getStringEmptyKeyValue(keyValue, keyType);
        if (ColumnModelType.DATETIME == keyType && keyValue instanceof String) {
            keyValue = DataTypesConvert.periodToDate(new PeriodWrapper((String)keyValue));
        }
        return keyValue;
    }
}

