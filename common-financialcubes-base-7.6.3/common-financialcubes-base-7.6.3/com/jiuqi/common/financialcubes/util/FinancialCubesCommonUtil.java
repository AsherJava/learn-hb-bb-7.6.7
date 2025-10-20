/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.financialcubes.util;

import com.jiuqi.common.financialcubes.common.FinancialCubesPeriodTypeEnum;

public class FinancialCubesCommonUtil {
    public static String getFinancialCubesDimTableName(FinancialCubesPeriodTypeEnum periodTypeEnum) {
        return "GC_FINCUBES_DIM_" + periodTypeEnum.getCode();
    }

    public static String getFinancialCubesCfTableName(FinancialCubesPeriodTypeEnum periodTypeEnum) {
        return "GC_FINCUBES_CF_" + periodTypeEnum.getCode();
    }

    public static String getFinancialCubesAgingTableName(FinancialCubesPeriodTypeEnum periodTypeEnum) {
        return "GC_FINCUBES_AGING_" + periodTypeEnum.getCode();
    }
}

