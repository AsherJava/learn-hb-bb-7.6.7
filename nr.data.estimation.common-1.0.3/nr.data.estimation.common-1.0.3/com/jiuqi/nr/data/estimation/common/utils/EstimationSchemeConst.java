/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.estimation.common.utils;

import java.util.Map;

public class EstimationSchemeConst {
    public static final String DB_SUFFIX_DE_ = "_DE_";
    public static final String DB_SUFFIX_TITLE_DE_ = "\u6d4b\u7b97\u5206\u5e93";
    public static final String estimation_scheme_code = "ESTIMATION_SCHEME";
    public static final String estimation_scheme_title = "\u6d4b\u7b97\u65b9\u6848\u6807\u8bc6";
    public static final String estimation_scheme_key = "estimationScheme";
    public static final String estimation_manager_code = "manager_created_scheme";

    private EstimationSchemeConst() {
    }

    public static boolean isEstimationDataEntry(Map<String, Object> variableMap) {
        return variableMap != null && variableMap.containsKey(estimation_scheme_key);
    }
}

