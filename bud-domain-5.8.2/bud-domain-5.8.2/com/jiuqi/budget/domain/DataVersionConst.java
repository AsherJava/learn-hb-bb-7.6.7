/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

import com.jiuqi.budget.autoconfigure.DimensionConst;

public interface DataVersionConst {
    public static final String STAGE_TJ_LATEST = "LATEST";
    public static final String STAGE_FINAL = "FINAL";
    public static final String FINAL_VERSION = "00000000-0000-0000-0000-000000000000";
    public static final String MINUS_ONE_VERSION = "10000000-0000-0000-0000-000000000001";

    public static String getUpStage(int batchNumber) {
        return DimensionConst.StageDefine.INNER_UP_STAGES[batchNumber];
    }

    public static String getDownStage(int batchNumber) {
        return DimensionConst.StageDefine.INNER_DOWN_STAGES[batchNumber];
    }
}

