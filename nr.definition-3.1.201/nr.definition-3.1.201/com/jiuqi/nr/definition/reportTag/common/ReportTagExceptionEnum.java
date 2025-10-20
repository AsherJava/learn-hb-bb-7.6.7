/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.definition.reportTag.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ReportTagExceptionEnum implements ErrorEnum
{
    REPORT_TAG_ERROR_001("001", "\u5220\u9664\u6807\u7b7e\u6570\u636e\u5f02\u5e38"),
    REPORT_TAG_ERROR_002("002", "\u65b0\u589e\u6807\u7b7e\u6570\u636e\u5f02\u5e38"),
    REPORT_TAG_ERROR_003("003", "\u6839\u636e\u6a21\u677f\u5220\u9664\u6807\u7b7e\u6570\u636e\u5f02\u5e38"),
    REPORT_TAG_ERROR_004("004", "\u4fdd\u5b58\u6807\u7b7e\u4fe1\u606f\u5f02\u5e38"),
    REPORT_TAG_ERROR_005("005", "\u89e3\u6790\u6a21\u677f\u5185\u81ea\u5b9a\u4e49\u6807\u7b7e\u5f02\u5e38"),
    REPORT_TAG_ERROR_006("006", "\u6807\u7b7e\u6570\u636e\u5220\u9664\u53c2\u6570\u4e3a\u7a7a"),
    REPORT_TAG_ERROR_007("007", "\u6807\u7b7e\u6570\u636e\u6279\u91cf\u63d2\u5165\u53c2\u6570\u5f02\u5e38"),
    REPORT_TAG_ERROR_008("008", "\u6807\u7b7e\u6570\u636e\u5bfc\u51fa\u5f02\u5e38"),
    REPORT_TAG_ERROR_009("009", "\u6807\u7b7e\u6570\u636e\u5bfc\u5165\u5f02\u5e38"),
    REPORT_TAG_ERROR_010("010", "\u4e0d\u662f\u6709\u6548\u7684\u5bfc\u5165\u6587\u4ef6"),
    REPORT_TAG_ERROR_011("011", "\u67e5\u8be2\u4e3b\u7ef4\u5ea6\u5c5e\u6027\u5f02\u5e38");

    private String code;
    private String message;

    private ReportTagExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

