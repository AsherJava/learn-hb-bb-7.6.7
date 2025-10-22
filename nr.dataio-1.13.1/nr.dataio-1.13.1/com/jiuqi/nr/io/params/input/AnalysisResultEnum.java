/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.params.input;

public enum AnalysisResultEnum {
    ALL_MATCH_SUCCESS("000", "\u5339\u914d\u6210\u529f"),
    TASK_NOT_FOUND("001", "\u4efb\u52a1\u5339\u914d\u5931\u8d25"),
    FORMSCHEME_NOT_FOUND("002", "\u62a5\u8868\u65b9\u6848\u5339\u914d\u5931\u8d25"),
    UNIT_SAVE_FAILURE("003", "\u5355\u4f4d\u5217\u8868\u4fdd\u5b58\u5931\u8d25"),
    PERIOD_NOT_FOUND("004", "\u65f6\u671f\u5339\u914d\u5931\u8d25\uff01"),
    UNDEFINED_ERROR("999", "\u672a\u5b9a\u4e49\u5f02\u5e38"),
    VERSION_NOT_SUPPORT("005", "\u5f53\u524d\u7248\u672c\u65e0\u6cd5\u89e3\u6790\u6570\u636e\u5305"),
    PACKAGE_NOT_SUPPORT("006", "\u65e0\u6cd5\u8bc6\u522b\u6b64\u6570\u636e\u5305");

    private final String errorCode;
    private final String message;

    private AnalysisResultEnum(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getMessage() {
        return this.message;
    }
}

