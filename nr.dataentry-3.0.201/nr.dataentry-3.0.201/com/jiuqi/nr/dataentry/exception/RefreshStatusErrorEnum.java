/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.dataentry.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum RefreshStatusErrorEnum implements ErrorEnum
{
    SYSTEM_CHECK_EXCEPTION_001("001", "\u5237\u65b0\u6570\u636e\u5f02\u5e38"),
    SYSTEM_CHECK_EXCEPTION_002("002", "\u5237\u65b0\u6761\u4ef6\u5f02\u5e38");

    private String code;
    private String message;

    private RefreshStatusErrorEnum(String code, String message) {
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

