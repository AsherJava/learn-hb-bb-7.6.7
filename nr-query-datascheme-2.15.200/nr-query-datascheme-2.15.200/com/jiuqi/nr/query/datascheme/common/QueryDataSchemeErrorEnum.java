/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.query.datascheme.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum QueryDataSchemeErrorEnum implements ErrorEnum
{
    REFPARAMETER_EXCEPTION_000("000", "\u5173\u8054\u516c\u5171\u53c2\u6570\u5f02\u5e38");

    private String code;
    private String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private QueryDataSchemeErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

