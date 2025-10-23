/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nrdt.parampacket.manage.desktop.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum ExceptionEnum implements ErrorEnum
{
    UNIVERSAL("0", ""),
    QUERY_ERROR("0001", "\u67e5\u8be2\u9519\u8bef");

    String code;
    String message;

    private ExceptionEnum(String code, String message) {
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

