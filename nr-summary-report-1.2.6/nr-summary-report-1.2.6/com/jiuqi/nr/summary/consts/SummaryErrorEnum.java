/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.summary.consts;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum SummaryErrorEnum implements ErrorEnum
{
    SUMMARY_EXCEPTION_000("000", "\u65e0\u6743\u9650\u64cd\u4f5c\uff01"),
    SUMMARY_EXCEPTION_001("001", "\u5e8f\u5217\u5316\u5931\u8d25\uff01"),
    SUMMARY_EXCEPTION_100("100", "\u6570\u636e\u4fdd\u5b58\u9519\u8bef"),
    SUMMARY_EXCEPTION_110("110", "\u6570\u636e\u53d1\u5e03\u9519\u8bef"),
    SUMMARY_EXCEPTION_120("120", "\u6570\u636e\u6c47\u603b\u8fc7\u7a0b\u9519\u8bef"),
    SUMMARY_EXCEPTION_130("130", "\u6570\u636e\u67e5\u8be2\u9519\u8bef"),
    SUMMARY_EXCEPTION_140("140", "\u6570\u636e\u5bfc\u51fa\u9519\u8bef");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private SummaryErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

