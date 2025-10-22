/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.common;

import java.util.Map;

public final class BatchSummaryConst {
    public static final String BATCH_GATHER_SCHEME_CODE = "batchGatherSchemeCode";
    public static final String BATCH_SHOW_SCHEME_CODES = "batchShowSchemeCodes";

    public static boolean isBatchSummaryEntry(Map<String, Object> variableMap) {
        return variableMap != null && (variableMap.containsKey(BATCH_GATHER_SCHEME_CODE) || variableMap.containsKey(BATCH_SHOW_SCHEME_CODES));
    }
}

