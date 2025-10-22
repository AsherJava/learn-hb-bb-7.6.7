/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.form.analysis.common;

public class FormAnalysisParamEnum {
    private FormAnalysisParamEnum() {
    }

    public static enum PeriodDataRangeType {
        NONE,
        SELECTED,
        AS_DEST;

    }

    public static enum DimDataRangeType {
        AS_DEST,
        DEST_CHILDREN,
        DEST_ALL_CHILDREN,
        DEST_BROTHERS,
        SELECTED,
        CONDITION,
        NONE;

    }

    public static enum PosType {
        ROW,
        COL;

    }
}

