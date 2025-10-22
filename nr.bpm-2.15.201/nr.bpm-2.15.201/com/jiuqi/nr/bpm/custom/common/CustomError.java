/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.bpm.custom.common;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum CustomError implements ErrorEnum
{
    C_ERROR;

    String code;
    String message;

    private CustomError() {
    }

    private CustomError(String code, String message) {
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

