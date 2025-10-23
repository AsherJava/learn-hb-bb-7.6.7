/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.report.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NrReportErrorEnum implements ErrorEnum
{
    REPORT_ERROR_001("001", "\u65b0\u589e\u6a21\u677f\u5f02\u5e38"),
    REPORT_ERROR_002("002", "\u65b0\u589e\u6a21\u677f\u65f6\u6807\u7b7e\u65b0\u589e\u5f02\u5e38"),
    REPORT_ERROR_003("003", "\u66f4\u65b0\u6a21\u677f\u6587\u4ef6\u5f02\u5e38"),
    REPORT_ERROR_004("004", "\u66f4\u65b0\u6a21\u677f\u6587\u4ef6\u65f6\u5019\u6807\u7b7e\u5f02\u5e38"),
    REPORT_ERROR_005("005", "\u66f4\u65b0\u6a21\u677f\u6587\u4ef6\u5f02\u5e38"),
    REPORT_ERROR_006("006", "\u56fe\u8868\u67e5\u8be2\u5f02\u5e38");

    private String code;
    private String message;

    private NrReportErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return null;
    }

    public String getMessage() {
        return null;
    }
}

