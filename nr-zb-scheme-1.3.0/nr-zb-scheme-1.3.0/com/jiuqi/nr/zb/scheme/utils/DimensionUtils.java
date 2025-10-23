/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zb.scheme.utils;

import com.jiuqi.nr.zb.scheme.common.Dimension;
import org.springframework.util.StringUtils;

public class DimensionUtils {
    public static String getMeasureUnit(Dimension dimension, String measureUnit) {
        if (dimension != null) {
            return dimension.getValue() == 0 ? DimensionUtils.solutionMeasureUnit(measureUnit) : "NotDimession";
        }
        return null;
    }

    private static String solutionMeasureUnit(String measureUnit) {
        if (measureUnit == null) {
            return "9493b4eb-6516-48a8-a878-25a63a23e63a;-";
        }
        return "9493b4eb-6516-48a8-a878-25a63a23e63a;" + measureUnit;
    }

    public static String getMeasureUnit(String measureUnit) {
        if (StringUtils.hasLength(measureUnit)) {
            return measureUnit.replace("9493b4eb-6516-48a8-a878-25a63a23e63a;", "");
        }
        return null;
    }

    public static Dimension getDimension(String measureUnit) {
        if (measureUnit != null) {
            if (measureUnit.contains("NotDimession")) {
                return Dimension.NO_DIM;
            }
            return Dimension.MONEY;
        }
        return null;
    }
}

