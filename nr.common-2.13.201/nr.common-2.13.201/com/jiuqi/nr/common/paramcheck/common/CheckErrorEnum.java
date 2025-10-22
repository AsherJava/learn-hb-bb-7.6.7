/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.common.paramcheck.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum CheckErrorEnum implements ErrorEnum
{
    CHECK_EXECUTE_101("101", "\u6267\u884c\u53c2\u6570\u68c0\u67e5\u53d1\u751f\u4e86\u9519\u8bef."),
    BEAN_CHECK_201("201", "\u65e0\u6cd5\u627e\u5230\u8981\u6267\u884c\u7684\u5b9e\u73b0\u7c7b."),
    FIX_CHECK_201("301", "\u6267\u884c\u53c2\u6570\u4fee\u6539\u53d1\u751f\u4e86\u9519\u8bef.");

    private String code;
    private String message;

    private CheckErrorEnum(String code, String message) {
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

