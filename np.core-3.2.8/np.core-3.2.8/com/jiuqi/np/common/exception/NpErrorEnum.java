/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.common.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum NpErrorEnum implements ErrorEnum
{
    EXCEPTION_500("500", "\u9519\u8bef\u4ee3\u7801500"),
    EXCEPTION_501("501", "something is wrong, please try again\uff01");

    private String code;
    private String message;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    private NpErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

