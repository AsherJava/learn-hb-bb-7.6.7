/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.dataentry.report.constant;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ReportErrorEnum implements ErrorEnum
{
    NRDESINGER_EXCEPTION_200("200", "\u5f53\u524d\u62a5\u8868\u65b9\u6848\u4e0b\u4e0d\u5b58\u5728\u62a5\u544a"),
    NRDESINGER_EXCEPTION_201("201", "\u672a\u627e\u5230\u7b26\u5408\u9002\u5e94\u6761\u4ef6\u7684\u62a5\u544a"),
    NRDESINGER_EXCEPTION_202("202", "\u4e0b\u8f7d\u62a5\u544aIO\u5f02\u5e38"),
    NRDESINGER_EXCEPTION_203("203", "\u4e0b\u8f7d\u62a5\u544a\u89e3\u6790\u8868\u8fbe\u5f0f\u5f02\u5e38"),
    NRDESINGER_EXCEPTION_204("204", "\u6570\u636e\u62a5\u544a\u4e0b\u8f7d\u5f02\u5e38"),
    NRDESINGER_EXCEPTION_205("205", "\u6570\u636e\u62a5\u544a\u9884\u89c8\u5f02\u5e38");

    private String code;
    private String message;

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

