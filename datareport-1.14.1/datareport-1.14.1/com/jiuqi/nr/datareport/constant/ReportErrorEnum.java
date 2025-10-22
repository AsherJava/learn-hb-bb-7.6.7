/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.datareport.constant;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ReportErrorEnum implements ErrorEnum
{
    REPORT_EXCEPTION_200("200", "\u62a5\u544a\u6a21\u677f\u4e3a\u7a7a"),
    REPORT_EXCEPTION_201("201", "\u672a\u627e\u5230\u7b26\u5408\u9002\u5e94\u6761\u4ef6\u7684\u62a5\u544a"),
    REPORT_EXCEPTION_202("202", "\u4e0b\u8f7d\u62a5\u544aIO\u5f02\u5e38"),
    REPORT_EXCEPTION_203("203", "\u4e0b\u8f7d\u62a5\u544a\u89e3\u6790\u8868\u8fbe\u5f0f\u5f02\u5e38"),
    REPORT_EXCEPTION_204("204", "\u4e0b\u8f7d\u62a5\u544a\u6a21\u677f\u89e3\u6790\u5f02\u5e38");

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private ReportErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

