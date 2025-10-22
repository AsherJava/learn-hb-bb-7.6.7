/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 */
package com.jiuqi.nr.datascheme.adjustment.util;

import com.jiuqi.nr.datascheme.adjustment.entity.AdjustPeriodDO;
import com.jiuqi.nr.datascheme.adjustment.entity.DesignAdjustPeriodDO;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;

public class AdjustUtils {
    public static Boolean isAdjust(String entityId) {
        return "ADJUST".equals(entityId);
    }

    public static String getAdjustDimensionName() {
        return "ADJUST";
    }

    public static <E extends AdjustPeriod> AdjustPeriodDO createNotAdjust(E adjust) {
        if (adjust == null) {
            return null;
        }
        AdjustPeriodDO adjustPeriodDO = new AdjustPeriodDO();
        AdjustUtils.setInit(adjustPeriodDO, adjust);
        return adjustPeriodDO;
    }

    private static <E extends AdjustPeriod> void setInit(AdjustPeriodDO adjustPeriodDO, E adjust) {
        adjustPeriodDO.setDataSchemeKey(adjust.getDataSchemeKey());
        adjustPeriodDO.setPeriod(adjust.getPeriod());
        adjustPeriodDO.setCode("0");
        adjustPeriodDO.setTitle("\u4e0d\u8c03\u6574");
        adjustPeriodDO.setOrder(adjust.getOrder() == null ? "0" : adjust.getOrder());
    }

    public static <E extends AdjustPeriod> AdjustPeriodDO createNotAdjust(String schemeKey, String period) {
        AdjustPeriodDO adjustPeriodDO = new AdjustPeriodDO();
        adjustPeriodDO.setDataSchemeKey(schemeKey);
        adjustPeriodDO.setPeriod(period);
        adjustPeriodDO.setCode("0");
        adjustPeriodDO.setTitle("\u4e0d\u8c03\u6574");
        adjustPeriodDO.setOrder("0");
        return adjustPeriodDO;
    }

    public static <E extends AdjustPeriod> DesignAdjustPeriodDO createDesignNotAdjust(E adjust) {
        if (adjust == null) {
            return null;
        }
        DesignAdjustPeriodDO adjustPeriodDO = new DesignAdjustPeriodDO();
        AdjustUtils.setInit(adjustPeriodDO, adjust);
        return adjustPeriodDO;
    }

    public static <E extends AdjustPeriod> DesignAdjustPeriodDO createDesignNotAdjust(String schemeKey, String period) {
        DesignAdjustPeriodDO adjustPeriodDO = new DesignAdjustPeriodDO();
        adjustPeriodDO.setDataSchemeKey(schemeKey);
        adjustPeriodDO.setPeriod(period);
        adjustPeriodDO.setCode("0");
        adjustPeriodDO.setTitle("\u4e0d\u8c03\u6574");
        adjustPeriodDO.setOrder("0");
        return adjustPeriodDO;
    }

    public static <E extends AdjustPeriod> Boolean isNotAdjustData(E adjust) {
        if (adjust == null) {
            return Boolean.FALSE;
        }
        if (adjust.getCode().equals("0")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    public static <E extends AdjustPeriod> Boolean isAdjustData(E adjust) {
        if (adjust == null) {
            return Boolean.FALSE;
        }
        if (!adjust.getCode().equals("0")) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}

