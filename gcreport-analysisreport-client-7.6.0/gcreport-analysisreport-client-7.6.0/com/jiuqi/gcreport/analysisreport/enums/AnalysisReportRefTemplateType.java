/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.gcreport.analysisreport.enums;

import com.jiuqi.common.base.util.StringUtils;

public enum AnalysisReportRefTemplateType {
    NR("NR", "\u5173\u8054\u5206\u6790\u62a5\u544a\u6a21\u677f\u7c7b\u578b"),
    GC("GC", "\u5173\u8054\u591a\u7ae0\u8282\u6a21\u677f\u7c7b\u578b");

    private String code;
    private String title;

    private AnalysisReportRefTemplateType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public static AnalysisReportRefTemplateType getEnumByCode(String code) {
        AnalysisReportRefTemplateType[] enums;
        if (StringUtils.isEmpty((String)code)) {
            return null;
        }
        for (AnalysisReportRefTemplateType templateType : enums = AnalysisReportRefTemplateType.values()) {
            if (!templateType.getCode().equals(code)) continue;
            return templateType;
        }
        return null;
    }
}

