/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.util;

import org.springframework.util.StringUtils;

public class DimensionWrapper {
    private Integer dimension;
    private String measureUnit;

    public static String toString(Integer dimension, String measureUnit) {
        if (dimension != null) {
            return dimension == 0 ? DimensionWrapper.solutionMeasureUnit(measureUnit) : "NotDimession";
        }
        return null;
    }

    private static String solutionMeasureUnit(String measureUnit) {
        if (measureUnit == null) {
            return "9493b4eb-6516-48a8-a878-25a63a23e63a;-";
        }
        return "9493b4eb-6516-48a8-a878-25a63a23e63a;" + measureUnit;
    }

    public static DimensionWrapper build(String measureUnit) {
        DimensionWrapper wrapper = new DimensionWrapper();
        if (StringUtils.hasLength(measureUnit)) {
            wrapper.dimension = measureUnit.contains("NotDimession") ? 1 : 0;
            wrapper.measureUnit = measureUnit.replace("9493b4eb-6516-48a8-a878-25a63a23e63a;", "");
        }
        return wrapper;
    }

    public Integer getDimension() {
        return this.dimension;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }
}

