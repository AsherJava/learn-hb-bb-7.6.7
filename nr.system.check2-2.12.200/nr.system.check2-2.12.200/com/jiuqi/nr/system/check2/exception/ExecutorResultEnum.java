/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.system.check2.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ExecutorResultEnum implements ErrorEnum
{
    EXECUTOR_RESOURCE_EMPTY("E-01", "\u6267\u884c\u65f6\u67e5\u8be2\u7684\u8d44\u6e90\u4e3a\u7a7a"),
    EXECUTOR_AUTH_ERROR("E-02", "\u6267\u884c\u65f6\u6821\u9a8c\u7528\u6237\u6ca1\u6709\u6743\u9650"),
    EXECUTOR_EXECUTOR_ERROR("E-03", "\u6267\u884c\u65f6\u6821\u9a8c\u7528\u6237\u6ca1\u6709\u6743\u9650"),
    GET_ALL_TASK_ERROR("E-04", "\u6267\u884c\u65f6\u6821\u9a8c\u7528\u6237\u6ca1\u6709\u6743\u9650");

    private String code;
    private String message;

    private ExecutorResultEnum(String code, String message) {
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

