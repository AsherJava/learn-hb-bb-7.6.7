/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.bpm.custom.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum WorkflowException implements ErrorEnum
{
    ERROR,
    ERROR_INSERT("W001", "\u63d2\u5165\u9519\u8bef"),
    ERROR_UPDATE("W002", "\u66f4\u65b0\u9519\u8bef"),
    ERROR_DELETE("W003", "\u5220\u9664\u9519\u8bef"),
    ERROR_NOTFOUND("W004", "\u627e\u4e0d\u5230\u8981\u5bfc\u5165\u7684\u6d41\u7a0b\u5b9a\u4e49"),
    ERROR_FAIL("W005", "\u5220\u9664\u65e7\u7684\u6d41\u7a0b\u5b9a\u4e49\u5931\u8d25"),
    ERROR_SEARCH_FAIL("W006", "\u672a\u67e5\u8be2\u5230\u6570\u636e"),
    ERROR_REPEATE("W007", "id\u91cd\u590d"),
    ERROR_EMPTY("W008", "\u53c2\u6570\u4e3a\u7a7a"),
    ERROR_DATA("W009", "\u5220\u9664\u6570\u636e\u5931\u8d25");

    String code;
    String message;

    private WorkflowException() {
    }

    private WorkflowException(String code, String message) {
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

