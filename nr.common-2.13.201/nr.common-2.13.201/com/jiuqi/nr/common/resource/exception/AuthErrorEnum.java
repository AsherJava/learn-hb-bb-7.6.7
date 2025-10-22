/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.common.resource.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum AuthErrorEnum implements ErrorEnum
{
    AUTH_ROLE_005("", "\u6ca1\u6709\u5f53\u524d\u64cd\u4f5c\u6743\u9650\uff01"),
    AUTH("UN", "");

    private final String code;
    private final String message;

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    private AuthErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }
}

