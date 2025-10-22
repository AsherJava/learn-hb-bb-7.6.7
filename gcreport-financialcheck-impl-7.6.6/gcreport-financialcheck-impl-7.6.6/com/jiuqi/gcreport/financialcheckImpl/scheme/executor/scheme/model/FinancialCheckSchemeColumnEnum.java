/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.financialcheckImpl.scheme.executor.scheme.model;

public enum FinancialCheckSchemeColumnEnum {
    YEAR("year", "*\u5e74\u5ea6", true),
    GROUP_NAME("groupName", "*\u89c4\u5219\u5206\u7ec4", true),
    SCHEME_NAME("schemeName", "*\u89c4\u5219\u540d\u79f0", true),
    CHECK_MODE("checkMode", "*\u5bf9\u8d26\u65b9\u5f0f", true),
    UNIT_CODES("unitCodes", "\u9002\u7528\u5355\u4f4d\u4ee3\u7801", true),
    UNIT_TITLES("unitTitles", "\u9002\u7528\u5355\u4f4d\u540d\u79f0", true),
    CHECK_ATTRIBUTE("checkAttribute", "\u5bf9\u8d26\u5c5e\u6027", true),
    CHECK_DIMENSION("checkDimensions", "\u5bf9\u8d26\u7ef4\u5ea6", true),
    TOLERANCE_ENABLE("toleranceEnable", "\u542f\u7528\u5bb9\u5dee", true),
    CHECK_AMOUNT("checkAmount", "\u5bf9\u8d26\u91d1\u989d", true),
    TOLERANCE_AMOUNT("toleranceAmount", "\u5bb9\u5dee\u91d1\u989d", true),
    TOLERANCE_RATE("toleranceRate", "\u5bb9\u5dee\u7a0e\u7387", true),
    CHECK_PROJECT("checkProject", "*\u5bf9\u8d26\u9879\u76ee", true),
    CHECK_PROJECT_DIRECTION("checkProjectDirection", "*\u5bf9\u8d26\u9879\u76ee\u65b9\u5411", true),
    SUBJECT_CATEGORY("subjectCategory", "\u4e1a\u52a1\u89d2\u8272", true);

    private String code;
    private String title;
    private boolean required;

    private FinancialCheckSchemeColumnEnum(String code, String title, boolean required) {
        this.code = code;
        this.title = title;
        this.required = required;
    }
}

