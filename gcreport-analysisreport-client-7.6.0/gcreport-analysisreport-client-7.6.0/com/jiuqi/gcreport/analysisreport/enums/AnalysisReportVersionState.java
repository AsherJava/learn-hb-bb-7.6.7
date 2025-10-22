/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.analysisreport.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum AnalysisReportVersionState {
    REAL_TIME("REAL_TIME", "\u5b9e\u65f6\u7248\u672c"),
    LATEST_SAVED("LATEST_SAVED", "\u6700\u65b0\u4fdd\u5b58\u7248\u672c"),
    LATEST_CONFIRMED("LATEST_CONFIRMED", "\u6700\u65b0\u786e\u8ba4\u7248\u672c");

    private String code;
    private String title;

    private AnalysisReportVersionState(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static AnalysisReportVersionState getEnumByCode(String code) {
        AnalysisReportVersionState[] enums;
        if (StringUtils.isEmpty((String)code)) {
            return REAL_TIME;
        }
        for (AnalysisReportVersionState versionState : enums = AnalysisReportVersionState.values()) {
            if (!versionState.getCode().equals(code)) continue;
            return versionState;
        }
        return null;
    }
}

